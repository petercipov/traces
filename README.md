![Traces (doc/traces.png)](https://raw.githubusercontent.com/petercipov/traces/master/doc/traces.png)

Traces is a code flow tracing library.

[![Build Status](https://travis-ci.org/petercipov/traces.svg?branch=master)](https://travis-ci.org/petercipov/traces)

## Documentation

[The introduction article](http://www.petercipov.com/traces-sane-logging-in-async/) is a good place to start.

## Binaries
Use just api in all shared code that needs to be traced, i.e libraries. 

```xml
<dependency>
	<groupId>com.petercipov</groupId>
	<artifactId>traces-api</artifactId>
	<version>1.1.0</version>
</dependency>
```

In your top projects choose traces implementation.

Vizu that serializes traces to JSON

```xml
<dependency>
	<groupId>com.petercipov</groupId>
	<artifactId>traces-vizu</artifactId>
	<version>1.1.0</version>
</dependency>
```

Std I/O - simple output to console

```xml
<dependency>
	<groupId>com.petercipov</groupId>
	<artifactId>traces-stdio</artifactId>
	<version>1.1.0</version>
</dependency>
```

For junit tests use project

```xml
<dependency>
	<groupId>com.petercipov</groupId>
	<artifactId>traces-junit</artifactId>
	<version>1.1.0</version>
	<scope>test</scope>
</dependency>
```