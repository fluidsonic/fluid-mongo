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
import com.mongodb.ServerAddress
import com.mongodb.TransactionOptions
import com.mongodb.session.ServerSession
import org.bson.BsonDocument
import org.bson.BsonTimestamp


internal class CoroutineClientSession(
	override val async: com.mongodb.async.client.ClientSession
) : ClientSession {

	override val transactionOptions: TransactionOptions?
		get() = async.transactionOptions


	override fun getPinnedServerAddress(): ServerAddress? =
		async.pinnedServerAddress


	override fun getRecoveryToken(): BsonDocument? =
		async.recoveryToken


	override fun hasActiveTransaction() =
		async.hasActiveTransaction()


	override fun notifyMessageSent() =
		async.notifyMessageSent()


	override fun setPinnedServerAddress(address: ServerAddress?) {
		async.pinnedServerAddress = address
	}


	override fun setRecoveryToken(recoveryToken: BsonDocument?) {
		async.recoveryToken = recoveryToken
	}


	override fun startTransaction() =
		async.startTransaction()


	override fun startTransaction(transactionOptions: TransactionOptions) =
		async.startTransaction(transactionOptions)


	override suspend fun commitTransaction() {
		withCallback<Void> { async.commitTransaction(it) }
	}


	override suspend fun abortTransaction() {
		withCallback<Void> { async.abortTransaction(it) }
	}


	override fun getOriginator(): Any? =
		async.originator


	override fun advanceClusterTime(clusterTime: BsonDocument?) =
		async.advanceClusterTime(clusterTime)


	override fun isCausallyConsistent() =
		async.isCausallyConsistent


	override fun getClusterTime(): BsonDocument? =
		async.clusterTime


	override fun getOptions(): ClientSessionOptions =
		async.options


	override fun getOperationTime(): BsonTimestamp? =
		async.operationTime


	override fun close() =
		async.close()


	override fun getServerSession(): ServerSession? =
		async.serverSession


	override fun advanceOperationTime(operationTime: BsonTimestamp?) =
		async.advanceOperationTime(operationTime)
}


internal fun com.mongodb.async.client.ClientSession.wrap() =
	CoroutineClientSession(this)
