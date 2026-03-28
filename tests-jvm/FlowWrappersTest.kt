package io.fluidsonic.mongo

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.bson.*
import org.bson.Document
import kotlin.test.*


class FlowWrappersTest {

	// FindFlow

	@Test
	fun findFlowEmpty_returnsNull_fromFirstOrNull() = runBlocking {
		assertNull(FindFlow.empty<Document>().firstOrNull())
	}

	@Test
	fun findFlowEmpty_returnsSameInstance_fromBuilderMethods() {
		val flow = FindFlow.empty<Document>()
		assertSame(actual = flow.filter(null), expected = flow)
		assertSame(actual = flow.limit(10), expected = flow)
		assertSame(actual = flow.skip(5), expected = flow)
		assertSame(actual = flow.sort(null), expected = flow)
		assertSame(actual = flow.projection(null), expected = flow)
		assertSame(actual = flow.batchSize(100), expected = flow)
	}

	@Test
	fun findFlowEmpty_emitsNothing_whenCollected() = runBlocking {
		val elements = FindFlow.empty<Document>().toList()
		assertTrue(elements.isEmpty())
	}

	// AggregateFlow

	@Test
	fun aggregateFlowEmpty_returnsNull_fromFirstOrNull() = runBlocking {
		assertNull(AggregateFlow.empty<Document>().firstOrNull())
	}

	@Test
	fun aggregateFlowEmpty_returnsSameInstance_fromBuilderMethods() {
		val flow = AggregateFlow.empty<Document>()
		assertSame(actual = flow.allowDiskUse(true), expected = flow)
		assertSame(actual = flow.collation(null), expected = flow)
		assertSame(actual = flow.comment(null), expected = flow)
		assertSame(actual = flow.hint(null), expected = flow)
		assertSame(actual = flow.batchSize(100), expected = flow)
	}

	// DistinctFlow

	@Test
	fun distinctFlowEmpty_returnsNull_fromFirstOrNull() = runBlocking {
		assertNull(DistinctFlow.empty<Document>().firstOrNull())
	}

	@Test
	fun distinctFlowEmpty_returnsSameInstance_fromBuilderMethods() {
		val flow = DistinctFlow.empty<Document>()
		assertSame(actual = flow.filter(null), expected = flow)
		assertSame(actual = flow.collation(null), expected = flow)
		assertSame(actual = flow.batchSize(100), expected = flow)
	}

	// ChangeStreamFlow

	@Test
	fun changeStreamFlowEmpty_returnsNull_fromFirstOrNull() = runBlocking {
		assertNull(ChangeStreamFlow.empty<Document>().firstOrNull())
	}

	// ListCollectionsFlow

	@Test
	fun listCollectionsFlowEmpty_returnsNull_fromFirstOrNull() = runBlocking {
		assertNull(ListCollectionsFlow.empty<Document>().firstOrNull())
	}

	// ListDatabasesFlow

	@Test
	fun listDatabasesFlowEmpty_returnsNull_fromFirstOrNull() = runBlocking {
		assertNull(ListDatabasesFlow.empty<Document>().firstOrNull())
	}

	// ListIndexesFlow

	@Test
	fun listIndexesFlowEmpty_returnsNull_fromFirstOrNull() = runBlocking {
		assertNull(ListIndexesFlow.empty<Document>().firstOrNull())
	}

	// MapReduceFlow

	@Suppress("DEPRECATION")
	@Test
	fun mapReduceFlowEmpty_returnsNull_fromFirstOrNull() = runBlocking {
		assertNull(MapReduceFlow.empty<Document>().firstOrNull())
	}
}
