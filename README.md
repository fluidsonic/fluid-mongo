fluid-mongo
===========

[![Kotlin 1.3.21](https://img.shields.io/badge/Kotlin-1.3.21-blue.svg)](http://kotlinlang.org)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.fluidsonic/fluid-mongo.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.fluidsonic%22%20a%3A%22fluid-mongo%22)
[![#fluid-libraries Slack Channel](https://img.shields.io/badge/slack-%23fluid--libraries-543951.svg)](https://kotlinlang.slack.com/messages/C7UDFSVT2/)
[![Awesome Kotlin](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)

Kotlin coroutine support for MongoDB built on top of the official [MongoDB Asynchronous Java Driver 3.10](https://mongodb.github.io/mongo-java-driver/3.10/driver-async/).



Installation
------------

This library is [available in Maven Central](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.fluidsonic%22%20a%3A%22fluid-mongo%22) as `fluid-mongo` in the group `com.github.fluidsonic`.

`build.gradle.kts`:
```kotlin
dependencies {
    implementation("com.github.fluidsonic:fluid-mongo:0.9.5")
}
```


Example
-------

```kotlin
import com.github.fluidsonic.fluid.mongo.MongoClients
import org.bson.Document


suspend fun main() {
    val collection = MongoClients.create()
        .getDatabase("test")
        .getCollection("test")

    collection.insertOne(Document("hello", "world"))    // suspending call
    collection.insertOne(Document("it's", "so easy!"))  // suspending call

    for (document in collection.find()) {  // suspending calls
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
