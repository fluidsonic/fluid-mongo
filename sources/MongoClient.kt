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

package com.github.fluidsonic.fluid.mongo

import com.mongodb.ClientSessionOptions
import com.mongodb.annotations.Immutable
import org.bson.Document
import org.bson.conversions.Bson
import java.io.Closeable

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
interface MongoClient : Closeable {

	/**
	 * The underlying object from the async driver.
	 */
	val async: com.mongodb.async.client.MongoClient

	/**
	 * Creates a client session with default options.
	 *
	 * Note: A ClientSession instance can not be used concurrently in multiple asynchronous operations.
	 *
	 * @return the client session
	 * @mongodb.server.release 3.6
	 * @since 3.8
	 */
	suspend fun startSession(): ClientSession

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
	suspend fun startSession(options: ClientSessionOptions): ClientSession

	/**
	 * Gets the database with the given name.
	 *
	 * @param name the name of the database
	 * @return the database
	 * @throws IllegalArgumentException if databaseName is invalid
	 * @see com.mongodb.MongoNamespace.checkDatabaseNameValidity
	 */
	fun getDatabase(name: String): MongoDatabase

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
	fun listDatabaseNames(): MongoIterable<String>

	/**
	 * Get a list of the database names
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @return an iterable containing all the names of all the databases
	 * @mongodb.driver.manual reference/command/listDatabases List Databases
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun listDatabaseNames(clientSession: ClientSession): MongoIterable<String>

	/**
	 * Gets the list of databases
	 *
	 * @return the list databases iterable interface
	 */
	fun listDatabases(): ListDatabasesIterable<Document>

	/**
	 * Gets the list of databases
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @return the list databases iterable interface
	 * @mongodb.driver.manual reference/command/listDatabases List Databases
	 * @since 3.6
	 * @mongodb.server.release 3.6
	 */
	fun listDatabases(clientSession: ClientSession): ListDatabasesIterable<Document>

	/**
	 * Gets the list of databases
	 *
	 * @param resultClass the class to cast the database documents to
	 * @param <TResult>   the type of the class to use instead of `Document`.
	 * @return the list databases iterable interface
	 */
	fun <TResult> listDatabases(resultClass: Class<TResult>): ListDatabasesIterable<TResult>

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
	fun <TResult> listDatabases(clientSession: ClientSession, resultClass: Class<TResult>): ListDatabasesIterable<TResult>

	/**
	 * Creates a change stream for this client.
	 *
	 * @return the change stream iterable
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 * @since 3.8
	 * @mongodb.server.release 4.0
	 */
	fun watch(): ChangeStreamIterable<Document>

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
	fun <TResult> watch(resultClass: Class<TResult>): ChangeStreamIterable<TResult>

	/**
	 * Creates a change stream for this client.
	 *
	 * @param pipeline the aggregation pipeline to apply to the change stream.
	 * @return the change stream iterable
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 * @since 3.8
	 * @mongodb.server.release 4.0
	 */
	fun watch(pipeline: List<Bson>): ChangeStreamIterable<Document>

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
	fun <TResult> watch(pipeline: List<Bson>, resultClass: Class<TResult>): ChangeStreamIterable<TResult>

	/**
	 * Creates a change stream for this client.
	 *
	 * @param clientSession the client session with which to associate this operation
	 * @return the change stream iterable
	 * @since 3.8
	 * @mongodb.server.release 4.0
	 * @mongodb.driver.dochub core/changestreams Change Streams
	 */
	fun watch(clientSession: ClientSession): ChangeStreamIterable<Document>

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
	fun <TResult> watch(clientSession: ClientSession, resultClass: Class<TResult>): ChangeStreamIterable<TResult>

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
	fun watch(clientSession: ClientSession, pipeline: List<Bson>): ChangeStreamIterable<Document>

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
	fun <TResult> watch(clientSession: ClientSession, pipeline: List<Bson>, resultClass: Class<TResult>): ChangeStreamIterable<TResult>
}
