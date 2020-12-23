import io.fluidsonic.gradle.*

plugins {
	id("io.fluidsonic.gradle") version "1.1.16"
}

fluidLibrary(name = "mongo", version = "1.1.3")

fluidLibraryModule(description = "Kotlin coroutine support for MongoDB built on top of the official Reactive Streams Java Driver") {
	publishSingleTargetAsModule()

	targets {
		jvm {
			dependencies {
				api(kotlinx("coroutines-core", "1.4.2"))
				api(mongodb("driver-core"))

				implementation(kotlinx("coroutines-reactive", "1.4.2"))
				implementation(mongodb("driver-reactivestreams"))
			}
		}
	}
}


fun mongodb(name: String, version: String = "4.1.1") =
	"org.mongodb:mongodb-$name:$version"
