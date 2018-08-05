package net.simplyrin.rinstream.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import net.simplyrin.rinstream.RinStream;

public class ConciseFormatter extends Formatter {

	private RinStream rinStream;

	public ConciseFormatter(RinStream rinStream) {
		this.rinStream = rinStream;
	}

	@Override
	public String format(LogRecord logRecord) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(this.rinStream.getPrefix() + " ");
		stringBuilder.append(this.formatMessage(logRecord));
		stringBuilder.append('\n');

		if(logRecord.getThrown() != null) {
			StringWriter stringWriter = new StringWriter();
			logRecord.getThrown().printStackTrace(new PrintWriter(stringWriter));
			stringBuilder.append(stringWriter);
		}

		return stringBuilder.toString();
	}

}
