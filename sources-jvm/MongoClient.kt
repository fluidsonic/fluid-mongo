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
import kotlinx.coroutines.flow.*
import org.bson.*
import org.bson.conversions.*
import java.io.*
import kotlin.reflect.*

/**
 * A client-side representation of a MongoDB cluster.  Instances can represent either a standalone MongoDB instance, a replica set,
 * or a sharded cluster.  Instance of this class are responsible for maintaining an up-to-date state of the cluster,
 * and possibly cache resources related to this, including background threads for monitoring, and connection pools.
 * <p>
 * Instance of this class serve as factories for {@code MongoDatabase} instances.
 * </p>
 * @since 3.0
 */
@Immutable
public interface MongoClient : Closeable {

	/**
	 * Gets the database with the given name.
	 *
	 * @param name the name of the database
	 * @return the database
	 * @throws IllegalArgumentException if databaseName is invalid
	 * @see com.mongodb.MongoNamespace.checkDatabaseNameValidity
	 */
	public fun getDatabase(name: String): MongoDatabase

	/**
	 * Close the client, which will close all underlying cached resources, including, for example,
	 * sockets and background monitoring threads.
	 */
	override fun close()

	/**
	 * Get a list of the database names
	 *
	 * @return an iterable containing all the names of all the databases
	 * @mongodb.driver.manual reference/command/listDatabases List Databases
	 */
	public fun listDatabaseNames(): Flow<String>

	/**
	 * Get a list of the database names
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @return an iterable containing all the names of all the databases
	 * @mongodb.driver.manual reference/command/listDatabases List Databases
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	public fun listDatabaseNames(clientSession: ClientSession): Flow<String>

	/**
	 * Gets the list of databases
	 *
	 * @return the list databases iterable interface
	 */
	public fun listDatabases(): ListDatabasesFlow<Document>

	/**
	 * Gets the list of databases
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @return the list databases iterable interface
	 * @mongodb.driver.manual reference/command/listDatabases List Databases
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	public fun listDatabases(clientSession: ClientSession): ListDatabasesFlow<Document>

	/**
	 * Gets the list of databases
	 *
	 * @param resultClass the class to cast the database documents to
	 * @param <TResult>   the type of the class to use instead of `Document`.
	 * @return the list databases iterable interface
	 */
	public fun <TResult : Any> listDatabases(resultClass: KClass<out TResult>): ListDatabasesFlow<TResult>

	/**
	 * Gets the list of databases
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @param resultClass the class to cast the database documents to
	 * @param <TResult>   the type of the class to use instead of `Document`.
	 * @return the list databases iterable interface
	 * @mongodb.driver.manual reference/command/listDatabases List Databases
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	public fun <TResult : Any> listDatabases(clientSession: ClientSession, resultClass: KClass<out TResult>): ListDatabasesFlow<TResult>

	/**
	 * Creates a change stream for this client.
	 *
	 * @return the change stream iterable
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 * @since 3.8
	 * @mongodb.server.release 4.0
	 */
	public fun watch(): ChangeStreamFlow<Document>

	/**
	 * Creates a change stream for this client.
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
	 * Creates a change stream for this client.
	 *
	 * @param pipeline the aggregation pipeline to apply to the change stream.
	 * @return the change stream iterable
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 * @since 3.8
	 * @mongodb.server.release 4.0
	 */
	public fun watch(pipeline: List<Bson>): ChangeStreamFlow<Document>

	/**
	 * Creates a change stream for this client.
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
	 * Creates a change stream for this client.
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @return the change stream iterable
	 * @since 3.8
	 * @mongodb.server.release 4.0
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 */
	public fun watch(clientSession: ClientSession): ChangeStreamFlow<Document>

	/**
	 * Creates a change stream for this client.
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
	 * Creates a change stream for this client.
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
	 * Creates a change stream for this client.
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
	 * Creates a client session with default options.
	 *
	 * Note: A ClientSession instance can not be used concurrently in multiple asynchronous operations.
	 *
	 * @return the client session
	 * @mongodb.server.release 3.6
	 * @since 3.8
	 */
	public suspend fun startSession(): ClientSession

	/**
	 * Creates a client session.
	 *
	 * Note: A ClientSession instance can not be used concurrently in multiple asynchronous operations.
	 *
	 * @param options  the options for the client session
	 * @return the client session
	 * @mongodb.server.release 3.6
	 * @since 3.6
	 */
	public suspend fun startSession(options: ClientSessionOptions): ClientSession


	public companion object
}
