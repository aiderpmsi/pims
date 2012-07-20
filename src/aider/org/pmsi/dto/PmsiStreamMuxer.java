package aider.org.pmsi.dto;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PmsiStreamMuxer {

	private PipedInputStream inputStream;
	
	private PipedOutputStream outputStream;
	
	public PmsiStreamMuxer() throws IOException {
		inputStream = new PipedInputStream();
		outputStream = new PipedOutputStream(inputStream);
	}
	
	public PipedInputStream getInputStream() {
		return inputStream;
	}

	public PipedOutputStream getOutputStream() {
		return outputStream;
	}

	public void eof() throws IOException {
		outputStream.close();
		outputStream = null;
	}
	
	public boolean isClosed() {
		if (inputStream == null)
			return true;
		else
			return false;
	}
	
	public void close() throws IOException {
		if (outputStream != null)
			outputStream.close();
		if (inputStream != null)
			inputStream.close();
		
		inputStream = null;
		outputStream = null;
	}
}
