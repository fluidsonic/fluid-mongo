import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "1.3.1"
}

fluidLibrary(name = "mongo", version = "1.6.0")

fluidLibraryModule(description = "Kotlin coroutine support for MongoDB built on top of the official Reactive Streams Java Driver") {
	targets {
		jvm {
			dependencies {
				api(kotlinx("coroutines-core", "1.7.1"))
				api(mongodb("driver-core"))

				implementation(kotlinx("coroutines-reactive", "1.7.1"))
				implementation(mongodb("driver-reactivestreams"))
			}
		}
	}
}


fun mongodb(name: String, version: String = "4.10.0") =
	"org.mongodb:mongodb-$name:$version"
