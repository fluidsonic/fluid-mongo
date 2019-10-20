import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "1.0.3"
}

fluidJvmLibrary(name = "mongo", version = "1.0.0-beta.2")

fluidJvmLibraryVariant(JvmTarget.jdk8) {
	description = "Kotlin coroutine support for MongoDB built on top of the official MongoDB Asynchronous Java Driver"
}

dependencies {
	implementation(kotlinx("coroutines-reactive", "1.3.2"))
	implementation(mongodb("driver-reactivestreams", "1.12.0"))

	api(kotlinx("coroutines-core", "1.3.2"))
	api(mongodb("driver-core", "3.11.1"))
}


@Suppress("unused")
fun DependencyHandler.mongodb(name: String, version: String) =
	"org.mongodb:mongodb-$name:$version"
