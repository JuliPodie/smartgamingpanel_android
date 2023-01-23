package com.example.bluetoothtest.api;

import android.bluetooth.le.AdvertiseData;

@FunctionalInterface
public interface AdvertisementEmitter {
	void emitAdvertisement(AdvertiseData advertiseData);
}
