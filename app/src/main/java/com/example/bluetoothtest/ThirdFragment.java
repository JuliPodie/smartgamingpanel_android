package com.example.bluetoothtest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.bluetoothtest.api.BluetoothHelper;
import com.example.bluetoothtest.api.Emitter;
import com.example.bluetoothtest.api.Input;
import com.example.bluetoothtest.databinding.Fragment3Binding;

public class ThirdFragment extends Fragment {

	private Fragment3Binding binding;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = Fragment3Binding.inflate(inflater, container, false);
		return binding.getRoot();
	}

	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		Emitter emitter = BluetoothHelper.getCharacteristicsEmitter(this.getContext());
		binding.top.setOnClickListener(view1 -> emitter.enterCommand(Input.UP));
		binding.bottom.setOnClickListener(view1 -> emitter.enterCommand(Input.DOWN));
		binding.left.setOnClickListener(view1 -> emitter.enterCommand(Input.LEFT));
		binding.right.setOnClickListener(view1 -> emitter.enterCommand(Input.RIGHT));
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}

}