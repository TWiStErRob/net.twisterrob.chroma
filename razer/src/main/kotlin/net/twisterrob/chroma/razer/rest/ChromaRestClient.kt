package net.twisterrob.chroma.razer.rest

import com.fasterxml.jackson.databind.DeserializationFeature
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.contentType
import io.ktor.serialization.jackson.jackson
import io.ktor.utils.io.core.Closeable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import net.twisterrob.chroma.razer.rest.effect.UseCreatedEffectRequest
import net.twisterrob.chroma.razer.rest.keyboard.CreateEffectResponse
import net.twisterrob.chroma.razer.rest.keyboard.HeartbeatRequest
import net.twisterrob.chroma.razer.rest.keyboard.HeartbeatResponse
import net.twisterrob.chroma.razer.rest.keyboard.InfoResponse
import net.twisterrob.chroma.razer.rest.keyboard.InitRequest
import net.twisterrob.chroma.razer.rest.keyboard.InitResponse
import net.twisterrob.chroma.razer.rest.keyboard.KeyboardRequest
import net.twisterrob.chroma.razer.rest.keyboard.PutEffectResponse

class ChromaRestClient(
	private val baseUrl: String = URL_LOCALHOST
) : Closeable {

	private val client = HttpClient {
		install(Logging) {
			logger = object : Logger {
				override fun log(message: String) {
					println(message)
				}
			}
			level = LogLevel.ALL
		}
		install(ContentNegotiation) {
			jackson {
				configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			}
		}
		expectSuccess = true
	}

	private var _connected: Url? = null
	private val connected: Url
		get() {
			val connected = _connected
			requireNotNull(connected) { "Not connected, did you call connect()?" }
			return connected
		}

	suspend fun info(): InfoResponse {
		val info = client.get(baseUrl)
		println("Info: ${info.bodyAsText()}")
		return info.body<InfoResponse>().checkError()
	}

	suspend fun connect(request: InitRequest): InitResponse {
		val init = client.post(baseUrl) {
			header("Connection", "keep-alive")
			contentType(ContentType.Application.Json)
			setBody(request)
		}
		println("Connected to ${init.bodyAsText()}")
		val response = init.body<InitResponse>().checkError()
		_connected = Url(response.uri)
		return response
	}

	override fun close() {
		if (_connected != null) {
			println("Closing session: ${connected}")
			runBlocking(Dispatchers.Default) {
				client.delete(connected)
			}
		}
		client.close()
		_connected = null
	}

	suspend fun heartbeat(): HeartbeatResponse {
		val heartbeatUrl = URLBuilder(connected).apply { pathSegments += "heartbeat" }.build()
		return client.put(heartbeatUrl) {
			header("Connection", "keep-alive")
			contentType(ContentType.Application.Json)
			setBody(HeartbeatRequest())
		}.body<HeartbeatResponse>().checkError()
	}

	suspend fun showEffect(request: KeyboardRequest): PutEffectResponse {
		val keyboardUrl = URLBuilder(connected).apply { pathSegments += "keyboard" }.build()
		return client.put(keyboardUrl) {
			header("Connection", "keep-alive")
			contentType(ContentType.Application.Json)
			setBody(request)
		}.body<PutEffectResponse>().checkError()
	}

	@Suppress("unused", "UNUSED_PARAMETER", "RedundantSuspendModifier")
	suspend fun createEffect(request: KeyboardRequest): CreateEffectResponse = TODO()

	@Suppress("unused", "UNUSED_PARAMETER", "RedundantSuspendModifier")
	suspend fun useEffect(request: UseCreatedEffectRequest): CreateEffectResponse = TODO()

	companion object {

		/**
		 * https://assets.razerzone.com/dev_portal/REST/html/index.html#uri
		 */
		const val URL_LOCALHOST = "http://localhost:54235/razer/chromasdk"

		/**
		 * https://assets.razerzone.com/dev_portal/REST/html/index.html#uri
		 */
		const val URL_IO = "https://chromasdk.io:54236/razer/chromasdk"

		private fun <T : ChromaResponse> T.checkError(): T = apply {
			require(error == null) { "Error: ${error} / result: ${result}" }
			require(result == 0) { "Invalid result: ${result}" }
		}
	}
}
