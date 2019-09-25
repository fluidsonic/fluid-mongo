/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.fluidsonic.fluid.mongo

import com.mongodb.async.AsyncBatchCursor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.channels.toMutableList
import kotlinx.coroutines.channels.toMutableSet

/**
 * Operations that allow asynchronous iteration over a collection view.
 *
 * @param <TResult> the result type
 * @since 3.0
 */
interface MongoIterable<out TResult> {

	/**
	 * The underlying object from the async driver.
	 */
	val async: com.mongodb.async.client.MongoIterable<out TResult>

	/**
	 * Gets the number of documents to return per batch or null if not set.
	 *
	 * @return the batch size, which may be null
	 * @mongodb.driver.manual reference/method/cursor.batchSize/#cursor.batchSize Batch Size
	 * @since 3.7
	 */
	val batchSize: Int?

	/**
	 * Sets the number of documents to return per batch.
	 *
	 * @param batchSize the batch size
	 * @return this
	 * @mongodb.driver.manual reference/method/cursor.batchSize/#cursor.batchSize Batch Size
	 */
	fun batchSize(batchSize: Int): MongoIterable<TResult>

	/**
	 * Provide the underlying [com.mongodb.async.AsyncBatchCursor] allowing fine grained control of the cursor.
	 */
	suspend fun batchCursor(): AsyncBatchCursor<out TResult>
}


/**
 * Helper to return the first item in the iterator or null.
 */
suspend fun <TResult> MongoIterable<TResult>.firstOrNull(): TResult? {
	@Suppress("UNCHECKED_CAST") // I'll never fully understand variance…
	val async = async as com.mongodb.async.client.MongoIterable<TResult>

	return withCallback(async::first)
}


fun <TResult> MongoIterable<TResult>.produce(scope: CoroutineScope = GlobalScope) =
	scope.produce {
		@Suppress("UNCHECKED_CAST") // I'll never fully understand variance…
		val async = async as com.mongodb.async.client.MongoIterable<TResult>

		// TODO calling batchCursor here may cause a race condition if the async iterable is modified before this coroutine is started
		val cursor = withCallback<AsyncBatchCursor<TResult>?>(async::batchCursor) ?: return@produce
		while (true) {
			val elements = withCallback<List<TResult>?>(cursor::next) ?: break
			for (element in elements)
				send(element)
		}
	}


suspend fun <TResult> MongoIterable<TResult>.toList(): List<TResult> =
	toMutableList()


suspend fun <TResult> MongoIterable<TResult>.toMutableList(): MutableList<TResult> =
	produce().toMutableList()


suspend fun <TResult> MongoIterable<TResult>.toSet(): Set<TResult> =
	toMutableSet()


suspend fun <TResult> MongoIterable<TResult>.toMutableSet(): MutableSet<TResult> =
	produce().toMutableSet()
