package org.servalproject.batphone;

import org.servalproject.R;
import org.servalproject.audio.ICodec;

public class VoMP {
	/*
	 * Note that usage of this enum is deprecated, there are now enough other
	 * monitor commands to handle each call state change that we don't need to
	 * track the local or remote states ourselves, we can let servald do that
	 * work for us.
	 */
	@Deprecated
	public enum State {
		NoSuchCall(R.string.outgoing_call, 0),
		NoCall(R.string.outgoing_call, 1),
		CallPrep(R.string.outgoing_call, 2),
		RingingOut(R.string.outgoing_call, 3),
		RingingIn(R.string.incoming_call, 4),
		InCall(R.string.in_call, 5),
		CallEnded(R.string.call_ended, 6),
		Error(R.string.call_ended, 99);

		public final int code;
		public final int displayResource;

		State(int displayResource, int code) {
			this.displayResource = displayResource;
			this.code = code;
		}

		public static State getState(int value) {
			switch (value) {
			case 0:
				return NoSuchCall;
			case 1:
				return NoCall;
			case 2:
				return CallPrep;
			case 3:
				return RingingOut;
			case 4:
				return RingingIn;
			case 5:
				return InCall;
			case 6:
				return CallEnded;
			default:
			case 99:
				return Error;
			}
		}
	}

	public static final int MAX_AUDIO_BYTES = 1024;

	public enum Codec {
		Signed16(0x01, 1, null),
		Ulaw8(0x02, 2, org.servalproject.audio.ulaw.class),
		Alaw8(0x03, 2, org.servalproject.audio.alaw.class),
		Gsm(0x04, 0, org.servalproject.audio.GSM.class),
		BV16(0x05, 0, org.servalproject.audio.BV16.class),
		SILK8(0x06, 0, org.servalproject.audio.SILK8.class),
		SILK16(0x07, 0, org.servalproject.audio.SILK16.class),
		SILK24(0x08, 0, org.servalproject.audio.SILK24.class),
		Speex(0x09, 0, org.servalproject.audio.SILK24.class),
		G722(0x10, 0, org.servalproject.audio.SILK24.class);

		public final int code;
		// we put this string into audio packets quite a lot, lets only pay the
		// conversion cost once.
		public final String codeString;
		public final int preference;
		public final boolean supported;
		private final Class<? extends ICodec> claz;

		Codec(int code, int preference, Class<? extends ICodec> claz) {
			this.code = code;
			this.codeString = Integer.toString(code);
			this.preference = preference;
			this.supported = claz != null;
			this.claz = claz;
		}

		Codec(int code) {
			this(code, 0, null);
		}

		public ICodec create() throws IllegalAccessException,
				InstantiationException {
			return claz.newInstance();
		}
		public static Codec getCodec(int code) {
			switch (code) {
			case 0x01:
				return Signed16;
			case 0x02:
				return Ulaw8;
			case 0x03:
				return Alaw8;
			case 0x04:
				return Gsm;
			case 0x05:
				return BV16;
			case 0x06:
				return SILK8;
			case 0x07:
				return SILK16;
			case 0x08:
				return SILK24;
			case 0x09:
				return Speex;
			case 0x10:
				return G722;

			default:
				return null;
			}

		}
	}
}
