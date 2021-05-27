import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "1.1.23"
}

fluidLibrary(name = "mongo", version = "1.3.0")

fluidLibraryModule(description = "Kotlin coroutine support for MongoDB built on top of the official Reactive Streams Java Driver") {
	targets {
		jvm {
			dependencies {
				api(kotlinx("coroutines-core", "1.5.0"))
				api(mongodb("driver-core"))

				implementation(kotlinx("coroutines-reactive", "1.5.0"))
				implementation(mongodb("driver-reactivestreams"))
			}
		}
	}
}


fun mongodb(name: String, version: String = "4.2.3") =
	"org.mongodb:mongodb-$name:$version"
