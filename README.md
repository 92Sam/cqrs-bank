# Service Finance JAVA - (Springboot)


## Patterns Used
- Pattern CQRS - Command Query Resposability Segregation
- Pattern Events Sourcing (Producer-Consumer Broker)

### Technologies
- Java OpenJDK 18
- Apache Kafka
- MongoDB
- PostgreSQL
- Graphql
- Spring Boot
- Docker
- Postman
- Make
- Maven

### How Execute the project locally

1) Configure .env file variables.1
2) Execute the following command
```makefile
make run_container
```

##Graphql Endpoints Postman
- [Endpoints Graphql](https://github.com/92Sam/cqrs-bank/blob/develop/src/main/resources/doc/POSTMAN-CQRS-BANK.postman_collection.json)

### Structure Directories
``` 
├── infra
│   ├── app_service
│   └── pgsql_conf
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── company
    │   │           └── bankservice
    │   │               ├── config
    │   │               ├── dto
    │   │               │   ├── events
    │   │               │   └── resolvers
    │   │               ├── entities
    │   │               ├── enums
    │   │               │   └── errors
    │   │               ├── events
    │   │               ├── mappers
    │   │               ├── repositories
    │   │               │   └── impl
    │   │               ├── resolvers
    │   │               │   ├── command
    │   │               │   └── query
    │   │               ├── services
    │   │               │   └── impl
    │   │               └── utils
    │   └── resources
    │       ├── doc
    │       └── graphql
    └── test
        └── java
            └── com
                └── company
                    └── bankservice
                    └── graphql
```


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.5/maven-plugin/reference/html/#build-image)
