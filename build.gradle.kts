import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "1.0.10"
}

fluidJvmLibrary(name = "mongo", version = "1.0.0")

fluidJvmLibraryVariant(JvmTarget.jdk8) {
	description = "Kotlin coroutine support for MongoDB built on top of the official MongoDB Asynchronous Java Driver"
}

dependencies {
	implementation(kotlinx("coroutines-reactive", "1.3.3"))
	implementation(mongodb("driver-reactivestreams", "4.0.0"))

	api(kotlinx("coroutines-core", "1.3.3"))
	api(mongodb("driver-core", "4.0.0"))
}


@Suppress("unused")
fun DependencyHandler.mongodb(name: String, version: String) =
	"org.mongodb:mongodb-$name:$version"
