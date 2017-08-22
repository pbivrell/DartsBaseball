src=src/SimpleHTTPServer.java src/Baseball.java

all:
	javac $(src) -d bin/

baseball:
	java -cp bin/ Baseball

server:
	java -cp bin/ SimpleHTTPServer

clean:
	rm bin/*
