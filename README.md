# reddit4j

## About
Reddit4j is an unofficial Java library for the Reddit API.  Reddit4j is a work-in-progress and is not yet ready for consumption.

[![Build Status](https://travis-ci.org/reddit4j/reddit4j.png?branch=master)](https://travis-ci.org/reddit4j/reddit4j)

## Dependencies
* Apache Ant
* Apache HttpComponents HttpClient
* Apache Commons Lang
* Jackson
* Junit
* Mockito
* Slf4j

## Integration testing
Automated integration testing is not enabled with Travis at present, as storing account credentials in the clear is somewhat undesirable.  To integration test reddit4j, please create an integration.properties file in the `tst/com/reddit4j/integration` directory.  A sample `integration.properties.example` is provided.
