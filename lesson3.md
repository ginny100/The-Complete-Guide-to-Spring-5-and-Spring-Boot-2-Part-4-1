# Lesson 3: REST API Design

## Overview

In this lesson, we start building a REST API that performs CRUD operations. Here, we make use of the tennis player project from the [Part 3](https://github.com/ginny100/The-Complete-Guide-to-Spring-5-and-Spring-Boot-2-Part-3) to create a REST API to perform CRUD operations on the data stored in the in-memory H2 database.

## Demonstrated Concepts

### REST API design

Our REST API exposes endpoints which allow a REST client to:
- Get a list of players
- Get a player by ID
- Add a new player
- Update an existing player
- Update the titles of a player
- Delete a player

We use the HTTP `GET`, `POST`, `PUT`, `PATCH`, and `DELETE` methods to perform these operations.

To create service endpoints for our REST API, we identify the entity (main resource), which is __player__ in this case. A REST API design convention is to use the plural of the entity as the endpoint, so for this project, we have __/players__.

| Operation               | HTTP Method | URL           |
|-------------------------|-------------|---------------|
| Create a player         | `POST`        | `/players`     |
| Show all players        | `GET`         | `/players`     |
| Show player by ID       | `GET`         | `/players/{id}` |
| Update a player         | `PUT`         | `/players/{id}` |
| Partial update a player | `PATCH`       | `/players/{id}` |
| Delete a player         | `DELETE`      | `/players/{id}` |

The HTTP method, like `GET` and `POST`, defines the action to be performed.

### Required dependencies

`spring-webmvc`: supports web as well as RESTful applications

`jackson-databind`: automatically handles the conversion of JSON data to POJO and vice versa

`spring-boot-starter-web`: takes care of both above-mentioned dependencies

`spring-boot-starter-data-jpa`: supports Hibernate ORM

`h2`: for the in-memory database

`spring-boot-devtools`: provides the auto restart feature

`spring-boot-starter-test`: built-in support for testing

### Data source configuration

In the `application.properties` file, we can set up the data source URL by using the following property:

```properties
spring.datasource.url = jdbc:h2:mem:testdb
```

### `@Entity` annotation

The `@Entity` annotation is used to map the class to a database table. The name of the table is the same as the class, unless otherwise specified.

```java
@Entity
public class Player {
    //Class implementation
}
```

### `@Id` and `@GeneratedValue` annotations

The `@Id` and `@GeneratedValue` annotations are used to mark the _primary key_ and define the manner in which values are generated.

```java
@Entity
public class Player {
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private int id;

    private String name;
    private String nationality;
}
```

### `@JsonFormat` annotation

By default, dates are saved as `Timestamp` by Hibernate. When we annotate the `birthDate` field with `@JsonFormat` annotation, Jackson will use the provided format for serializing and deserializing the field.

```java
@Entity
public class Player {
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private int id;

    private String name;
    private String nationality;
    
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;

    private int titles;
    
    //constructors
    //getter and setter methods
}
```



### Database Initialization

Hibernate can generate the DDL based on the `spring.jpa.hibernate.ddl.auto` property. We will use `create-drop` as its value in the `application.properties` file.

```properties
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

The `spring.jpa.show-sql` property, when set to true, shows the queries used by Hibernate when creating the database.

The database is populated using the `import.sql` file placed at the root of the classpath. This file is executed on startup.

### Creating a repository

An interface `PlayerRepository.java` extends the `JpaRepository` interface and provide the entity and the data type of the primary key:

```java
import org.springframework.data.jpa.repository.JpaRepository; 

public interface PlayerRepository extends JpaRepository <Player, Integer> {

}
```

Simply by extending the `JpaRepository`, we get all basic CRUD operations like `findAll()`, `findById()`, `save()`, and `deleteById()` etc., without having to write any implementation.

### Service layer

As a best practice, we introduce a service layer on top of the repository. We create a class `PlayerService.java` and use the `@Service` annotation to indicate that this class belongs to the service layer.

To use the `PlayerRepository` in the service layer, we autowire it and then delegate calls to the methods provided by the `JpaRepository`.

```java
@Service
public class PlayerService {
    
    @Autowired
    private PlayerRepository repo;

    //method to return all players

    //method to find player by id

    //method to add player

    //...
}
```