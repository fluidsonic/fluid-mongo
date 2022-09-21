import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "1.2.1"
}

fluidLibrary(name = "mongo", version = "1.6.0")

fluidLibraryModule(description = "Kotlin coroutine support for MongoDB built on top of the official Reactive Streams Java Driver") {
	targets {
		jvm {
			dependencies {
				api(kotlinx("coroutines-core", "1.6.0"))
				api(mongodb("driver-core"))

				implementation(kotlinx("coroutines-reactive", "1.6.0"))
				implementation(mongodb("driver-reactivestreams"))
			}
		}
	}
}


fun mongodb(name: String, version: String = "4.7.1") =
	"org.mongodb:mongodb-$name:$version"
