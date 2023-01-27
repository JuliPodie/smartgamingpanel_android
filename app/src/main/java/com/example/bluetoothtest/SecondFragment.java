package com.example.bluetoothtest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.bluetoothtest.api.BluetoothHelper;
import com.example.bluetoothtest.api.CharacteristicsEmitter;
import com.example.bluetoothtest.api.Input;
import com.example.bluetoothtest.databinding.Fragment2Binding;

public class SecondFragment extends Fragment {

	private Fragment2Binding binding;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = Fragment2Binding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	@SuppressLint("MissingPermission")
	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		CharacteristicsEmitter emitter = BluetoothHelper.getCharacteristicsEmitter(this.getContext());
		binding.top.setOnClickListener(view1 -> emitter.enterCommand(Input.UP));
		binding.bottom.setOnClickListener(view1 -> emitter.enterCommand(Input.DOWN));
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}

}