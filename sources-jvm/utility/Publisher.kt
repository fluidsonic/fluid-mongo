package io.fluidsonic.mongo

import kotlinx.coroutines.reactive.*
import org.reactivestreams.*


internal suspend fun Publisher<out Void>.awaitCompletion() {
	awaitFirstOrNull()?.let {
		error("Expected no result but received one: $it")
	}
}
