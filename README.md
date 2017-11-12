# Reactive Spring 5

## Introduction


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

## TODO

* ~~Data model~~
* ~~Repositories~~
* ~~Populate database~~
* ~~Services~~
* ~~Controllers~~
* ~~Fetch scripts~~
* ~~Reactive Repositories~~
* ~~Reactive Services~~
* ~~Reactive Controllers~~



