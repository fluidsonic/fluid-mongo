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
 * Iterable for map reduce.
 *
 * @param <TResult> The type of the result.
 * @since 3.0
 */
interface MapReduceIterable<out TResult> : MongoIterable<TResult> {

	/**
	 * The underlying object from the async driver.
	 */
	override val async: com.mongodb.async.client.MapReduceIterable<out TResult>

	/**
	 * Sets the collectionName for the output of the MapReduce
	 *
	 * The default action is replace the collection if it exists, to change this use [.action].
	 *
	 * @param collectionName the name of the collection that you want the map-reduce operation to write its output.
	 * @return this
	 */
	fun collectionName(collectionName: String): MapReduceIterable<TResult>

	/**
	 * Sets the JavaScript function that follows the reduce method and modifies the output.
	 *
	 * @param finalizeFunction the JavaScript function that follows the reduce method and modifies the output.
	 * @return this
	 * @mongodb.driver.manual reference/command/mapReduce/#mapreduce-finalize-cmd Requirements for the finalize Function
	 */
	fun finalizeFunction(finalizeFunction: String?): MapReduceIterable<TResult>

	/**
	 * Sets the global variables that are accessible in the map, reduce and finalize functions.
	 *
	 * @param scope the global variables that are accessible in the map, reduce and finalize functions.
	 * @return this
	 * @mongodb.driver.manual reference/command/mapReduce mapReduce
	 */
	fun scope(scope: Bson?): MapReduceIterable<TResult>

	/**
	 * Sets the sort criteria to apply to the query.
	 *
	 * @param sort the sort criteria, which may be null.
	 * @return this
	 * @mongodb.driver.manual reference/method/cursor.sort/ Sort
	 */
	fun sort(sort: Bson?): MapReduceIterable<TResult>

	/**
	 * Sets the query filter to apply to the query.
	 *
	 * @param filter the filter to apply to the query.
	 * @return this
	 * @mongodb.driver.manual reference/method/db.collection.find/ Filter
	 */
	fun filter(filter: Bson?): MapReduceIterable<TResult>

	/**
	 * Sets the limit to apply.
	 *
	 * @param limit the limit
	 * @return this
	 * @mongodb.driver.manual reference/method/cursor.limit/#cursor.limit Limit
	 */
	fun limit(limit: Int): MapReduceIterable<TResult>

	/**
	 * Sets the flag that specifies whether to convert intermediate data into BSON format between the execution of the map and reduce
	 * functions. Defaults to false.
	 *
	 * @param jsMode the flag that specifies whether to convert intermediate data into BSON format between the execution of the map and
	 * reduce functions
	 * @return jsMode
	 * @mongodb.driver.manual reference/command/mapReduce mapReduce
	 */
	fun jsMode(jsMode: Boolean): MapReduceIterable<TResult>

	/**
	 * Sets whether to include the timing information in the result information.
	 *
	 * @param verbose whether to include the timing information in the result information.
	 * @return this
	 */
	fun verbose(verbose: Boolean): MapReduceIterable<TResult>

	/**
	 * Sets the maximum execution time on the server for this operation.
	 *
	 * @param maxTime  the max time
	 * @param timeUnit the time unit, which may not be null
	 * @return this
	 * @mongodb.driver.manual reference/method/cursor.maxTimeMS/#cursor.maxTimeMS Max Time
	 */
	fun maxTime(maxTime: Long, timeUnit: TimeUnit): MapReduceIterable<TResult>

	/**
	 * Specify the `MapReduceAction` to be used when writing to a collection.
	 *
	 * @param action an [com.mongodb.client.model.MapReduceAction] to perform on the collection
	 * @return this
	 */
	fun action(action: MapReduceAction): MapReduceIterable<TResult>

	/**
	 * Sets the name of the database to output into.
	 *
	 * @param databaseName the name of the database to output into.
	 * @return this
	 * @mongodb.driver.manual reference/command/mapReduce/#output-to-a-collection-with-an-action output with an action
	 */
	fun databaseName(databaseName: String?): MapReduceIterable<TResult>

	/**
	 * Sets if the output database is sharded
	 *
	 * @param sharded if the output database is sharded
	 * @return this
	 * @mongodb.driver.manual reference/command/mapReduce/#output-to-a-collection-with-an-action output with an action
	 */
	fun sharded(sharded: Boolean): MapReduceIterable<TResult>

	/**
	 * Sets if the post-processing step will prevent MongoDB from locking the database.
	 *
	 * Valid only with the `MapReduceAction.MERGE` or `MapReduceAction.REDUCE` actions.
	 *
	 * @param nonAtomic if the post-processing step will prevent MongoDB from locking the database.
	 * @return this
	 * @mongodb.driver.manual reference/command/mapReduce/#output-to-a-collection-with-an-action output with an action
	 */
	fun nonAtomic(nonAtomic: Boolean): MapReduceIterable<TResult>

	/**
	 * Sets the number of documents to return per batch.
	 *
	 * @param batchSize the batch size
	 * @return this
	 * @mongodb.driver.manual reference/method/cursor.batchSize/#cursor.batchSize Batch Size
	 */
	override fun batchSize(batchSize: Int): MapReduceIterable<TResult>

	/**
	 * Sets the bypass document level validation flag.
	 *
	 * Note: This only applies when an $out stage is specified.
	 *
	 * @param bypassDocumentValidation If true, allows the write to opt-out of document level validation.
	 * @return this
	 * @since 3.2
	 * @mongodb.driver.manual reference/command/mapReduce mapReduce
	 * @mongodb.server.release 3.2
	 */
	fun bypassDocumentValidation(bypassDocumentValidation: Boolean?): MapReduceIterable<TResult>

	/**
	 * Sets the collation options
	 *
	 * A null value represents the server default.
	 * @param collation the collation options to use
	 * @return this
	 * @since 3.4
	 * @mongodb.server.release 3.4
	 */
	fun collation(collation: Collation?): MapReduceIterable<TResult>
}
