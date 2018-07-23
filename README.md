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

Average time per request.

![Average Times](img/chart-average.png)

Total time to perform all iterations.

![Total Times](img/chart-totals.png)

### Java WebMVC
```
Client=1, Iterations=1000, Average=31.1ms, Total time=31.1s
Client=2, Iterations=500, Average=32.0ms, Total time=47.2s
Client=5, Iterations=200, Average=42.2ms, Total time=55.7s
Client=10, Iterations=100, Average=70.8ms, Total time=62.9s
Client=20, Iterations=50, Average=137.7ms, Total time=70.3s
Client=50, Iterations=20, Average=315.7ms, Total time=78.2s
Client=100, Iterations=10, Average=258.2ms, Total time=85.7s
Client=200, Iterations=5, Average=332.8ms, Total time=93.1s
```
### Java WebFlux
```
Client=1, Iterations=1000, Average=43.2ms, Total time=43.2s
Client=2, Iterations=500, Average=45.4ms, Total time=66.0s
Client=5, Iterations=200, Average=75.3ms, Total time=81.2s
Client=10, Iterations=100, Average=105.2ms, Total time=91.8s
Client=20, Iterations=50, Average=241.4ms, Total time=104.5s
Client=50, Iterations=20, Average=480.7ms, Total time=115.3s
Client=100, Iterations=10, Average=723.5ms, Total time=125.7s
Client=200, Iterations=5, Average=1963.1ms, Total time=138.8s
```
### Kotlin WebFlux
```
Client=1, Iterations=1000, Average=40.8ms, Total time=40.8s
Client=2, Iterations=500, Average=43.4ms, Total time=62.6s
Client=5, Iterations=200, Average=62.5ms, Total time=75.2s
Client=10, Iterations=100, Average=94.3ms, Total time=84.8s
Client=20, Iterations=50, Average=180.9ms, Total time=94.3s
Client=50, Iterations=20, Average=419.6ms, Total time=104.6s
Client=100, Iterations=10, Average=615.4ms, Total time=115.4s
Client=200, Iterations=5, Average=333.3ms, Total time=124.8s
```
### Spring Fu
```
Client=1, Iterations=1000, Average=15.5ms, Total time=15.5s
Client=2, Iterations=500, Average=15.3ms, Total time=23.2s
Client=5, Iterations=200, Average=20.5ms, Total time=27.4s
Client=10, Iterations=100, Average=36.3ms, Total time=31.1s
Client=20, Iterations=50, Average=69.2ms, Total time=34.9s
Client=50, Iterations=20, Average=179.4ms, Total time=40.0s
Client=100, Iterations=10, Average=294.8ms, Total time=44.2s
Client=200, Iterations=5, Average=289.7ms, Total time=47.6s
```
