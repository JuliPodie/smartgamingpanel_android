package com.example.bluetoothtest.api;

public enum Input implements Command {
	UP((byte) 0x01), DOWN((byte) 0x02), LEFT((byte) 0x03), RIGHT((byte) 0x04), SNAKE((byte) 0x05), PONG((byte) 0x06),LIGHT((byte) 0x10);
	private final byte code;

	Input(byte code) {
		this.code = code;
	}

	@Override
	public byte[] getCommand() {
		return new byte[]{code};
	}
}
