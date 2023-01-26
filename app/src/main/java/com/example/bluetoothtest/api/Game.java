package com.example.bluetoothtest.api;

public enum Game implements Command {

	SNAKE((byte) 0xFF);

	private final byte code;

	Game(byte code) {
		this.code = code;
	}

	@Override
	public byte[] getCommand() {
		return new byte[]{code};
	}
}
