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

import com.mongodb.client.model.*
import java.util.concurrent.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.Flow
import org.bson.conversions.*

/**
 * Flow for distinct.
 *
 * @param <TResult> The type of the result.
 * @since 3.0
 */
public interface DistinctFlow<out TResult : Any> : Flow<TResult> {

	/**
	 * Sets the query filter to apply to the query.
	 *
	 * @param filter the filter, which may be null.
	 * @return this
	 * @mongodb.driver.manual reference/method/db.collection.find/ Filter
	 */
	public fun filter(filter: Bson?): DistinctFlow<TResult>

	/**
	 * Sets the maximum execution time on the server for this operation.
	 *
	 * @param maxTime  the max time
	 * @param timeUnit the time unit, which may not be null
	 * @return this
	 */
	public fun maxTime(maxTime: Long, timeUnit: TimeUnit): DistinctFlow<TResult>

	/**
	 * Sets the collation options
	 *
	 * A null value represents the server default.
	 * @param collation the collation options to use
	 * @return this
	 * @since 1.3
	 * @mongodb.server.release 3.4
	 */
	public fun collation(collation: Collation?): DistinctFlow<TResult>

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
	public fun batchSize(batchSize: Int): DistinctFlow<TResult>

	/**
	 * Helper to return first result.
	 *
	 * @return the first result or null
	 * @since 1.8
	 */
	public suspend fun firstOrNull(): TResult?


	public companion object {

		public fun <TResult : Any> empty(): DistinctFlow<TResult> =
			Empty
	}


	private object Empty : DistinctFlow<Nothing> {

		override fun filter(filter: Bson?) =
			this


		override fun maxTime(maxTime: Long, timeUnit: TimeUnit) =
			this


		override fun collation(collation: Collation?) =
			this


		override fun batchSize(batchSize: Int) =
			this


		override suspend fun firstOrNull() =
			null


		override suspend fun collect(collector: FlowCollector<Nothing>) =
			Unit
	}
}
