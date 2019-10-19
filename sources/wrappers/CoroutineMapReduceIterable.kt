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


internal class CoroutineMapReduceIterable<TResult>(
	override val async: com.mongodb.async.client.MapReduceIterable<TResult>
) : CoroutineMongoIterable<TResult>(async), MapReduceIterable<TResult> {

	override fun collectionName(collectionName: String): MapReduceIterable<TResult> {
		async.collectionName(collectionName)
		return this
	}


	override fun finalizeFunction(finalizeFunction: String?): MapReduceIterable<TResult> {
		async.finalizeFunction(finalizeFunction)
		return this
	}


	override fun scope(scope: Bson?): MapReduceIterable<TResult> {
		async.scope(scope)
		return this
	}


	override fun sort(sort: Bson?): MapReduceIterable<TResult> {
		async.sort(sort)
		return this
	}


	override fun filter(filter: Bson?): MapReduceIterable<TResult> {
		async.filter(filter)
		return this
	}


	override fun limit(limit: Int): MapReduceIterable<TResult> {
		async.limit(limit)
		return this
	}


	override fun jsMode(jsMode: Boolean): MapReduceIterable<TResult> {
		async.jsMode(jsMode)
		return this
	}


	override fun verbose(verbose: Boolean): MapReduceIterable<TResult> {
		async.verbose(verbose)
		return this
	}


	override fun maxTime(maxTime: Long, timeUnit: TimeUnit): MapReduceIterable<TResult> {
		async.maxTime(maxTime, timeUnit)
		return this
	}


	override fun action(action: MapReduceAction): MapReduceIterable<TResult> {
		async.action(action)
		return this
	}


	override fun databaseName(databaseName: String?): MapReduceIterable<TResult> {
		async.databaseName(databaseName)
		return this
	}


	override fun sharded(sharded: Boolean): MapReduceIterable<TResult> {
		async.sharded(sharded)
		return this
	}


	override fun nonAtomic(nonAtomic: Boolean): MapReduceIterable<TResult> {
		async.nonAtomic(nonAtomic)
		return this
	}


	override fun batchSize(batchSize: Int): MapReduceIterable<TResult> {
		async.batchSize(batchSize)
		return this
	}


	override fun bypassDocumentValidation(bypassDocumentValidation: Boolean?): MapReduceIterable<TResult> {
		async.bypassDocumentValidation(bypassDocumentValidation)
		return this
	}


	override fun collation(collation: Collation?): MapReduceIterable<TResult> {
		async.collation(collation)
		return this
	}
}


internal fun <TResult> com.mongodb.async.client.MapReduceIterable<TResult>.wrap() =
	CoroutineMapReduceIterable(this)
