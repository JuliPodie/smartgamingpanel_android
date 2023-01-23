package com.example.bluetoothtest.api;

import android.bluetooth.le.AdvertiseData;
import android.util.Log;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Advertiser {

	private final AdvertisementEmitter advertiser;
	private short counter = 0;

	public Advertiser(AdvertisementEmitter advertiser) {
		this.advertiser = advertiser;
	}

	public void enterCommand(Command command) {
		byte[] payload = ByteBuffer.allocate(4).putShort(counter).put((byte) 58) //ascii -> :
				.put(command.getCommand()).array();
		Log.d("DEBUG", Arrays.toString(payload));
		AdvertiseData data = new AdvertiseData.Builder().addServiceData(Constants.PLAYER_UUID, payload).build();
		advertiser.emitAdvertisement(data);
		counter++;
	}

}
