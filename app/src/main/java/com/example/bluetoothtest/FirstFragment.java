package com.example.bluetoothtest;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.*;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bluetoothtest.api.Advertiser;
import com.example.bluetoothtest.api.BluetoothHelper;
import com.example.bluetoothtest.api.Constants;
import com.example.bluetoothtest.api.Input;
import com.example.bluetoothtest.databinding.FragmentFirstBinding;

import java.util.Collections;
import java.util.List;

import static com.example.bluetoothtest.api.BluetoothHelper.LOG_CALLBACK;
import static com.example.bluetoothtest.api.BluetoothHelper.getDefaultAdvertisementSettings;

public class FirstFragment extends Fragment {


	private FragmentFirstBinding binding;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = FragmentFirstBinding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	@SuppressLint("MissingPermission")
	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);


		BluetoothAdapter defaultAdapter = BluetoothHelper.getBluetoothAdapter();
		BluetoothLeAdvertiser bluetoothLeAdvertiser = BluetoothHelper.getBluetoothLEAdvertiser();
		BluetoothLeScanner bluetoothLeScanner = defaultAdapter.getBluetoothLeScanner();


		List<ScanFilter> serviceFilter =
				Collections.singletonList(new ScanFilter.Builder().setServiceUuid(Constants.SERVER_UUID)
				.build());
		bluetoothLeScanner.startScan(serviceFilter, new ScanSettings.Builder().build(), new ScanCallback() {
			@SuppressLint("MissingPermission")
			@Override
			public void onScanResult(int callbackType, ScanResult result) {
				Log.d("DEBUG", "SCAN RESULT: " + result.toString());

				binding.snake.setVisibility(View.VISIBLE);
				binding.pong.setVisibility(View.VISIBLE);

			}
		});

		binding.snake.setOnClickListener(view1 -> {

			if (connectGame(0) >= 0) NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_ThirdFragment);
		});

		binding.pong.setOnClickListener(view1 -> {
			if (connectGame(1) >= 0) NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
		});
		binding.snake.setVisibility(View.VISIBLE);
		binding.pong.setVisibility(View.VISIBLE);
	}





	public int connectGame(int game){
		try {
			BluetoothLeAdvertiser bluetoothLeAdvertiser = BluetoothHelper.getBluetoothLEAdvertiser();

			@SuppressLint("MissingPermission") Advertiser advertiser = new Advertiser(adv -> {
				bluetoothLeAdvertiser.stopAdvertising(LOG_CALLBACK);
				bluetoothLeAdvertiser.startAdvertising(getDefaultAdvertisementSettings(), adv, LOG_CALLBACK);
			});

			if (game == 0) advertiser.enterCommand(Input.SNAKE);
			if (game == 1) advertiser.enterCommand(Input.PONG);

			return 0;
		} catch (Exception e){
			return -1;
		}

	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}

}