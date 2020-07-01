package net.simplyrin.rinstream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joor.Reflect;

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
public class RinStream extends PrintStream {

	private File file;

	private String prefix = "HH:mm:ss";
	private boolean saveLog;
	private File logFolder;

	@SuppressWarnings("resource")
	public static void init() {
		new RinStream();
	}

	private RinStream rinStream;

	public RinStream() {
		super(System.out);
		System.setOut(this);

		this.rinStream = this;

		this.file = this.getAvailableName(new File("logs"));
	}

	public void enableError() {
		new ErrorStream();
	}

	public class ErrorStream extends PrintStream {

		public ErrorStream() {
			super(System.err);
			Reflect.on(System.class).set("err", this);
		}

		@Override
		public void print(String value) {
			rinStream.setError();
			rinStream.print(value, Tag.ERROR);
			rinStream.clearError();
		}

	}

	@Override
	public void print(String value) {
		this.print(value, Tag.INFO);
	}

	public void print(String value, Tag tag) {
		if (value == null) {
			value = "null";
		}
		value = value.replaceAll("\n", "\n" + this.getPrefix(tag) + " ");

		String line = this.getPrefix(tag) + " " + value;
		Reflect.on(this).call("write", line.trim());

		if (this.saveLog) {
			try {
				FileWriter fileWriter = new FileWriter(this.file, true);
				fileWriter.write(line + "\n");
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public File getFile() {
		return this.file;
	}

	public enum Tag {
		INFO, ERROR
	}

	/**
	 * @return RinStream
	 * Windows 10 のみで動作確認。
	 * PowerShell を使用して Tail が機能します。
	 */
	public RinStream tail() {
		if (!System.getProperty("os.name").equals("Windows 10")) {
			throw new RuntimeException("This method only works on Windows 10");
		}
		try {
			ProcessBuilder processBuilder = new ProcessBuilder(new String[] { "cmd", "/c", "start", "powershell", "Get-Content", this.file.getAbsolutePath(), "-wait", "-tail", "0"});
			processBuilder.redirectErrorStream(true);
			processBuilder.start();
		} catch (Exception e) {
		}
		return this;
	}

	public RinStream setPrefix(String contents) {
		this.prefix = contents;
		return this;
	}

	public RinStream setSaveLog(boolean bool) {
		this.saveLog = bool;
		return this;
	}

	public boolean isSaveLog() {
		return this.saveLog;
	}

	public RinStream setLogFolder(File folder) {
		if (!folder.exists()) {
			folder.mkdirs();
		}
		if (!folder.isDirectory()) {
			return this;
		}

		this.logFolder = folder;
		this.file = this.getAvailableName(this.logFolder);
		return this;
	}

	private String getPrefix(Tag tag) {
		SimpleDateFormat simpleDataFormat = new SimpleDateFormat(this.prefix);
		Date date = new Date();

		return "[" + simpleDataFormat.format(date) + "|" + tag.name() + "]";
	}

	private File getAvailableName(File folder) {
		if (!folder.exists()) {
			folder.mkdirs();
		}

		int i = 1;
		while (true) {
			String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			File file = new File(folder, time + "-" + i + ".log");

			if (!file.exists()) {
				return file;
			}

			i++;
		}

	}

	public void printOpenSourceLicense() {
		this.print("**・[jOOR | Apache License 2.0](https://github.com/jOOQ/jOOR/blob/master/LICENSE.txt)**\r\n" +
				"```\r\n" +
				"Licensed under the Apache License, Version 2.0 (the \"License\");\r\n" +
				"you may not use this file except in compliance with the License.\r\n" +
				"You may obtain a copy of the License at\r\n" +
				"\r\n" +
				"    http://www.apache.org/licenses/LICENSE-2.0\r\n" +
				"\r\n" +
				"Unless required by applicable law or agreed to in writing, software\r\n" +
				"distributed under the License is distributed on an \"AS IS\" BASIS,\r\n" +
				"WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\r\n" +
				"See the License for the specific language governing permissions and\r\n" +
				"limitations under the License.\r\n" +
				"```");
	}

}
