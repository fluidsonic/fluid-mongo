import com.github.fluidsonic.fluid.library.*

plugins {
	id("com.github.fluidsonic.fluid-library") version "0.9.29"
}

fluidJvmLibrary {
	name = "fluid-mongo"
	version = "0.9.9"
}

fluidJvmLibraryVariant {
	description = "Kotlin coroutine support for MongoDB built on top of the official MongoDB Asynchronous Java Driver"
	jdk = JvmTarget.jdk8
}

dependencies {
	implementation(mongodb("driver-async"))

	api(kotlinx("coroutines-core", "1.3.1"))
	api(mongodb("driver-core"))
}


@Suppress("unused")
fun DependencyHandler.mongodb(name: String, version: String = "3.11.0") =
	"org.mongodb:mongodb-$name:$version"
