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
import com.mongodb.reactivestreams.client.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.*
import org.bson.conversions.*
import java.util.concurrent.*


internal class ReactiveFindFlow<out TResult : Any>(
	private val source: FindPublisher<out TResult>
) : FindFlow<TResult>, Flow<TResult> by source.asFlow() {

	override fun filter(filter: Bson?) = apply {
		source.filter(filter)
	}


	override fun limit(limit: Int) = apply {
		source.limit(limit)
	}


	override fun skip(skip: Int) = apply {
		source.skip(skip)
	}


	override fun maxTime(maxTime: Long, timeUnit: TimeUnit) = apply {
		source.maxTime(maxTime, timeUnit)
	}


	override fun maxAwaitTime(maxAwaitTime: Long, timeUnit: TimeUnit) = apply {
		source.maxAwaitTime(maxAwaitTime, timeUnit)
	}


	override fun projection(projection: Bson?) = apply {
		source.projection(projection)
	}


	override fun sort(sort: Bson?) = apply {
		source.sort(sort)
	}


	override fun noCursorTimeout(noCursorTimeout: Boolean) = apply {
		source.noCursorTimeout(noCursorTimeout)
	}


	@Deprecated("oplogReplay has been deprecated in MongoDB 4.4.")
	@Suppress("DEPRECATION")
	override fun oplogReplay(oplogReplay: Boolean) = apply {
		source.oplogReplay(oplogReplay)
	}


	override fun partial(partial: Boolean) = apply {
		source.partial(partial)
	}


	override fun cursorType(cursorType: CursorType) = apply {
		source.cursorType(cursorType)
	}


	override fun batchSize(batchSize: Int) = apply {
		source.batchSize(batchSize)
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


	override fun max(max: Bson?) = apply {
		source.max(max)
	}


	override fun min(min: Bson?) = apply {
		source.min(min)
	}


	override fun returnKey(returnKey: Boolean) = apply {
		source.returnKey(returnKey)
	}


	override fun showRecordId(showRecordId: Boolean) = apply {
		source.showRecordId(showRecordId)
	}


	override suspend fun firstOrNull(): TResult? =
		source.first().awaitFirstOrNull()
}


internal fun <TResult : Any> FindPublisher<out TResult>.wrap() =
	ReactiveFindFlow(this)
