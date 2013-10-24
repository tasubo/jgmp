jGMP
====
[![Build Status](https://drone.io/github.com/tasubo/jgmp/status.png)](https://drone.io/github.com/tasubo/jgmp/latest)

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
    <version>1.4</version>
</dependency>
```

Examples
------
```java
        MpClient mpClient = MpClient.withTrackingId("UA-40659159-1")
                .withClientId(clientId)
                .withCacheBuster()
                .create();

        App app = App.named("jGMP integration test").create();

        mpClient.send(AppView.hit().with(app));
```

```java
        MpClient mpClient = MpClient.withTrackingId("UA-40659159-1")
                .withClientId(clientId)
                .withCacheBuster()
                .create();

        Document document = Document.with()
                .description("long document")
                .hostname("localhost.com")
                .path("/root")
                .title("my document title")
                .create();

        mpClient.send(PageView.hit().with(document));
```

```java
        MpClient mpClient = MpClient.withTrackingId("UA-40659159-1")
                .withClientId(clientId)
                .withCacheBuster()
                .create();

        App app = App.named("jGMP integration test").create();

        mpClient.send(Event.of("Test", "Integration").action("testhit").with(app));

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

        mpClient.send(userTiming.with(referrer));
```

```java
        MpClient mpClient = MpClient.withTrackingId("UA-40659159-1")
                .withCacheBuster()
                .noCacheBuster()
                .anonymizingIp()
                .noAnonymizingIp()
                .usePost()
                .useSsl()
                .usePlainHttp()
                .useGet()
                .create();

        App app = App.named("jGMP integration test").create();

        mpClient.send(AppView.hit().with(app));
```

```java

		// example for asynchronous, non-blocking requests

		int concurrentRequests = 10;

        JavaGetConnectionRequester blockingHttpRequester = 
				new JavaGetConnectionRequester();		
		AsyncHttpRequester asyncHttpRequester = 
				new AsyncHttpRequester(blockingHttpRequester, concurrentRequests);
		
		MpClient asyncMpClient = MpClient.withTrackingId("UA-40659159-1")
				.withClientId(clientId)
				.httpRequester(asyncHttpRequester)
				.create();

        App app = App.named("jGMP integration test").create();

        asyncMpClient.send(AppView.hit().with(app));

		// when your program shuts down you have to shutdown AsyncHttpRequester
		asyncHttpRequester.shutdown();

```

For more usage examples please see tests
