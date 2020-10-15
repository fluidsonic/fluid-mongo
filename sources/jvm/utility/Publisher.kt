package io.fluidsonic.mongo

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.*
import org.reactivestreams.*


internal fun <Value> Publisher<out Value>.ioAsFlow(): Flow<Value> =
	asFlow().flowOn(Dispatchers.IO)


internal suspend fun Publisher<out Void>.ioAwaitCompletion() {
	ioAwaitFirstOrNull()?.let {
		error("Expected no result but received one: $it")
	}
}


internal suspend fun <Value> Publisher<out Value>.ioAwaitFirst(): Value =
	withContext(Dispatchers.IO) {
		awaitFirst()
	}


internal suspend fun <Value> Publisher<out Value>.ioAwaitFirstOrNull(): Value? =
	withContext(Dispatchers.IO) {
		awaitFirstOrNull()
	}
