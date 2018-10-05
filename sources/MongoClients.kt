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

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoDriverInformation
import com.mongodb.async.client.MongoClients
import org.bson.codecs.configuration.CodecRegistry

/**
 * A factory for MongoClient instances.
 *
 * @since 3.0
 */
object MongoClients {

	/**
	 * Gets the default codec registry.  It includes the following providers:
	 *
	 *  * [org.bson.codecs.ValueCodecProvider]
	 *  * [org.bson.codecs.BsonValueCodecProvider]
	 *  * [com.mongodb.DBRefCodecProvider]
	 *  * [org.bson.codecs.DocumentCodecProvider]
	 *  * [org.bson.codecs.IterableCodecProvider]
	 *  * [org.bson.codecs.MapCodecProvider]
	 *  * [com.mongodb.client.model.geojson.codecs.GeoJsonCodecProvider]
	 *  * [com.mongodb.client.gridfs.codecs.GridFSFileCodecProvider]
	 *  * [org.bson.codecs.jsr310.Jsr310CodecProvider]
	 *
	 * @return the default codec registry
	 * @see com.mongodb.MongoClientSettings.getCodecRegistry
	 * @since 3.1
	 */
	val defaultCodecRegistry: CodecRegistry
		get() = MongoClients.getDefaultCodecRegistry()

	/**
	 * Creates a new client with the default connection string "mongodb://localhost".
	 *
	 * @return the client
	 */
	fun create(): MongoClient =
		MongoClients.create().wrap()

	/**
	 * Create a new client with the given connection string as if by a call to [.create].
	 *
	 * @param connectionString the connection
	 * @return the client
	 * @see .create
	 */
	fun create(connectionString: String): MongoClient =
		MongoClients.create(connectionString).wrap()

	/**
	 * Create a new client with the given connection string.
	 *
	 * For each of the settings classed configurable via [com.mongodb.MongoClientSettings], the connection string is applied by
	 * calling the `applyConnectionString` method on an instance of setting's builder class, building the setting, and adding it to
	 * an instance of [com.mongodb.MongoClientSettings.Builder].
	 *
	 * The connection string's stream type is then applied by setting the
	 * [com.mongodb.connection.StreamFactory] to an instance of NettyStreamFactory,
	 *
	 * @param connectionString the settings
	 * @return the client
	 * @throws IllegalArgumentException if the connection string's stream type is not one of "netty" or "nio2"
	 *
	 * @see ConnectionString.getStreamType
	 * @see com.mongodb.MongoClientSettings.Builder
	 *
	 * @see com.mongodb.connection.ClusterSettings.Builder.applyConnectionString
	 * @see com.mongodb.connection.ConnectionPoolSettings.Builder.applyConnectionString
	 * @see com.mongodb.connection.ServerSettings.Builder.applyConnectionString
	 * @see com.mongodb.connection.SslSettings.Builder.applyConnectionString
	 * @see com.mongodb.connection.SocketSettings.Builder.applyConnectionString
	 */
	fun create(connectionString: ConnectionString): MongoClient =
		MongoClients.create(connectionString).wrap()

	/**
	 * Create a new client with the given connection string.
	 *
	 * Note: Intended for driver and library authors to associate extra driver metadata with the connections.
	 *
	 * @param connectionString       the settings
	 * @param mongoDriverInformation any driver information to associate with the MongoClient
	 * @return the client
	 * @throws IllegalArgumentException if the connection string's stream type is not one of "netty" or "nio2"
	 * @see MongoClients.create
	 */
	fun create(connectionString: ConnectionString, mongoDriverInformation: MongoDriverInformation?): MongoClient =
		MongoClients.create(connectionString, mongoDriverInformation).wrap()

	/**
	 * Create a new client with the given client settings.
	 *
	 * @param settings the settings
	 * @return the client
	 * @since 3.7
	 */
	fun create(settings: MongoClientSettings): MongoClient =
		MongoClients.create(settings).wrap()

	/**
	 * Creates a new client with the given client settings.
	 *
	 * Note: Intended for driver and library authors to associate extra driver metadata with the connections.
	 *
	 * @param settings               the settings
	 * @param mongoDriverInformation any driver information to associate with the MongoClient
	 * @return the client
	 * @since 3.7
	 */
	fun create(settings: MongoClientSettings, mongoDriverInformation: MongoDriverInformation?): MongoClient =
		MongoClients.create(settings, mongoDriverInformation).wrap()
}
