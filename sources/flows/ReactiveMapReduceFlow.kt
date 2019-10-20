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
import com.mongodb.reactivestreams.client.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.*
import org.bson.conversions.*
import java.util.concurrent.*


internal class ReactiveMapReduceFlow<out TResult : Any>(
	private val source: MapReducePublisher<out TResult>
) : MapReduceFlow<TResult>, Flow<TResult> by source.asFlow() {

	override fun collectionName(collectionName: String) = apply {
		source.collectionName(collectionName)
	}


	override fun finalizeFunction(finalizeFunction: String?) = apply {
		source.finalizeFunction(finalizeFunction)
	}


	override fun scope(scope: Bson?) = apply {
		source.scope(scope)
	}


	override fun sort(sort: Bson?) = apply {
		source.sort(sort)
	}


	override fun filter(filter: Bson?) = apply {
		source.filter(filter)
	}


	override fun limit(limit: Int) = apply {
		source.limit(limit)
	}


	override fun jsMode(jsMode: Boolean) = apply {
		source.jsMode(jsMode)
	}


	override fun verbose(verbose: Boolean) = apply {
		source.verbose(verbose)
	}


	override fun maxTime(maxTime: Long, timeUnit: TimeUnit) = apply {
		source.maxTime(maxTime, timeUnit)
	}


	override fun action(action: MapReduceAction) = apply {
		source.action(action)
	}


	override fun databaseName(databaseName: String?) = apply {
		source.databaseName(databaseName)
	}


	override fun sharded(sharded: Boolean) = apply {
		source.sharded(sharded)
	}


	override fun nonAtomic(nonAtomic: Boolean) = apply {
		source.nonAtomic(nonAtomic)
	}


	override fun bypassDocumentValidation(bypassDocumentValidation: Boolean?) = apply {
		source.bypassDocumentValidation(bypassDocumentValidation)
	}


	override suspend fun toCollection() {
		source.toCollection().awaitSuccess()
	}


	override fun collation(collation: Collation?) = apply {
		source.collation(collation)
	}


	override fun batchSize(batchSize: Int) = apply {
		source.batchSize(batchSize)
	}


	override suspend fun firstOrNull(): TResult? =
		source.first().awaitFirstOrNull()
}


internal fun <TResult : Any> MapReducePublisher<out TResult>.wrap() =
	ReactiveMapReduceFlow(this)
