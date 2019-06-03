# micronaut-server

# Issue:
I have defined two HTTP GET APIs /test-mono and /test-no-mono, both are exactly same except /test-mono is using Mono return type. HelloControllerSpec tests both APIs 1000 times using Thread pool of 100, and /test-no-mono takes 45 seconds but /test-mono takes more than 4 minutes, why? Looks like in the default concurrency configuration of /test-no-mono is way better than /test-mono.

# Log from test run
$ ./gradlew clean test

> Task :test

sk.test.server.HelloControllerSpec STANDARD_OUT

    09:37:32.473 [Test worker] INFO  i.m.context.env.DefaultEnvironment - Established active environments: [test]

sk.test.server.HelloControllerSpec > using Mono STANDARD_OUT

    09:37:34.262 [Test worker] INFO  sk.test.server.HelloControllerSpec - Tests using mono
    09:41:46.972 [Test worker] INFO  sk.test.server.HelloControllerSpec - Done tests using mono

# Tests run in > 4 minutes

sk.test.server.HelloControllerSpec > without Mono STANDARD_OUT

    09:41:46.975 [Test worker] INFO  sk.test.server.HelloControllerSpec - Tests without mono
    09:42:27.216 [Test worker] INFO  sk.test.server.HelloControllerSpec - Done tests without mono

# Same test in 45 seconds.

# I want to use non-blocking IO, how do I fix this issue?

