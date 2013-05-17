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

import android.telephony.TelephonyManager;

abstract class CodecBase implements ICodec {
	protected String CODEC_NAME;
	protected String CODEC_USER_NAME;
	protected int CODEC_NUMBER;
	protected int CODEC_SAMPLE_RATE=8000;		// default for most narrow band codecs
	protected int CODEC_FRAME_SIZE=160;		// default for most narrow band codecs
	protected String CODEC_DESCRIPTION;
	protected String CODEC_DEFAULT_SETTING = "never";

	private boolean loaded = false,failed = false;
	private boolean enabled = false;
	private boolean wlanOnly = false,wlanOr3GOnly = false;
	private String value;

	@Override
	public void update() {
		if (value == null) {
			value = CODEC_DEFAULT_SETTING;
			// updateFlags(value);
		}
		// if (Receiver.mContext != null) {
		// SharedPreferences sp =
		// PreferenceManager.getDefaultSharedPreferences(Receiver.mContext);
		// value = sp.getString(key(), CODEC_DEFAULT_SETTING);
		// updateFlags(value);
		// }
	}

	@Override
	public String getValue() {
		return value;
	}

	void load() {
		update();
		loaded = true;
	}

	@Override
	public int samp_rate() {
		return CODEC_SAMPLE_RATE;
	}

	@Override
	public int frame_size() {
		return CODEC_FRAME_SIZE;
	}

	public boolean isLoaded() {
		return loaded;
	}

	@Override
	public boolean isFailed() {
		return failed;
	}

	@Override
	public void fail() {
		update();
		failed = true;
	}

	public void enable(boolean e) {
		enabled = e;
	}

	public boolean isEnabled() {
		return enabled;
	}

	TelephonyManager tm;
	int nt;

	// public boolean isValid() {
	// if (!isEnabled())
	// return false;
	// if (Receiver.on_wlan)
	// return true;
	// if (wlanOnly())
	// return false;
	// if (tm == null) tm = (TelephonyManager)
	// Receiver.mContext.getSystemService(Context.TELEPHONY_SERVICE);
	// nt = tm.getNetworkType();
	// if (wlanOr3GOnly() && nt < TelephonyManager.NETWORK_TYPE_UMTS)
	// return false;
	// if (nt < TelephonyManager.NETWORK_TYPE_EDGE)
	// return false;
	// return true;
	// }



	@Override
	public String name() {
		return CODEC_NAME;
	}

	@Override
	public String key() {
		return CODEC_NAME+"_new";
	}

	@Override
	public String userName() {
		return CODEC_USER_NAME;
	}

	@Override
	public String getTitle() {
		return CODEC_NAME + " (" + CODEC_DESCRIPTION + ")";
	}

	@Override
	public int number() {
		return CODEC_NUMBER;
	}

	// public void setListPreference(ListPreference l) {
	// l.setOnPreferenceChangeListener(this);
	// l.setValue(value);
	// }

	// @Override
	// public boolean onPreferenceChange(Preference p, Object newValue) {
	// ListPreference l = (ListPreference)p;
	// value = (String)newValue;
	//
	// updateFlags(value);
	//
	// l.setValue(value);
	// l.setSummary(l.getEntry());
	//
	// return true;
	// }

	// private void updateFlags(String v) {
	//
	// if (v.equals("never")) {
	// enabled = false;
	// } else {
	// enabled = true;
	// if (v.equals("wlan"))
	// wlanOnly = true;
	// else
	// wlanOnly = false;
	// if (v.equals("wlanor3g"))
	// wlanOr3GOnly = true;
	// else
	// wlanOr3GOnly = false;
	// }
	// }

	@Override
	public String toString() {
		return "CODEC{ " + CODEC_NUMBER + ": " + getTitle() + "}";
	}

	private static short dummy[] = new short[8 * 120];

	@Override
	public int sampleDurationFrames(byte[] buff, int i, int dataLen) {
		// TODO Auto-generated method stub

		int res = this.decode(buff, dummy, i, dataLen);

		return res / 2;
	}

}
