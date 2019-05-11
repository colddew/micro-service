[![Build Status](https://travis-ci.org/colddew/micro-service.svg?branch=master)](https://travis-ci.org/colddew/micro-service)
[![codecov](https://codecov.io/gh/colddew/micro-service/branch/master/graph/badge.svg)](https://codecov.io/gh/colddew/micro-service)
[![License](http://img.shields.io/:license-mit-blue.svg?style=flat)](http://doge.mit-license.org)

Brief
=======
Micro Service architecture based on latest popular technologies

Dev Env
=======
- Desktop MBP
- Java 8
- IDEA 2019
- MySQL 5.7

Tech Stack & Legacy Issue
=======
- [x] spring-boot 2.x
- [ ] spring-boot-admin
- [ ] swagger
- [ ] retrofit
- [ ] okhttp
- [ ] logback
- [ ] mybatis
- [ ] druid
- [ ] jedis
- [ ] kafka
- [ ] mongodb
- [ ] cassandra
- [ ] shardbatis
- [ ] turbine
- [ ] hystrix
- [ ] zxing
- [ ] neo4j
- [ ] caffeine

Future Plans
=======
- refactor
- upgrade all dependencies from spring-boot 1.x to 2.x
- upgrade mysql from 5.x to 8.x with docker
- improve mongodb, kafka, cassandra, neo4j, rabbitmq
- auto configuration, SpringApplication loading analysis, externalized configuration, customized metrics
- introduce RxJava, WebFlux, Reactive Stack
- introduce exception handle, multi-level log management, transaction, jwt/openid
- introduce kotlin, javascript, groovy, lua, scala, clojure

Build
=======
mvn versions:set versions:commit -DnewVersion=`<version>`  
mvn clean -Dmaven.test.skip=true build