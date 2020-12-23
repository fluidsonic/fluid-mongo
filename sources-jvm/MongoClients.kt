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

import com.mongodb.reactivestreams.client.MongoClients as ReactiveMongoClients
import com.mongodb.*
import org.bson.codecs.configuration.*

/**
 * A factory for MongoClient instances.
 *
 * @since 3.0
 */
public object MongoClients {

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
	public val defaultCodecRegistry: CodecRegistry
		get() = ReactiveMongoClients.getDefaultCodecRegistry()

	/**
	 * Creates a new client with the default connection string "mongodb://localhost".
	 *
	 * @return the client
	 */
	public fun create(): MongoClient =
		ReactiveMongoClients.create().wrap()

	/**
	 * Create a new client with the given connection string.
	 *
	 * @param connectionString the connection
	 * @return the client
	 */
	public fun create(connectionString: String): MongoClient =
		ReactiveMongoClients.create(connectionString).wrap()

	/**
	 * Create a new client with the given connection string.
	 *
	 * @param connectionString the settings
	 * @return the client
	 */
	public fun create(connectionString: ConnectionString): MongoClient =
		ReactiveMongoClients.create(connectionString).wrap()

	/**
	 * Create a new client with the given connection string.
	 *
	 * Note: Intended for driver and library authors to associate extra driver metadata with the connections.
	 *
	 * @param connectionString the settings
	 * @param mongoDriverInformation any driver information to associate with the MongoClient
	 * @return the client
	 * @since 1.3
	 */
	public fun create(connectionString: ConnectionString, mongoDriverInformation: MongoDriverInformation?): MongoClient =
		ReactiveMongoClients.create(connectionString, mongoDriverInformation).wrap()

	/**
	 * Create a new client with the given client settings.
	 *
	 * @param settings the settings
	 * @return the client
	 * @since 3.7
	 */
	public fun create(settings: MongoClientSettings): MongoClient =
		ReactiveMongoClients.create(settings).wrap()

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
	public fun create(settings: MongoClientSettings, mongoDriverInformation: MongoDriverInformation?): MongoClient =
		ReactiveMongoClients.create(settings, mongoDriverInformation).wrap()
}
