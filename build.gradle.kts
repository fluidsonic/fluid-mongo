import io.fluidsonic.gradle.*
import org.jetbrains.kotlin.gradle.plugin.*

plugins {
	id("io.fluidsonic.gradle") version "1.1.0"
}

fluidLibrary(name = "mongo", version = "1.1.0")

fluidLibraryModule(description = "Kotlin coroutine support for MongoDB built on top of the official Reactive Streams Java Driver") {
	publishSingleTargetAsModule()

	targets {
		jvm {
			dependencies {
				implementation(kotlinx("coroutines-reactive", "1.3.8-1.4.0-rc"))
				implementation(mongodb("driver-reactivestreams"))

				api(kotlinx("coroutines-core", "1.3.8-1.4.0-rc"))
				api(mongodb("driver-core"))
			}
		}
	}
}


@Suppress("unused")
fun KotlinDependencyHandler.mongodb(name: String, version: String = "4.0.5") =
	"org.mongodb:mongodb-$name:$version"
