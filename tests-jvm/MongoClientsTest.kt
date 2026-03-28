package io.fluidsonic.mongo

import kotlin.test.*


class MongoClientsTest {

	@Test
	fun create_returnsMongoClient_withDefaultConnection() {
		val client = MongoClients.create()
		try {
			assertNotNull(client)
		}
		finally {
			client.close()
		}
	}

	@Test
	fun create_returnsMongoClient_withConnectionString() {
		val client = MongoClients.create("mongodb://localhost")
		try {
			assertNotNull(client)
		}
		finally {
			client.close()
		}
	}

	@Test
	fun defaultCodecRegistry_isNotNull() {
		assertNotNull(MongoClients.defaultCodecRegistry)
	}
}
