package net.simplyrin.rinstream.utils;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import jline.console.ConsoleReader;
import net.simplyrin.rinstream.RinStream;

/**
 * Copyright (c) 2012, md_5. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * The name of the author may not be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * You may not use the software for commercial software hosting services without
 * written permission from the author.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
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
