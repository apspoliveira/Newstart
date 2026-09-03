# Fix Gradle Distribution Download Timeout

The user is experiencing a `java.net.SocketTimeoutException: Read timed out` when attempting to download Gradle 8.5. This usually happens when the network connection is slow or unstable, exceeding Gradle's default 10-second timeout.

## Proposed Changes

### Build Configuration

#### [MODIFY] [gradle.properties](file:///C:/Users/apspo/Igreja/Newstart/gradle.properties)
I will add system properties to increase the connection and socket timeouts for Gradle's internal HTTP client. This should give the wrapper more time to complete the download.

```properties
systemProp.org.gradle.internal.http.connectionTimeout=600000
systemProp.org.gradle.internal.http.socketTimeout=600000
```

## Verification Plan

### Manual Verification
1. After applying the changes, the user should attempt to sync the project or run `./gradlew` again.
2. If the download still fails, I will provide instructions for a manual download of the Gradle distribution.
