package io.fluidsonic.mongo

import kotlin.test.*


class ReactiveCoroutineMongoClientTest {

	private lateinit var client: MongoClient

	@BeforeTest
	fun setUp() {
		client = MongoClients.create()
	}

	@AfterTest
	fun tearDown() {
		client.close()
	}

	@Test
	fun getDatabase_returnsMongoDatabaseWrapper() {
		val database = client.getDatabase("test")
		assertNotNull(database)
	}

	@Test
	fun listDatabaseNames_returnsFlow() {
		val flow = client.listDatabaseNames()
		assertNotNull(flow)
	}

	@Test
	fun listDatabases_returnsListDatabasesFlow() {
		val flow = client.listDatabases()
		assertIs<ListDatabasesFlow<*>>(flow)
	}
}
