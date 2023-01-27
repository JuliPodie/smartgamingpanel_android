package com.example.bluetoothtest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bluetoothtest.api.BluetoothHelper;
import com.example.bluetoothtest.api.Command;
import com.example.bluetoothtest.api.Emitter;
import com.example.bluetoothtest.api.Input;
import com.example.bluetoothtest.databinding.Fragment3Binding;
import com.example.bluetoothtest.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Emitter emitter = BluetoothHelper.getCharacteristicsEmitter(this.getContext());
        //binding.slider.addOnChangeListener((slider, value, fromUser) -> emitter.enterCommand(Input.LIGHT ));//+value));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}