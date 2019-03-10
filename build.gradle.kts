import com.github.fluidsonic.fluid.library.*

plugins {
	id("com.github.fluidsonic.fluid-library") version "0.9.3"
}

fluidLibrary {
	name = "fluid-mongo"
	version = "0.9.4"
}

fluidLibraryVariant {
	description = "Kotlin coroutine support for MongoDB built on top of the official MongoDB Asynchronous Java Driver"
	jdk = JDK.v1_8
}

dependencies {
	implementation(mongodb("driver-async"))

	api(kotlinx("coroutines-core", "1.1.1"))
	api(mongodb("driver-core"))
}


@Suppress("unused")
fun DependencyHandler.mongodb(name: String, version: String = "3.10.1") =
	"org.mongodb:mongodb-$name:$version"
