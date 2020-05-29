import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "1.0.12"
}

fluidJvmLibrary(name = "mongo", version = "1.0.1")

fluidJvmLibraryVariant(JvmTarget.jdk8) {
	description = "Kotlin coroutine support for MongoDB built on top of the official MongoDB Asynchronous Java Driver"
}

dependencies {
	implementation(kotlinx("coroutines-reactive", "1.3.7"))
	implementation(mongodb("driver-reactivestreams"))

	api(kotlinx("coroutines-core", "1.3.7"))
	api(mongodb("driver-core"))
}


@Suppress("unused")
fun DependencyHandler.mongodb(name: String, version: String = "4.0.3") =
	"org.mongodb:mongodb-$name:$version"
