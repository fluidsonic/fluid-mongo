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
import org.bson.conversions.*
import java.util.concurrent.*


internal class CoroutineAggregateIterable<TResult>(
	override val async: com.mongodb.async.client.AggregateIterable<TResult>
) : CoroutineMongoIterable<TResult>(async), AggregateIterable<TResult> {

	override fun allowDiskUse(allowDiskUse: Boolean?): AggregateIterable<TResult> {
		async.allowDiskUse(allowDiskUse)
		return this
	}


	override fun maxTime(maxTime: Long, timeUnit: TimeUnit): AggregateIterable<TResult> {
		async.maxTime(maxTime, timeUnit)
		return this
	}


	override fun maxAwaitTime(maxAwaitTime: Long, timeUnit: TimeUnit): AggregateIterable<TResult> {
		async.maxAwaitTime(maxAwaitTime, timeUnit)
		return this
	}


	override fun batchSize(batchSize: Int): AggregateIterable<TResult> {
		async.batchSize(batchSize)
		return this
	}


	override fun bypassDocumentValidation(bypassDocumentValidation: Boolean?): AggregateIterable<TResult> {
		async.bypassDocumentValidation(bypassDocumentValidation)
		return this
	}


	override fun collation(collation: Collation?): AggregateIterable<TResult> {
		async.collation(collation)
		return this
	}


	override fun comment(comment: String?): AggregateIterable<TResult> {
		async.comment(comment)
		return this
	}


	override fun hint(hint: Bson?): AggregateIterable<TResult> {
		async.hint(hint)
		return this
	}
}


internal fun <TResult> com.mongodb.async.client.AggregateIterable<TResult>.wrap() =
	CoroutineAggregateIterable(this)
