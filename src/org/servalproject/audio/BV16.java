/*
 * Copyright (C) 2009 The Sipdroid Open Source Project
 *
 * This file is part of Sipdroid (http://www.sipdroid.org)
 *
 * Sipdroid is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This source code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this source code; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.servalproject.audio;


public class BV16 extends CodecBase implements ICodec {

// 	private static final int DEFAULT_COMPRESSION = 6;

	// void load() {
	// try {
	// System.loadLibrary("bv16_jni");
	// super.load();
	// } catch (Throwable e) {
	//
	// }
	//
	// }
	static boolean loaded = false;



	BV16() {
		CODEC_NAME = "BV16";
		CODEC_USER_NAME = "BV16";
		CODEC_DESCRIPTION = "16kbit";
		CODEC_NUMBER = 106;
		CODEC_DEFAULT_SETTING = "always";

		if (!loaded) {
			System.loadLibrary("bv16_jni");
			super.load();
			loaded = true;
		}

		// load and open codec for 1st time
		super.update();
	}







	public native int open();
	@Override
	public native int decode(byte encoded[], short lin[], int offset, int size);
	@Override
	public native int encode(short lin[], int offset, byte encoded[], int size);
	@Override
	public native void close();

	@Override
	public void init() {
		load();
		if (isLoaded())
			open();
	}
}
