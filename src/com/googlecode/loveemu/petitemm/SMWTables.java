package com.googlecode.loveemu.petitemm;

public final class SMWTables {
	
	/**
	 * Midi pan values indexed by y (y0 = right, y20 = left).
	 */
	protected static final int[] PAN_TABLE = {
			128, 127, 126, 122, 116, 109, 102, 93, 85, 75, 64, 53, 43, 35, 26, 19, 12, 6, 2, 1, 0
	};
	
	/**
	 * v values indexed by volume output value (from 0 to 77).
	 */
	protected static final int[] VOLUME_TABLE = {
			33, 44, 52, 59, 66, 72, 79, 84, 89, 93, 97, 101, 106, 110, 113, 117,
			120, 123, 127, 131, 134, 137, 140, 143, 147, 149, 152, 154, 157, 159, 162, 165,
			167, 170, 172, 174, 177, 179, 182, 184, 186, 188, 190, 194, 196, 198, 200, 202,
			204, 206, 208, 210, 212, 214, 216, 217, 220, 222, 223, 225, 227, 228, 231, 232,
			234, 236, 237, 239, 241, 243, 244, 246, 248, 249, 251, 253, 254, 255
	};
	
	/**
	 * Pan multiplier values from AMK's main.asm.
	 */
	protected static final int[] PAN_VALUES = {
			0x00, 0x01, 0x03, 0x07, 0x0D, 0x15, 0x1E, 0x29, 0x34, 0x42, 0x51,
			0x5E, 0x67, 0x6E, 0x73, 0x77, 0x7A, 0x7C, 0x7D, 0x7E, 0x7F
	};
	
	/**
	 * Velocity multiplier values from AMK's main.asm.
	 */
	protected static final int[] VELOCITY_VALUES = {
			0x19, 0x33, 0x4C, 0x66, 0x72, 0x7F, 0x8C, 0x99,
			0xA5, 0xB2, 0xBf, 0xCC, 0xD8, 0xE5, 0xF2, 0xFC
	};
	
	/**
	 * Duration multiplier values from AMK's main.asm.
	 */
	protected static final int[] NOTE_DURATIONS = {
			0x33, 0x66, 0x80, 0x99, 0xB3, 0xCC, 0xE6, 0x100
	};
	
	private SMWTables() {
		super();
	}
	
	
}
