package net.simplyrin.rinstream;

import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.fusesource.jansi.Ansi;

import jline.console.ConsoleReader;
import net.simplyrin.rinstream.utils.BungeeLogger;
import net.simplyrin.rinstream.utils.LoggingOutputStream;

/**
 * Created by SimplyRin on 2018/08/04.
 *
 * Copyright (c) 2018 SimplyRin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class RinStream {

	private String prefix = "[HH:mm:ss]";

	public RinStream() {
		ConsoleReader consoleReader = null;
		try {
			consoleReader = new ConsoleReader();
		} catch (IOException e) {
			e.printStackTrace();
		}
        consoleReader.setExpandEvents( false );

		Logger logger = new BungeeLogger(this, "RinStream", consoleReader);
		System.setOut(new PrintStream(new LoggingOutputStream(logger, Level.INFO), true));
	}

	public RinStream setPrefix(String contents) {
		this.prefix = contents;
		return this;
	}

	public String getPrefix() {
		SimpleDateFormat simpleDataFormat = new SimpleDateFormat(this.prefix);
		Date date = new Date();

		return simpleDataFormat.format(date);
	}

	public static class Color {

		public static final String BLACK = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).boldOff().toString();
		public static final String DARK_BLUE = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).boldOff().toString();
		public static final String DARK_GREEN = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).boldOff().toString();
		public static final String DARK_AQUA = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).boldOff().toString();
		public static final String DARK_RED = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).boldOff().toString();
		public static final String DARK_PURPLE = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).boldOff().toString();
		public static final String GOLD = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).boldOff().toString();
		public static final String GRAY = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).boldOff().toString();
		public static final String DARK_GRAY = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).bold().toString();
		public static final String BLUE = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).bold().toString();
		public static final String GREEN = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).bold().toString();
		public static final String AQUA = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).bold().toString();
		public static final String RED = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).bold().toString();
		public static final String LIGHT_PURPLE = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).bold().toString();
		public static final String YELLOW = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).bold().toString();
		public static final String WHITE = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).bold().toString();
		public static final String MAGIC = Ansi.ansi().a(Ansi.Attribute.BLINK_SLOW).toString();
		public static final String BOLD = Ansi.ansi().a(Ansi.Attribute.UNDERLINE_DOUBLE).toString();
		public static final String STRIKETHROUGH = Ansi.ansi().a(Ansi.Attribute.STRIKETHROUGH_ON).toString();
		public static final String UNDERLINE = Ansi.ansi().a(Ansi.Attribute.UNDERLINE).toString();
		public static final String ITALIC = Ansi.ansi().a(Ansi.Attribute.ITALIC).toString();
		public static final String RESET = Ansi.ansi().a(Ansi.Attribute.RESET).toString();
	}

}
