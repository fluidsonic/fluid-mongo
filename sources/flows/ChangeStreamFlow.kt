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
import com.mongodb.client.model.changestream.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.Flow
import org.bson.*
import java.util.concurrent.*
import kotlin.reflect.*

/**
 * Flow for change streams.
 *
 * @param <TResult> The type of the result.
 * @mongodb.server.release 3.6
 * @since 3.6
 */
interface ChangeStreamFlow<out TResult : Any> : Flow<ChangeStreamDocument<out TResult>> {

	/**
	 * Sets the fullDocument value.
	 *
	 * @param fullDocument the fullDocument
	 * @return this
	 */
	fun fullDocument(fullDocument: FullDocument): ChangeStreamFlow<TResult>

	/**
	 * Sets the logical starting point for the new change stream.
	 *
	 * @param resumeToken the resume token
	 * @return this
	 */
	fun resumeAfter(resumeToken: BsonDocument): ChangeStreamFlow<TResult>

	/**
	 * The change stream will only provide changes that occurred after the specified timestamp.
	 *
	 *
	 * Any command run against the server will return an operation time that can be used here.
	 *
	 * The default value is an operation time obtained from the server before the change stream was created.
	 *
	 * @param startAtOperationTime the start at operation time
	 * @since 1.9
	 * @return this
	 * @mongodb.server.release 4.0
	 * @mongodb.driver.manual reference/method/db.runCommand/
	 */
	fun startAtOperationTime(startAtOperationTime: BsonTimestamp): ChangeStreamFlow<TResult>

	/**
	 * Similar to `resumeAfter`, this option takes a resume token and starts a
	 * new change stream returning the first notification after the token.
	 *
	 *
	 * This will allow users to watch collections that have been dropped and recreated
	 * or newly renamed collections without missing any notifications.
	 *
	 *
	 * Note: The server will report an error if both `startAfter` and `resumeAfter` are specified.
	 *
	 * @param startAfter the startAfter resumeToken
	 * @return this
	 * @since 1.12
	 * @mongodb.server.release 4.2
	 * @mongodb.driver.manual changeStreams/#change-stream-start-after
	 */
	fun startAfter(startAfter: BsonDocument): ChangeStreamFlow<TResult>

	/**
	 * Sets the maximum await execution time on the server for this operation.
	 *
	 * @param maxAwaitTime  the max await time.  A zero value will be ignored, and indicates that the driver should respect the server's
	 * default value
	 * @param timeUnit the time unit, which may not be null
	 * @return this
	 */
	fun maxAwaitTime(maxAwaitTime: Long, timeUnit: TimeUnit): ChangeStreamFlow<TResult>

	/**
	 * Sets the collation options
	 *
	 *
	 * A null value represents the server default.
	 * @param collation the collation options to use
	 * @return this
	 */
	fun collation(collation: Collation?): ChangeStreamFlow<TResult>

	/**
	 * Returns a `Flow` containing the results of the change stream based on the document class provided.
	 *
	 * @param clazz the class to use for the raw result.
	 * @param <TDocument> the result type
	 * @return the new Flow
	 */
	fun <TDocument : Any> withDocumentClass(clazz: KClass<out TDocument>): Flow<TDocument>

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
	fun batchSize(batchSize: Int): ChangeStreamFlow<TResult>

	/**
	 * Helper to return first result.
	 *
	 * @return the first result or null
	 * @since 1.8
	 */
	suspend fun firstOrNull(): ChangeStreamDocument<out TResult>?


	companion object {

		fun <TResult : Any> empty(): ChangeStreamFlow<TResult> =
			Empty
	}


	private object Empty : ChangeStreamFlow<Nothing> {

		override fun fullDocument(fullDocument: FullDocument) =
			this


		override fun resumeAfter(resumeToken: BsonDocument) =
			this


		override fun startAtOperationTime(startAtOperationTime: BsonTimestamp) =
			this


		override fun startAfter(startAfter: BsonDocument) =
			this


		override fun maxAwaitTime(maxAwaitTime: Long, timeUnit: TimeUnit) =
			this


		override fun collation(collation: Collation?) =
			this


		override fun <TDocument : Any> withDocumentClass(clazz: KClass<out TDocument>) =
			emptyFlow<TDocument>()


		override fun batchSize(batchSize: Int) =
			this


		override suspend fun firstOrNull(): Nothing? =
			null


		@InternalCoroutinesApi
		override suspend fun collect(collector: FlowCollector<ChangeStreamDocument<out Nothing>>) =
			Unit
	}
}
