package org.servalproject.audio;


public interface ICodec {

	int decode(byte encoded[], short lin[], int offset, int size);

	/**
	 * Encode a linear pcm audio stream
	 *
	 * @param lin
	 *            The linear stream to encode
	 *
	 * @param offset
	 *            The offset into the linear stream to begin
	 *
	 * @param encoded
	 *            The buffer to place the encoded stream
	 *
	 * @param size
	 *            the size of the linear pcm stream (in words)
	 *
	 * @returns the length (in bytes) of the encoded stream
	 */
	int encode(short lin[], int offset, byte alaw[], int frames);

	/**
	 * The sampling rate for this particular codec
	 */
	int samp_rate();

	/**
	 * The audio frame size for this particular codec
	 */
	int frame_size();

	/**
	 * Optionally used to initiallize the codec before any encoding or decoding
	 */
	void init();

	void update();

	/**
	 * Optionally used to free any resources allocated in init after encoding or
	 * decoding is complete
	 */
	void close();

	boolean isFailed();

	void fail();

	// boolean isValid();

	/**
	 * (implemented by {@link CodecBase}
	 *
	 * @returns The user friendly string for the codec (should include both the
	 *          name and the bandwidth
	 */
	String getTitle();

	/**
	 * (implemented by {@link CodecBase}
	 *
	 * @returns The RTP assigned name string for the codec
	 */
	String name();

	String key();

	String getValue();

	/**
	 * (implemented by {@link CodecBase}
	 *
	 * @returns The commonly used name for the codec.
	 */
	String userName();

	/**
	 * (implemented by {@link CodecBase}
	 *
	 * @returns The RTP assigned number for the codec
	 */
	int number();

	int sampleDurationFrames(byte[] buff, int i, int dataLen);

}
