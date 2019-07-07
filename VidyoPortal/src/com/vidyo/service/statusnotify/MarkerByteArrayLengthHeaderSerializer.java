/**
 *
 */
package com.vidyo.service.statusnotify;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArrayLengthHeaderSerializer;
import org.springframework.integration.ip.tcp.serializer.SoftEndOfStreamException;

/**
 * @author ganesh
 *
 */
public class MarkerByteArrayLengthHeaderSerializer extends ByteArrayLengthHeaderSerializer{

	protected static final Logger logger = LoggerFactory.getLogger(MarkerByteArrayLengthHeaderSerializer.class);

	/**
	 * Constructs the serializer using {@link #HEADER_SIZE_INT}
	 */
	public MarkerByteArrayLengthHeaderSerializer() {
		super(HEADER_SIZE_INT);
		this.headerSize = 7;
	}

	private final int headerSize;

	private String marker = "VDY";

	@Override
	public void serialize(byte[] bytes, OutputStream outputStream) throws IOException {
		byte[] markerBytes = marker.getBytes();
		outputStream.write(markerBytes);
		this.writeHeader(outputStream, bytes.length);
		outputStream.write(bytes);
	}

	@Override
	protected int readHeader(InputStream inputStream) throws IOException {
		byte[] lengthPart = new byte[this.headerSize];
		try {
			int status = read(inputStream, lengthPart, true);
			if (status < 0) {
				throw new SoftEndOfStreamException("Stream closed between payloads");
			}
			int messageLength;
			switch (this.headerSize) {
			case 7:
				messageLength = ByteBuffer.wrap(lengthPart, 3, 4).getInt();
				if (messageLength < 0) {
					throw new IllegalArgumentException("Length header:"
							+ messageLength
							+ " is negative");
				}
				break;
			default:
				throw new IllegalArgumentException("Bad header size:" + this.headerSize);
			}
			return messageLength;
		}
		catch (SoftEndOfStreamException e) {
			throw e;
		}
		catch (IOException e) {
			publishEvent(e, lengthPart, -1);
			throw e;
		}
		catch (RuntimeException e) {
			publishEvent(e, lengthPart, -1);
			throw e;
		}
	}

}
