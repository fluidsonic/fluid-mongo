package io.fluidsonic.mongo

import com.mongodb.reactivestreams.client.*
import kotlinx.coroutines.reactive.*
import org.reactivestreams.*


internal suspend fun Publisher<Success>.awaitSuccess() {
	awaitFirst()
}


internal suspend fun Publisher<Void>.awaitUnit() {
	awaitFirst()
}
