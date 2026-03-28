package io.fluidsonic.mongo

import kotlinx.coroutines.*
import org.reactivestreams.*
import kotlin.test.*


class PublisherTest {

	@Test
	fun awaitCompletion_completesNormally_whenPublisherEmitsNoElements() = runBlocking {
		val publisher = Publisher<Void> { subscriber ->
			subscriber.onSubscribe(object : Subscription {
				override fun request(n: Long) {
					subscriber.onComplete()
				}

				override fun cancel() {}
			})
		}

		publisher.awaitCompletion()
	}

	@Test
	fun awaitCompletion_throwsError_whenPublisherFails() = runBlocking {
		val expectedException = RuntimeException("test error")

		val publisher = Publisher<Void> { subscriber ->
			subscriber.onSubscribe(object : Subscription {
				override fun request(n: Long) {
					subscriber.onError(expectedException)
				}

				override fun cancel() {}
			})
		}

		val thrown = assertFailsWith<RuntimeException> {
			publisher.awaitCompletion()
		}
		assertEquals(actual = thrown.message, expected = "test error")
	}
}
