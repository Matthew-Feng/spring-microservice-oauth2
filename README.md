# Bootiful Microservices with Spring Boot :: following the tutorial of Matt Raible, with some fix and customize   

This example shows how to create a microservices architecture with Spring Boot and display its data with an Angular UI.

Please read [Build a Microservices Architecture for Microbrews with Spring Boot](https://developer.okta.com/blog/2017/06/15/build-microservices-architecture-spring-boot) for a tutorial that shows you how to build this application.

**Prerequisites:** [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

> [Okta](https://developer.okta.com/) has Authentication and User Management APIs that reduce development time with instant-on, scalable user infrastructure. Okta's intuitive API and expert support make it easy for developers to authenticate, manage and secure users and roles in any application.

* [Getting Started](#getting-started)
* [Help](#help)
* [Links](#links)
* [License](#license)

## Getting Started

This will get a copy of the project installed locally. To run the client and all the servers, execute `./run.sh`, or execute the [commands in this file](https://github.com/oktadeveloper/spring-boot-microservices-example/blob/master/run.sh) manually.

```bash
r=`pwd`
echo $r

# Eureka
cd $r/eureka-service
echo "Starting Eureka Service..."
mvn -q clean spring-boot:run &

# Beer Service
echo "Starting Beer Catalog Service..."
cd $r/beer-catalog-service
mvn -q clean spring-boot:run &

# Edge Service
echo "Starting Edge Service..."
cd $r/edge-service
mvn -q clean spring-boot:run &


