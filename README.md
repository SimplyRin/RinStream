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
