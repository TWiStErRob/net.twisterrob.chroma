package net.twisterrob.chroma.intellij.shortcuts.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

/**
 * A [CoroutineScope] that can be started and stopped.
 *
 * @param context the [CoroutineContext] to use for the scope.
 * @see CoroutineContext.cancelChildren in newer versions of coroutines... TODO replace when possible.
 */
class RestartableScope(
	private val context: CoroutineContext = Dispatchers.IO
) : CoroutineScope {

	private var _scope: CoroutineScope? = null

	override val coroutineContext: CoroutineContext
		get() = (_scope ?: error("Not started")).coroutineContext

	fun start() {
		check(_scope == null) { "Already started" }
		_scope = CoroutineScope(context + Job())
	}

	fun stop() {
		_scope?.cancel("Stopped")
		_scope = null
	}

	fun dispose() {
		_scope?.cancel("Disposed")
		_scope = null
	}
}
