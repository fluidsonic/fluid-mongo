fluid-mongo
===========

[![Maven Central](https://img.shields.io/maven-central/v/io.fluidsonic.mongo/fluid-mongo?label=Maven%20Central)](https://search.maven.org/artifact/io.fluidsonic.mongo/fluid-mongo)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.4.31-blue.svg)](https://github.com/JetBrains/kotlin/releases/v1.4.31)
[![MongoDB](https://img.shields.io/badge/MongoDB-Reactive%20Streams%204.2.2-blue.svg)](https://github.com/mongodb/mongo-java-driver/releases/tag/r4.2.2)
[![#fluid-libraries Slack Channel](https://img.shields.io/badge/slack-%23fluid--libraries-543951.svg?label=Slack)](https://kotlinlang.slack.com/messages/C7UDFSVT2/)

Kotlin coroutine support for MongoDB built on top of the
official [MongoDB Reactive Streams Java Driver](https://mongodb.github.io/mongo-java-driver/4.2/driver-reactive/).



Installation
------------

`build.gradle.kts`:

```kotlin
dependencies {
    implementation("io.fluidsonic.mongo:fluid-mongo:1.2.0")
}
```

Example
-------

```kotlin
import io.fluidsonic.mongo.MongoClients
import org.bson.Document


suspend fun main() {
    val collection = MongoClients.create()
        .getDatabase("test")
        .getCollection("test")

    collection.insertOne(mapOf("hello" to "world"))    // suspending call
    collection.insertOne(mapOf("it's" to "so easy!"))  // suspending call

    collection.find().collect { document ->  // Kotlin Flow
        println(document)
    }
}
```

Output:

```
Document{{_id=5bb6b56f9cfa62686b5afc87, hello=world}}
Document{{_id=5bb6b56f9cfa62686b5afc88, it's=so easy!}}
```

License
-------

Apache 2.0


--------------------------

[![Awesome Kotlin](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
