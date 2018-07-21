# Reactive Spring 5

## Introduction

This repository is used as part of a presentation on Reactive Spring 5.
The presentation is aimed as gentle introduction to Reactive Spring 5 while illustrating some real-world benefits.

## Requirements

* Java 8
* MongoDB 3.x
* curl

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
http POST http://localhost:8080/load?count=5000
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
http POST http://localhost:8080/load?count=5000
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
