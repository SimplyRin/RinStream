package net.simplyrin.rinstream.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.LogRecord;

public class LogDispatcher extends Thread {

	private final BungeeLogger bungeeLogger;
	private final BlockingQueue<LogRecord> blockingQueue = new LinkedBlockingQueue<>();

	public LogDispatcher(BungeeLogger logger) {
		super("RinStream Logger Thread");
		this.bungeeLogger = logger;
	}

	@Override
	public void run() {
		while(!this.isInterrupted()) {
			LogRecord logRecord;
			try {
				logRecord = this.blockingQueue.take();
			} catch (InterruptedException e) {
				continue;
			}

			this.bungeeLogger.doLog(logRecord);
		}
		for(LogRecord logRecord : this.blockingQueue) {
			this.bungeeLogger.doLog(logRecord);
		}
	}

	public void queue(LogRecord logRecord) {
		if(!this.isInterrupted()) {
			this.blockingQueue.add(logRecord);
		}
	}

}

