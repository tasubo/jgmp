jGMP
====

Java library for Google's Measurement Protocol

https://developers.google.com/analytics/devguides/collection/protocol/v1/


Example
```java
MpClient mp = MpClient.withTrackingId("UA-XXXX-Y")
                .withClientId("35009a79-1a05-49d7-b876-2b884d0f825b")
                .withCacheBuster()
                .anonymizingIp()
                .create();

Event event = Event.of("Category", "Label").action("Action");

mp.send(event);

```

For more usage examples please see tests
