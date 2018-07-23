# Reactive Spring 5

## Introduction

This repository is used as part of a presentation on Reactive Spring 5.
The presentation is aimed as gentle introduction to Reactive Spring 5 while illustrating some real-world benefits.

## Requirements

* Java 8
* MongoDB 3.x
* curl
* http

## Mongo Setup

```
use admin
db.createUser(
    {
        user: "admin",
        pwd: "password",
        roles: [{role: "userAdminAnyDatabase", db: "admin"}]
    }
)

use locs
db.createUser(
    {
        user: "user",
        pwd: "password",
        roles: [{role: "readWrite", db: "locs"}]
    }
)
```

## Running

### Start MongoDB

Start mongodb with `mongod --dbpath <folder>` with folder on a slow drive. 
This will allow for a pronounced difference between non-reactive and reactive tests.

### Populate Database
Populate the database using: 
```bash
./gradlew -Dtest.single=PopulateDatabaseManual :nonreactive-web:test
```
OR when using Spring Fu version 
```bash
http POST http://localhost:8080/load?count=1000
```

### Run non-reactive version
```bash
./gradlew :nonreactive-web:bootRun
```

### Run reactive version
```bash
./gradlew :reactive-web:bootRun
```

### Run Kotlin Reactive version
```bash
./gradelw :reactive-kotlin:bootRun
```

### Run Spring Fu version
```bash
./gradlew :reactive-fu:bootRun
```

Data can be loaded using
```bash
http POST http://localhost:8080/load?count=1000
```
### Scripts

The `scripts` folder contains scripts that use `curl` to perform measurements of the rest calls.

OR

```bash
http http://localhost:8080/last30days
http http://localhost:8080/extlast30days
```

### Generate Load

```bash
./gradlew :load-generator:run
```


## Performance Comparison

The load generator is configured with a number of iterations and will then divide the work over a number of threads stepping up through 1, 2, 5, 10, 20, 50, 100, 200.
 
The performance testing isn't exhaustive since is was done in a single machine.

### Total time to perform iterations.

![Total Times](img/chart-totals.png)

| Type         | Measure |        1 |        2 |        5 |       10 |       20 |       50 |      100 |      200 |
|--------------|---------|---------:|---------:|---------:|---------:|---------:|---------:|---------:|---------:|
|      WebMVC | Totals  |     32.6 |     17.1 |      9.1 |      7.9 |      7.9 |      8.0 |      9.6 |      8.7 |
|      WebFlux | Totals  |     45.3 |     26.8 |     13.8 |     13.6 |     11.0 |     14.1 |     11.7 |     13.3 |
|    WebFluxKt | Totals  |     42.2 |     23.3 |     13.5 |     10.4 |     10.1 |     10.8 |     11.2 |     10.4 |
|     SpringFu | Totals  |     16.2 |      8.2 |      4.6 |      3.8 |      3.3 |      4.6 |      4.0 |      3.8 |

### Average response time per request.

![Average Times](img/chart-average.png)

| Type         | Measure |        1 |        2 |        5 |       10 |       20 |       50 |      100 |      200 |
|--------------|---------|---------:|---------:|---------:|---------:|---------:|---------:|---------:|---------:|
|      WebMVC | Average |     32.5 |     34.1 |     45.3 |     75.8 |    152.0 |    342.5 |    617.2 |   1395.3 |
|      WebFlux | Average |     45.3 |     53.6 |     68.9 |    133.4 |    205.6 |    607.9 |    941.2 |   1570.7 |
|    WebFluxKt | Average |     42.2 |     46.6 |     67.0 |    102.3 |    194.2 |    461.2 |    723.8 |    739.2 |
|     SpringFu | Average |     16.2 |     16.3 |     22.8 |     36.3 |     63.0 |    190.9 |    187.1 |    517.2 |
