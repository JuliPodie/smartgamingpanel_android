package com.example.bluetoothtest.api;

import android.annotation.SuppressLint;
import android.bluetooth.*;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.widget.Toast;
import com.example.bluetoothtest.R;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class CharacteristicsEmitter implements Emitter {

	private final Context context;
	private BluetoothGatt gatt;
	private BluetoothGattCharacteristic characteristic;

	@SuppressLint("MissingPermission")
	public CharacteristicsEmitter(Context context) {
		this.context = context;
		BluetoothLeScanner bluetoothLeScanner = BluetoothHelper.getBluetoothAdapter().getBluetoothLeScanner();
		byte[] userName = context.getString(R.string.user).getBytes(StandardCharsets.UTF_8);

		bluetoothLeScanner.startScan(new ScanCallback() {
			@Override
			public void onScanResult(int callbackType, ScanResult result) {
				result.getDevice().connectGatt(context, true, new BluetoothGattCallback() {
					@Override
					public void onServicesDiscovered(BluetoothGatt gatt, int status) {
						BluetoothGattService service =
								gatt.getService(UUID.fromString(context.getString(R.string.service_uuid)));
						for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
							List<BluetoothGattDescriptor> descriptors = characteristic.getDescriptors()
									.stream()
									.sorted(Comparator.comparing(BluetoothGattDescriptor::getUuid))
									.collect(Collectors.toList());
							for (BluetoothGattDescriptor descriptor : descriptors) {
								if (Arrays.equals(descriptor.getValue(), "NO USER".getBytes(StandardCharsets.UTF_8))) {
									if (descriptor.setValue(userName)) {
										gatt.writeDescriptor(descriptor);
									}
								}
							}
						}

					}

					@Override
					public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic,
					                                  int status) {
						String message = status == BluetoothGatt.GATT_SUCCESS ? "Send command" :
								String.format(Locale.ENGLISH,
										"Failed to send command. Error %d",
										status);
						Toast.makeText(context, message, Toast.LENGTH_LONG).show();
					}

					@Override
					public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
						String text;
						if (Arrays.equals(descriptor.getValue(), userName)) {
							text = "Logged in";
							characteristic = descriptor.getCharacteristic();
						} else {
							text = "Failed to login";
						}
						Toast.makeText(context, text, Toast.LENGTH_LONG).show();
					}

					@Override
					public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
						if (newState == BluetoothGatt.STATE_CONNECTED) {
							CharacteristicsEmitter.this.gatt = gatt;
						}
					}
				});
			}
		});

	}

	@Override
	@SuppressLint("MissingPermission")
	public void enterCommand(Command command) {
		if (characteristic.setValue(command.getCommand())) {
			gatt.writeCharacteristic(characteristic);
		}
	}
}
