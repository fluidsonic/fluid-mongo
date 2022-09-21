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

import com.mongodb.reactivestreams.client.MongoCollection as ReactiveMongoCollection
import com.mongodb.*
import com.mongodb.bulk.*
import com.mongodb.client.model.*
import com.mongodb.client.result.*
import kotlin.reflect.*
import kotlinx.coroutines.reactive.*
import org.bson.codecs.configuration.*
import org.bson.conversions.*


internal class ReactiveCoroutineMongoCollection<TDocument : Any>(
	private val source: ReactiveMongoCollection<TDocument>,
) : MongoCollection<TDocument> {

	override val namespace: MongoNamespace
		get() = source.namespace

	override val documentClass: KClass<TDocument>
		get() = source.documentClass.kotlin

	override val codecRegistry: CodecRegistry
		get() = source.codecRegistry

	override val readPreference: ReadPreference
		get() = source.readPreference

	override val writeConcern: WriteConcern
		get() = source.writeConcern

	override val readConcern: ReadConcern
		get() = source.readConcern


	override fun <NewTDocument : Any> withDocumentClass(newDocumentClass: KClass<NewTDocument>) =
		source.withDocumentClass(newDocumentClass.java).wrap()


	override fun withCodecRegistry(codecRegistry: CodecRegistry) =
		source.withCodecRegistry(codecRegistry).wrap()


	override fun withReadPreference(readPreference: ReadPreference) =
		source.withReadPreference(readPreference).wrap()


	override fun withWriteConcern(writeConcern: WriteConcern) =
		source.withWriteConcern(writeConcern).wrap()


	override fun withReadConcern(readConcern: ReadConcern) =
		source.withReadConcern(readConcern).wrap()


	override suspend fun countDocuments(): Long =
		source.countDocuments().awaitFirst()


	override suspend fun countDocuments(filter: Bson): Long =
		source.countDocuments(filter).awaitFirst()


	override suspend fun countDocuments(filter: Bson, options: CountOptions): Long =
		source.countDocuments(filter, options).awaitFirst()


	override suspend fun countDocuments(clientSession: ClientSession): Long =
		source.countDocuments(clientSession.unwrap()).awaitFirst()


	override suspend fun countDocuments(clientSession: ClientSession, filter: Bson): Long =
		source.countDocuments(clientSession.unwrap(), filter).awaitFirst()


	override suspend fun countDocuments(clientSession: ClientSession, filter: Bson, options: CountOptions): Long =
		source.countDocuments(clientSession.unwrap(), filter, options).awaitFirst()


	override suspend fun estimatedDocumentCount(): Long =
		source.estimatedDocumentCount().awaitFirst()


	override suspend fun estimatedDocumentCount(options: EstimatedDocumentCountOptions): Long =
		source.estimatedDocumentCount(options).awaitFirst()


	override fun <TResult : Any> distinct(fieldName: String, resultClass: KClass<out TResult>) =
		source.distinct(fieldName, resultClass.java).wrap()


	override fun <TResult : Any> distinct(fieldName: String, filter: Bson, resultClass: KClass<out TResult>) =
		source.distinct(fieldName, filter, resultClass.java).wrap()


	override fun <TResult : Any> distinct(clientSession: ClientSession, fieldName: String, resultClass: KClass<out TResult>) =
		source.distinct(clientSession.unwrap(), fieldName, resultClass.java).wrap()


	override fun <TResult : Any> distinct(clientSession: ClientSession, fieldName: String, filter: Bson, resultClass: KClass<out TResult>) =
		source.distinct(clientSession.unwrap(), fieldName, filter, resultClass.java).wrap()


	override fun find() =
		source.find().wrap()


	override fun <TResult : Any> find(resultClass: KClass<out TResult>) =
		source.find(resultClass.java).wrap()


	override fun find(filter: Bson) =
		source.find(filter).wrap()


	override fun <TResult : Any> find(filter: Bson, resultClass: KClass<out TResult>) =
		source.find(filter, resultClass.java).wrap()


	override fun find(clientSession: ClientSession) =
		source.find(clientSession.unwrap()).wrap()


	override fun <TResult : Any> find(clientSession: ClientSession, resultClass: KClass<out TResult>) =
		source.find(clientSession.unwrap(), resultClass.java).wrap()


	override fun find(clientSession: ClientSession, filter: Bson) =
		source.find(clientSession.unwrap(), filter).wrap()


	override fun <TResult : Any> find(clientSession: ClientSession, filter: Bson, resultClass: KClass<out TResult>) =
		source.find(clientSession.unwrap(), filter, resultClass.java).wrap()


	override fun aggregate(pipeline: List<Bson>) =
		source.aggregate(pipeline).wrap()


	override fun <TResult : Any> aggregate(pipeline: List<Bson>, resultClass: KClass<out TResult>) =
		source.aggregate(pipeline, resultClass.java).wrap()


	override fun aggregate(clientSession: ClientSession, pipeline: List<Bson>) =
		source.aggregate(clientSession.unwrap(), pipeline).wrap()


	override fun <TResult : Any> aggregate(clientSession: ClientSession, pipeline: List<Bson>, resultClass: KClass<out TResult>) =
		source.aggregate(clientSession.unwrap(), pipeline, resultClass.java).wrap()


	override fun watch() =
		source.watch().wrap()


	override fun <TResult : Any> watch(resultClass: KClass<out TResult>) =
		source.watch(resultClass.java).wrap()


	override fun watch(pipeline: List<Bson>) =
		source.watch(pipeline).wrap()


	override fun <TResult : Any> watch(pipeline: List<Bson>, resultClass: KClass<out TResult>) =
		source.watch(pipeline, resultClass.java).wrap()


	override fun watch(clientSession: ClientSession) =
		source.watch(clientSession.unwrap()).wrap()


	override fun <TResult : Any> watch(clientSession: ClientSession, resultClass: KClass<out TResult>) =
		source.watch(clientSession.unwrap(), resultClass.java).wrap()


	override fun watch(clientSession: ClientSession, pipeline: List<Bson>) =
		source.watch(clientSession.unwrap(), pipeline).wrap()


	override fun <TResult : Any> watch(clientSession: ClientSession, pipeline: List<Bson>, resultClass: KClass<out TResult>) =
		source.watch(clientSession.unwrap(), pipeline, resultClass.java).wrap()


	@Deprecated(message = "Deprecated in Java driver.")
	@Suppress("DEPRECATION")
	override fun mapReduce(mapFunction: String, reduceFunction: String) =
		source.mapReduce(mapFunction, reduceFunction).wrap()


	@Deprecated(message = "Deprecated in Java driver.")
	@Suppress("DEPRECATION")
	override fun <TResult : Any> mapReduce(mapFunction: String, reduceFunction: String, resultClass: KClass<out TResult>) =
		source.mapReduce(mapFunction, reduceFunction, resultClass.java).wrap()


	@Deprecated(message = "Deprecated in Java driver.")
	@Suppress("DEPRECATION")
	override fun mapReduce(clientSession: ClientSession, mapFunction: String, reduceFunction: String) =
		source.mapReduce(clientSession.unwrap(), mapFunction, reduceFunction).wrap()


	@Deprecated(message = "Deprecated in Java driver.")
	@Suppress("DEPRECATION")
	override fun <TResult : Any> mapReduce(clientSession: ClientSession, mapFunction: String, reduceFunction: String, resultClass: KClass<out TResult>) =
		source.mapReduce(clientSession.unwrap(), mapFunction, reduceFunction, resultClass.java).wrap()


	override suspend fun bulkWrite(requests: List<WriteModel<out TDocument>>): BulkWriteResult =
		source.bulkWrite(requests).awaitFirst()


	override suspend fun bulkWrite(requests: List<WriteModel<out TDocument>>, options: BulkWriteOptions): BulkWriteResult =
		source.bulkWrite(requests, options).awaitFirst()


	override suspend fun bulkWrite(clientSession: ClientSession, requests: List<WriteModel<out TDocument>>): BulkWriteResult =
		source.bulkWrite(clientSession.unwrap(), requests).awaitFirst()


	override suspend fun bulkWrite(clientSession: ClientSession, requests: List<WriteModel<out TDocument>>, options: BulkWriteOptions): BulkWriteResult =
		source.bulkWrite(clientSession.unwrap(), requests, options).awaitFirst()


	override suspend fun insertOne(document: TDocument): InsertOneResult =
		source.insertOne(document).awaitFirst()


	override suspend fun insertOne(document: TDocument, options: InsertOneOptions): InsertOneResult =
		source.insertOne(document, options).awaitFirst()


	override suspend fun insertOne(clientSession: ClientSession, document: TDocument): InsertOneResult =
		source.insertOne(clientSession.unwrap(), document).awaitFirst()


	override suspend fun insertOne(clientSession: ClientSession, document: TDocument, options: InsertOneOptions): InsertOneResult =
		source.insertOne(clientSession.unwrap(), document, options).awaitFirst()


	override suspend fun insertMany(documents: List<TDocument>): InsertManyResult =
		source.insertMany(documents).awaitFirst()


	override suspend fun insertMany(documents: List<TDocument>, options: InsertManyOptions): InsertManyResult =
		source.insertMany(documents, options).awaitFirst()


	override suspend fun insertMany(clientSession: ClientSession, documents: List<TDocument>): InsertManyResult =
		source.insertMany(clientSession.unwrap(), documents).awaitFirst()


	override suspend fun insertMany(clientSession: ClientSession, documents: List<TDocument>, options: InsertManyOptions): InsertManyResult =
		source.insertMany(clientSession.unwrap(), documents, options).awaitFirst()


	override suspend fun deleteOne(filter: Bson): DeleteResult =
		source.deleteOne(filter).awaitFirst()


	override suspend fun deleteOne(filter: Bson, options: DeleteOptions): DeleteResult =
		source.deleteOne(filter, options).awaitFirst()


	override suspend fun deleteOne(clientSession: ClientSession, filter: Bson): DeleteResult =
		source.deleteOne(clientSession.unwrap(), filter).awaitFirst()


	override suspend fun deleteOne(clientSession: ClientSession, filter: Bson, options: DeleteOptions): DeleteResult =
		source.deleteOne(clientSession.unwrap(), filter, options).awaitFirst()


	override suspend fun deleteMany(filter: Bson): DeleteResult =
		source.deleteMany(filter).awaitFirst()


	override suspend fun deleteMany(filter: Bson, options: DeleteOptions): DeleteResult =
		source.deleteMany(filter, options).awaitFirst()


	override suspend fun deleteMany(clientSession: ClientSession, filter: Bson): DeleteResult =
		source.deleteMany(clientSession.unwrap(), filter).awaitFirst()


	override suspend fun deleteMany(clientSession: ClientSession, filter: Bson, options: DeleteOptions): DeleteResult =
		source.deleteMany(clientSession.unwrap(), filter, options).awaitFirst()


	override suspend fun replaceOne(filter: Bson, replacement: TDocument): UpdateResult =
		source.replaceOne(filter, replacement).awaitFirst()


	override suspend fun replaceOne(filter: Bson, replacement: TDocument, options: ReplaceOptions): UpdateResult =
		source.replaceOne(filter, replacement, options).awaitFirst()


	override suspend fun replaceOne(clientSession: ClientSession, filter: Bson, replacement: TDocument): UpdateResult =
		source.replaceOne(clientSession.unwrap(), filter, replacement).awaitFirst()


	override suspend fun replaceOne(clientSession: ClientSession, filter: Bson, replacement: TDocument, options: ReplaceOptions): UpdateResult =
		source.replaceOne(clientSession.unwrap(), filter, replacement, options).awaitFirst()


	override suspend fun updateOne(filter: Bson, update: Bson): UpdateResult =
		source.updateOne(filter, update).awaitFirst()


	override suspend fun updateOne(filter: Bson, update: Bson, options: UpdateOptions): UpdateResult =
		source.updateOne(filter, update, options).awaitFirst()


	override suspend fun updateOne(clientSession: ClientSession, filter: Bson, update: Bson): UpdateResult =
		source.updateOne(clientSession.unwrap(), filter, update).awaitFirst()


	override suspend fun updateOne(clientSession: ClientSession, filter: Bson, update: Bson, options: UpdateOptions): UpdateResult =
		source.updateOne(clientSession.unwrap(), filter, update).awaitFirst()


	override suspend fun updateMany(filter: Bson, update: Bson): UpdateResult =
		source.updateMany(filter, update).awaitFirst()


	override suspend fun updateMany(filter: Bson, update: Bson, options: UpdateOptions): UpdateResult =
		source.updateMany(filter, update, options).awaitFirst()


	override suspend fun updateMany(clientSession: ClientSession, filter: Bson, update: Bson): UpdateResult =
		source.updateMany(clientSession.unwrap(), filter, update).awaitFirst()


	override suspend fun updateMany(clientSession: ClientSession, filter: Bson, update: Bson, options: UpdateOptions): UpdateResult =
		source.updateMany(clientSession.unwrap(), filter, update, options).awaitFirst()


	override suspend fun findOneAndDelete(filter: Bson) =
		source.findOneAndDelete(filter).awaitFirstOrNull()


	override suspend fun findOneAndDelete(filter: Bson, options: FindOneAndDeleteOptions) =
		source.findOneAndDelete(filter, options).awaitFirstOrNull()


	override suspend fun findOneAndDelete(clientSession: ClientSession, filter: Bson) =
		source.findOneAndDelete(clientSession.unwrap(), filter).awaitFirstOrNull()


	override suspend fun findOneAndDelete(clientSession: ClientSession, filter: Bson, options: FindOneAndDeleteOptions) =
		source.findOneAndDelete(clientSession.unwrap(), filter, options).awaitFirstOrNull()


	override suspend fun findOneAndReplace(filter: Bson, replacement: TDocument) =
		source.findOneAndReplace(filter, replacement).awaitFirstOrNull()


	override suspend fun findOneAndReplace(filter: Bson, replacement: TDocument, options: FindOneAndReplaceOptions) =
		source.findOneAndReplace(filter, replacement, options).awaitFirstOrNull()


	override suspend fun findOneAndReplace(clientSession: ClientSession, filter: Bson, replacement: TDocument) =
		source.findOneAndReplace(clientSession.unwrap(), filter, replacement).awaitFirstOrNull()


	override suspend fun findOneAndReplace(clientSession: ClientSession, filter: Bson, replacement: TDocument, options: FindOneAndReplaceOptions) =
		source.findOneAndReplace(clientSession.unwrap(), filter, replacement, options).awaitFirstOrNull()


	override suspend fun findOneAndUpdate(filter: Bson, update: Bson) =
		source.findOneAndUpdate(filter, update).awaitFirstOrNull()


	override suspend fun findOneAndUpdate(filter: Bson, update: Bson, options: FindOneAndUpdateOptions) =
		source.findOneAndUpdate(filter, update, options).awaitFirstOrNull()


	override suspend fun findOneAndUpdate(clientSession: ClientSession, filter: Bson, update: Bson) =
		source.findOneAndUpdate(clientSession.unwrap(), filter, update).awaitFirstOrNull()


	override suspend fun findOneAndUpdate(clientSession: ClientSession, filter: Bson, update: Bson, options: FindOneAndUpdateOptions) =
		source.findOneAndUpdate(clientSession.unwrap(), filter, update, options).awaitFirstOrNull()


	override suspend fun drop() {
		source.drop().awaitCompletion()
	}


	override suspend fun drop(clientSession: ClientSession) {
		source.drop(clientSession.unwrap()).awaitCompletion()
	}


	override suspend fun createIndex(key: Bson): String =
		source.createIndex(key).awaitFirst()


	override suspend fun createIndex(key: Bson, options: IndexOptions): String =
		source.createIndex(key, options).awaitFirst()


	override suspend fun createIndex(clientSession: ClientSession, key: Bson): String =
		source.createIndex(clientSession.unwrap(), key).awaitFirst()


	override suspend fun createIndex(clientSession: ClientSession, key: Bson, options: IndexOptions): String =
		source.createIndex(clientSession.unwrap(), key, options).awaitFirst()


	override suspend fun createIndexes(indexes: List<IndexModel>) =
		source.createIndexes(indexes).asFlow()


	override suspend fun createIndexes(indexes: List<IndexModel>, createIndexOptions: CreateIndexOptions) =
		source.createIndexes(indexes, createIndexOptions).asFlow()


	override suspend fun createIndexes(clientSession: ClientSession, indexes: List<IndexModel>) =
		source.createIndexes(clientSession.unwrap(), indexes).asFlow()


	override suspend fun createIndexes(clientSession: ClientSession, indexes: List<IndexModel>, createIndexOptions: CreateIndexOptions) =
		source.createIndexes(clientSession.unwrap(), indexes, createIndexOptions).asFlow()


	override fun listIndexes() =
		source.listIndexes().wrap()


	override fun <TResult : Any> listIndexes(resultClass: KClass<out TResult>) =
		source.listIndexes(resultClass.java).wrap()


	override fun listIndexes(clientSession: ClientSession) =
		source.listIndexes(clientSession.unwrap()).wrap()


	override fun <TResult : Any> listIndexes(clientSession: ClientSession, resultClass: KClass<out TResult>) =
		source.listIndexes(clientSession.unwrap(), resultClass.java).wrap()


	override suspend fun dropIndex(indexName: String) {
		source.dropIndex(indexName).awaitCompletion()
	}


	override suspend fun dropIndex(indexName: String, dropIndexOptions: DropIndexOptions) {
		source.dropIndex(indexName, dropIndexOptions).awaitCompletion()
	}


	override suspend fun dropIndex(keys: Bson) {
		source.dropIndex(keys).awaitCompletion()
	}


	override suspend fun dropIndex(keys: Bson, dropIndexOptions: DropIndexOptions) {
		source.dropIndex(keys, dropIndexOptions).awaitCompletion()
	}


	override suspend fun dropIndex(clientSession: ClientSession, indexName: String) {
		source.dropIndex(clientSession.unwrap(), indexName).awaitCompletion()
	}


	override suspend fun dropIndex(clientSession: ClientSession, indexName: String, dropIndexOptions: DropIndexOptions) {
		source.dropIndex(clientSession.unwrap(), indexName, dropIndexOptions).awaitCompletion()
	}


	override suspend fun dropIndex(clientSession: ClientSession, keys: Bson) {
		source.dropIndex(clientSession.unwrap(), keys).awaitCompletion()
	}


	override suspend fun dropIndex(clientSession: ClientSession, keys: Bson, dropIndexOptions: DropIndexOptions) {
		source.dropIndex(clientSession.unwrap(), keys, dropIndexOptions).awaitCompletion()
	}


	override suspend fun dropIndexes() {
		source.dropIndexes().awaitCompletion()
	}


	override suspend fun dropIndexes(dropIndexOptions: DropIndexOptions) {
		source.dropIndexes(dropIndexOptions).awaitCompletion()
	}


	override suspend fun dropIndexes(clientSession: ClientSession) {
		source.dropIndexes(clientSession.unwrap()).awaitCompletion()
	}


	override suspend fun dropIndexes(clientSession: ClientSession, dropIndexOptions: DropIndexOptions) {
		source.dropIndexes(clientSession.unwrap(), dropIndexOptions).awaitCompletion()
	}


	override suspend fun renameCollection(newCollectionNamespace: MongoNamespace) {
		source.renameCollection(newCollectionNamespace).awaitCompletion()
	}


	override suspend fun renameCollection(newCollectionNamespace: MongoNamespace, options: RenameCollectionOptions) {
		source.renameCollection(newCollectionNamespace, options).awaitCompletion()
	}


	override suspend fun renameCollection(clientSession: ClientSession, newCollectionNamespace: MongoNamespace) {
		source.renameCollection(clientSession.unwrap(), newCollectionNamespace).awaitCompletion()
	}


	override suspend fun renameCollection(clientSession: ClientSession, newCollectionNamespace: MongoNamespace, options: RenameCollectionOptions) {
		source.renameCollection(clientSession.unwrap(), newCollectionNamespace, options).awaitCompletion()
	}
}


internal fun <TDocument : Any> ReactiveMongoCollection<TDocument>.wrap() =
	ReactiveCoroutineMongoCollection(source = this)
