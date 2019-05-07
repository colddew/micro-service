Brief
=======
Micro Service architecture based on latest popular technologies

Tech Stack & Legacy Issue
=======
- [x] spring-boot: upgrade to 2.x
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
- refactor project
- upgrade all dependencies from spring boot 1.x to 2.x
- auto configuration, SpringApplication loading, externalized configuration, webflux
- improve kafka, cassandra, neo4j, rabbitmq
- introduce consul, elk, jwt
- implement javascript, groovy, lua, scala, clojure illustration
- introduce RxJava, WebFlux, Reactive Stack
- add exception handle, log, transaction

Build
=======
mvn versions:set versions:commit -DnewVersion=`<version>`