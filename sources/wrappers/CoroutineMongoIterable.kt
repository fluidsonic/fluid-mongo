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

import com.mongodb.async.AsyncBatchCursor
import com.mongodb.async.SingleResultCallback
import kotlinx.coroutines.channels.ReceiveChannel


internal open class CoroutineMongoIterable<out TResult>(
	override val async: com.mongodb.async.client.MongoIterable<out TResult>
) : MongoIterable<TResult>, ReceiveChannel<TResult> by async.toReceiveChannel() {

	override val batchSize: Int?
		get() = async.batchSize


	@Suppress("UNCHECKED_CAST")
	override suspend fun first(): TResult? =
		withCallback<TResult> { (async as com.mongodb.async.client.MongoIterable<TResult>).first(it) }


	override fun batchSize(batchSize: Int): MongoIterable<TResult> {
		async.batchSize(batchSize)
		return this
	}


	@Suppress("UNCHECKED_CAST")
	override suspend fun batchCursor() =
		withCallback<AsyncBatchCursor<out TResult>> {
			(async as com.mongodb.async.client.MongoIterable<TResult>).batchCursor(it as SingleResultCallback<AsyncBatchCursor<TResult>>)
		}
}


internal fun <TResult> com.mongodb.async.client.MongoIterable<TResult>.wrap() =
	CoroutineMongoIterable(this)
