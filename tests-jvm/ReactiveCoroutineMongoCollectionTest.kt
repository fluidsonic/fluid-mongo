package io.fluidsonic.mongo

import org.bson.Document
import kotlin.test.*


class ReactiveCoroutineMongoCollectionTest {

	private lateinit var client: MongoClient
	private lateinit var collection: MongoCollection<Document>

	@BeforeTest
	fun setUp() {
		client = MongoClients.create()
		collection = client.getDatabase("test").getCollection("test")
	}

	@AfterTest
	fun tearDown() {
		client.close()
	}

	@Test
	fun namespace_returnsCorrectNamespace() {
		assertEquals(actual = collection.namespace.databaseName, expected = "test")
		assertEquals(actual = collection.namespace.collectionName, expected = "test")
	}

	@Test
	fun find_returnsFindFlow() {
		val flow = collection.find()
		assertIs<FindFlow<*>>(flow)
	}

	@Test
	fun aggregate_returnsAggregateFlow() {
		val flow = collection.aggregate(emptyList())
		assertIs<AggregateFlow<*>>(flow)
	}

	@Test
	fun distinct_returnsDistinctFlow() {
		val flow = collection.distinct("field", String::class)
		assertIs<DistinctFlow<*>>(flow)
	}

	@Test
	fun withDocumentClass_returnsNewCollectionWrapper() {
		val newCollection = collection.withDocumentClass(Document::class)
		assertEquals(actual = newCollection.documentClass, expected = Document::class)
	}

	@Test
	fun codecRegistry_isNotNull() {
		assertNotNull(collection.codecRegistry)
	}

	@Test
	fun readPreference_isNotNull() {
		assertNotNull(collection.readPreference)
	}
}
