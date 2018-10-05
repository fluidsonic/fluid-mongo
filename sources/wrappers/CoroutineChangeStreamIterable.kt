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

import com.mongodb.client.model.Collation
import com.mongodb.client.model.changestream.ChangeStreamDocument
import com.mongodb.client.model.changestream.FullDocument
import org.bson.BsonDocument
import org.bson.BsonTimestamp
import java.util.concurrent.TimeUnit


internal class CoroutineChangeStreamIterable<TResult>(
	override val async: com.mongodb.async.client.ChangeStreamIterable<TResult>
) : CoroutineMongoIterable<ChangeStreamDocument<out TResult>>(async), ChangeStreamIterable<TResult> {

	override fun fullDocument(fullDocument: FullDocument): ChangeStreamIterable<TResult> {
		async.fullDocument(fullDocument)
		return this
	}


	override fun resumeAfter(resumeToken: BsonDocument): ChangeStreamIterable<TResult> {
		async.resumeAfter(resumeToken)
		return this
	}


	override fun batchSize(batchSize: Int): ChangeStreamIterable<TResult> {
		async.batchSize(batchSize)
		return this
	}


	override fun maxAwaitTime(maxAwaitTime: Long, timeUnit: TimeUnit): ChangeStreamIterable<TResult> {
		async.maxAwaitTime(maxAwaitTime, timeUnit)
		return this
	}


	override fun collation(collation: Collation?): ChangeStreamIterable<TResult> {
		async.collation(collation)
		return this
	}


	override fun <TDocument> withDocumentClass(clazz: Class<TDocument>) =
		async.withDocumentClass(clazz).wrap()


	override fun startAtOperationTime(startAtOperationTime: BsonTimestamp): ChangeStreamIterable<TResult> {
		async.startAtOperationTime(startAtOperationTime)
		return this
	}
}


internal fun <TResult> com.mongodb.async.client.ChangeStreamIterable<TResult>.wrap() =
	CoroutineChangeStreamIterable(this)
