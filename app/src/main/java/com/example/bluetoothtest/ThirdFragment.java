package com.example.bluetoothtest;

import static com.example.bluetoothtest.api.BluetoothHelper.LOG_CALLBACK;
import static com.example.bluetoothtest.api.BluetoothHelper.getDefaultAdvertisementSettings;

import android.annotation.SuppressLint;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bluetoothtest.api.Advertiser;
import com.example.bluetoothtest.api.BluetoothHelper;
import com.example.bluetoothtest.api.Input;
import com.example.bluetoothtest.databinding.Fragment2Binding;
import com.example.bluetoothtest.databinding.Fragment3Binding;

public class ThirdFragment extends Fragment {

	private Fragment3Binding binding;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = Fragment3Binding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	@SuppressLint("MissingPermission")
	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		BluetoothLeAdvertiser bluetoothLeAdvertiser = BluetoothHelper.getBluetoothLEAdvertiser();

		Advertiser advertiser = new Advertiser(adv -> {
			bluetoothLeAdvertiser.stopAdvertising(LOG_CALLBACK);
			bluetoothLeAdvertiser.startAdvertising(getDefaultAdvertisementSettings(), adv, LOG_CALLBACK);
		});

		binding.top.setOnClickListener(view1 -> advertiser.enterCommand(Input.UP));
		binding.bottom.setOnClickListener(view1 -> advertiser.enterCommand(Input.DOWN));
		binding.left.setOnClickListener(view1 -> advertiser.enterCommand(Input.LEFT));
		binding.right.setOnClickListener(view1 -> advertiser.enterCommand(Input.RIGHT));
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}

}