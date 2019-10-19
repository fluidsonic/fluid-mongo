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
import org.bson.conversions.*
import java.util.concurrent.*

/**
 * Iterable for find.
 *
 * @param <T> The type of the result.
 * @since 3.0
 */
interface FindIterable<out T> : MongoIterable<T> {

	/**
	 * The underlying object from the async driver.
	 */
	override val async: com.mongodb.async.client.FindIterable<out T>

	/**
	 * Sets the query filter to apply to the query.
	 *
	 * @param filter the filter, which may be null.
	 * @return this
	 * @mongodb.driver.manual reference/method/db.collection.find/ Filter
	 */
	fun filter(filter: Bson?): FindIterable<T>

	/**
	 * Sets the limit to apply.
	 *
	 * @param limit the limit, which may be null
	 * @return this
	 * @mongodb.driver.manual reference/method/cursor.limit/#cursor.limit Limit
	 */
	fun limit(limit: Int): FindIterable<T>

	/**
	 * Sets the number of documents to skip.
	 *
	 * @param skip the number of documents to skip
	 * @return this
	 * @mongodb.driver.manual reference/method/cursor.skip/#cursor.skip Skip
	 */
	fun skip(skip: Int): FindIterable<T>

	/**
	 * Sets the maximum execution time on the server for this operation.
	 *
	 * @param maxTime  the max time
	 * @param timeUnit the time unit, which may not be null
	 * @return this
	 * @mongodb.driver.manual reference/method/cursor.maxTimeMS/#cursor.maxTimeMS Max Time
	 */
	fun maxTime(maxTime: Long, timeUnit: TimeUnit): FindIterable<T>

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
	 * @since 3.2
	 */
	fun maxAwaitTime(maxAwaitTime: Long, timeUnit: TimeUnit): FindIterable<T>

	/**
	 * Sets a document describing the fields to return for all matching documents.
	 *
	 * @param projection the project document, which may be null.
	 * @return this
	 * @mongodb.driver.manual reference/method/db.collection.find/ Projection
	 */
	fun projection(projection: Bson?): FindIterable<T>

	/**
	 * Sets the sort criteria to apply to the query.
	 *
	 * @param sort the sort criteria, which may be null.
	 * @return this
	 * @mongodb.driver.manual reference/method/cursor.sort/ Sort
	 */
	fun sort(sort: Bson?): FindIterable<T>

	/**
	 * The server normally times out idle cursors after an inactivity period (10 minutes)
	 * to prevent excess memory use. Set this option to prevent that.
	 *
	 * @param noCursorTimeout true if cursor timeout is disabled
	 * @return this
	 */
	fun noCursorTimeout(noCursorTimeout: Boolean): FindIterable<T>

	/**
	 * Users should not set this under normal circumstances.
	 *
	 * @param oplogReplay if oplog replay is enabled
	 * @return this
	 */
	fun oplogReplay(oplogReplay: Boolean): FindIterable<T>

	/**
	 * Get partial results from a sharded cluster if one or more shards are unreachable (instead of throwing an error).
	 *
	 * @param partial if partial results for sharded clusters is enabled
	 * @return this
	 */
	fun partial(partial: Boolean): FindIterable<T>


	/**
	 * Sets the cursor type.
	 *
	 * @param cursorType the cursor type
	 * @return this
	 */
	fun cursorType(cursorType: CursorType): FindIterable<T>

	/**
	 * Sets the number of documents to return per batch.
	 *
	 * @param batchSize the batch size
	 * @return this
	 * @mongodb.driver.manual reference/method/cursor.batchSize/#cursor.batchSize Batch Size
	 */
	override fun batchSize(batchSize: Int): FindIterable<T>

	/**
	 * Sets the collation options
	 *
	 *
	 * A null value represents the server default.
	 * @param collation the collation options to use
	 * @return this
	 * @since 3.4
	 * @mongodb.server.release 3.4
	 */
	fun collation(collation: Collation?): FindIterable<T>

	/**
	 * Sets the comment to the query. A null value means no comment is set.
	 *
	 * @param comment the comment
	 * @return this
	 * @since 3.5
	 */
	fun comment(comment: String?): FindIterable<T>

	/**
	 * Sets the hint for which index to use. A null value means no hint is set.
	 *
	 * @param hint the hint
	 * @return this
	 * @since 3.5
	 */
	fun hint(hint: Bson?): FindIterable<T>

	/**
	 * Sets the exclusive upper bound for a specific index. A null value means no max is set.
	 *
	 * @param max the max
	 * @return this
	 * @since 3.5
	 */
	fun max(max: Bson?): FindIterable<T>

	/**
	 * Sets the minimum inclusive lower bound for a specific index. A null value means no max is set.
	 *
	 * @param min the min
	 * @return this
	 * @since 3.5
	 */
	fun min(min: Bson?): FindIterable<T>

	/**
	 * Sets the returnKey. If true the find operation will return only the index keys in the resulting documents.
	 *
	 * @param returnKey the returnKey
	 * @return this
	 * @since 3.5
	 */
	fun returnKey(returnKey: Boolean): FindIterable<T>

	/**
	 * Sets the showRecordId. Set to true to add a field `$recordId` to the returned documents.
	 *
	 * @param showRecordId the showRecordId
	 * @return this
	 * @since 3.5
	 */
	fun showRecordId(showRecordId: Boolean): FindIterable<T>
}
