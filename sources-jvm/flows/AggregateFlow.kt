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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.Flow
import org.bson.conversions.*
import java.util.concurrent.*

/**
 * Flow for aggregate.
 *
 * @param <TResult> The type of the result.
 * @since 3.0
 */
public interface AggregateFlow<out TResult : Any> : Flow<TResult> {

	/**
	 * Enables writing to temporary files. A null value indicates that it's unspecified.
	 *
	 * @param allowDiskUse true if writing to temporary files is enabled
	 * @return this
	 * @mongodb.driver.manual reference/command/aggregate/ Aggregation
	 * @mongodb.server.release 2.6
	 */
	public fun allowDiskUse(allowDiskUse: Boolean?): AggregateFlow<TResult>

	/**
	 * Sets the maximum execution time on the server for this operation.
	 *
	 * @param maxTime  the max time
	 * @param timeUnit the time unit, which may not be null
	 * @return this
	 * @mongodb.driver.manual reference/method/cursor.maxTimeMS/#cursor.maxTimeMS Max Time
	 */
	public fun maxTime(maxTime: Long, timeUnit: TimeUnit): AggregateFlow<TResult>

	/**
	 * The maximum amount of time for the server to wait on new documents to satisfy a `$changeStream` aggregation.
	 *
	 * A zero value will be ignored.
	 *
	 * @param maxAwaitTime  the max await time
	 * @param timeUnit the time unit to return the result in
	 * @return the maximum await execution time in the given time unit
	 * @mongodb.server.release 3.6
	 * @since 1.6
	 */
	public fun maxAwaitTime(maxAwaitTime: Long, timeUnit: TimeUnit): AggregateFlow<TResult>

	/**
	 * Sets the bypass document level validation flag.
	 *
	 * Note: This only applies when an $out stage is specified.
	 *
	 * @param bypassDocumentValidation If true, allows the write to opt-out of document level validation.
	 * @return this
	 * @since 1.2
	 * @mongodb.driver.manual reference/command/aggregate/ Aggregation
	 * @mongodb.server.release 3.2
	 */
	public fun bypassDocumentValidation(bypassDocumentValidation: Boolean?): AggregateFlow<TResult>

	/**
	 * Aggregates documents according to the specified aggregation pipeline, which must end with a $out stage.
	 *
	 * @return a publisher with a single element indicating when the operation has completed
	 * @mongodb.driver.manual aggregation/ Aggregation
	 */
	public suspend fun toCollection()

	/**
	 * Sets the collation options
	 *
	 * A null value represents the server default.
	 * @param collation the collation options to use
	 * @return this
	 * @since 1.3
	 * @mongodb.server.release 3.4
	 */
	public fun collation(collation: Collation?): AggregateFlow<TResult>

	/**
	 * Sets the comment to the aggregation. A null value means no comment is set.
	 *
	 * @param comment the comment
	 * @return this
	 * @mongodb.server.release 3.6
	 * @since 1.7
	 */
	public fun comment(comment: String?): AggregateFlow<TResult>

	/**
	 * Sets the hint for which index to use. A null value means no hint is set.
	 *
	 * @param hint the hint
	 * @return this
	 * @mongodb.server.release 3.6
	 * @since 1.7
	 */
	public fun hint(hint: Bson?): AggregateFlow<TResult>

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
	public fun batchSize(batchSize: Int): AggregateFlow<TResult>


	/**
	 * Helper to return first result.
	 *
	 * @return the first result or null
	 * @since 1.8
	 */
	public suspend fun firstOrNull(): TResult?


	public companion object {

		public fun <TResult : Any> empty(): AggregateFlow<TResult> =
			Empty
	}


	private object Empty : AggregateFlow<Nothing> {

		override fun allowDiskUse(allowDiskUse: Boolean?) =
			this


		override fun maxTime(maxTime: Long, timeUnit: TimeUnit) =
			this


		override fun maxAwaitTime(maxAwaitTime: Long, timeUnit: TimeUnit) =
			this


		override fun bypassDocumentValidation(bypassDocumentValidation: Boolean?) =
			this


		override suspend fun toCollection() =
			Unit


		override fun collation(collation: Collation?) =
			this


		override fun comment(comment: String?) =
			this


		override fun hint(hint: Bson?) =
			this


		override fun batchSize(batchSize: Int) =
			this


		override suspend fun firstOrNull() =
			null


		override suspend fun collect(collector: FlowCollector<Nothing>) =
			Unit
	}
}
