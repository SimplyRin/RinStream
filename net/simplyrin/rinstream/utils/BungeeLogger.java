package net.simplyrin.rinstream.utils;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import jline.console.ConsoleReader;
import net.simplyrin.rinstream.RinStream;

public class BungeeLogger extends Logger {

	private final RinStream rinStream;
	private final Formatter formatter;
	private final LogDispatcher logDispatcher = new LogDispatcher(this);

	public BungeeLogger(RinStream rinStream, String loggerName, ConsoleReader consoleReader) {
		super(loggerName, null);
		this.rinStream = rinStream;
		this.formatter = new ConciseFormatter(this.rinStream);

		this.setLevel(Level.ALL);

		ColouredWriter consoleHandler = new ColouredWriter(consoleReader);
		consoleHandler.setLevel(Level.INFO);
		consoleHandler.setFormatter(this.formatter);
		this.addHandler(consoleHandler);

		this.logDispatcher.start();
	}

	@Override
	public void log(LogRecord logRecord) {
		this.logDispatcher.queue(logRecord);
	}

	public void doLog(LogRecord logRecord) {
		super.log(logRecord);
	}

}
