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

import kotlinx.coroutines.flow.Flow
import org.bson.conversions.*
import java.util.concurrent.*

/**
 * Flow for ListDatabases.
 *
 * @param <T> The type of the result.
 */
interface ListDatabasesFlow<out TResult : Any> : Flow<TResult> {

	/**
	 * Sets the maximum execution time on the server for this operation.
	 *
	 * @param maxTime  the max time
	 * @param timeUnit the time unit, which may not be null
	 * @return this
	 * @mongodb.driver.manual reference/operator/meta/maxTimeMS/ Max Time
	 */
	fun maxTime(maxTime: Long, timeUnit: TimeUnit): ListDatabasesFlow<TResult>

	/**
	 * Sets the query filter to apply to the returned database names.
	 *
	 * @param filter the filter, which may be null.
	 * @return this
	 * @mongodb.server.release 3.4.2
	 * @since 1.7
	 */
	fun filter(filter: Bson?): ListDatabasesFlow<TResult>

	/**
	 * Sets the nameOnly flag that indicates whether the command should return just the database names or return the database names and
	 * size information.
	 *
	 * @param nameOnly the nameOnly flag, which may be null
	 * @return this
	 * @mongodb.server.release 3.4.3
	 * @since 1.7
	 */
	fun nameOnly(nameOnly: Boolean?): ListDatabasesFlow<TResult>

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
	fun batchSize(batchSize: Int): ListDatabasesFlow<TResult>

	/**
	 * Helper to return first result.
	 *
	 * @return the first result or null
	 * @since 1.8
	 */
	suspend fun firstOrNull(): TResult?
}
