# TaskList

## About
The ACME company decided to create a task list and the business area defined that the MVP must contain only the following features: Create the list; Add item to list; Remove item from list; Mark item as completed.

This solution was created using Spring Boot + JPA + Hibernate + PostgreSQL + Maven + JUnit + vue.JS.

## How to Run (Unix or MacOS):

<strong>Create a postgres database name "tasklist".</strong>

Run API with unit tests:

```
mvn install
java -jar target/tasklist-0.0.1-SNAPSHOT.jar
```

Run Front-end client:

```
cd tasklist-client
npm install
npm run serve
```

## List of endpoints:

https://acme-tasklist.herokuapp.com/
