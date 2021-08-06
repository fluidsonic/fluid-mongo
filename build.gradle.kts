import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "1.1.24"
}

fluidLibrary(name = "mongo", version = "1.4.0")

fluidLibraryModule(description = "Kotlin coroutine support for MongoDB built on top of the official Reactive Streams Java Driver") {
	targets {
		jvm {
			dependencies {
				api(kotlinx("coroutines-core", "1.5.1"))
				api(mongodb("driver-core"))

				implementation(kotlinx("coroutines-reactive", "1.5.1"))
				implementation(mongodb("driver-reactivestreams"))
			}
		}
	}
}


fun mongodb(name: String, version: String = "4.3.1") =
	"org.mongodb:mongodb-$name:$version"
