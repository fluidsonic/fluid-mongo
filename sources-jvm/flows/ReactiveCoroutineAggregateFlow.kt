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
import java.util.concurrent.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.*
import org.bson.conversions.*


internal class ReactiveCoroutineAggregateFlow<out TResult : Any>(
	private val source: AggregatePublisher<out TResult>,
) : AggregateFlow<TResult>, Flow<TResult> by source.asFlow() {

	override fun allowDiskUse(allowDiskUse: Boolean?) = apply {
		source.allowDiskUse(allowDiskUse)
	}


	override fun maxTime(maxTime: Long, timeUnit: TimeUnit) = apply {
		source.maxTime(maxTime, timeUnit)
	}


	override fun maxAwaitTime(maxAwaitTime: Long, timeUnit: TimeUnit) = apply {
		source.maxAwaitTime(maxAwaitTime, timeUnit)
	}


	override fun batchSize(batchSize: Int) = apply {
		source.batchSize(batchSize)
	}


	override fun bypassDocumentValidation(bypassDocumentValidation: Boolean?) = apply {
		source.bypassDocumentValidation(bypassDocumentValidation)
	}


	override suspend fun toCollection() {
		source.toCollection().awaitCompletion()
	}


	override fun collation(collation: Collation?) = apply {
		source.collation(collation)
	}


	override fun comment(comment: String?) = apply {
		source.comment(comment)
	}


	override fun hint(hint: Bson?) = apply {
		source.hint(hint)
	}


	override suspend fun firstOrNull(): TResult? =
		source.first().awaitFirstOrNull()
}


internal fun <TResult : Any> AggregatePublisher<out TResult>.wrap() =
	ReactiveCoroutineAggregateFlow(source = this)
