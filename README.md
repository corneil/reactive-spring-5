# Reactive Spring 5

## Introduction

This repository is used as part of a presentation on Reactive Spring 5.
The presentation is aimed as gentle introduction to Reactive Spring 5 while illustrating some real-world benefits.

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
Populate the database using: `./gradlew -Dtest.single=PopulateDatabaseManual :nonreactive-web:test`

### Run non-reactive version
`./gradlew :nonreactive-web:bootRun`

### Run reactive version
`./gradlew :reactive-web:bootRun`

### Scripts

The `scrtips` folder contains scripts that use `curl` to perform measurements of the rest calls.




