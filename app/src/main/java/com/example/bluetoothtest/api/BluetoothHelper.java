package com.example.bluetoothtest.api;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BluetoothHelper {

	private static BluetoothAdapter bluetoothAdapter;

	private static CharacteristicsEmitter characteristicsEmitter;


	public static BluetoothAdapter getBluetoothAdapter() {
		return bluetoothAdapter;
	}

	public static CharacteristicsEmitter getCharacteristicsEmitter(Context context) {
		if (characteristicsEmitter == null) {
			characteristicsEmitter = new CharacteristicsEmitter(context);
		}
		return characteristicsEmitter;
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

}
