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
import org.bson.*
import org.bson.codecs.configuration.*
import org.bson.conversions.*


internal class CoroutineMongoDatabase(
	override val async: com.mongodb.async.client.MongoDatabase
) : MongoDatabase {

	override val name: String
		get() = async.name

	override val codecRegistry: CodecRegistry
		get() = async.codecRegistry

	override val readPreference: ReadPreference
		get() = async.readPreference

	override val writeConcern: WriteConcern
		get() = async.writeConcern

	override val readConcern: ReadConcern
		get() = async.readConcern


	override fun withCodecRegistry(codecRegistry: CodecRegistry) =
		async.withCodecRegistry(codecRegistry).wrap()


	override fun withReadPreference(readPreference: ReadPreference) =
		async.withReadPreference(readPreference).wrap()


	override fun withWriteConcern(writeConcern: WriteConcern) =
		async.withWriteConcern(writeConcern).wrap()


	override fun withReadConcern(readConcern: ReadConcern) =
		async.withReadConcern(readConcern).wrap()


	override fun getCollection(collectionName: String) =
		async.getCollection(collectionName).wrap()


	override fun <TDocument : Any> getCollection(collectionName: String, documentClass: Class<TDocument>) =
		async.getCollection(collectionName, documentClass).wrap()


	override suspend fun runCommand(command: Bson) =
		withCallback<Document> { async.runCommand(command, it) }


	override suspend fun runCommand(command: Bson, readPreference: ReadPreference) =
		withCallback<Document> { async.runCommand(command, readPreference, it) }


	override suspend fun <TResult> runCommand(command: Bson, resultClass: Class<TResult>) =
		withCallback<TResult> { async.runCommand(command, resultClass, it) }


	override suspend fun <TResult> runCommand(command: Bson, readPreference: ReadPreference, resultClass: Class<TResult>) =
		withCallback<TResult> { async.runCommand(command, readPreference, resultClass, it) }


	override suspend fun runCommand(clientSession: ClientSession, command: Bson) =
		withCallback<Document> { async.runCommand(clientSession.async, command, it) }


	override suspend fun runCommand(clientSession: ClientSession, command: Bson, readPreference: ReadPreference) =
		withCallback<Document> { async.runCommand(clientSession.async, command, readPreference, it) }


	override suspend fun <TResult> runCommand(clientSession: ClientSession, command: Bson, resultClass: Class<TResult>) =
		withCallback<TResult> { async.runCommand(clientSession.async, command, resultClass, it) }


	override suspend fun <TResult> runCommand(clientSession: ClientSession, command: Bson, readPreference: ReadPreference, resultClass: Class<TResult>) =
		withCallback<TResult> { async.runCommand(clientSession.async, command, readPreference, resultClass, it) }


	override suspend fun drop() {
		withCallback<Void> { async.drop(it) }
	}


	override suspend fun drop(clientSession: ClientSession) {
		withCallback<Void> { async.drop(clientSession.async, it) }
	}


	override fun listCollectionNames() =
		async.listCollectionNames().wrap()


	override fun listCollectionNames(clientSession: ClientSession) =
		async.listCollectionNames(clientSession.async).wrap()


	override fun listCollections() =
		async.listCollections().wrap()


	override fun <TResult> listCollections(resultClass: Class<TResult>) =
		async.listCollections(resultClass).wrap()


	override fun listCollections(clientSession: ClientSession) =
		async.listCollections(clientSession.async).wrap()


	override fun <TResult> listCollections(clientSession: ClientSession, resultClass: Class<TResult>) =
		async.listCollections(clientSession.async, resultClass).wrap()


	override suspend fun createCollection(collectionName: String) {
		withCallback<Void> { async.createCollection(collectionName, it) }
	}


	override suspend fun createCollection(collectionName: String, options: CreateCollectionOptions) {
		withCallback<Void> { async.createCollection(collectionName, options, it) }
	}


	override suspend fun createCollection(clientSession: ClientSession, collectionName: String) {
		withCallback<Void> { async.createCollection(clientSession.async, collectionName, it) }
	}


	override suspend fun createCollection(clientSession: ClientSession, collectionName: String, options: CreateCollectionOptions) {
		withCallback<Void> { async.createCollection(clientSession.async, collectionName, options, it) }
	}


	override suspend fun createView(viewName: String, viewOn: String, pipeline: List<Bson>) {
		withCallback<Void> { async.createView(viewName, viewOn, pipeline, it) }
	}


	override suspend fun createView(viewName: String, viewOn: String, pipeline: List<Bson>, createViewOptions: CreateViewOptions) {
		withCallback<Void> { async.createView(viewName, viewOn, pipeline, createViewOptions, it) }
	}


	override suspend fun createView(clientSession: ClientSession, viewName: String, viewOn: String, pipeline: List<Bson>) {
		withCallback<Void> { async.createView(clientSession.async, viewName, viewOn, pipeline, it) }
	}


	override suspend fun createView(clientSession: ClientSession, viewName: String, viewOn: String, pipeline: List<Bson>, createViewOptions: CreateViewOptions) {
		withCallback<Void> { async.createView(clientSession.async, viewName, viewOn, pipeline, createViewOptions, it) }
	}


	override fun watch() =
		async.watch().wrap()


	override fun <TResult> watch(resultClass: Class<TResult>) =
		async.watch(resultClass).wrap()


	override fun watch(pipeline: List<Bson>) =
		async.watch(pipeline).wrap()


	override fun <TResult> watch(pipeline: List<Bson>, resultClass: Class<TResult>) =
		async.watch(pipeline, resultClass).wrap()


	override fun watch(clientSession: ClientSession) =
		async.watch(clientSession.async).wrap()


	override fun <TResult> watch(clientSession: ClientSession, resultClass: Class<TResult>) =
		async.watch(clientSession.async, resultClass).wrap()


	override fun watch(clientSession: ClientSession, pipeline: List<Bson>) =
		async.watch(clientSession.async, pipeline).wrap()


	override fun <TResult> watch(clientSession: ClientSession, pipeline: List<Bson>, resultClass: Class<TResult>) =
		async.watch(clientSession.async, pipeline, resultClass).wrap()
}


internal fun com.mongodb.async.client.MongoDatabase.wrap() =
	CoroutineMongoDatabase(this)
