package net.twisterrob.chroma.razer

import io.ktor.utils.io.core.Closeable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.twisterrob.chroma.razer.rest.ChromaRestClient
import net.twisterrob.chroma.razer.rest.keyboard.InitRequest
import net.twisterrob.chroma.razer.rest.keyboard.KeyboardRequest
import kotlin.time.Duration.Companion.seconds

class ChromaController(
	private val client: ChromaRestClient = ChromaRestClient(),
) : Closeable {

	private var job: Job? = null

	suspend fun start() {
		println("Starting!")
		client.info()
		client.connect(
			InitRequest(
				title = "TWiStErRob Test App",
				description = "TWiStErRob Test Description.",
				author = InitRequest.InitAuthor(
					name = "TWiStErRob",
					contact = "www.twisterrob.net"
				),
				device_supported = listOf(InitRequest.InitDevice.keyboard),
				category = "application"
			)
		)
		// For some reason immediately after starting the things don't work, so hack around a bit.
		delay(2.seconds)
		client.heartbeat()
		@Suppress("OPT_IN_USAGE")
		job = GlobalScope.launch {
			while (true) {
				client.heartbeat()
				delay(1.seconds)
			}
		}
	}

	suspend fun none() {
		client.showEffect(KeyboardRequest("CHROMA_NONE", null))
	}

	suspend fun static(color: ChromaColor) {
		client.showEffect(KeyboardRequest("CHROMA_STATIC", KeyboardRequest.StaticEffectParams(color.abgr)))
	}

	suspend fun customKey(params: KeyboardRequest.CustomEffectParams) {
		client.showEffect(KeyboardRequest("CHROMA_CUSTOM_KEY", params))
	}

	override fun close() {
		println("Bye!")
		job?.cancel()
		client.close()
	}
}
