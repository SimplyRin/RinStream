package net.simplyrin.rinstream;

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

	private String prefix = "[HH:mm:ss]";

	public RinStream() {
		super(System.out);
		System.setOut(this);
	}

	@Override
	public void print(String contents) {
		if(contents == null) {
			contents = "null";
		}
		Reflect.on(this).call("write", this.getPrefix() + " " + contents);
	}

	public RinStream setPrefix(String contents) {
		this.prefix = contents;
		return this;
	}

	private String getPrefix() {
		SimpleDateFormat simpleDataFormat = new SimpleDateFormat(this.prefix);
		Date date = new Date();

		return simpleDataFormat.format(date);
	}

}
