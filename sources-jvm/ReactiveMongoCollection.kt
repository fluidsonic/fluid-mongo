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
import com.mongodb.bulk.*
import com.mongodb.client.model.*
import com.mongodb.client.result.*
import kotlin.reflect.*
import org.bson.codecs.configuration.*
import org.bson.conversions.*


internal class ReactiveMongoCollection<TDocument : Any>(
	val source: com.mongodb.reactivestreams.client.MongoCollection<TDocument>,
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
		source.countDocuments().ioAwaitFirst()


	override suspend fun countDocuments(filter: Bson): Long =
		source.countDocuments(filter).ioAwaitFirst()


	override suspend fun countDocuments(filter: Bson, options: CountOptions): Long =
		source.countDocuments(filter, options).ioAwaitFirst()


	override suspend fun countDocuments(clientSession: ClientSession): Long =
		source.countDocuments(clientSession.unwrap()).ioAwaitFirst()


	override suspend fun countDocuments(clientSession: ClientSession, filter: Bson): Long =
		source.countDocuments(clientSession.unwrap(), filter).ioAwaitFirst()


	override suspend fun countDocuments(clientSession: ClientSession, filter: Bson, options: CountOptions): Long =
		source.countDocuments(clientSession.unwrap(), filter, options).ioAwaitFirst()


	override suspend fun estimatedDocumentCount(): Long =
		source.estimatedDocumentCount().ioAwaitFirst()


	override suspend fun estimatedDocumentCount(options: EstimatedDocumentCountOptions): Long =
		source.estimatedDocumentCount(options).ioAwaitFirst()


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


	override fun mapReduce(mapFunction: String, reduceFunction: String) =
		source.mapReduce(mapFunction, reduceFunction).wrap()


	override fun <TResult : Any> mapReduce(mapFunction: String, reduceFunction: String, resultClass: KClass<out TResult>) =
		source.mapReduce(mapFunction, reduceFunction, resultClass.java).wrap()


	override fun mapReduce(clientSession: ClientSession, mapFunction: String, reduceFunction: String) =
		source.mapReduce(clientSession.unwrap(), mapFunction, reduceFunction).wrap()


	override fun <TResult : Any> mapReduce(clientSession: ClientSession, mapFunction: String, reduceFunction: String, resultClass: KClass<out TResult>) =
		source.mapReduce(clientSession.unwrap(), mapFunction, reduceFunction, resultClass.java).wrap()


	override suspend fun bulkWrite(requests: List<WriteModel<out TDocument>>): BulkWriteResult =
		source.bulkWrite(requests).ioAwaitFirst()


	override suspend fun bulkWrite(requests: List<WriteModel<out TDocument>>, options: BulkWriteOptions): BulkWriteResult =
		source.bulkWrite(requests, options).ioAwaitFirst()


	override suspend fun bulkWrite(clientSession: ClientSession, requests: List<WriteModel<out TDocument>>): BulkWriteResult =
		source.bulkWrite(clientSession.unwrap(), requests).ioAwaitFirst()


	override suspend fun bulkWrite(clientSession: ClientSession, requests: List<WriteModel<out TDocument>>, options: BulkWriteOptions): BulkWriteResult =
		source.bulkWrite(clientSession.unwrap(), requests, options).ioAwaitFirst()


	override suspend fun insertOne(document: TDocument): InsertOneResult =
		source.insertOne(document).ioAwaitFirst()


	override suspend fun insertOne(document: TDocument, options: InsertOneOptions): InsertOneResult =
		source.insertOne(document, options).ioAwaitFirst()


	override suspend fun insertOne(clientSession: ClientSession, document: TDocument): InsertOneResult =
		source.insertOne(clientSession.unwrap(), document).ioAwaitFirst()


	override suspend fun insertOne(clientSession: ClientSession, document: TDocument, options: InsertOneOptions): InsertOneResult =
		source.insertOne(clientSession.unwrap(), document, options).ioAwaitFirst()


	override suspend fun insertMany(documents: List<TDocument>): InsertManyResult =
		source.insertMany(documents).ioAwaitFirst()


	override suspend fun insertMany(documents: List<TDocument>, options: InsertManyOptions): InsertManyResult =
		source.insertMany(documents, options).ioAwaitFirst()


	override suspend fun insertMany(clientSession: ClientSession, documents: List<TDocument>): InsertManyResult =
		source.insertMany(clientSession.unwrap(), documents).ioAwaitFirst()


	override suspend fun insertMany(clientSession: ClientSession, documents: List<TDocument>, options: InsertManyOptions): InsertManyResult =
		source.insertMany(clientSession.unwrap(), documents, options).ioAwaitFirst()


	override suspend fun deleteOne(filter: Bson): DeleteResult =
		source.deleteOne(filter).ioAwaitFirst()


	override suspend fun deleteOne(filter: Bson, options: DeleteOptions): DeleteResult =
		source.deleteOne(filter, options).ioAwaitFirst()


	override suspend fun deleteOne(clientSession: ClientSession, filter: Bson): DeleteResult =
		source.deleteOne(clientSession.unwrap(), filter).ioAwaitFirst()


	override suspend fun deleteOne(clientSession: ClientSession, filter: Bson, options: DeleteOptions): DeleteResult =
		source.deleteOne(clientSession.unwrap(), filter, options).ioAwaitFirst()


	override suspend fun deleteMany(filter: Bson): DeleteResult =
		source.deleteMany(filter).ioAwaitFirst()


	override suspend fun deleteMany(filter: Bson, options: DeleteOptions): DeleteResult =
		source.deleteMany(filter, options).ioAwaitFirst()


	override suspend fun deleteMany(clientSession: ClientSession, filter: Bson): DeleteResult =
		source.deleteMany(clientSession.unwrap(), filter).ioAwaitFirst()


	override suspend fun deleteMany(clientSession: ClientSession, filter: Bson, options: DeleteOptions): DeleteResult =
		source.deleteMany(clientSession.unwrap(), filter, options).ioAwaitFirst()


	override suspend fun replaceOne(filter: Bson, replacement: TDocument): UpdateResult =
		source.replaceOne(filter, replacement).ioAwaitFirst()


	override suspend fun replaceOne(filter: Bson, replacement: TDocument, options: ReplaceOptions): UpdateResult =
		source.replaceOne(filter, replacement, options).ioAwaitFirst()


	override suspend fun replaceOne(clientSession: ClientSession, filter: Bson, replacement: TDocument): UpdateResult =
		source.replaceOne(clientSession.unwrap(), filter, replacement).ioAwaitFirst()


	override suspend fun replaceOne(clientSession: ClientSession, filter: Bson, replacement: TDocument, options: ReplaceOptions): UpdateResult =
		source.replaceOne(clientSession.unwrap(), filter, replacement, options).ioAwaitFirst()


	override suspend fun updateOne(filter: Bson, update: Bson): UpdateResult =
		source.updateOne(filter, update).ioAwaitFirst()


	override suspend fun updateOne(filter: Bson, update: Bson, options: UpdateOptions): UpdateResult =
		source.updateOne(filter, update, options).ioAwaitFirst()


	override suspend fun updateOne(clientSession: ClientSession, filter: Bson, update: Bson): UpdateResult =
		source.updateOne(clientSession.unwrap(), filter, update).ioAwaitFirst()


	override suspend fun updateOne(clientSession: ClientSession, filter: Bson, update: Bson, options: UpdateOptions): UpdateResult =
		source.updateOne(clientSession.unwrap(), filter, update).ioAwaitFirst()


	override suspend fun updateMany(filter: Bson, update: Bson): UpdateResult =
		source.updateMany(filter, update).ioAwaitFirst()


	override suspend fun updateMany(filter: Bson, update: Bson, options: UpdateOptions): UpdateResult =
		source.updateMany(filter, update, options).ioAwaitFirst()


	override suspend fun updateMany(clientSession: ClientSession, filter: Bson, update: Bson): UpdateResult =
		source.updateMany(clientSession.unwrap(), filter, update).ioAwaitFirst()


	override suspend fun updateMany(clientSession: ClientSession, filter: Bson, update: Bson, options: UpdateOptions): UpdateResult =
		source.updateMany(clientSession.unwrap(), filter, update, options).ioAwaitFirst()


	override suspend fun findOneAndDelete(filter: Bson) =
		source.findOneAndDelete(filter).ioAwaitFirstOrNull()


	override suspend fun findOneAndDelete(filter: Bson, options: FindOneAndDeleteOptions) =
		source.findOneAndDelete(filter, options).ioAwaitFirstOrNull()


	override suspend fun findOneAndDelete(clientSession: ClientSession, filter: Bson) =
		source.findOneAndDelete(clientSession.unwrap(), filter).ioAwaitFirstOrNull()


	override suspend fun findOneAndDelete(clientSession: ClientSession, filter: Bson, options: FindOneAndDeleteOptions) =
		source.findOneAndDelete(clientSession.unwrap(), filter, options).ioAwaitFirstOrNull()


	override suspend fun findOneAndReplace(filter: Bson, replacement: TDocument) =
		source.findOneAndReplace(filter, replacement).ioAwaitFirstOrNull()


	override suspend fun findOneAndReplace(filter: Bson, replacement: TDocument, options: FindOneAndReplaceOptions) =
		source.findOneAndReplace(filter, replacement, options).ioAwaitFirstOrNull()


	override suspend fun findOneAndReplace(clientSession: ClientSession, filter: Bson, replacement: TDocument) =
		source.findOneAndReplace(clientSession.unwrap(), filter, replacement).ioAwaitFirstOrNull()


	override suspend fun findOneAndReplace(clientSession: ClientSession, filter: Bson, replacement: TDocument, options: FindOneAndReplaceOptions) =
		source.findOneAndReplace(clientSession.unwrap(), filter, replacement, options).ioAwaitFirstOrNull()


	override suspend fun findOneAndUpdate(filter: Bson, update: Bson) =
		source.findOneAndUpdate(filter, update).ioAwaitFirstOrNull()


	override suspend fun findOneAndUpdate(filter: Bson, update: Bson, options: FindOneAndUpdateOptions) =
		source.findOneAndUpdate(filter, update, options).ioAwaitFirstOrNull()


	override suspend fun findOneAndUpdate(clientSession: ClientSession, filter: Bson, update: Bson) =
		source.findOneAndUpdate(clientSession.unwrap(), filter, update).ioAwaitFirstOrNull()


	override suspend fun findOneAndUpdate(clientSession: ClientSession, filter: Bson, update: Bson, options: FindOneAndUpdateOptions) =
		source.findOneAndUpdate(clientSession.unwrap(), filter, update, options).ioAwaitFirstOrNull()


	override suspend fun drop() {
		source.drop().ioAwaitCompletion()
	}


	override suspend fun drop(clientSession: ClientSession) {
		source.drop(clientSession.unwrap()).ioAwaitCompletion()
	}


	override suspend fun createIndex(key: Bson): String =
		source.createIndex(key).ioAwaitFirst()


	override suspend fun createIndex(key: Bson, options: IndexOptions): String =
		source.createIndex(key, options).ioAwaitFirst()


	override suspend fun createIndex(clientSession: ClientSession, key: Bson): String =
		source.createIndex(clientSession.unwrap(), key).ioAwaitFirst()


	override suspend fun createIndex(clientSession: ClientSession, key: Bson, options: IndexOptions): String =
		source.createIndex(clientSession.unwrap(), key, options).ioAwaitFirst()


	override suspend fun createIndexes(indexes: List<IndexModel>) =
		source.createIndexes(indexes).ioAsFlow()


	override suspend fun createIndexes(indexes: List<IndexModel>, createIndexOptions: CreateIndexOptions) =
		source.createIndexes(indexes, createIndexOptions).ioAsFlow()


	override suspend fun createIndexes(clientSession: ClientSession, indexes: List<IndexModel>) =
		source.createIndexes(clientSession.unwrap(), indexes).ioAsFlow()


	override suspend fun createIndexes(clientSession: ClientSession, indexes: List<IndexModel>, createIndexOptions: CreateIndexOptions) =
		source.createIndexes(clientSession.unwrap(), indexes, createIndexOptions).ioAsFlow()


	override fun listIndexes() =
		source.listIndexes().wrap()


	override fun <TResult : Any> listIndexes(resultClass: KClass<out TResult>) =
		source.listIndexes(resultClass.java).wrap()


	override fun listIndexes(clientSession: ClientSession) =
		source.listIndexes(clientSession.unwrap()).wrap()


	override fun <TResult : Any> listIndexes(clientSession: ClientSession, resultClass: KClass<out TResult>) =
		source.listIndexes(clientSession.unwrap(), resultClass.java).wrap()


	override suspend fun dropIndex(indexName: String) {
		source.dropIndex(indexName).ioAwaitCompletion()
	}


	override suspend fun dropIndex(indexName: String, dropIndexOptions: DropIndexOptions) {
		source.dropIndex(indexName, dropIndexOptions).ioAwaitCompletion()
	}


	override suspend fun dropIndex(keys: Bson) {
		source.dropIndex(keys).ioAwaitCompletion()
	}


	override suspend fun dropIndex(keys: Bson, dropIndexOptions: DropIndexOptions) {
		source.dropIndex(keys, dropIndexOptions).ioAwaitCompletion()
	}


	override suspend fun dropIndex(clientSession: ClientSession, indexName: String) {
		source.dropIndex(clientSession.unwrap(), indexName).ioAwaitCompletion()
	}


	override suspend fun dropIndex(clientSession: ClientSession, indexName: String, dropIndexOptions: DropIndexOptions) {
		source.dropIndex(clientSession.unwrap(), indexName, dropIndexOptions).ioAwaitCompletion()
	}


	override suspend fun dropIndex(clientSession: ClientSession, keys: Bson) {
		source.dropIndex(clientSession.unwrap(), keys).ioAwaitCompletion()
	}


	override suspend fun dropIndex(clientSession: ClientSession, keys: Bson, dropIndexOptions: DropIndexOptions) {
		source.dropIndex(clientSession.unwrap(), keys, dropIndexOptions).ioAwaitCompletion()
	}


	override suspend fun dropIndexes() {
		source.dropIndexes().ioAwaitCompletion()
	}


	override suspend fun dropIndexes(dropIndexOptions: DropIndexOptions) {
		source.dropIndexes(dropIndexOptions).ioAwaitCompletion()
	}


	override suspend fun dropIndexes(clientSession: ClientSession) {
		source.dropIndexes(clientSession.unwrap()).ioAwaitCompletion()
	}


	override suspend fun dropIndexes(clientSession: ClientSession, dropIndexOptions: DropIndexOptions) {
		source.dropIndexes(clientSession.unwrap(), dropIndexOptions).ioAwaitCompletion()
	}


	override suspend fun renameCollection(newCollectionNamespace: MongoNamespace) {
		source.renameCollection(newCollectionNamespace).ioAwaitCompletion()
	}


	override suspend fun renameCollection(newCollectionNamespace: MongoNamespace, options: RenameCollectionOptions) {
		source.renameCollection(newCollectionNamespace, options).ioAwaitCompletion()
	}


	override suspend fun renameCollection(clientSession: ClientSession, newCollectionNamespace: MongoNamespace) {
		source.renameCollection(clientSession.unwrap(), newCollectionNamespace).ioAwaitCompletion()
	}


	override suspend fun renameCollection(clientSession: ClientSession, newCollectionNamespace: MongoNamespace, options: RenameCollectionOptions) {
		source.renameCollection(clientSession.unwrap(), newCollectionNamespace, options).ioAwaitCompletion()
	}
}


internal fun <TDocument : Any> com.mongodb.reactivestreams.client.MongoCollection<TDocument>.wrap() =
	ReactiveMongoCollection(this)
