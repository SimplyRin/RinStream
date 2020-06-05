# RinStream
`System.out.println("");` などを行うときにメッセージの最初に Prefix (時間など) を一緒に出力されるようになる物

# Usage
```Java
public static void main(String[] args) {
	RinStream rinStream = new RinStream();

  // true にすると logs フォルダにファイルが生成されログが保存されていきますわよ
  rinStream.setSaveLog(true);

  // 出力結果: [HH:mm:ss] Hey!
	System.out.println("Hey!");
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
      <version>1.3</version>
    </dependency>
  </dependencies>
```

# Open Source License
**・[jOOR | Apache License 2.0](https://github.com/jOOQ/jOOR/blob/master/LICENSE.txt)**
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
