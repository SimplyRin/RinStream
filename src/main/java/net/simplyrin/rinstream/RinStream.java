package net.simplyrin.rinstream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import lombok.Getter;

/**
 * Created by SimplyRin on 2018/08/04.
 *
 * Copyright (c) 2018-2021 SimplyRin
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

	static {
		RinStream rs = new RinStream();

		rs.isEnableTranslateColor();
		rs.isSaveLog();
		rs.isEnableColor();

		rs.getFile();
		rs.getFormat();

		rs.getTailProcess();
	}

	@Getter
	private File file;

	@Getter
	private Process tailProcess;
	@Getter
	private String tailName;

	@Getter
	private String format = "[%prefix] [%type]";
	private String prefix = "HH:mm:ss";

	@Getter
	private boolean saveLog;
	@Getter
	private boolean enableColor;
	@Getter
	private boolean enableTranslateColor;

	private static String TAG_INFO = "INFO";
	private static String TAG_ERROR = "ERROR";

	@Getter
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

	@Override
	public void print(String value) {
		this.print(value, Tag.INFO);
	}

	public void print(String value, Tag tag) {
		if (value == null) {
			value = "null";
		}
		value = value.replaceAll("\n", "\n" + this.getPrefix(tag) + " ");

		if (this.enableTranslateColor) {
			value = ChatColor.translate(value);
		}
		String line = this.getPrefix(tag) + " " + value;

		String trimLine = line.trim() + (this.enableColor ? ChatColor.RESET : "");
		
		try {
			this.write(trimLine.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (this.saveLog) {
			try {
				FileWriter fileWriter = new FileWriter(this.file, true);
				fileWriter.write((this.enableColor ? ChatColor.stripColor(line) : line) + "\n");
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public enum Tag {
		INFO, ERROR;

		public String getTagWithColor() {
			if (this.equals(INFO)) {
				return ChatColor.BLUE + TAG_INFO + ChatColor.RESET;
			}
			return ChatColor.RED + TAG_ERROR + ChatColor.RESET;
		}

		public String getName() {
			if (this.equals(INFO)) {
				return TAG_INFO;
			}
			return TAG_ERROR;
		}
	}

	/**
	 * @return RinStream
	 * Windows 10 のみで動作確認。
	 * PowerShell を使用して Tail が機能します。
	 */
	public RinStream killPowerShell() {
		ProcessBuilder processBuilder = new ProcessBuilder(new String[] { "taskkill", "/F", "/FI", "\"WINDOWTITLE eq " + this.tailName + "\"", "/T" });
		try {
			System.out.println("Killing " + this.tailName);
			processBuilder.start();
		} catch (Exception e) {
		}
		return this;
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
			this.tailName = "[RinStream] Windows PowerShell " + UUID.randomUUID().toString().split("-")[0];
			ProcessBuilder processBuilder = new ProcessBuilder(new String[] { "cmd", "/c", "start", "\""
					+ this.tailName + "\"", "powershell", "Get-Content", this.file.getAbsolutePath(), "-wait", "-tail", "0"});
			processBuilder.redirectErrorStream(true);
			this.tailProcess = processBuilder.start();
		} catch (Exception e) {
		}
		return this;
	}

	public RinStream setFormat(String format) {
		this.format = format;
		return this;
	}

	private String getPrefix(Tag tag) {
		SimpleDateFormat simpleDataFormat = new SimpleDateFormat(this.prefix);
		Date date = new Date();

		return this.format.replace("%prefix", simpleDataFormat.format(date))
				.replace("%type", this.enableColor ? tag.getTagWithColor() : tag.getName());
	}

	public RinStream setPrefix(String contents) {
		this.prefix = contents;
		return this;
	}

	public RinStream setEnableColor(boolean bool) {
		this.enableColor = bool;
		return this;
	}
	public RinStream setEnableTranslateColor(boolean bool) {
		this.enableTranslateColor = bool;
		return this;
	}

	public RinStream setSaveLog(boolean bool) {
		this.saveLog = bool;
		return this;
	}

	public static void setTagInfo(String tagInfo) {
		TAG_INFO = tagInfo;
	}

	public static String getTagInfo() {
		return TAG_INFO;
	}

	public static void setTagError(String tagError) {
		TAG_ERROR = tagError;
	}

	public static String getTagError() {
		return TAG_ERROR;
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

	public RinStream enableError() {
		new ErrorStream();
		return this;
	}

	public class ErrorStream extends PrintStream {

		public ErrorStream() {
			super(System.err);
			System.setErr(this);
		}

		@Override
		public void print(String value) {
			rinStream.setError();
			rinStream.print(value, Tag.ERROR);
			rinStream.clearError();
		}

	}

	public void printOpenSourceLicense() {
		this.print("**・[jOOR | Apache License 2.0](https://github.com/jOOQ/jOOR/blob/master/LICENSE.txt)**\n" +
				"```\n" +
				"Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
				"you may not use this file except in compliance with the License.\n" +
				"You may obtain a copy of the License at\n" +
				"\n" +
				"    http://www.apache.org/licenses/LICENSE-2.0\n" +
				"\n" +
				"Unless required by applicable law or agreed to in writing, software\n" +
				"distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
				"WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
				"See the License for the specific language governing permissions and\n" +
				"limitations under the License.\n" +
				"```\n" +
				"\n" +
				"**・[BungeeCord (ChatColor) | BSD 3-Clause \"New\" or \"Revised\" License](https://github.com/SpigotMC/BungeeCord/blob/master/LICENSE)**\n" +
				"```\n" +
				"Copyright (c) 2012, md_5. All rights reserved.\n" +
				"\n" +
				"Redistribution and use in source and binary forms, with or without\n" +
				"modification, are permitted provided that the following conditions are met:\n" +
				"\n" +
				"Redistributions of source code must retain the above copyright notice, this\n" +
				"list of conditions and the following disclaimer.\n" +
				"\n" +
				"Redistributions in binary form must reproduce the above copyright notice,\n" +
				"this list of conditions and the following disclaimer in the documentation\n" +
				"and/or other materials provided with the distribution.\n" +
				"\n" +
				"The name of the author may not be used to endorse or promote products derived\n" +
				"from this software without specific prior written permission.\n" +
				"\n" +
				"You may not use the software for commercial software hosting services without\n" +
				"written permission from the author.\n" +
				"\n" +
				"THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\"\n" +
				"AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE\n" +
				"IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE\n" +
				"ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE\n" +
				"LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR\n" +
				"CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF\n" +
				"SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS\n" +
				"INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN\n" +
				"CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)\n" +
				"ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE\n" +
				"POSSIBILITY OF SUCH DAMAGE.\n" +
				"```\n" +
				"\n" +
				"**・[Lombok](https://github.com/rzwitserloot/lombok/blob/master/LICENSE)**\n" +
				"```\n" +
				"Copyright (C) 2009-2021 The Project Lombok Authors.\n" +
				"\n" +
				"Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
				"of this software and associated documentation files (the \"Software\"), to deal\n" +
				"in the Software without restriction, including without limitation the rights\n" +
				"to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
				"copies of the Software, and to permit persons to whom the Software is\n" +
				"furnished to do so, subject to the following conditions:\n" +
				"\n" +
				"The above copyright notice and this permission notice shall be included in\n" +
				"all copies or substantial portions of the Software.\n" +
				"\n" +
				"THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
				"IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
				"FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
				"AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
				"LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
				"OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN\n" +
				"THE SOFTWARE.\n" +
				"\n" +
				"==============================================================================\n" +
				"Licenses for included components:\n" +
				"\n" +
				"org.ow2.asm:asm\n" +
				"org.ow2.asm:asm-analysis\n" +
				"org.ow2.asm:asm-commons\n" +
				"org.ow2.asm:asm-tree\n" +
				"org.ow2.asm:asm-util\n" +
				"ASM: a very small and fast Java bytecode manipulation framework\n" +
				" Copyright (c) 2000-2011 INRIA, France Telecom\n" +
				" All rights reserved.\n" +
				"\n" +
				" Redistribution and use in source and binary forms, with or without\n" +
				" modification, are permitted provided that the following conditions\n" +
				" are met:\n" +
				" 1. Redistributions of source code must retain the above copyright\n" +
				"    notice, this list of conditions and the following disclaimer.\n" +
				" 2. Redistributions in binary form must reproduce the above copyright\n" +
				"    notice, this list of conditions and the following disclaimer in the\n" +
				"    documentation and/or other materials provided with the distribution.\n" +
				" 3. Neither the name of the copyright holders nor the names of its\n" +
				"    contributors may be used to endorse or promote products derived from\n" +
				"    this software without specific prior written permission.\n" +
				"\n" +
				" THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\"\n" +
				" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE\n" +
				" IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE\n" +
				" ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE\n" +
				" LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR\n" +
				" CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF\n" +
				" SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS\n" +
				" INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN\n" +
				" CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)\n" +
				" ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF\n" +
				" THE POSSIBILITY OF SUCH DAMAGE.\n" +
				"\n" +
				"------------------------------------------------------------------------------\n" +
				"rzwitserloot/com.zwitserloot.cmdreader \n" +
				" \n" +
				" Copyright © 2010 Reinier Zwitserloot.\n" +
				"\n" +
				" Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
				" of this software and associated documentation files (the \"Software\"), to deal\n" +
				" in the Software without restriction, including without limitation the rights\n" +
				" to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
				" copies of the Software, and to permit persons to whom the Software is\n" +
				" furnished to do so, subject to the following conditions:\n" +
				"\n" +
				" The above copyright notice and this permission notice shall be included in\n" +
				" all copies or substantial portions of the Software.\n" +
				"\n" +
				" THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
				" IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
				" FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
				" AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
				" LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
				" OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN\n" +
				" THE SOFTWARE.\n" +
				"\n" +
				"------------------------------------------------------------------------------\n" +
				"\n" +
				"rzwitserloot/lombok.patcher\n" +
				"\n" +
				" Copyright (C) 2009-2021 The Project Lombok Authors.\n" +
				" \n" +
				" Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
				" of this software and associated documentation files (the \"Software\"), to deal\n" +
				" in the Software without restriction, including without limitation the rights\n" +
				" to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
				" copies of the Software, and to permit persons to whom the Software is\n" +
				" furnished to do so, subject to the following conditions:\n" +
				"\n" +
				" The above copyright notice and this permission notice shall be included in\n" +
				" all copies or substantial portions of the Software.\n" +
				"\n" +
				" THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
				" IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
				" FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
				" AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
				" LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
				" OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN\n" +
				" THE SOFTWARE.\n" +
				"\n" +
				"------------------------------------------------------------------------------\n" +
				"``\n" +
				"");
	}

}
