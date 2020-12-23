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

import com.mongodb.reactivestreams.client.MongoClient as ReactiveMongoClient
import com.mongodb.*
import kotlin.reflect.*
import kotlinx.coroutines.reactive.*
import org.bson.conversions.*


internal class ReactiveCoroutineMongoClient(
	private val source: ReactiveMongoClient,
) : MongoClient {

	override fun getDatabase(name: String) =
		source.getDatabase(name).wrap()


	override fun close() {
		source.close()
	}


	override fun listDatabaseNames() =
		source.listDatabaseNames().asFlow()


	override fun listDatabaseNames(clientSession: ClientSession) =
		source.listDatabaseNames(clientSession.unwrap()).asFlow()


	override fun listDatabases() =
		source.listDatabases().wrap()


	override fun listDatabases(clientSession: ClientSession) =
		source.listDatabases(clientSession.unwrap()).wrap()


	override fun <TResult : Any> listDatabases(resultClass: KClass<out TResult>) =
		source.listDatabases(resultClass.java).wrap()


	override fun <TResult : Any> listDatabases(clientSession: ClientSession, resultClass: KClass<out TResult>) =
		source.listDatabases(clientSession.unwrap(), resultClass.java).wrap()


	override fun watch() =
		source.watch().wrap()


	override fun <TResult : Any> watch(resultClass: KClass<out TResult>) =
		source.watch(resultClass.java).wrap()


	override fun watch(pipeline: List<Bson>) =
		source.watch(pipeline).wrap()


	override fun <TResult : Any> watch(pipeline: List<Bson>, resultClass: KClass<out TResult>) =
		source.watch(pipeline, resultClass.java).wrap()


	override fun watch(clientSession: ClientSession) =
		source.watch(clientSession.unwrap()).wrap()


	override fun <TResult : Any> watch(clientSession: ClientSession, resultClass: KClass<out TResult>) =
		source.watch(clientSession.unwrap(), resultClass.java).wrap()


	override fun watch(clientSession: ClientSession, pipeline: List<Bson>) =
		source.watch(clientSession.unwrap(), pipeline).wrap()


	override fun <TResult : Any> watch(clientSession: ClientSession, pipeline: List<Bson>, resultClass: KClass<out TResult>) =
		source.watch(clientSession.unwrap(), pipeline, resultClass.java).wrap()


	override suspend fun startSession() =
		source.startSession().awaitFirst().wrap()


	override suspend fun startSession(options: ClientSessionOptions) =
		source.startSession(options).awaitFirst().wrap()
}


internal fun ReactiveMongoClient.wrap() =
	ReactiveCoroutineMongoClient(source = this)
