import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "1.0.0"
}

fluidJvmLibrary(name = "mongo", version = "0.9.11")

fluidJvmLibraryVariant(JvmTarget.jdk8) {
	description = "Kotlin coroutine support for MongoDB built on top of the official MongoDB Asynchronous Java Driver"
}

dependencies {
	implementation(mongodb("driver-async"))

	api(kotlinx("coroutines-core", "1.3.2"))
	api(mongodb("driver-core"))
}


@Suppress("unused")
fun DependencyHandler.mongodb(name: String, version: String = "3.11.1") =
	"org.mongodb:mongodb-$name:$version"
