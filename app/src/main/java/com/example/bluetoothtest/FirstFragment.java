package com.example.bluetoothtest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.bluetoothtest.api.BluetoothHelper;
import com.example.bluetoothtest.api.Emitter;
import com.example.bluetoothtest.api.Input;
import com.example.bluetoothtest.databinding.FragmentFirstBinding;

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

		binding.snake.setOnClickListener(view1 -> {

			if (connectGame(0) >= 0) NavHostFragment.findNavController(FirstFragment.this)
					.navigate(R.id.action_FirstFragment_to_ThirdFragment);
		});

		binding.pong.setOnClickListener(view1 -> {
			if (connectGame(1) >= 0) NavHostFragment.findNavController(FirstFragment.this)
					.navigate(R.id.action_FirstFragment_to_SecondFragment);
		});
		binding.snake.setVisibility(View.VISIBLE);
		binding.pong.setVisibility(View.VISIBLE);
	}


	public int connectGame(int game) {
		try {
			Emitter emitter = BluetoothHelper.getCharacteristicsEmitter(this.getContext());

			if (game == 0) emitter.enterCommand(Input.SNAKE);
			if (game == 1) emitter.enterCommand(Input.PONG);

			return 0;
		} catch (Exception e) {
			return -1;
		}

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}

}