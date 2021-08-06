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

import com.mongodb.reactivestreams.client.ClientSession as ReactiveClientSession
import com.mongodb.session.ClientSession as BaseClientSession
import com.mongodb.*
import com.mongodb.session.*
import org.bson.*


internal class ReactiveCoroutineClientSession(
	private val source: ReactiveClientSession,
) : ClientSession {

	override suspend fun abortTransaction() {
		source.abortTransaction().awaitCompletion()
	}


	override fun advanceClusterTime(clusterTime: BsonDocument) {
		source.advanceClusterTime(clusterTime)
	}


	override fun advanceOperationTime(operationTime: BsonTimestamp) {
		source.advanceOperationTime(operationTime)
	}


	override fun clearTransactionContext() {
		source.clearTransactionContext()
	}


	override fun close() {
		source.close()
	}


	override suspend fun commitTransaction() {
		source.commitTransaction().awaitCompletion()
	}


	override fun getClusterTime(): BsonDocument =
		source.clusterTime


	override fun getOperationTime(): BsonTimestamp =
		source.operationTime


	override fun getOptions(): ClientSessionOptions =
		source.options


	override fun getOriginator(): Any =
		source.originator


	override fun getPinnedServerAddress(): ServerAddress? =
		source.pinnedServerAddress


	override fun getRecoveryToken(): BsonDocument? =
		source.recoveryToken


	override fun getServerSession(): ServerSession =
		source.serverSession


	override fun getSnapshotTimestamp(): BsonTimestamp? =
		source.snapshotTimestamp


	override fun getTransactionContext(): Any? =
		source.transactionContext


	override fun hasActiveTransaction(): Boolean =
		source.hasActiveTransaction()


	override fun isCausallyConsistent(): Boolean =
		source.isCausallyConsistent


	override fun setRecoveryToken(recoveryToken: BsonDocument) {
		source.recoveryToken = recoveryToken
	}


	override fun setSnapshotTimestamp(snapshotTimestamp: BsonTimestamp) {
		source.snapshotTimestamp = snapshotTimestamp
	}


	override fun setTransactionContext(address: ServerAddress, transactionContext: Any) {
		source.setTransactionContext(address, transactionContext)
	}


	override fun startTransaction() {
		source.startTransaction()
	}


	override fun startTransaction(transactionOptions: TransactionOptions) {
		source.startTransaction(transactionOptions)
	}


	override val transactionOptions: TransactionOptions
		get() = source.transactionOptions


	fun unwrap() =
		source
}


internal fun BaseClientSession.unwrap() =
	(this as ReactiveCoroutineClientSession).unwrap()


internal fun ReactiveClientSession.wrap() =
	ReactiveCoroutineClientSession(source = this)
