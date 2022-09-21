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

import com.mongodb.*
import com.mongodb.client.model.*
import java.util.concurrent.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.Flow
import org.bson.conversions.*

/**
 * Flow for find.
 *
 * @param <T> The type of the result.
 * @since 3.0
 */
public interface FindFlow<out TResult : Any> : Flow<TResult> {

	/**
	 * Sets the query filter to apply to the query.
	 *
	 * @param filter the filter, which may be null.
	 * @return this
	 * @mongodb.driver.manual reference/method/db.collection.find/ Filter
	 */
	public fun filter(filter: Bson?): FindFlow<TResult>

	/**
	 * Sets the limit to apply.
	 *
	 * @param limit the limit, which may be null
	 * @return this
	 * @mongodb.driver.manual reference/method/cursor.limit/#cursor.limit Limit
	 */
	public fun limit(limit: Int): FindFlow<TResult>

	/**
	 * Sets the number of documents to skip.
	 *
	 * @param skip the number of documents to skip
	 * @return this
	 * @mongodb.driver.manual reference/method/cursor.skip/#cursor.skip Skip
	 */
	public fun skip(skip: Int): FindFlow<TResult>

	/**
	 * Sets the maximum execution time on the server for this operation.
	 *
	 * @param maxTime  the max time
	 * @param timeUnit the time unit, which may not be null
	 * @return this
	 * @mongodb.driver.manual reference/method/cursor.maxTimeMS/#cursor.maxTimeMS Max Time
	 */
	public fun maxTime(maxTime: Long, timeUnit: TimeUnit): FindFlow<TResult>

	/**
	 * The maximum amount of time for the server to wait on new documents to satisfy a tailable cursor
	 * query. This only applies to a TAILABLE_AWAIT cursor. When the cursor is not a TAILABLE_AWAIT cursor,
	 * this option is ignored.
	 *
	 * On servers &gt;= 3.2, this option will be specified on the getMore command as "maxTimeMS". The default
	 * is no value: no "maxTimeMS" is sent to the server with the getMore command.
	 *
	 * On servers &lt; 3.2, this option is ignored, and indicates that the driver should respect the server's default value
	 *
	 * A zero value will be ignored.
	 *
	 * @param maxAwaitTime  the max await time
	 * @param timeUnit the time unit to return the result in
	 * @return the maximum await execution time in the given time unit
	 * @mongodb.driver.manual reference/method/cursor.maxTimeMS/#cursor.maxTimeMS Max Time
	 * @since 1.2
	 */
	public fun maxAwaitTime(maxAwaitTime: Long, timeUnit: TimeUnit): FindFlow<TResult>

	/**
	 * Sets a document describing the fields to return for all matching documents.
	 *
	 * @param projection the project document, which may be null.
	 * @return this
	 * @mongodb.driver.manual reference/method/db.collection.find/ Projection
	 */
	public fun projection(projection: Bson?): FindFlow<TResult>

	/**
	 * Sets the sort criteria to apply to the query.
	 *
	 * @param sort the sort criteria, which may be null.
	 * @return this
	 * @mongodb.driver.manual reference/method/cursor.sort/ Sort
	 */
	public fun sort(sort: Bson?): FindFlow<TResult>

	/**
	 * The server normally times out idle cursors after an inactivity period (10 minutes)
	 * to prevent excess memory use. Set this option to prevent that.
	 *
	 * @param noCursorTimeout true if cursor timeout is disabled
	 * @return this
	 */
	public fun noCursorTimeout(noCursorTimeout: Boolean): FindFlow<TResult>

	/**
	 * Users should not set this under normal circumstances.
	 *
	 * @param oplogReplay if oplog replay is enabled
	 * @return this
	 */
	public fun oplogReplay(oplogReplay: Boolean): FindFlow<TResult>

	/**
	 * Get partial results from a sharded cluster if one or more shards are unreachable (instead of throwing an error).
	 *
	 * @param partial if partial results for sharded clusters is enabled
	 * @return this
	 */
	public fun partial(partial: Boolean): FindFlow<TResult>

	/**
	 * Sets the cursor type.
	 *
	 * @param cursorType the cursor type
	 * @return this
	 */
	public fun cursorType(cursorType: CursorType): FindFlow<TResult>

	/**
	 * Sets the collation options
	 *
	 * A null value represents the server default.
	 * @param collation the collation options to use
	 * @return this
	 * @since 1.3
	 * @mongodb.server.release 3.4
	 */
	public fun collation(collation: Collation?): FindFlow<TResult>

	/**
	 * Sets the comment to the query. A null value means no comment is set.
	 *
	 * @param comment the comment
	 * @return this
	 * @since 1.6
	 */
	public fun comment(comment: String?): FindFlow<TResult>

	/**
	 * Sets the hint for which index to use. A null value means no hint is set.
	 *
	 * @param hint the hint
	 * @return this
	 * @since 1.6
	 */
	public fun hint(hint: Bson?): FindFlow<TResult>

	/**
	 * Sets the exclusive upper bound for a specific index. A null value means no max is set.
	 *
	 * @param max the max
	 * @return this
	 * @since 1.6
	 */
	public fun max(max: Bson?): FindFlow<TResult>

	/**
	 * Sets the minimum inclusive lower bound for a specific index. A null value means no max is set.
	 *
	 * @param min the min
	 * @return this
	 * @since 1.6
	 */
	public fun min(min: Bson?): FindFlow<TResult>

	/**
	 * Sets the returnKey. If true the find operation will return only the index keys in the resulting documents.
	 *
	 * @param returnKey the returnKey
	 * @return this
	 * @since 1.6
	 */
	public fun returnKey(returnKey: Boolean): FindFlow<TResult>

	/**
	 * Sets the showRecordId. Set to true to add a field `$recordId` to the returned documents.
	 *
	 * @param showRecordId the showRecordId
	 * @return this
	 * @since 1.6
	 */
	public fun showRecordId(showRecordId: Boolean): FindFlow<TResult>

	/**
	 * Sets the number of documents to return per batch.
	 *
	 *
	 * Overrides the [org.reactivestreams.Subscription.request] value for setting the batch size, allowing for fine grained
	 * control over the underlying cursor.
	 *
	 * @param batchSize the batch size
	 * @return this
	 * @since 1.8
	 * @mongodb.driver.manual reference/method/cursor.batchSize/#cursor.batchSize Batch Size
	 */
	public fun batchSize(batchSize: Int): FindFlow<TResult>

	/**
	 * Helper to return first result.
	 *
	 * @return the first result or null
	 * @since 1.8
	 */
	public suspend fun firstOrNull(): TResult?


	public companion object {

		public fun <TResult : Any> empty(): FindFlow<TResult> =
			Empty
	}


	private object Empty : FindFlow<Nothing> {

		override fun filter(filter: Bson?) =
			this


		override fun limit(limit: Int) =
			this


		override fun skip(skip: Int) =
			this


		override fun maxTime(maxTime: Long, timeUnit: TimeUnit) =
			this


		override fun maxAwaitTime(maxAwaitTime: Long, timeUnit: TimeUnit) =
			this


		override fun projection(projection: Bson?) =
			this


		override fun sort(sort: Bson?) =
			this


		override fun noCursorTimeout(noCursorTimeout: Boolean) =
			this


		override fun oplogReplay(oplogReplay: Boolean) =
			this


		override fun partial(partial: Boolean) =
			this


		override fun cursorType(cursorType: CursorType) =
			this


		override fun collation(collation: Collation?) =
			this


		override fun comment(comment: String?) =
			this


		override fun hint(hint: Bson?) =
			this


		override fun max(max: Bson?) =
			this


		override fun min(min: Bson?) =
			this


		override fun returnKey(returnKey: Boolean) =
			this


		override fun showRecordId(showRecordId: Boolean) =
			this


		override fun batchSize(batchSize: Int) =
			this


		override suspend fun firstOrNull() =
			null


		override suspend fun collect(collector: FlowCollector<Nothing>) =
			Unit
	}
}
