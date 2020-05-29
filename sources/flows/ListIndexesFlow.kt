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

package io.fluidsonic.mongo

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.*

/**
 * Flow for ListIndexes.
 *
 * @param <TResult> The type of the result.
 * @since 3.0
 */
interface ListIndexesFlow<out TResult : Any> : Flow<TResult> {

	/**
	 * Sets the maximum execution time on the server for this operation.
	 *
	 * @param maxTime  the max time
	 * @param timeUnit the time unit, which may not be null
	 * @return this
	 * @mongodb.driver.manual reference/operator/meta/maxTimeMS/ Max Time
	 */
	fun maxTime(maxTime: Long, timeUnit: TimeUnit): ListIndexesFlow<TResult>

	/**
	 * Sets the number of documents to return per batch.
	 *
	 * Overrides the [org.reactivestreams.Subscription.request] value for setting the batch size, allowing for fine grained
	 * control over the underlying cursor.
	 *
	 * @param batchSize the batch size
	 * @return this
	 * @since 1.8
	 * @mongodb.driver.manual reference/method/cursor.batchSize/#cursor.batchSize Batch Size
	 */
	fun batchSize(batchSize: Int): ListIndexesFlow<TResult>

	/**
	 * Helper to return first result.
	 *
	 * @return the first result or null
	 * @since 1.8
	 */
	suspend fun firstOrNull(): TResult?


	companion object {

		fun <TResult : Any> empty(): ListIndexesFlow<TResult> =
			Empty
	}


	private object Empty : ListIndexesFlow<Nothing> {

		override fun maxTime(maxTime: Long, timeUnit: TimeUnit) =
			this


		override fun batchSize(batchSize: Int) =
			this


		override suspend fun firstOrNull() =
			null


		@InternalCoroutinesApi
		override suspend fun collect(collector: FlowCollector<Nothing>) =
			Unit
	}
}
