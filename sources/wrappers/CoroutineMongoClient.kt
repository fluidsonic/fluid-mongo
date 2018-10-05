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
import org.bson.conversions.Bson


internal class CoroutineMongoClient(
	override val async: com.mongodb.async.client.MongoClient
) : MongoClient {

	override suspend fun startSession() =
		withCallback<com.mongodb.async.client.ClientSession> { async.startSession(it) }.wrap()


	override suspend fun startSession(options: ClientSessionOptions) =
		withCallback<com.mongodb.async.client.ClientSession> { async.startSession(options, it) }.wrap()


	override fun getDatabase(name: String) =
		async.getDatabase(name).wrap()


	override fun close() =
		async.close()


	override fun listDatabaseNames() =
		async.listDatabaseNames().wrap()


	override fun listDatabaseNames(clientSession: ClientSession) =
		async.listDatabaseNames(clientSession.async).wrap()


	override fun listDatabases() =
		async.listDatabases().wrap()


	override fun listDatabases(clientSession: ClientSession) =
		async.listDatabases(clientSession.async).wrap()


	override fun <TResult> listDatabases(resultClass: Class<TResult>) =
		async.listDatabases(resultClass).wrap()


	override fun <TResult> listDatabases(clientSession: ClientSession, resultClass: Class<TResult>) =
		async.listDatabases(clientSession.async, resultClass).wrap()


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


internal fun com.mongodb.async.client.MongoClient.wrap() =
	CoroutineMongoClient(this)
