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
import com.mongodb.client.model.*
import kotlinx.coroutines.flow.*
import org.bson.*
import org.bson.codecs.configuration.*
import org.bson.conversions.*
import kotlin.reflect.*

/**
 * The MongoDatabase interface.
 *
 * Note: Additions to this interface will not be considered to break binary compatibility.
 *
 * @since 3.0
 */
@ThreadSafe
public interface MongoDatabase {

	/**
	 * Gets the name of the database.
	 *
	 * @return the database name
	 */
	public val name: String

	/**
	 * Get the codec registry for the MongoDatabase.
	 *
	 * @return the [org.bson.codecs.configuration.CodecRegistry]
	 */
	public val codecRegistry: CodecRegistry

	/**
	 * Get the read preference for the MongoDatabase.
	 *
	 * @return the [com.mongodb.ReadPreference]
	 */
	public val readPreference: ReadPreference

	/**
	 * Get the write concern for the MongoDatabase.
	 *
	 * @return the [com.mongodb.WriteConcern]
	 */
	public val writeConcern: WriteConcern

	/**
	 * Get the read concern for the MongoDatabase.
	 *
	 * @return the [com.mongodb.ReadConcern]
	 * @since 3.2
	 * @mongodb.server.release 3.2
	 * @mongodb.driver.manual reference/readConcern/ Read Concern
	 */
	public val readConcern: ReadConcern

	/**
	 * Create a new MongoDatabase instance with a different codec registry.
	 *
	 * @param codecRegistry the new [org.bson.codecs.configuration.CodecRegistry] for the database
	 * @return a new MongoDatabase instance with the different codec registry
	 */
	public fun withCodecRegistry(codecRegistry: CodecRegistry): MongoDatabase

	/**
	 * Create a new MongoDatabase instance with a different read preference.
	 *
	 * @param readPreference the new [com.mongodb.ReadPreference] for the database
	 * @return a new MongoDatabase instance with the different readPreference
	 */
	public fun withReadPreference(readPreference: ReadPreference): MongoDatabase

	/**
	 * Create a new MongoDatabase instance with a different write concern.
	 *
	 * @param writeConcern the new [com.mongodb.WriteConcern] for the database
	 * @return a new MongoDatabase instance with the different writeConcern
	 */
	public fun withWriteConcern(writeConcern: WriteConcern): MongoDatabase

	/**
	 * Create a new MongoDatabase instance with a different read concern.
	 *
	 * @param readConcern the new [ReadConcern] for the database
	 * @return a new MongoDatabase instance with the different ReadConcern
	 * @since 3.2
	 * @mongodb.server.release 3.2
	 * @mongodb.driver.manual reference/readConcern/ Read Concern
	 */
	public fun withReadConcern(readConcern: ReadConcern): MongoDatabase

	/**
	 * Gets a collection.
	 *
	 * @param name the name of the collection to return
	 * @return the collection
	 * @throws IllegalArgumentException if collectionName is invalid
	 * @see com.mongodb.MongoNamespace.checkCollectionNameValidity
	 */
	public fun getCollection(name: String): MongoCollection<Document>

	/**
	 * Gets a collection, with a specific default document class.
	 *
	 * @param name the name of the collection to return
	 * @param documentClass the default class to cast any documents returned from the database into.
	 * @param <TDocument>   the type of the class to use instead of `Document`.
	 * @return the collection
	 */
	public fun <TDocument : Any> getCollection(name: String, documentClass: KClass<TDocument>): MongoCollection<TDocument>

	/**
	 * Executes the given command in the context of the current database with a read preference of [ReadPreference.primary].
	 *
	 * @param command  the command to be run
	 */
	public suspend fun runCommand(command: Bson): Document

	/**
	 * Executes the given command in the context of the current database with the given read preference.
	 *
	 * @param command        the command to be run
	 * @param readPreference the [com.mongodb.ReadPreference] to be used when executing the command
	 */
	public suspend fun runCommand(command: Bson, readPreference: ReadPreference): Document

	/**
	 * Executes the given command in the context of the current database with a read preference of [ReadPreference.primary].
	 *
	 * @param command     the command to be run
	 * @param resultClass the default class to cast any documents returned from the database into.
	 * @param <TResult>   the type of the class to use instead of `Document`.
	 */
	public suspend fun <TResult : Any> runCommand(command: Bson, resultClass: KClass<out TResult>): TResult

	/**
	 * Executes the given command in the context of the current database with the given read preference.
	 *
	 * @param command        the command to be run
	 * @param readPreference the [com.mongodb.ReadPreference] to be used when executing the command
	 * @param resultClass    the default class to cast any documents returned from the database into.
	 * @param <TResult>      the type of the class to use instead of `Document`.
	 */
	public suspend fun <TResult : Any> runCommand(command: Bson, readPreference: ReadPreference, resultClass: KClass<out TResult>): TResult

	/**
	 * Executes the given command in the context of the current database with a read preference of [ReadPreference.primary].
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @param command  the command to be run
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	public suspend fun runCommand(clientSession: ClientSession, command: Bson): Document

	/**
	 * Executes the given command in the context of the current database with the given read preference.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param command        the command to be run
	 * @param readPreference the [ReadPreference] to be used when executing the command
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	public suspend fun runCommand(clientSession: ClientSession, command: Bson, readPreference: ReadPreference): Document

	/**
	 * Executes the given command in the context of the current database with a read preference of [ReadPreference.primary].
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param command     the command to be run
	 * @param resultClass the default class to cast any documents returned from the database into.
	 * @param <TResult>   the type of the class to use instead of `Document`.
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	public suspend fun <TResult : Any> runCommand(clientSession: ClientSession, command: Bson, resultClass: KClass<out TResult>): TResult

	/**
	 * Executes the given command in the context of the current database with the given read preference.
	 *
	 * @param clientSession  the client session with which to associate this operation
	 * @param command        the command to be run
	 * @param readPreference the [com.mongodb.ReadPreference] to be used when executing the command
	 * @param resultClass    the default class to cast any documents returned from the database into.
	 * @param <TResult>      the type of the class to use instead of `Document`.
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	public suspend fun <TResult : Any> runCommand(clientSession: ClientSession, command: Bson, readPreference: ReadPreference, resultClass: KClass<out TResult>): TResult

	/**
	 * Drops this database.
	 *
	 * @mongodb.driver.manual reference/command/dropDatabase/#dbcmd.dropDatabase Drop database
	 */
	public suspend fun drop()

	/**
	 * Drops this database.
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @mongodb.driver.manual reference/command/dropDatabase/#dbcmd.dropDatabase Drop database
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	public suspend fun drop(clientSession: ClientSession)

	/**
	 * Gets the names of all the collections in this database.
	 *
	 * @return an iterable containing all the names of all the collections in this database
	 */
	public fun listCollectionNames(): Flow<String>

	/**
	 * Gets the names of all the collections in this database.
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @return an iterable containing all the names of all the collections in this database
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	public fun listCollectionNames(clientSession: ClientSession): Flow<String>

	/**
	 * Finds all the collections in this database.
	 *
	 * @return the list collections iterable interface
	 * @mongodb.driver.manual reference/command/listCollections listCollections
	 */
	public fun listCollections(): ListCollectionsFlow<Document>

	/**
	 * Finds all the collections in this database.
	 *
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return the list collections iterable interface
	 * @mongodb.driver.manual reference/command/listCollections listCollections
	 */
	public fun <TResult : Any> listCollections(resultClass: KClass<out TResult>): ListCollectionsFlow<TResult>

	/**
	 * Finds all the collections in this database.
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @return the list collections iterable interface
	 * @mongodb.driver.manual reference/command/listCollections listCollections
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	public fun listCollections(clientSession: ClientSession): ListCollectionsFlow<Document>

	/**
	 * Finds all the collections in this database.
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return the list collections iterable interface
	 * @mongodb.driver.manual reference/command/listCollections listCollections
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	public fun <TResult : Any> listCollections(clientSession: ClientSession, resultClass: KClass<out TResult>): ListCollectionsFlow<TResult>

	/**
	 * Create a new collection with the given name.
	 *
	 * @param collectionName the name for the new collection to create
	 * @mongodb.driver.manual reference/command/create Create Command
	 */
	public suspend fun createCollection(collectionName: String)

	/**
	 * Create a new collection with the selected options
	 *
	 * @param collectionName the name for the new collection to create
	 * @param options        various options for creating the collection
	 * @mongodb.driver.manual reference/command/create Create Command
	 */
	public suspend fun createCollection(collectionName: String, options: CreateCollectionOptions)

	/**
	 * Create a new collection with the given name.
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @param collectionName the name for the new collection to create
	 * @mongodb.driver.manual reference/command/create Create Command
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	public suspend fun createCollection(clientSession: ClientSession, collectionName: String)

	/**
	 * Create a new collection with the selected options
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @param collectionName the name for the new collection to create
	 * @param options        various options for creating the collection
	 * @mongodb.driver.manual reference/command/create Create Command
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	public suspend fun createCollection(clientSession: ClientSession, collectionName: String, options: CreateCollectionOptions)

	/**
	 * Creates a view with the given name, backing collection/view name, and aggregation pipeline that defines the view.
	 *
	 * @param viewName the name of the view to create
	 * @param viewOn   the backing collection/view for the view
	 * @param pipeline the pipeline that defines the view
	 * @mongodb.driver.manual reference/command/create Create Command
	 * @since 3.4
	 * @mongodb.server.release 3.4
	 */
	public suspend fun createView(viewName: String, viewOn: String, pipeline: List<Bson>)

	/**
	 * Creates a view with the given name, backing collection/view name, aggregation pipeline, and options that defines the view.
	 *
	 * @param viewName the name of the view to create
	 * @param viewOn   the backing collection/view for the view
	 * @param pipeline the pipeline that defines the view
	 * @param createViewOptions various options for creating the view
	 * @mongodb.driver.manual reference/command/create Create Command
	 * @since 3.4
	 * @mongodb.server.release 3.4
	 */
	public suspend fun createView(viewName: String, viewOn: String, pipeline: List<Bson>, createViewOptions: CreateViewOptions)

	/**
	 * Creates a view with the given name, backing collection/view name, and aggregation pipeline that defines the view.
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @param viewName the name of the view to create
	 * @param viewOn   the backing collection/view for the view
	 * @param pipeline the pipeline that defines the view
	 * @mongodb.driver.manual reference/command/create Create Command
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	public suspend fun createView(clientSession: ClientSession, viewName: String, viewOn: String, pipeline: List<Bson>)

	/**
	 * Creates a view with the given name, backing collection/view name, aggregation pipeline, and options that defines the view.
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @param viewName the name of the view to create
	 * @param viewOn   the backing collection/view for the view
	 * @param pipeline the pipeline that defines the view
	 * @param createViewOptions various options for creating the view
	 * @mongodb.driver.manual reference/command/create Create Command
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	public suspend fun createView(clientSession: ClientSession, viewName: String, viewOn: String, pipeline: List<Bson>,
	                              createViewOptions: CreateViewOptions)

	/**
	 * Creates a change stream for this database.
	 *
	 * @return the change stream iterable
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 * @since 3.8
	 * @mongodb.server.release 4.0
	 */
	public fun watch(): ChangeStreamFlow<Document>

	/**
	 * Creates a change stream for this database.
	 *
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return the change stream iterable
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 * @since 3.8
	 * @mongodb.server.release 4.0
	 */
	public fun <TResult : Any> watch(resultClass: KClass<out TResult>): ChangeStreamFlow<TResult>

	/**
	 * Creates a change stream for this database.
	 *
	 * @param pipeline the aggregation pipeline to apply to the change stream.
	 * @return the change stream iterable
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 * @since 3.8
	 * @mongodb.server.release 4.0
	 */
	public fun watch(pipeline: List<Bson>): ChangeStreamFlow<Document>

	/**
	 * Creates a change stream for this database.
	 *
	 * @param pipeline    the aggregation pipeline to apply to the change stream
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return the change stream iterable
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 * @since 3.8
	 * @mongodb.server.release 4.0
	 */
	public fun <TResult : Any> watch(pipeline: List<Bson>, resultClass: KClass<out TResult>): ChangeStreamFlow<TResult>

	/**
	 * Creates a change stream for this database.
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @return the change stream iterable
	 * @since 3.8
	 * @mongodb.server.release 4.0
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 */
	public fun watch(clientSession: ClientSession): ChangeStreamFlow<Document>

	/**
	 * Creates a change stream for this database.
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return the change stream iterable
	 * @since 3.8
	 * @mongodb.server.release 4.0
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 */
	public fun <TResult : Any> watch(clientSession: ClientSession, resultClass: KClass<out TResult>): ChangeStreamFlow<TResult>

	/**
	 * Creates a change stream for this database.
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @param pipeline the aggregation pipeline to apply to the change stream.
	 * @return the change stream iterable
	 * @since 3.8
	 * @mongodb.server.release 4.0
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 */
	public fun watch(clientSession: ClientSession, pipeline: List<Bson>): ChangeStreamFlow<Document>

	/**
	 * Creates a change stream for this database.
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @param pipeline    the aggregation pipeline to apply to the change stream
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return the change stream iterable
	 * @since 3.8
	 * @mongodb.server.release 4.0
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 */
	public fun <TResult : Any> watch(clientSession: ClientSession, pipeline: List<Bson>, resultClass: KClass<out TResult>): ChangeStreamFlow<TResult>

	/**
	 * Runs an aggregation framework pipeline on the database for pipeline stages
	 * that do not require an underlying collection, such as `$currentOp` and `$listLocalSessions`.
	 *
	 * @param pipeline the aggregation pipeline
	 * @return an iterable containing the result of the aggregation operation
	 * @since 1.11
	 * @mongodb.driver.manual reference/command/aggregate/#dbcmd.aggregate Aggregate Command
	 * @mongodb.server.release 3.6
	 */
	public fun aggregate(pipeline: List<Bson>): AggregateFlow<Document>

	/**
	 * Runs an aggregation framework pipeline on the database for pipeline stages
	 * that do not require an underlying collection, such as `$currentOp` and `$listLocalSessions`.
	 *
	 * @param pipeline    the aggregation pipeline
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return an iterable containing the result of the aggregation operation
	 * @since 1.11
	 * @mongodb.driver.manual reference/command/aggregate/#dbcmd.aggregate Aggregate Command
	 * @mongodb.server.release 3.6
	</TResult> */
	public fun <TResult : Any> aggregate(pipeline: List<Bson>, resultClass: KClass<out TResult>): AggregateFlow<TResult>

	/**
	 * Runs an aggregation framework pipeline on the database for pipeline stages
	 * that do not require an underlying collection, such as `$currentOp` and `$listLocalSessions`.
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @param pipeline the aggregation pipeline
	 * @return an iterable containing the result of the aggregation operation
	 * @since 1.11
	 * @mongodb.driver.manual reference/command/aggregate/#dbcmd.aggregate Aggregate Command
	 * @mongodb.server.release 3.6
	 */
	public fun aggregate(clientSession: ClientSession, pipeline: List<Bson>): AggregateFlow<Document>

	/**
	 * Runs an aggregation framework pipeline on the database for pipeline stages
	 * that do not require an underlying collection, such as `$currentOp` and `$listLocalSessions`.
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @param pipeline    the aggregation pipeline
	 * @param resultClass the class to decode each document into
	 * @param <TResult>   the target document type of the iterable.
	 * @return an iterable containing the result of the aggregation operation
	 * @since 1.11
	 * @mongodb.driver.manual reference/command/aggregate/#dbcmd.aggregate Aggregate Command
	 * @mongodb.server.release 3.6
	</TResult> */
	public fun <TResult : Any> aggregate(clientSession: ClientSession, pipeline: List<Bson>, resultClass: KClass<out TResult>): AggregateFlow<TResult>


	public companion object
}


/**
 * Gets a collection, with a specific default document class.
 *
 * @param name the name of the collection to return
 * @param <TDocument>   the type of the class to use instead of `Document`.
 * @return the collection
 */
public inline fun <reified TDocument : Any> MongoDatabase.getCollectionOf(name: String): MongoCollection<TDocument> =
	getCollection(name = name, documentClass = TDocument::class)
