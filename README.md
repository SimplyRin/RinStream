# RinStream
`System.out.println("");` などを行うときにメッセージの最初に Prefix (時間など) を一緒に出力されるようになる物

# Usage
```Java
public static void main(String[] args) {
	new RinStream();
	System.out.println("Hey!");
	// 出力結果: [HH:mm:ss] Hey!
}
```

# Maven
- Repository
```XML
  <repositories>
    <repository>
      <id>net.simplyrin</id>
      <name>api</name>
      <url>https://api.simplyrin.net/maven/</url>
    </repository>
  </repositories>
```

- Dependency
```XML
  <dependencies>
    <dependency>
      <groupId>net.simplyrin.rinstream</groupId>
      <artifactId>RinStream</artifactId>
      <version>1.1</version>
    </dependency>
  </dependencies>
```
**※ 文字化けする場合は 1.0 を使用してください。**

# Open Source License
**・BungeeCord (ChatColor/Log API) | BSD 3-Clause "New" or "Revised" License**
```
Copyright (c) 2012, md_5. All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.

The name of the author may not be used to endorse or promote products derived
from this software without specific prior written permission.

You may not use the software for commercial software hosting services without
written permission from the author.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
```
