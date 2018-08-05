package net.simplyrin.rinstream.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Charsets;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoggingOutputStream extends ByteArrayOutputStream {

	private final Logger logger;
	private final Level level;

	@Override
	public void flush() throws IOException {
		String contents = this.toString(Charsets.UTF_8.name());
		super.reset();
		if(!contents.isEmpty() && !contents.equals(System.getProperty("line.separator"))) {
			this.logger.logp(this.level, "", "", contents);
		}
	}

}
