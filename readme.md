# SearchEngineRequester
It is an application that prompts the user for a text string, performs a Web search (Google) and returns the title and URL of the first result.<br/>
### Launching
 * cd /projectDirectory
 * ./start.sh
 * The program asks u to enter text to search or to enter #quit for stopping
 * Once u hit enter a web request will be made to https://www.google.com
 * Upon success recieved html will be save in work/google.html file
 * Then a parser will find 1st result and present it to u.

### Compiling
This is the maven project, so maven is required to complie it.
For running maven isnt required, all libraries are already included.
```bash
mvn clean install
mvn dependency:copy-dependencies -Dmdep.useSubDirectoryPerScope=true
```
### Configuration
config directory contains two xml configuration files:
* logger.xml - logback configuration file
* httpClient.xml - apache http client configuration file

By defaul logs are written to logs/main.log file.
### Used libraries
* _weld-se_ is used to provide DI in se environment, its more powerful and flexible then Guice.
* _lombok_ is a small library which generates setters\getters\toString and other helpful methods.
* _slf4j_ allows to abstract from concrete logger implementation
* _logback_ is a logger implementation, used by slf4j
* _simple-xml_ is an easy to use xml serialization library
* _apache http client_ open source, well documented apache library for making http requests, its better then HttpUrlConnection and has more features
* _jsoup_  good library for parsing html, supports css queries and well documented
