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
import org.bson.conversions.*
import java.util.concurrent.*


internal class CoroutineFindIterable<TResult>(
	override val async: com.mongodb.async.client.FindIterable<TResult>
) : CoroutineMongoIterable<TResult>(async), FindIterable<TResult> {

	override fun filter(filter: Bson?): FindIterable<TResult> {
		async.filter(filter)
		return this
	}


	override fun limit(limit: Int): FindIterable<TResult> {
		async.limit(limit)
		return this
	}


	override fun skip(skip: Int): FindIterable<TResult> {
		async.skip(skip)
		return this
	}


	override fun maxTime(maxTime: Long, timeUnit: TimeUnit): FindIterable<TResult> {
		async.maxTime(maxTime, timeUnit)
		return this
	}


	override fun maxAwaitTime(maxAwaitTime: Long, timeUnit: TimeUnit): FindIterable<TResult> {
		async.maxAwaitTime(maxAwaitTime, timeUnit)
		return this
	}


	override fun projection(projection: Bson?): FindIterable<TResult> {
		async.projection(projection)
		return this
	}


	override fun sort(sort: Bson?): FindIterable<TResult> {
		async.sort(sort)
		return this
	}


	override fun noCursorTimeout(noCursorTimeout: Boolean): FindIterable<TResult> {
		async.noCursorTimeout(noCursorTimeout)
		return this
	}


	override fun oplogReplay(oplogReplay: Boolean): FindIterable<TResult> {
		async.oplogReplay(oplogReplay)
		return this
	}


	override fun partial(partial: Boolean): FindIterable<TResult> {
		async.partial(partial)
		return this
	}


	override fun cursorType(cursorType: CursorType): FindIterable<TResult> {
		async.cursorType(cursorType)
		return this
	}


	override fun batchSize(batchSize: Int): FindIterable<TResult> {
		async.batchSize(batchSize)
		return this
	}


	override fun collation(collation: Collation?): FindIterable<TResult> {
		async.collation(collation)
		return this
	}


	override fun comment(comment: String?): FindIterable<TResult> {
		async.comment(comment)
		return this
	}


	override fun hint(hint: Bson?): FindIterable<TResult> {
		async.hint(hint)
		return this
	}


	override fun max(max: Bson?): FindIterable<TResult> {
		async.max(max)
		return this
	}


	override fun min(min: Bson?): FindIterable<TResult> {
		async.min(min)
		return this
	}


	override fun returnKey(returnKey: Boolean): FindIterable<TResult> {
		async.returnKey(returnKey)
		return this
	}


	override fun showRecordId(showRecordId: Boolean): FindIterable<TResult> {
		async.showRecordId(showRecordId)
		return this
	}
}


internal fun <TResult> com.mongodb.async.client.FindIterable<TResult>.wrap() =
	CoroutineFindIterable(this)
