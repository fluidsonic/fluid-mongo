package io.fluidsonic.mongo

import kotlinx.coroutines.reactive.*
import org.reactivestreams.*


internal suspend fun Publisher<out Void>.awaitUnit() {
	awaitFirst()
}
