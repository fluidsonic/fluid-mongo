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
import org.bson.conversions.*
import java.util.concurrent.*

/**
 * Iterable for aggregate.
 *
 * @param <TResult> The type of the result.
 * @since 3.0
 */
interface AggregateIterable<out TResult> : MongoIterable<TResult> {

	/**
	 * The underlying object from the async driver.
	 */
	override val async: com.mongodb.async.client.AggregateIterable<out TResult>

	/**
	 * Enables writing to temporary files. A null value indicates that it's unspecified.
	 *
	 * @param allowDiskUse true if writing to temporary files is enabled
	 * @return this
	 * @mongodb.driver.manual reference/command/aggregate/ Aggregation
	 * @mongodb.server.release 2.6
	 */
	fun allowDiskUse(allowDiskUse: Boolean?): AggregateIterable<TResult>

	/**
	 * Sets the maximum execution time on the server for this operation.
	 *
	 * @param maxTime  the max time
	 * @param timeUnit the time unit, which may not be null
	 * @return this
	 * @mongodb.driver.manual reference/method/cursor.maxTimeMS/#cursor.maxTimeMS Max Time
	 */
	fun maxTime(maxTime: Long, timeUnit: TimeUnit): AggregateIterable<TResult>

	/**
	 * The maximum amount of time for the server to wait on new documents to satisfy a `$changeStream` aggregation.
	 *
	 * A zero value will be ignored.
	 *
	 * @param maxAwaitTime  the max await time
	 * @param timeUnit the time unit to return the result in
	 * @return the maximum await execution time in the given time unit
	 * @mongodb.server.release 3.6
	 * @since 3.6
	 */
	fun maxAwaitTime(maxAwaitTime: Long, timeUnit: TimeUnit): AggregateIterable<TResult>

	/**
	 * Sets the number of documents to return per batch.
	 *
	 * @param batchSize the batch size
	 * @return this
	 * @mongodb.driver.manual reference/method/cursor.batchSize/#cursor.batchSize Batch Size
	 */
	override fun batchSize(batchSize: Int): AggregateIterable<TResult>

	/**
	 * Sets the bypass document level validation flag.
	 *
	 * Note: This only applies when an $out stage is specified.
	 *
	 * @param bypassDocumentValidation If true, allows the write to opt-out of document level validation.
	 * @return this
	 * @since 3.2
	 * @mongodb.driver.manual reference/command/aggregate/ Aggregation
	 * @mongodb.server.release 3.2
	 */
	fun bypassDocumentValidation(bypassDocumentValidation: Boolean?): AggregateIterable<TResult>

	/**
	 * Sets the collation options
	 *
	 * A null value represents the server default.
	 * @param collation the collation options to use
	 * @return this
	 * @since 3.4
	 * @mongodb.server.release 3.4
	 */
	fun collation(collation: Collation?): AggregateIterable<TResult>

	/**
	 * Sets the comment to the aggregation. A null value means no comment is set.
	 *
	 * @param comment the comment
	 * @return this
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun comment(comment: String?): AggregateIterable<TResult>

	/**
	 * Sets the hint for which index to use. A null value means no hint is set.
	 *
	 * @param hint the hint
	 * @return this
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun hint(hint: Bson?): AggregateIterable<TResult>
}
