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
import com.mongodb.annotations.*
import com.mongodb.bulk.*
import com.mongodb.client.model.*
import com.mongodb.client.result.*
import kotlinx.coroutines.flow.*
import org.bson.*
import org.bson.codecs.configuration.*
import org.bson.conversions.*

/**
 * The MongoCollection interface.
 *
 * Note: Additions to this interface will not be considered to break binary compatibility.
 *
 * @param <TDocument> The type that this collection will encode documents from and decode documents to.
 * @since 3.0
 */
@ThreadSafe
interface MongoCollection<TDocument : Any> {

	/**
	 * Gets the namespace of this collection.
	 *
	 * @return the namespace
	 */
	val namespace: MongoNamespace

	/**
	 * Get the class of documents stored in this collection.
	 *
	 * @return the class
	 */
	val documentClass: Class<TDocument>

	/**
	 * Get the codec registry for the MongoCollection.
	 *
	 * @return the [org.bson.codecs.configuration.CodecRegistry]
	 */
	val codecRegistry: CodecRegistry

	/**
	 * Get the read preference for the MongoCollection.
	 *
	 * @return the [com.mongodb.ReadPreference]
	 */
	val readPreference: ReadPreference

	/**
	 * Get the write concern for the MongoCollection.
	 *
	 * @return the [com.mongodb.WriteConcern]
	 */
	val writeConcern: WriteConcern

	/**
	 * Get the read concern for the MongoCollection.
	 *
	 * @return the [com.mongodb.ReadConcern]
	 * @since 3.2
	 * @mongodb.server.release 3.2
	 * @mongodb.driver.manual reference/readConcern/ Read Concern
	 */
	val readConcern: ReadConcern

	/**
	 * Create a new MongoCollection instance with a different default class to cast any documents returned from the database into..
	 *
	 * @param newDocumentClass the default class to cast any documents returned from the database into.
	 * @param <NewTDocument>   the type that the new collection will encode documents from and decode documents to
	 * @return a new MongoCollection instance with the different default class
	 */
	fun <NewTDocument : Any> withDocumentClass(newDocumentClass: Class<NewTDocument>): MongoCollection<NewTDocument>

	/**
	 * Create a new MongoCollection instance with a different codec registry.
	 *
	 * @param codecRegistry the new [org.bson.codecs.configuration.CodecRegistry] for the collection
	 * @return a new MongoCollection instance with the different codec registry
	 */
	fun withCodecRegistry(codecRegistry: CodecRegistry): MongoCollection<TDocument>

	/**
	 * Create a new MongoCollection instance with a different read preference.
	 *
	 * @param readPreference the new [com.mongodb.ReadPreference] for the collection
	 * @return a new MongoCollection instance with the different readPreference
	 */
	fun withReadPreference(readPreference: ReadPreference): MongoCollection<TDocument>

	/**
	 * Create a new MongoCollection instance with a different write concern.
	 *
	 * @param writeConcern the new [com.mongodb.WriteConcern] for the collection
	 * @return a new MongoCollection instance with the different writeConcern
	 */
	fun withWriteConcern(writeConcern: WriteConcern): MongoCollection<TDocument>

	/**
	 * Create a new MongoCollection instance with a different read concern.
	 *
	 * @param readConcern the new [ReadConcern] for the collection
	 * @return a new MongoCollection instance with the different ReadConcern
	 * @since 3.2
	 * @mongodb.server.release 3.2
	 * @mongodb.driver.manual reference/readConcern/ Read Concern
	 */
	fun withReadConcern(readConcern: ReadConcern): MongoCollection<TDocument>

	/**
	 * Gets an estimate of the count of documents in a collection using collection metadata.
	 *
	 * @since 3.8
	 */
	suspend fun estimatedDocumentCount(): Long

	/**
	 * Gets an estimate of the count of documents in a collection using collection metadata.
	 *
	 * @param options the options describing the count
	 * @since 3.8
	 */
	suspend fun estimatedDocumentCount(options: EstimatedDocumentCountOptions): Long

	/**
	 * Counts the number of documents in the collection.
	 *
	 * Note: When migrating from `count()` to `countDocuments()` the following query operators must be replaced:
	 *
	 * ```
	 * +-------------+--------------------------------+
	 * | Operator    | Replacement                    |
	 * +=============+================================+
	 * | $where      |  $expr                         |
	 * +-------------+--------------------------------+
	 * | $near       |  $geoWithin with $center       |
	 * +-------------+--------------------------------+
	 * | $nearSphere |  $geoWithin with $centerSphere |
	 * +-------------+--------------------------------+
	 * ```
	 *
	 * @since 3.8
	 */
	suspend fun countDocuments(): Long

	/**
	 * Counts the number of documents in the collection according to the given options.
	 *
	 * Note: When migrating from `count()` to `countDocuments()` the following query operators must be replaced:
	 *
	 * ```
	 * +-------------+--------------------------------+
	 * | Operator    | Replacement                    |
	 * +=============+================================+
	 * | $where      |  $expr                         |
	 * +-------------+--------------------------------+
	 * | $near       |  $geoWithin with $center       |
	 * +-------------+--------------------------------+
	 * | $nearSphere |  $geoWithin with $centerSphere |
	 * +-------------+--------------------------------+
	 * ```
	 *
	 * @param filter the query filter
	 * @since 3.8
	 */
	suspend fun countDocuments(filter: Bson): Long

	/**
	 * Counts the number of documents in the collection according to the given options.
	 *
	 * Note: When migrating from `count()` to `countDocuments()` the following query operators must be replaced:
	 *
	 * ```
	 * +-------------+--------------------------------+
	 * | Operator    | Replacement                    |
	 * +=============+================================+
	 * | $where      |  $expr                         |
	 * +-------------+--------------------------------+
	 * | $near       |  $geoWithin with $center       |
	 * +-------------+--------------------------------+
	 * | $nearSphere |  $geoWithin with $centerSphere |
	 * +-------------+--------------------------------+
	 * ```
	 *
	 * @param filter  the query filter
	 * @param options the options describing the count
	 * @since 3.8
	 */
	suspend fun countDocuments(filter: Bson, options: CountOptions): Long

	/**
	 * Counts the number of documents in the collection.
	 *
	 * Note: When migrating from `count()` to `countDocuments()` the following query operators must be replaced:
	 *
	 * ```
	 * +-------------+--------------------------------+
	 * | Operator    | Replacement                    |
	 * +=============+================================+
	 * | $where      |  $expr                         |
	 * +-------------+--------------------------------+
	 * | $near       |  $geoWithin with $center       |
	 * +-------------+--------------------------------+
	 * | $nearSphere |  $geoWithin with $centerSphere |
	 * +-------------+--------------------------------+
	 * ```
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @since 3.8
	 * @mongodb.server.release 3.6
	 */
	suspend fun countDocuments(clientSession: ClientSession): Long

	/**
	 * Counts the number of documents in the collection according to the given options.
	 *
	 * Note: When migrating from `count()` to `countDocuments()` the following query operators must be replaced:
	 *
	 * ```
	 * +-------------+--------------------------------+
	 * | Operator    | Replacement                    |
	 * +=============+================================+
	 * | $where      |  $expr                         |
	 * +-------------+--------------------------------+
	 * | $near       |  $geoWithin with $center       |
	 * +-------------+--------------------------------+
	 * | $nearSphere |  $geoWithin with $centerSphere |
	 * +-------------+--------------------------------+
	 * ```
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @param filter the query filter
	 * @since 3.8
	 * @mongodb.server.release 3.6
	 */
	suspend fun countDocuments(clientSession: ClientSession, filter: Bson): Long

	/**
	 * Counts the number of documents in the collection according to the given options.
	 *
	 * Note: When migrating from `count()` to `countDocuments()` the following query operators must be replaced:
	 *
	 * ```
	 * +-------------+--------------------------------+
	 * | Operator    | Replacement                    |
	 * +=============+================================+
	 * | $where      |  $expr                         |
	 * +-------------+--------------------------------+
	 * | $near       |  $geoWithin with $center       |
	 * +-------------+--------------------------------+
	 * | $nearSphere |  $geoWithin with $centerSphere |
	 * +-------------+--------------------------------+
	 * ```
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @param filter  the query filter
	 * @param options the options describing the count
	 * @since 3.8
	 * @mongodb.server.release 3.6
	 */
	suspend fun countDocuments(clientSession: ClientSession, filter: Bson, options: CountOptions): Long

	/**
	 * Gets the distinct values of the specified field name.
	 *
	 * @param fieldName   the field name
	 * @param resultClass the default class to cast any distinct items into.
	 * @param <TResult>   the target type of the iterable.
	 * @return an iterable of distinct values
	 * @mongodb.driver.manual reference/command/distinct/ Distinct
	 */
	fun <TResult : Any> distinct(fieldName: String, resultClass: Class<TResult>): DistinctFlow<TResult>

	/**
	 * Gets the distinct values of the specified field name.
	 *
	 * @param fieldName   the field name
	 * @param filter      the query filter
	 * @param resultClass the default class to cast any distinct items into.
	 * @param <TResult>   the target type of the iterable.
	 * @return an iterable of distinct values
	 * @mongodb.driver.manual reference/command/distinct/ Distinct
	 */
	fun <TResult : Any> distinct(fieldName: String, filter: Bson, resultClass: Class<TResult>): DistinctFlow<TResult>

	/**
	 * Gets the distinct values of the specified field name.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param fieldName   the field name
	 * @param resultClass the default class to cast any distinct items into.
	 * @param <TResult>   the target type of the iterable.
	 * @return an iterable of distinct values
	 * @mongodb.driver.manual reference/command/distinct/ Distinct
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun <TResult : Any> distinct(clientSession: ClientSession, fieldName: String, resultClass: Class<TResult>): DistinctFlow<TResult>

	/**
	 * Gets the distinct values of the specified field name.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param fieldName   the field name
	 * @param filter      the query filter
	 * @param resultClass the default class to cast any distinct items into.
	 * @param <TResult>   the target type of the iterable.
	 * @return an iterable of distinct values
	 * @mongodb.driver.manual reference/command/distinct/ Distinct
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun <TResult : Any> distinct(clientSession: ClientSession, fieldName: String, filter: Bson, resultClass: Class<TResult>): DistinctFlow<TResult>

	/**
	 * Finds all documents in the collection.
	 *
	 * @return the find iterable interface
	 * @mongodb.driver.manual tutorial/query-documents/ Find
	 */
	fun find(): FindFlow<TDocument>

	/**
	 * Finds all documents in the collection.
	 *
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return the find iterable interface
	 * @mongodb.driver.manual tutorial/query-documents/ Find
	 */
	fun <TResult : Any> find(resultClass: Class<TResult>): FindFlow<TResult>

	/**
	 * Finds all documents in the collection.
	 *
	 * @param filter the query filter
	 * @return the find iterable interface
	 * @mongodb.driver.manual tutorial/query-documents/ Find
	 */
	fun find(filter: Bson): FindFlow<TDocument>

	/**
	 * Finds all documents in the collection.
	 *
	 * @param filter      the query filter
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return the find iterable interface
	 * @mongodb.driver.manual tutorial/query-documents/ Find
	 */
	fun <TResult : Any> find(filter: Bson, resultClass: Class<TResult>): FindFlow<TResult>

	/**
	 * Finds all documents in the collection.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @return the find iterable interface
	 * @mongodb.driver.manual tutorial/query-documents/ Find
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun find(clientSession: ClientSession): FindFlow<TDocument>

	/**
	 * Finds all documents in the collection.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return the find iterable interface
	 * @mongodb.driver.manual tutorial/query-documents/ Find
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun <TResult : Any> find(clientSession: ClientSession, resultClass: Class<TResult>): FindFlow<TResult>

	/**
	 * Finds all documents in the collection.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter the query filter
	 * @return the find iterable interface
	 * @mongodb.driver.manual tutorial/query-documents/ Find
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun find(clientSession: ClientSession, filter: Bson): FindFlow<TDocument>

	/**
	 * Finds all documents in the collection.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter      the query filter
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return the find iterable interface
	 * @mongodb.driver.manual tutorial/query-documents/ Find
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun <TResult : Any> find(clientSession: ClientSession, filter: Bson, resultClass: Class<TResult>): FindFlow<TResult>

	/**
	 * Aggregates documents according to the specified aggregation pipeline.  If the pipeline ends with a $out stage, the returned
	 * iterable will be a query of the collection that the aggregation was written to.  Note that in this case the pipeline will be
	 * executed even if the iterable is never iterated.
	 *
	 * @param pipeline the aggregate pipeline
	 * @return an iterable containing the result of the aggregation operation
	 * @mongodb.driver.manual aggregation/ Aggregation
	 */
	fun aggregate(pipeline: List<Bson>): AggregateFlow<Document>

	/**
	 * Aggregates documents according to the specified aggregation pipeline.  If the pipeline ends with a $out stage, the returned
	 * iterable will be a query of the collection that the aggregation was written to.  Note that in this case the pipeline will be
	 * executed even if the iterable is never iterated.
	 *
	 * @param pipeline    the aggregate pipeline
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return an iterable containing the result of the aggregation operation
	 * @mongodb.driver.manual aggregation/ Aggregation
	 */
	fun <TResult : Any> aggregate(pipeline: List<Bson>, resultClass: Class<TResult>): AggregateFlow<TResult>

	/**
	 * Aggregates documents according to the specified aggregation pipeline.  If the pipeline ends with a $out stage, the returned
	 * iterable will be a query of the collection that the aggregation was written to.  Note that in this case the pipeline will be
	 * executed even if the iterable is never iterated.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param pipeline the aggregate pipeline
	 * @return an iterable containing the result of the aggregation operation
	 * @mongodb.driver.manual aggregation/ Aggregation
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun aggregate(clientSession: ClientSession, pipeline: List<Bson>): AggregateFlow<Document>

	/**
	 * Aggregates documents according to the specified aggregation pipeline.  If the pipeline ends with a $out stage, the returned
	 * iterable will be a query of the collection that the aggregation was written to.  Note that in this case the pipeline will be
	 * executed even if the iterable is never iterated.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param pipeline    the aggregate pipeline
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return an iterable containing the result of the aggregation operation
	 * @mongodb.driver.manual aggregation/ Aggregation
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun <TResult : Any> aggregate(clientSession: ClientSession, pipeline: List<Bson>, resultClass: Class<TResult>): AggregateFlow<TResult>

	/**
	 * Creates a change stream for this collection.
	 *
	 * @return the change stream iterable
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun watch(): ChangeStreamFlow<Document>

	/**
	 * Creates a change stream for this collection.
	 *
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return the change stream iterable
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun <TResult : Any> watch(resultClass: Class<TResult>): ChangeStreamFlow<TResult>

	/**
	 * Creates a change stream for this collection.
	 *
	 * @param pipeline the aggregation pipeline to apply to the change stream
	 * @return the change stream iterable
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun watch(pipeline: List<Bson>): ChangeStreamFlow<Document>

	/**
	 * Creates a change stream for this collection.
	 *
	 * @param pipeline    the aggregation pipeline to apply to the change stream
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return the change stream iterable
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun <TResult : Any> watch(pipeline: List<Bson>, resultClass: Class<TResult>): ChangeStreamFlow<TResult>

	/**
	 * Creates a change stream for this collection.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @return the change stream iterable
	 * @mongodb.driver.manual reference/operator/aggregation/changeStream $changeStream
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun watch(clientSession: ClientSession): ChangeStreamFlow<Document>

	/**
	 * Creates a change stream for this collection.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return the change stream iterable
	 * @mongodb.driver.manual reference/operator/aggregation/changeStream $changeStream
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun <TResult : Any> watch(clientSession: ClientSession, resultClass: Class<TResult>): ChangeStreamFlow<TResult>

	/**
	 * Creates a change stream for this collection.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param pipeline the aggregation pipeline to apply to the change stream
	 * @return the change stream iterable
	 * @mongodb.driver.manual reference/operator/aggregation/changeStream $changeStream
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun watch(clientSession: ClientSession, pipeline: List<Bson>): ChangeStreamFlow<Document>

	/**
	 * Creates a change stream for this collection.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param pipeline    the aggregation pipeline to apply to the change stream
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return the change stream iterable
	 * @mongodb.driver.manual reference/operator/aggregation/changeStream $changeStream
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun <TResult : Any> watch(clientSession: ClientSession, pipeline: List<Bson>, resultClass: Class<TResult>): ChangeStreamFlow<TResult>

	/**
	 * Aggregates documents according to the specified map-reduce function.
	 *
	 * @param mapFunction    A JavaScript function that associates or "maps" a value with a key and emits the key and value pair.
	 * @param reduceFunction A JavaScript function that "reduces" to a single object all the values associated with a particular key.
	 * @return an iterable containing the result of the map-reduce operation
	 * @mongodb.driver.manual reference/command/mapReduce/ map-reduce
	 */
	fun mapReduce(mapFunction: String, reduceFunction: String): MapReduceFlow<Document>

	/**
	 * Aggregates documents according to the specified map-reduce function.
	 *
	 * @param mapFunction    A JavaScript function that associates or "maps" a value with a key and emits the key and value pair.
	 * @param reduceFunction A JavaScript function that "reduces" to a single object all the values associated with a particular key.
	 * @param resultClass    the class to decode each resulting document into.
	 * @param <TResult>      the target document type of the iterable.
	 * @return an iterable containing the result of the map-reduce operation
	 * @mongodb.driver.manual reference/command/mapReduce/ map-reduce
	 */
	fun <TResult : Any> mapReduce(mapFunction: String, reduceFunction: String, resultClass: Class<TResult>): MapReduceFlow<TResult>

	/**
	 * Aggregates documents according to the specified map-reduce function.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param mapFunction    A JavaScript function that associates or "maps" a value with a key and emits the key and value pair.
	 * @param reduceFunction A JavaScript function that "reduces" to a single object all the values associated with a particular key.
	 * @return an iterable containing the result of the map-reduce operation
	 * @mongodb.driver.manual reference/command/mapReduce/ map-reduce
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun mapReduce(clientSession: ClientSession, mapFunction: String, reduceFunction: String): MapReduceFlow<Document>

	/**
	 * Aggregates documents according to the specified map-reduce function.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param mapFunction    A JavaScript function that associates or "maps" a value with a key and emits the key and value pair.
	 * @param reduceFunction A JavaScript function that "reduces" to a single object all the values associated with a particular key.
	 * @param resultClass    the class to decode each resulting document into.
	 * @param <TResult>      the target document type of the iterable.
	 * @return an iterable containing the result of the map-reduce operation
	 * @mongodb.driver.manual reference/command/mapReduce/ map-reduce
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun <TResult : Any> mapReduce(clientSession: ClientSession, mapFunction: String, reduceFunction: String,
	                              resultClass: Class<TResult>): MapReduceFlow<TResult>

	/**
	 * Executes a mix of inserts, updates, replaces, and deletes.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * The eligibility for retryable write support for bulk operations is determined on the whole bulk write. If the `requests`
	 * contain any `UpdateManyModels` or `DeleteManyModels` then the bulk operation will not support retryable writes.
	 * @param requests the writes to execute
	 */
	suspend fun bulkWrite(requests: List<WriteModel<out TDocument>>): BulkWriteResult

	/**
	 * Executes a mix of inserts, updates, replaces, and deletes.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * The eligibility for retryable write support for bulk operations is determined on the whole bulk write. If the `requests`
	 * contain any `UpdateManyModels` or `DeleteManyModels` then the bulk operation will not support retryable writes.
	 * @param requests the writes to execute
	 * @param options  the options to apply to the bulk write operation
	 */
	suspend fun bulkWrite(requests: List<WriteModel<out TDocument>>, options: BulkWriteOptions): BulkWriteResult

	/**
	 * Executes a mix of inserts, updates, replaces, and deletes.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * The eligibility for retryable write support for bulk operations is determined on the whole bulk write. If the `requests`
	 * contain any `UpdateManyModels` or `DeleteManyModels` then the bulk operation will not support retryable writes.
	 * @param clientSession  the client session with which to associate this operation
	 * @param requests the writes to execute
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun bulkWrite(clientSession: ClientSession, requests: List<WriteModel<out TDocument>>): BulkWriteResult

	/**
	 * Executes a mix of inserts, updates, replaces, and deletes.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * The eligibility for retryable write support for bulk operations is determined on the whole bulk write. If the `requests`
	 * contain any `UpdateManyModels` or `DeleteManyModels` then the bulk operation will not support retryable writes.
	 * @param clientSession  the client session with which to associate this operation
	 * @param requests the writes to execute
	 * @param options  the options to apply to the bulk write operation
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun bulkWrite(clientSession: ClientSession, requests: List<WriteModel<out TDocument>>, options: BulkWriteOptions): BulkWriteResult

	/**
	 * Inserts the provided document. If the document is missing an identifier, the driver should generate one.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param document the document to insert
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 */
	suspend fun insertOne(document: TDocument)

	/**
	 * Inserts the provided document. If the document is missing an identifier, the driver should generate one.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param document the document to insert
	 * @param options  the options to apply to the operation
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoCommandException
	 * @throws com.mongodb.MongoException
	 * @since 3.2
	 */
	suspend fun insertOne(document: TDocument, options: InsertOneOptions)

	/**
	 * Inserts the provided document. If the document is missing an identifier, the driver should generate one.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param clientSession  the client session with which to associate this operation
	 * @param document the document to insert
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun insertOne(clientSession: ClientSession, document: TDocument)

	/**
	 * Inserts the provided document. If the document is missing an identifier, the driver should generate one.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param clientSession  the client session with which to associate this operation
	 * @param document the document to insert
	 * @param options  the options to apply to the operation
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoCommandException
	 * @throws com.mongodb.MongoException
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun insertOne(clientSession: ClientSession, document: TDocument, options: InsertOneOptions)

	/**
	 * Inserts one or more documents.  A call to this method is equivalent to a call to the `bulkWrite` method
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param documents the documents to insert
	 * @throws com.mongodb.MongoBulkWriteException if there's an exception in the bulk write operation
	 * @throws com.mongodb.MongoException          if the write failed due some other failure
	 * @see com.mongodb.async.client.MongoCollection.bulkWrite
	 */
	suspend fun insertMany(documents: List<TDocument>)

	/**
	 * Inserts one or more documents.  A call to this method is equivalent to a call to the `bulkWrite` method
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param documents the documents to insert
	 * @param options   the options to apply to the operation
	 * @throws com.mongodb.MongoBulkWriteException if there's an exception in the bulk write operation
	 * @throws com.mongodb.MongoException          if the write failed due some other failure
	 * @see com.mongodb.async.client.MongoCollection.bulkWrite
	 */
	suspend fun insertMany(documents: List<TDocument>, options: InsertManyOptions)

	/**
	 * Inserts one or more documents.  A call to this method is equivalent to a call to the `bulkWrite` method
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param clientSession  the client session with which to associate this operation
	 * @param documents the documents to insert
	 * @throws com.mongodb.MongoBulkWriteException if there's an exception in the bulk write operation
	 * @throws com.mongodb.MongoException          if the write failed due some other failure
	 * @see com.mongodb.async.client.MongoCollection.bulkWrite
	 *
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun insertMany(clientSession: ClientSession, documents: List<TDocument>)

	/**
	 * Inserts one or more documents.  A call to this method is equivalent to a call to the `bulkWrite` method
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param clientSession  the client session with which to associate this operation
	 * @param documents the documents to insert
	 * @param options   the options to apply to the operation
	 * @throws com.mongodb.MongoBulkWriteException if there's an exception in the bulk write operation
	 * @throws com.mongodb.MongoException          if the write failed due some other failure
	 * @see com.mongodb.async.client.MongoCollection.bulkWrite
	 *
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun insertMany(clientSession: ClientSession, documents: List<TDocument>, options: InsertManyOptions)

	/**
	 * Removes at most one document from the collection that matches the given filter.  If no documents match, the collection is not
	 * modified.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param filter   the query filter to apply the the delete operation
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 */
	suspend fun deleteOne(filter: Bson): DeleteResult

	/**
	 * Removes at most one document from the collection that matches the given filter.  If no documents match, the collection is not
	 * modified.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param filter   the query filter to apply the the delete operation
	 * @param options  the options to apply to the delete operation
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 */
	suspend fun deleteOne(filter: Bson, options: DeleteOptions): DeleteResult

	/**
	 * Removes at most one document from the collection that matches the given filter.  If no documents match, the collection is not
	 * modified.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter   the query filter to apply the the delete operation
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun deleteOne(clientSession: ClientSession, filter: Bson): DeleteResult

	/**
	 * Removes at most one document from the collection that matches the given filter.  If no documents match, the collection is not
	 * modified.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter   the query filter to apply the the delete operation
	 * @param options  the options to apply to the delete operation
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun deleteOne(clientSession: ClientSession, filter: Bson, options: DeleteOptions): DeleteResult

	/**
	 * Removes all documents from the collection that match the given query filter.  If no documents match, the collection is not modified.
	 *
	 * @param filter   the query filter to apply the the delete operation
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 */
	suspend fun deleteMany(filter: Bson): DeleteResult

	/**
	 * Removes all documents from the collection that match the given query filter.  If no documents match, the collection is not modified.
	 *
	 * @param filter   the query filter to apply the the delete operation
	 * @param options  the options to apply to the delete operation
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 */
	suspend fun deleteMany(filter: Bson, options: DeleteOptions): DeleteResult

	/**
	 * Removes all documents from the collection that match the given query filter.  If no documents match, the collection is not modified.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter   the query filter to apply the the delete operation
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun deleteMany(clientSession: ClientSession, filter: Bson): DeleteResult

	/**
	 * Removes all documents from the collection that match the given query filter.  If no documents match, the collection is not modified.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter   the query filter to apply the the delete operation
	 * @param options  the options to apply to the delete operation
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun deleteMany(clientSession: ClientSession, filter: Bson, options: DeleteOptions): DeleteResult

	/**
	 * Replace a document in the collection according to the specified arguments.
	 *
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param filter      the query filter to apply the the replace operation
	 * @param replacement the replacement document
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 * @mongodb.driver.manual tutorial/modify-documents/#replace-the-document Replace
	 */
	suspend fun replaceOne(filter: Bson, replacement: TDocument): UpdateResult

	/**
	 * Replace a document in the collection according to the specified arguments.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param filter      the query filter to apply the the replace operation
	 * @param replacement the replacement document
	 * @param options     the options to apply to the replace operation
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 * @mongodb.driver.manual tutorial/modify-documents/#replace-the-document Replace
	 * @since 3.7
	 */
	suspend fun replaceOne(filter: Bson, replacement: TDocument, options: ReplaceOptions): UpdateResult

	/**
	 * Replace a document in the collection according to the specified arguments.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter      the query filter to apply the the replace operation
	 * @param replacement the replacement document
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 * @mongodb.driver.manual tutorial/modify-documents/#replace-the-document Replace
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun replaceOne(clientSession: ClientSession, filter: Bson, replacement: TDocument): UpdateResult

	/**
	 * Replace a document in the collection according to the specified arguments.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter      the query filter to apply the the replace operation
	 * @param replacement the replacement document
	 * @param options     the options to apply to the replace operation
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 * @mongodb.driver.manual tutorial/modify-documents/#replace-the-document Replace
	 * @mongodb.server.release 3.6
	 * @since 3.7
	 */
	suspend fun replaceOne(clientSession: ClientSession, filter: Bson, replacement: TDocument, options: ReplaceOptions): UpdateResult

	/**
	 * Update a single document in the collection according to the specified arguments.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param filter   a document describing the query filter, which may not be null.
	 * @param update   a document describing the update, which may not be null. The update to apply must include only update operators.
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 * @mongodb.driver.manual tutorial/modify-documents/ Updates
	 * @mongodb.driver.manual reference/operator/update/ Update Operators
	 */
	suspend fun updateOne(filter: Bson, update: Bson): UpdateResult

	/**
	 * Update a single document in the collection according to the specified arguments.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param filter   a document describing the query filter, which may not be null.
	 * @param update   a document describing the update, which may not be null. The update to apply must include only update operators.
	 * @param options  the options to apply to the update operation
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 * @mongodb.driver.manual tutorial/modify-documents/ Updates
	 * @mongodb.driver.manual reference/operator/update/ Update Operators
	 */
	suspend fun updateOne(filter: Bson, update: Bson, options: UpdateOptions): UpdateResult

	/**
	 * Update a single document in the collection according to the specified arguments.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter   a document describing the query filter, which may not be null.
	 * @param update   a document describing the update, which may not be null. The update to apply must include only update operators.
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 * @mongodb.driver.manual tutorial/modify-documents/ Updates
	 * @mongodb.driver.manual reference/operator/update/ Update Operators
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun updateOne(clientSession: ClientSession, filter: Bson, update: Bson): UpdateResult

	/**
	 * Update a single document in the collection according to the specified arguments.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter   a document describing the query filter, which may not be null.
	 * @param update   a document describing the update, which may not be null. The update to apply must include only update operators.
	 * @param options  the options to apply to the update operation
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 * @mongodb.driver.manual tutorial/modify-documents/ Updates
	 * @mongodb.driver.manual reference/operator/update/ Update Operators
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun updateOne(clientSession: ClientSession, filter: Bson, update: Bson, options: UpdateOptions): UpdateResult

	/**
	 * Update all documents in the collection according to the specified arguments.
	 *
	 * @param filter   a document describing the query filter, which may not be null.
	 * @param update   a document describing the update, which may not be null. The update to apply must include only update operators. T
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 * @mongodb.driver.manual tutorial/modify-documents/ Updates
	 * @mongodb.driver.manual reference/operator/update/ Update Operators
	 */
	suspend fun updateMany(filter: Bson, update: Bson): UpdateResult

	/**
	 * Update all documents in the collection according to the specified arguments.
	 *
	 * @param filter   a document describing the query filter, which may not be null.
	 * @param update   a document describing the update, which may not be null. The update to apply must include only update operators.
	 * @param options  the options to apply to the update operation
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 * @mongodb.driver.manual tutorial/modify-documents/ Updates
	 * @mongodb.driver.manual reference/operator/update/ Update Operators
	 */
	suspend fun updateMany(filter: Bson, update: Bson, options: UpdateOptions): UpdateResult

	/**
	 * Update all documents in the collection according to the specified arguments.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter   a document describing the query filter, which may not be null.
	 * @param update   a document describing the update, which may not be null. The update to apply must include only update operators. T
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 * @mongodb.driver.manual tutorial/modify-documents/ Updates
	 * @mongodb.driver.manual reference/operator/update/ Update Operators
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun updateMany(clientSession: ClientSession, filter: Bson, update: Bson): UpdateResult

	/**
	 * Update all documents in the collection according to the specified arguments.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter   a document describing the query filter, which may not be null.
	 * @param update   a document describing the update, which may not be null. The update to apply must include only update operators.
	 * @param options  the options to apply to the update operation
	 * @throws com.mongodb.MongoWriteException
	 * @throws com.mongodb.MongoWriteConcernException
	 * @throws com.mongodb.MongoException
	 * @mongodb.driver.manual tutorial/modify-documents/ Updates
	 * @mongodb.driver.manual reference/operator/update/ Update Operators
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun updateMany(clientSession: ClientSession, filter: Bson, update: Bson, options: UpdateOptions): UpdateResult

	/**
	 * Atomically find a document and remove it.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param filter   the query filter to find the document with
	 * returned
	 */
	suspend fun findOneAndDelete(filter: Bson): TDocument?

	/**
	 * Atomically find a document and remove it.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param filter   the query filter to find the document with
	 * @param options  the options to apply to the operation
	 * returned
	 */
	suspend fun findOneAndDelete(filter: Bson, options: FindOneAndDeleteOptions): TDocument?

	/**
	 * Atomically find a document and remove it.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter   the query filter to find the document with
	 * returned
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun findOneAndDelete(clientSession: ClientSession, filter: Bson): TDocument?

	/**
	 * Atomically find a document and remove it.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter   the query filter to find the document with
	 * @param options  the options to apply to the operation
	 * returned
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun findOneAndDelete(clientSession: ClientSession, filter: Bson, options: FindOneAndDeleteOptions): TDocument?

	/**
	 * Atomically find a document and replace it.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param filter      the query filter to apply the the replace operation
	 * @param replacement the replacement document
	 * property, this will either be the document as it was before the update or as it is after the update.  If no
	 * documents matched the query filter, then null will be returned
	 */
	suspend fun findOneAndReplace(filter: Bson, replacement: TDocument): TDocument?

	/**
	 * Atomically find a document and replace it.
	 *
	 * @param filter      the query filter to apply the the replace operation
	 * @param replacement the replacement document
	 * @param options     the options to apply to the operation
	 * property, this will either be the document as it was before the update or as it is after the update.  If no
	 * documents matched the query filter, then null will be returned
	 */
	suspend fun findOneAndReplace(filter: Bson, replacement: TDocument, options: FindOneAndReplaceOptions): TDocument?

	/**
	 * Atomically find a document and replace it.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter      the query filter to apply the the replace operation
	 * @param replacement the replacement document
	 * property, this will either be the document as it was before the update or as it is after the update.  If no
	 * documents matched the query filter, then null will be returned
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun findOneAndReplace(clientSession: ClientSession, filter: Bson, replacement: TDocument): TDocument?

	/**
	 * Atomically find a document and replace it.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter      the query filter to apply the the replace operation
	 * @param replacement the replacement document
	 * @param options     the options to apply to the operation
	 * property, this will either be the document as it was before the update or as it is after the update.  If no
	 * documents matched the query filter, then null will be returned
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun findOneAndReplace(clientSession: ClientSession, filter: Bson, replacement: TDocument, options: FindOneAndReplaceOptions): TDocument?

	/**
	 * Atomically find a document and update it.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param filter   a document describing the query filter, which may not be null.
	 * @param update   a document describing the update, which may not be null. The update to apply must include only update operators.
	 * filter, then null will be returned
	 */
	suspend fun findOneAndUpdate(filter: Bson, update: Bson): TDocument?

	/**
	 * Atomically find a document and update it.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param filter   a document describing the query filter, which may not be null.
	 * @param update   a document describing the update, which may not be null. The update to apply must include only update operators.
	 * @param options  the options to apply to the operation
	 * this will either be the document as it was before the update or as it is after the update.  If no documents matched
	 * the query filter, then null will be returned
	 */
	suspend fun findOneAndUpdate(filter: Bson, update: Bson, options: FindOneAndUpdateOptions): TDocument?

	/**
	 * Atomically find a document and update it.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter   a document describing the query filter, which may not be null.
	 * @param update   a document describing the update, which may not be null. The update to apply must include only update operators.
	 * filter, then null will be returned
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun findOneAndUpdate(clientSession: ClientSession, filter: Bson, update: Bson): TDocument?

	/**
	 * Atomically find a document and update it.
	 *
	 * Note: Supports retryable writes on MongoDB server versions 3.6 or higher when the retryWrites setting is enabled.
	 * @param clientSession  the client session with which to associate this operation
	 * @param filter   a document describing the query filter, which may not be null.
	 * @param update   a document describing the update, which may not be null. The update to apply must include only update operators.
	 * @param options  the options to apply to the operation
	 * this will either be the document as it was before the update or as it is after the update.  If no documents matched
	 * the query filter, then null will be returned
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun findOneAndUpdate(clientSession: ClientSession, filter: Bson, update: Bson, options: FindOneAndUpdateOptions): TDocument?

	/**
	 * Drops this collection from the Database.
	 *
	 * @mongodb.driver.manual reference/command/drop/ Drop Collection
	 */
	suspend fun drop()

	/**
	 * Drops this collection from the Database.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @mongodb.driver.manual reference/command/drop/ Drop Collection
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun drop(clientSession: ClientSession)

	/**
	 * Creates an index.
	 *
	 * @param key      an object describing the index key(s), which may not be null.
	 * @return the name of the created index
	 * @mongodb.driver.manual reference/command/createIndexes/ Create indexes
	 */
	suspend fun createIndex(key: Bson): String

	/**
	 * Creates an index.
	 *
	 * @param key      an object describing the index key(s), which may not be null.
	 * @param options  the options for the index
	 * @return the name of the created index
	 * @mongodb.driver.manual reference/command/createIndexes/ Create indexes
	 */
	suspend fun createIndex(key: Bson, options: IndexOptions): String

	/**
	 * Creates an index.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param key      an object describing the index key(s), which may not be null.
	 * @return the name of the created index
	 * @mongodb.driver.manual reference/command/createIndexes/ Create indexes
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun createIndex(clientSession: ClientSession, key: Bson): String

	/**
	 * Creates an index.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param key      an object describing the index key(s), which may not be null.
	 * @param options  the options for the index
	 * @return the name of the created index
	 * @mongodb.driver.manual reference/command/createIndexes/ Create indexes
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun createIndex(clientSession: ClientSession, key: Bson, options: IndexOptions): String

	/**
	 * Create multiple indexes.
	 *
	 * @param indexes the list of indexes
	 * @return a list of the names of the created indexes
	 * @mongodb.driver.manual reference/command/createIndexes Create indexes
	 * @mongodb.server.release 2.6
	 */
	suspend fun createIndexes(indexes: List<IndexModel>): Flow<String>

	/**
	 * Create multiple indexes.
	 *
	 * @param indexes the list of indexes
	 * @param createIndexOptions options to use when creating indexes
	 * @return a list of the names of the created indexes
	 * @mongodb.driver.manual reference/command/createIndexes Create indexes
	 * @since 3.6
	 */
	suspend fun createIndexes(indexes: List<IndexModel>, createIndexOptions: CreateIndexOptions): Flow<String>

	/**
	 * Create multiple indexes.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param indexes the list of indexes
	 * @return a list of the names of the created indexes
	 * @mongodb.driver.manual reference/command/createIndexes Create indexes
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun createIndexes(clientSession: ClientSession, indexes: List<IndexModel>): Flow<String>

	/**
	 * Create multiple indexes.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param indexes the list of indexes
	 * @param createIndexOptions options to use when creating indexes
	 * @return a list of the names of the created indexes
	 * @mongodb.driver.manual reference/command/createIndexes Create indexes
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun createIndexes(clientSession: ClientSession, indexes: List<IndexModel>, createIndexOptions: CreateIndexOptions): Flow<String>

	/**
	 * Get all the indexes in this collection.
	 *
	 * @return the list indexes iterable interface
	 * @mongodb.driver.manual reference/command/listIndexes/ List indexes
	 */
	fun listIndexes(): ListIndexesFlow<Document>

	/**
	 * Get all the indexes in this collection.
	 *
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return the list indexes iterable interface
	 * @mongodb.driver.manual reference/command/listIndexes/ List indexes
	 */
	fun <TResult : Any> listIndexes(resultClass: Class<TResult>): ListIndexesFlow<TResult>

	/**
	 * Get all the indexes in this collection.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @return the list indexes iterable interface
	 * @mongodb.driver.manual reference/command/listIndexes/ List indexes
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun listIndexes(clientSession: ClientSession): ListIndexesFlow<Document>

	/**
	 * Get all the indexes in this collection.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return the list indexes iterable interface
	 * @mongodb.driver.manual reference/command/listIndexes/ List indexes
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun <TResult : Any> listIndexes(clientSession: ClientSession, resultClass: Class<TResult>): ListIndexesFlow<TResult>

	/**
	 * Drops the index given its name.
	 *
	 * @param indexName the name of the index to remove
	 * @mongodb.driver.manual reference/command/dropIndexes/ Drop indexes
	 */
	suspend fun dropIndex(indexName: String)

	/**
	 * Drops the index given its name.
	 *
	 * @param indexName the name of the index to remove
	 * @param dropIndexOptions options to use when dropping indexes
	 * @mongodb.driver.manual reference/command/dropIndexes/ Drop indexes
	 * @since 3.6
	 */
	suspend fun dropIndex(indexName: String, dropIndexOptions: DropIndexOptions)

	/**
	 * Drops the index given the keys used to create it.
	 *
	 * @param keys the keys of the index to remove
	 * @mongodb.driver.manual reference/command/dropIndexes/ Drop indexes
	 */
	suspend fun dropIndex(keys: Bson)

	/**
	 * Drops the index given the keys used to create it.
	 *
	 * @param keys the keys of the index to remove
	 * @param dropIndexOptions options to use when dropping indexes
	 * @mongodb.driver.manual reference/command/dropIndexes/ Drop indexes
	 * @since 3.6
	 */
	suspend fun dropIndex(keys: Bson, dropIndexOptions: DropIndexOptions)

	/**
	 * Drops the index given its name.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param indexName the name of the index to remove
	 * @mongodb.driver.manual reference/command/dropIndexes/ Drop indexes
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun dropIndex(clientSession: ClientSession, indexName: String)

	/**
	 * Drops the index given its name.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param indexName the name of the index to remove
	 * @param dropIndexOptions options to use when dropping indexes
	 * @mongodb.driver.manual reference/command/dropIndexes/ Drop indexes
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun dropIndex(clientSession: ClientSession, indexName: String, dropIndexOptions: DropIndexOptions)

	/**
	 * Drops the index given the keys used to create it.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param keys the keys of the index to remove
	 * @mongodb.driver.manual reference/command/dropIndexes/ Drop indexes
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun dropIndex(clientSession: ClientSession, keys: Bson)

	/**
	 * Drops the index given the keys used to create it.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param keys the keys of the index to remove
	 * @param dropIndexOptions options to use when dropping indexes
	 * @mongodb.driver.manual reference/command/dropIndexes/ Drop indexes
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun dropIndex(clientSession: ClientSession, keys: Bson, dropIndexOptions: DropIndexOptions)

	/**
	 * Drop all the indexes on this collection, except for the default on _id.
	 *
	 * @mongodb.driver.manual reference/command/dropIndexes/ Drop indexes
	 */
	suspend fun dropIndexes()

	/**
	 * Drop all the indexes on this collection, except for the default on _id.
	 *
	 * @param dropIndexOptions options to use when dropping indexes
	 * @mongodb.driver.manual reference/command/dropIndexes/ Drop indexes
	 * @since 3.6
	 */
	suspend fun dropIndexes(dropIndexOptions: DropIndexOptions)

	/**
	 * Drop all the indexes on this collection, except for the default on _id.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @mongodb.driver.manual reference/command/dropIndexes/ Drop indexes
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun dropIndexes(clientSession: ClientSession)

	/**
	 * Drop all the indexes on this collection, except for the default on _id.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param dropIndexOptions options to use when dropping indexes
	 * @mongodb.driver.manual reference/command/dropIndexes/ Drop indexes
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun dropIndexes(clientSession: ClientSession, dropIndexOptions: DropIndexOptions)

	/**
	 * Rename the collection with oldCollectionName to the newCollectionName.
	 *
	 * @param newCollectionNamespace the namespace the collection will be renamed to
	 * @throws com.mongodb.MongoServerException if you provide a newCollectionName that is the name of an existing collection, or if the
	 * oldCollectionName is the name of a collection that doesn't exist
	 * @mongodb.driver.manual reference/command/renameCollection Rename collection
	 */
	suspend fun renameCollection(newCollectionNamespace: MongoNamespace)

	/**
	 * Rename the collection with oldCollectionName to the newCollectionName.
	 *
	 * @param newCollectionNamespace the name the collection will be renamed to
	 * @param options                the options for renaming a collection
	 * @throws com.mongodb.MongoServerException if you provide a newCollectionName that is the name of an existing collection and dropTarget
	 * is false, or if the oldCollectionName is the name of a collection that doesn't exist
	 * @mongodb.driver.manual reference/command/renameCollection Rename collection
	 */
	suspend fun renameCollection(newCollectionNamespace: MongoNamespace, options: RenameCollectionOptions)

	/**
	 * Rename the collection with oldCollectionName to the newCollectionName.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param newCollectionNamespace the namespace the collection will be renamed to
	 * @throws com.mongodb.MongoServerException if you provide a newCollectionName that is the name of an existing collection, or if the
	 * oldCollectionName is the name of a collection that doesn't exist
	 * @mongodb.driver.manual reference/command/renameCollection Rename collection
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun renameCollection(clientSession: ClientSession, newCollectionNamespace: MongoNamespace)

	/**
	 * Rename the collection with oldCollectionName to the newCollectionName.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param newCollectionNamespace the name the collection will be renamed to
	 * @param options                the options for renaming a collection
	 * @throws com.mongodb.MongoServerException if you provide a newCollectionName that is the name of an existing collection and dropTarget
	 * is false, or if the oldCollectionName is the name of a collection that doesn't exist
	 * @mongodb.driver.manual reference/command/renameCollection Rename collection
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	suspend fun renameCollection(clientSession: ClientSession, newCollectionNamespace: MongoNamespace, options: RenameCollectionOptions)
}
