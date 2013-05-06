jGMP
====

Java library for Google's Measurement Protocol

https://developers.google.com/analytics/devguides/collection/protocol/v1/

Supports almost all features. 97% of lines covered by tests.

All builders and payload messages are immutable.

Maven
------
```xml
<dependency>
    <groupId>com.github.tasubo</groupId>
    <artifactId>jgmp</artifactId>
    <version>1.0</version>
</dependency>
```

Examples
------
```java
MpClient mp = MpClient.withTrackingId("UA-XXXX-Y")
                .withClientId("35009a79-1a05-49d7-b876-2b884d0f825b")
                .withCacheBuster()
                .anonymizingIp()
                .create();

Event event = Event.of("Category", "Label").action("Action");

mp.send(event);

```

```java
App app = App.named("jGMP integration test")
        .version("0.1337")
        .create();

SystemInfo systemInfo = SystemInfo.with()
        .colorBits(24)
        .screenResolution(800, 600)
        .userLanguage("lt_LT")
        .documentEncoding("UTF-8")
        .javaEnabled()
        .create();

MpClient mpClient = MpClient.withTrackingId("UA-40659159-1")
        .withClientId(clientId)
        .withCacheBuster()
        .using(systemInfo)
        .using(app)
        .create();

mpClient.send(Timing.pageLoad(23));
```

```java
App app = App.named("jGMP integration test")
        .version("0.8008")
        .create();

MpClient mpClient = MpClient.withTrackingId("UA-40659159-1")
        .withClientId(clientId)
        .withCacheBuster()
        .using(app)
        .create();

Decorating referrer = Referrer.from("http://localhost/");
UserTiming userTiming = Timing.user().name("test").time(4).create();

mpClient.send(referrer.with(userTiming));
```



For more usage examples please see tests
