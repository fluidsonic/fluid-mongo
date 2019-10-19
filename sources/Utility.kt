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

import com.mongodb.async.*
import kotlin.coroutines.*


internal suspend inline fun <T> withCallback(crossinline callback: (SingleResultCallback<T>) -> Unit): T =
	suspendCoroutine { continuation ->
		callback(SingleResultCallback { result, throwable ->
			continuation.resumeWith(
				if (throwable != null) Result.failure<T>(throwable)
				else Result.success(result)
			)
		})
	}
