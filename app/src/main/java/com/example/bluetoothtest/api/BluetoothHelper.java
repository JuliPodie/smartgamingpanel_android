package com.example.bluetoothtest.api;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BluetoothHelper {

	private static BluetoothAdapter bluetoothAdapter;
	private static BluetoothLeAdvertiser bluetoothLeAdvertiser;

	public static final AdvertiseCallback LOG_CALLBACK = new AdvertiseCallback() {
		@Override
		public void onStartSuccess(AdvertiseSettings settingsInEffect) {
			Log.d("DEBUG", "SUCCESS: " + settingsInEffect.toString());
		}

		@Override
		public void onStartFailure(int errorCode) {
			Log.d("DEBUG", "FAILURE: " + errorCode);
		}
	};

	private static final AdvertiseSettings settings =
			new AdvertiseSettings.Builder().setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
					.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
					.setConnectable(false)
					.setTimeout(2500)
					.build();


	public static BluetoothAdapter getBluetoothAdapter() {
		return bluetoothAdapter;
	}

	public static BluetoothLeAdvertiser getBluetoothLEAdvertiser() {
		if (bluetoothLeAdvertiser == null)
			bluetoothLeAdvertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
		return bluetoothLeAdvertiser;
	}

	public static void initialize(Activity activity) {
		if (bluetoothAdapter == null) {
			final BluetoothManager bluetoothManager =
					(BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE);
			bluetoothAdapter = bluetoothManager.getAdapter();
			requestPermissions(activity);
		}
	}

	public static void requestPermissions(Activity activity) {
		if (ActivityCompat.checkSelfPermission(activity,
				Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
				activity,
				Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
				ActivityCompat.requestPermissions(activity,
						new String[]{Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_ADVERTISE},
						42069);
			}
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
		}
	}

	public static AdvertiseSettings getDefaultAdvertisementSettings() {
		return settings;
	}
}
