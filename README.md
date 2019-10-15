fluid-mongo
===========

[![Maven Central](https://img.shields.io/maven-central/v/com.github.fluidsonic/fluid-mongo?label=Maven%20Central)](https://search.maven.org/artifact/com.github.fluidsonic/fluid-mongo)
[![JCenter](https://img.shields.io/bintray/v/bintray/jcenter/fluid-mongo?label=JCenter)](https://bintray.com/fluidsonic/maven/fluid-mongo)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.50-blue.svg)](https://github.com/JetBrains/kotlin/releases/v1.3.50)
[![Kotlin Coroutines](https://img.shields.io/badge/Kotlin%20Coroutines-1.3.2-blue.svg)](https://github.com/Kotlin/kotlinx.coroutines/releases/tag/1.3.2)
[![MongoDB Driver](https://img.shields.io/badge/MongoDB%20Driver-Async%203.11.1-blue.svg)](https://mongodb.github.io/mongo-java-driver/3.11/driver-async/)
[![#fluid-libraries Slack Channel](https://img.shields.io/badge/slack-%23fluid--libraries-543951.svg?label=Slack)](https://kotlinlang.slack.com/messages/C7UDFSVT2/)

Kotlin coroutine support for MongoDB built on top of the official [MongoDB Asynchronous Java Driver](https://mongodb.github.io/mongo-java-driver/3.11/driver-async/).



Installation
------------

`build.gradle.kts`:
```kotlin
dependencies {
    implementation("com.github.fluidsonic:fluid-mongo:0.9.10")
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

    for (document in collection.find().produce()) {  // suspending calls
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
