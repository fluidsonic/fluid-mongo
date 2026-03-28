package io.fluidsonic.mongo

import com.mongodb.session.ClientSession as BaseClientSession
import kotlin.test.*


class ReactiveCoroutineClientSessionTest {

	@Test
	fun clientSessionInterface_extendsBaseClientSession() {
		assertTrue(BaseClientSession::class.java.isAssignableFrom(ClientSession::class.java))
	}
}
