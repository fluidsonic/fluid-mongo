import com.github.fluidsonic.fluid.library.*

plugins {
	id("com.github.fluidsonic.fluid-library") version "0.9.21"
}

fluidJvmLibrary {
	name = "fluid-mongo"
	version = "0.9.7"
}

fluidJvmLibraryVariant {
	description = "Kotlin coroutine support for MongoDB built on top of the official MongoDB Asynchronous Java Driver"
	jdk = JvmTarget.jdk8
}

dependencies {
	implementation(mongodb("driver-async"))

	api(kotlinx("coroutines-core", "1.2.2"))
	api(mongodb("driver-core"))
}


@Suppress("unused")
fun DependencyHandler.mongodb(name: String, version: String = "3.10.2") =
	"org.mongodb:mongodb-$name:$version"
