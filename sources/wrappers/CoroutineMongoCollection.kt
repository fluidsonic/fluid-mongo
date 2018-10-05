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

import com.mongodb.MongoNamespace
import com.mongodb.ReadConcern
import com.mongodb.ReadPreference
import com.mongodb.WriteConcern
import com.mongodb.bulk.BulkWriteResult
import com.mongodb.client.model.BulkWriteOptions
import com.mongodb.client.model.CountOptions
import com.mongodb.client.model.CreateIndexOptions
import com.mongodb.client.model.DeleteOptions
import com.mongodb.client.model.DropIndexOptions
import com.mongodb.client.model.EstimatedDocumentCountOptions
import com.mongodb.client.model.FindOneAndDeleteOptions
import com.mongodb.client.model.FindOneAndReplaceOptions
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.IndexModel
import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.InsertManyOptions
import com.mongodb.client.model.InsertOneOptions
import com.mongodb.client.model.RenameCollectionOptions
import com.mongodb.client.model.ReplaceOptions
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.WriteModel
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import org.bson.codecs.configuration.CodecRegistry
import org.bson.conversions.Bson


internal class CoroutineMongoCollection<TDocument : Any>(
	override val async: com.mongodb.async.client.MongoCollection<TDocument>
) : MongoCollection<TDocument> {

	override val namespace: MongoNamespace
		get() = async.namespace

	override val documentClass: Class<TDocument>
		get() = async.documentClass

	override val codecRegistry: CodecRegistry
		get() = async.codecRegistry

	override val readPreference: ReadPreference
		get() = async.readPreference

	override val writeConcern: WriteConcern
		get() = async.writeConcern

	override val readConcern: ReadConcern
		get() = async.readConcern


	override fun <NewTDocument : Any> withDocumentClass(newDocumentClass: Class<NewTDocument>) =
		async.withDocumentClass(newDocumentClass).wrap()


	override fun withCodecRegistry(codecRegistry: CodecRegistry) =
		async.withCodecRegistry(codecRegistry).wrap()


	override fun withReadPreference(readPreference: ReadPreference) =
		async.withReadPreference(readPreference).wrap()


	override fun withWriteConcern(writeConcern: WriteConcern) =
		async.withWriteConcern(writeConcern).wrap()


	override fun withReadConcern(readConcern: ReadConcern) =
		async.withReadConcern(readConcern).wrap()


	override suspend fun countDocuments() =
		withCallback<Long> { async.countDocuments(it) }


	override suspend fun countDocuments(filter: Bson) =
		withCallback<Long> { async.countDocuments(filter, it) }


	override suspend fun countDocuments(filter: Bson, options: CountOptions) =
		withCallback<Long> { async.countDocuments(filter, options, it) }


	override suspend fun countDocuments(clientSession: ClientSession) =
		withCallback<Long> { async.countDocuments(clientSession.async, it) }


	override suspend fun countDocuments(clientSession: ClientSession, filter: Bson) =
		withCallback<Long> { async.countDocuments(clientSession.async, filter, it) }


	override suspend fun countDocuments(clientSession: ClientSession, filter: Bson, options: CountOptions) =
		withCallback<Long> { async.countDocuments(clientSession.async, filter, options, it) }


	override suspend fun estimatedDocumentCount() =
		withCallback<Long> { async.estimatedDocumentCount(it) }


	override suspend fun estimatedDocumentCount(options: EstimatedDocumentCountOptions) =
		withCallback<Long> { async.estimatedDocumentCount(options, it) }


	override fun <TResult> distinct(fieldName: String, resultClass: Class<TResult>) =
		async.distinct(fieldName, resultClass).wrap()


	override fun <TResult> distinct(fieldName: String, filter: Bson, resultClass: Class<TResult>) =
		async.distinct(fieldName, filter, resultClass).wrap()


	override fun <TResult> distinct(clientSession: ClientSession, fieldName: String, resultClass: Class<TResult>) =
		async.distinct(clientSession.async, fieldName, resultClass).wrap()


	override fun <TResult> distinct(clientSession: ClientSession, fieldName: String, filter: Bson, resultClass: Class<TResult>) =
		async.distinct(clientSession.async, fieldName, filter, resultClass).wrap()


	override fun find() =
		async.find().wrap()


	override fun <TResult> find(resultClass: Class<TResult>) =
		async.find(resultClass).wrap()


	override fun find(filter: Bson) =
		async.find(filter).wrap()


	override fun <TResult> find(filter: Bson, resultClass: Class<TResult>) =
		async.find(filter, resultClass).wrap()


	override fun find(clientSession: ClientSession) =
		async.find(clientSession.async).wrap()


	override fun <TResult> find(clientSession: ClientSession, resultClass: Class<TResult>) =
		async.find(clientSession.async, resultClass).wrap()


	override fun find(clientSession: ClientSession, filter: Bson) =
		async.find(clientSession.async, filter).wrap()


	override fun <TResult> find(clientSession: ClientSession, filter: Bson, resultClass: Class<TResult>) =
		async.find(clientSession.async, filter, resultClass).wrap()


	override fun aggregate(pipeline: List<Bson>) =
		async.aggregate(pipeline).wrap()


	override fun <TResult> aggregate(pipeline: List<Bson>, resultClass: Class<TResult>) =
		async.aggregate(pipeline, resultClass).wrap()


	override fun aggregate(clientSession: ClientSession, pipeline: List<Bson>) =
		async.aggregate(clientSession.async, pipeline).wrap()


	override fun <TResult> aggregate(clientSession: ClientSession, pipeline: List<Bson>, resultClass: Class<TResult>) =
		async.aggregate(clientSession.async, pipeline, resultClass).wrap()


	override fun watch() =
		async.watch().wrap()


	override fun <TResult> watch(resultClass: Class<TResult>) =
		async.watch(resultClass).wrap()


	override fun watch(pipeline: List<Bson>) =
		async.watch(pipeline).wrap()


	override fun <TResult> watch(pipeline: List<Bson>, resultClass: Class<TResult>) =
		async.watch(pipeline, resultClass).wrap()


	override fun watch(clientSession: ClientSession) =
		async.watch(clientSession.async).wrap()


	override fun <TResult> watch(clientSession: ClientSession, resultClass: Class<TResult>) =
		async.watch(clientSession.async, resultClass).wrap()


	override fun watch(clientSession: ClientSession, pipeline: List<Bson>) =
		async.watch(clientSession.async, pipeline).wrap()


	override fun <TResult> watch(clientSession: ClientSession, pipeline: List<Bson>, resultClass: Class<TResult>) =
		async.watch(clientSession.async, pipeline, resultClass).wrap()


	override fun mapReduce(mapFunction: String, reduceFunction: String) =
		async.mapReduce(mapFunction, reduceFunction).wrap()


	override fun <TResult> mapReduce(mapFunction: String, reduceFunction: String, resultClass: Class<TResult>) =
		async.mapReduce(mapFunction, reduceFunction, resultClass).wrap()


	override fun mapReduce(clientSession: ClientSession, mapFunction: String, reduceFunction: String) =
		async.mapReduce(clientSession.async, mapFunction, reduceFunction).wrap()


	override fun <TResult> mapReduce(clientSession: ClientSession, mapFunction: String, reduceFunction: String, resultClass: Class<TResult>) =
		async.mapReduce(clientSession.async, mapFunction, reduceFunction, resultClass).wrap()


	override suspend fun bulkWrite(requests: List<WriteModel<out TDocument>>) =
		withCallback<BulkWriteResult> { async.bulkWrite(requests, it) }


	override suspend fun bulkWrite(requests: List<WriteModel<out TDocument>>, options: BulkWriteOptions) =
		withCallback<BulkWriteResult> { async.bulkWrite(requests, options, it) }


	override suspend fun bulkWrite(clientSession: ClientSession, requests: List<WriteModel<out TDocument>>) =
		withCallback<BulkWriteResult> { async.bulkWrite(clientSession.async, requests, it) }


	override suspend fun bulkWrite(clientSession: ClientSession, requests: List<WriteModel<out TDocument>>, options: BulkWriteOptions) =
		withCallback<BulkWriteResult> { async.bulkWrite(clientSession.async, requests, options, it) }


	override suspend fun insertOne(document: TDocument) {
		withCallback<Void> { async.insertOne(document, it) }
	}


	override suspend fun insertOne(document: TDocument, options: InsertOneOptions) {
		withCallback<Void> { async.insertOne(document, options, it) }
	}


	override suspend fun insertOne(clientSession: ClientSession, document: TDocument) {
		withCallback<Void> { async.insertOne(clientSession.async, document, it) }
	}


	override suspend fun insertOne(clientSession: ClientSession, document: TDocument, options: InsertOneOptions) {
		withCallback<Void> { async.insertOne(clientSession.async, document, options, it) }
	}


	override suspend fun insertMany(documents: List<TDocument>) {
		withCallback<Void> { async.insertMany(documents, it) }
	}


	override suspend fun insertMany(documents: List<TDocument>, options: InsertManyOptions) {
		withCallback<Void> { async.insertMany(documents, options, it) }
	}


	override suspend fun insertMany(clientSession: ClientSession, documents: List<TDocument>) {
		withCallback<Void> { async.insertMany(clientSession.async, documents, it) }
	}


	override suspend fun insertMany(clientSession: ClientSession, documents: List<TDocument>, options: InsertManyOptions) {
		withCallback<Void> { async.insertMany(clientSession.async, documents, options, it) }
	}


	override suspend fun deleteOne(filter: Bson) =
		withCallback<DeleteResult> { async.deleteOne(filter, it) }


	override suspend fun deleteOne(filter: Bson, options: DeleteOptions) =
		withCallback<DeleteResult> { async.deleteOne(filter, options, it) }


	override suspend fun deleteOne(clientSession: ClientSession, filter: Bson) =
		withCallback<DeleteResult> { async.deleteOne(clientSession.async, filter, it) }


	override suspend fun deleteOne(clientSession: ClientSession, filter: Bson, options: DeleteOptions) =
		withCallback<DeleteResult> { async.deleteOne(clientSession.async, filter, options, it) }


	override suspend fun deleteMany(filter: Bson) =
		withCallback<DeleteResult> { async.deleteMany(filter, it) }


	override suspend fun deleteMany(filter: Bson, options: DeleteOptions) =
		withCallback<DeleteResult> { async.deleteMany(filter, options, it) }


	override suspend fun deleteMany(clientSession: ClientSession, filter: Bson) =
		withCallback<DeleteResult> { async.deleteMany(clientSession.async, filter, it) }


	override suspend fun deleteMany(clientSession: ClientSession, filter: Bson, options: DeleteOptions) =
		withCallback<DeleteResult> { async.deleteMany(clientSession.async, filter, options, it) }


	override suspend fun replaceOne(filter: Bson, replacement: TDocument) =
		withCallback<UpdateResult> { async.replaceOne(filter, replacement, it) }


	override suspend fun replaceOne(filter: Bson, replacement: TDocument, options: ReplaceOptions) =
		withCallback<UpdateResult> { async.replaceOne(filter, replacement, options, it) }


	override suspend fun replaceOne(clientSession: ClientSession, filter: Bson, replacement: TDocument) =
		withCallback<UpdateResult> { async.replaceOne(clientSession.async, filter, replacement, it) }


	override suspend fun replaceOne(clientSession: ClientSession, filter: Bson, replacement: TDocument, options: ReplaceOptions) =
		withCallback<UpdateResult> { async.replaceOne(clientSession.async, filter, replacement, options, it) }


	override suspend fun updateOne(filter: Bson, update: Bson) =
		withCallback<UpdateResult> { async.updateOne(filter, update, it) }


	override suspend fun updateOne(filter: Bson, update: Bson, options: UpdateOptions) =
		withCallback<UpdateResult> { async.updateOne(filter, update, options, it) }


	override suspend fun updateOne(clientSession: ClientSession, filter: Bson, update: Bson) =
		withCallback<UpdateResult> { async.updateOne(clientSession.async, filter, update, it) }


	override suspend fun updateOne(clientSession: ClientSession, filter: Bson, update: Bson, options: UpdateOptions) =
		withCallback<UpdateResult> { async.updateOne(clientSession.async, filter, update, it) }


	override suspend fun updateMany(filter: Bson, update: Bson) =
		withCallback<UpdateResult> { async.updateMany(filter, update, it) }


	override suspend fun updateMany(filter: Bson, update: Bson, options: UpdateOptions) =
		withCallback<UpdateResult> { async.updateMany(filter, update, options, it) }


	override suspend fun updateMany(clientSession: ClientSession, filter: Bson, update: Bson) =
		withCallback<UpdateResult> { async.updateMany(clientSession.async, filter, update, it) }


	override suspend fun updateMany(clientSession: ClientSession, filter: Bson, update: Bson, options: UpdateOptions) =
		withCallback<UpdateResult> { async.updateMany(clientSession.async, filter, update, options, it) }


	override suspend fun findOneAndDelete(filter: Bson) =
		withCallback<TDocument?> { async.findOneAndDelete(filter, it) }


	override suspend fun findOneAndDelete(filter: Bson, options: FindOneAndDeleteOptions) =
		withCallback<TDocument?> { async.findOneAndDelete(filter, options, it) }


	override suspend fun findOneAndDelete(clientSession: ClientSession, filter: Bson) =
		withCallback<TDocument?> { async.findOneAndDelete(clientSession.async, filter, it) }


	override suspend fun findOneAndDelete(clientSession: ClientSession, filter: Bson, options: FindOneAndDeleteOptions) =
		withCallback<TDocument?> { async.findOneAndDelete(clientSession.async, filter, options, it) }


	override suspend fun findOneAndReplace(filter: Bson, replacement: TDocument) =
		withCallback<TDocument?> { async.findOneAndReplace(filter, replacement, it) }


	override suspend fun findOneAndReplace(filter: Bson, replacement: TDocument, options: FindOneAndReplaceOptions) =
		withCallback<TDocument?> { async.findOneAndReplace(filter, replacement, options, it) }


	override suspend fun findOneAndReplace(clientSession: ClientSession, filter: Bson, replacement: TDocument) =
		withCallback<TDocument?> { async.findOneAndReplace(clientSession.async, filter, replacement, it) }


	override suspend fun findOneAndReplace(clientSession: ClientSession, filter: Bson, replacement: TDocument, options: FindOneAndReplaceOptions) =
		withCallback<TDocument?> { async.findOneAndReplace(clientSession.async, filter, replacement, options, it) }


	override suspend fun findOneAndUpdate(filter: Bson, update: Bson) =
		withCallback<TDocument?> { async.findOneAndUpdate(filter, update, it) }


	override suspend fun findOneAndUpdate(filter: Bson, update: Bson, options: FindOneAndUpdateOptions) =
		withCallback<TDocument?> { async.findOneAndUpdate(filter, update, options, it) }


	override suspend fun findOneAndUpdate(clientSession: ClientSession, filter: Bson, update: Bson) =
		withCallback<TDocument?> { async.findOneAndUpdate(clientSession.async, filter, update, it) }


	override suspend fun findOneAndUpdate(clientSession: ClientSession, filter: Bson, update: Bson, options: FindOneAndUpdateOptions) =
		withCallback<TDocument?> { async.findOneAndUpdate(clientSession.async, filter, update, options, it) }


	override suspend fun drop() {
		withCallback<Void> { async.drop(it) }
	}


	override suspend fun drop(clientSession: ClientSession) {
		withCallback<Void> { async.drop(clientSession.async, it) }
	}


	override suspend fun createIndex(key: Bson) =
		withCallback<String> { async.createIndex(key, it) }


	override suspend fun createIndex(key: Bson, options: IndexOptions) =
		withCallback<String> { async.createIndex(key, options, it) }


	override suspend fun createIndex(clientSession: ClientSession, key: Bson) =
		withCallback<String> { async.createIndex(clientSession.async, key, it) }


	override suspend fun createIndex(clientSession: ClientSession, key: Bson, options: IndexOptions) =
		withCallback<String> { async.createIndex(clientSession.async, key, options, it) }


	override suspend fun createIndexes(indexes: List<IndexModel>) =
		withCallback<List<String>> { async.createIndexes(indexes, it) }


	override suspend fun createIndexes(indexes: List<IndexModel>, createIndexOptions: CreateIndexOptions) =
		withCallback<List<String>> { async.createIndexes(indexes, createIndexOptions, it) }


	override suspend fun createIndexes(clientSession: ClientSession, indexes: List<IndexModel>) =
		withCallback<List<String>> { async.createIndexes(clientSession.async, indexes, it) }


	override suspend fun createIndexes(clientSession: ClientSession, indexes: List<IndexModel>, createIndexOptions: CreateIndexOptions) =
		withCallback<List<String>> { async.createIndexes(clientSession.async, indexes, createIndexOptions, it) }


	override fun listIndexes() =
		async.listIndexes().wrap()


	override fun <TResult> listIndexes(resultClass: Class<TResult>) =
		async.listIndexes(resultClass).wrap()


	override fun listIndexes(clientSession: ClientSession) =
		async.listIndexes(clientSession.async).wrap()


	override fun <TResult> listIndexes(clientSession: ClientSession, resultClass: Class<TResult>) =
		async.listIndexes(clientSession.async, resultClass).wrap()


	override suspend fun dropIndex(indexName: String) {
		withCallback<Void> { async.dropIndex(indexName, it) }
	}


	override suspend fun dropIndex(indexName: String, dropIndexOptions: DropIndexOptions) {
		withCallback<Void> { async.dropIndex(indexName, dropIndexOptions, it) }
	}


	override suspend fun dropIndex(keys: Bson) {
		withCallback<Void> { async.dropIndex(keys, it) }
	}


	override suspend fun dropIndex(keys: Bson, dropIndexOptions: DropIndexOptions) {
		withCallback<Void> { async.dropIndex(keys, dropIndexOptions, it) }
	}


	override suspend fun dropIndex(clientSession: ClientSession, indexName: String) {
		withCallback<Void> { async.dropIndex(clientSession.async, indexName, it) }
	}


	override suspend fun dropIndex(clientSession: ClientSession, indexName: String, dropIndexOptions: DropIndexOptions) {
		withCallback<Void> { async.dropIndex(clientSession.async, indexName, dropIndexOptions, it) }
	}


	override suspend fun dropIndex(clientSession: ClientSession, keys: Bson) {
		withCallback<Void> { async.dropIndex(clientSession.async, keys, it) }
	}


	override suspend fun dropIndex(clientSession: ClientSession, keys: Bson, dropIndexOptions: DropIndexOptions) {
		withCallback<Void> { async.dropIndex(clientSession.async, keys, dropIndexOptions, it) }
	}


	override suspend fun dropIndexes() {
		withCallback<Void> { async.dropIndexes(it) }
	}


	override suspend fun dropIndexes(dropIndexOptions: DropIndexOptions) {
		withCallback<Void> { async.dropIndexes(dropIndexOptions, it) }
	}


	override suspend fun dropIndexes(clientSession: ClientSession) {
		withCallback<Void> { async.dropIndexes(clientSession.async, it) }
	}


	override suspend fun dropIndexes(clientSession: ClientSession, dropIndexOptions: DropIndexOptions) {
		withCallback<Void> { async.dropIndexes(clientSession.async, dropIndexOptions, it) }
	}


	override suspend fun renameCollection(newCollectionNamespace: MongoNamespace) {
		withCallback<Void> { async.renameCollection(newCollectionNamespace, it) }
	}


	override suspend fun renameCollection(newCollectionNamespace: MongoNamespace, options: RenameCollectionOptions) {
		withCallback<Void> { async.renameCollection(newCollectionNamespace, options, it) }
	}


	override suspend fun renameCollection(clientSession: ClientSession, newCollectionNamespace: MongoNamespace) {
		withCallback<Void> { async.renameCollection(clientSession.async, newCollectionNamespace, it) }
	}


	override suspend fun renameCollection(clientSession: ClientSession, newCollectionNamespace: MongoNamespace, options: RenameCollectionOptions) {
		withCallback<Void> { async.renameCollection(clientSession.async, newCollectionNamespace, options, it) }
	}
}


internal fun <TDocument : Any> com.mongodb.async.client.MongoCollection<TDocument>.wrap() =
	CoroutineMongoCollection(this)
