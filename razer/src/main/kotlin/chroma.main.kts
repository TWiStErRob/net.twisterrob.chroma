//@file:RuntimeOptions("-J--illegal-access=deny")

// Based on:
// main.kts: https://kotlinlang.org/docs/custom-script-deps-tutorial.html
// HttpClient: https://ktor.io/docs/http-client-engines.html#java
// Jackson: https://ktor.io/docs/serialization-client.html > Jackson
// jackson {} config: https://www.baeldung.com/jackson-deserialize-json-unknown-properties

// Implicit dependency: Java 11 (because ktor 2.x is compiled as Class 55)
// Note: normally these dependencies are listed without a -jvm suffix,
// but there's no Gradle resolution in play here, so we have to pick a platform manually.
@file:Repository("https://repo1.maven.org/maven2/")
@file:DependsOn("io.ktor:ktor-client-java-jvm:2.2.3")
@file:DependsOn("io.ktor:ktor-client-content-negotiation-jvm:2.2.3")
@file:DependsOn("io.ktor:ktor-serialization-jackson-jvm:2.2.3")
@file:DependsOn("io.ktor:ktor-client-logging-jvm:2.2.3")

// Override transitively included jaxb-impl:2.2 to avoid warning when marshalling Kml.
// > Illegal reflective access by com.sun.xml.bind.v2.runtime.reflect.opt.Injector$1 (jaxb-impl-2.2.jar)
// > to method java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int)
@file:DependsOn("com.sun.xml.bind:jaxb-impl:2.3.8")

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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.swing.JFrame
import javax.swing.SwingUtilities
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

runBlocking(Dispatchers.Default) {
	main()
}

suspend fun main() {
	ChromaSDK(ChromaSDK.CHROMASDK_1).use { sdk ->
		println("Starting!")
		sdk.info()
		sdk.connect(
			ChromaSDK.InitRequest(
				title = "TWiStErRob Test App",
				description = "TWiStErRob Test Description.",
				author = ChromaSDK.InitRequest.InitAuthor(
					name = "TWiStErRob",
					contact = "www.twisterrob.net"
				),
				device_supported = listOf(ChromaSDK.InitRequest.InitDevice.keyboard),
				category = "application"
			)
		)
		@Suppress("OPT_IN_USAGE")
		val job = GlobalScope.launch {
			while (true) {
				sdk.heartbeat()
				delay(1.seconds)
			}
		}
		try {
			snake(sdk)
		} finally {
			println("Bye!")
			job.cancel()
		}
	}
}

suspend fun play(sdk: ChromaSDK) {
	sdk.none()
	delay(1.seconds)
	println("color")
	sdk.static(0x00ff00)
	println("wait")
	delay(1.seconds)
	for (i in 0..1000) {
		print("on")
		sdk.customKey(ChromaSDK.EffectRequest.CustomEffectParams().apply {
			val key = RZKEY.values().filter { it != RZKEY.RZKEY_INVALID }.random()
			val color = (Math.random() * 0xffffff).toInt()
			print(" $key (${key.row}, ${key.column}) = ${color} (${color.toString(16).padStart(6, '0')})")
			this.color[key.row][key.column] = color
		})
//		sdk.customKey(ChromaSDK.EffectRequest.CustomEffectParams().apply {
//			val key = RZKEY.RZKEY_O
//			val color = 0x000000 + i * 16
//			print(" $key (${key.row}, ${key.column}) = ${color} (${color.toString(16).padStart(6, '0')})")
//			this.color[key.row][key.column] = color
//		})
		println(" - ")
		delay(1.milliseconds)

//		print("off")
//		sdk.none()
//		delay(50.milliseconds)
//		println("!")
	}
	println("color")
	sdk.static(255)
	println("wait")
	delay(1.seconds)
}

suspend fun snake(sdk: ChromaSDK) {
	var direction: Pair<Int, Int>
	System.setProperty("java.awt.headless", "false")
	SwingUtilities.invokeLater {
		JFrame("Snake").apply {
			defaultCloseOperation = JFrame.EXIT_ON_CLOSE
			setSize(200, 200)
			setLocationRelativeTo(null)
			isVisible = true
			toFront()
			isAlwaysOnTop = true
			requestFocus()
			isAlwaysOnTop = false
			addKeyListener(object : java.awt.event.KeyAdapter() {
				override fun keyPressed(e: java.awt.event.KeyEvent) {
					when (e.keyCode) {
						java.awt.event.KeyEvent.VK_LEFT -> direction = Pair(0, -1)
						java.awt.event.KeyEvent.VK_RIGHT -> direction = Pair(0, 1)
						java.awt.event.KeyEvent.VK_UP -> direction = Pair(-1, 0)
						java.awt.event.KeyEvent.VK_DOWN -> direction = Pair(1, 0)
						java.awt.event.KeyEvent.VK_ESCAPE -> {
							runBlocking {
								sdk.static(0xff0000)
								sdk.close()
							}
							System.exit(0)
						}
					}
				}
			})
		}
	}
	while (true) {
		sdk.none()
		val snake = ArrayDeque(listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(0, 3), Pair(0, 4)))
		direction = Pair(0, 1)
		var apple = Pair(3, 8)
		var last = System.currentTimeMillis()
		var speed = 500
		while (snake.toSet().size == snake.size) {
			if (System.currentTimeMillis() - last < speed) continue
			last = System.currentTimeMillis()
			print(".")
			sdk.customKey(ChromaSDK.EffectRequest.CustomEffectParams().apply {
				snake.forEachIndexed { index, part ->
					val comp = ((index.toDouble() / (snake.size - 1).toDouble()) * 0xdf + 0x20).toInt()
					val c = (comp shl 16) or (comp shl 8) or (comp shl 0)
					color[part.first][part.second] = c
				}
				color[apple.first][apple.second] = 0x0000ff
			})
			val step = snake.last().first + direction.first to snake.last().second + direction.second
			val next = (step.first + 6) % 6 to (step.second + 22) % 22
			if (next == apple) {
				do {
					apple = (Math.random() * 6).toInt() to (Math.random() * 22).toInt()
				} while (snake.contains(apple) || RZKEY.from(apple.first, apple.second) == RZKEY.RZKEY_INVALID)
			} else {
				snake.removeFirst()
			}
			snake.addLast(next)
			speed = (500 - snake.size * 30).coerceAtLeast(200)
		}
		for (i in 0..8) {
			sdk.static(255)
			delay(100.milliseconds)
			sdk.none()
			delay(40.milliseconds)
		}
	}
}

@Suppress("RemoveEmptyPrimaryConstructor", "EmptyDefaultConstructor")
class ChromaSDK(private val baseUrl: String) : Closeable {

	private val client = HttpClient {
		install(ContentNegotiation) {
			install(Logging) {
				logger = object : Logger {
					override fun log(message: String) {
						println(message)
					}
				}
				level = LogLevel.ALL
			}
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

	suspend fun connect(request: InitRequest) {
		val init = client.post(baseUrl) {
			header("Connection", "keep-alive")
			contentType(ContentType.Application.Json)
			setBody(request)
		}
		println("Connected to ${init.bodyAsText()}")
		_connected = Url(init.body<InitResponse>().checkError().uri)
	}

	override fun close() {
		println("Deleting session: ${connected}")
		runBlocking(Dispatchers.Default) {
			println("Making request: ${connected}")
			client.delete(connected)
			println("Done request: ${connected}")
		}
		println("Close client: ${connected}")
		client.close()
		println("Forget connection: ${connected}")
		_connected = null
	}

	suspend fun heartbeat(): Int {
		val heartbeatUrl = URLBuilder(connected).apply { pathSegments += "heartbeat" }.build()
		return client.put(heartbeatUrl) {
			header("Connection", "keep-alive")
			contentType(ContentType.Application.Json)
			setBody(HeartbeatRequest())
		}.body<HeartbeatResponse>().checkError().tick
	}

	suspend fun none() {
		showKeyboardEffect(EffectRequest("CHROMA_NONE", null))
	}

	suspend fun static(color: Int) {
		showKeyboardEffect(EffectRequest("CHROMA_STATIC", EffectRequest.StaticEffectParams(color)))
	}

	suspend fun noKeys() {
		customKey(EffectRequest.CustomEffectParams())
	}

	suspend fun customKey(params: EffectRequest.CustomEffectParams) {
		showKeyboardEffect(EffectRequest("CHROMA_CUSTOM_KEY", params))
	}

	private suspend fun showKeyboardEffect(request: EffectRequest) {
		val keyboardUrl = URLBuilder(connected).apply { pathSegments += "keyboard" }.build()
		client.put(keyboardUrl) {
			header("Connection", "keep-alive")
			contentType(ContentType.Application.Json)
			setBody(request)
		}.body<PutEffectResponse>().checkError()
	}

	private suspend fun createKeyboardEffect(request: EffectRequest): String {
		val keyboardUrl = URLBuilder(connected).apply { pathSegments += "keyboard" }.build()
		return client.post(keyboardUrl) {
			header("Connection", "keep-alive")
			contentType(ContentType.Application.Json)
			setBody(request)
		}.body<CreateEffectResponse>().checkError().id
	}

	companion object {

		const val CHROMASDK_1 = "http://localhost:54235/razer/chromasdk"
		const val CHROMASDK_2 = "https://chromasdk.io:54236/razer/chromasdk"

		fun <T : ChromaResponse> T.checkError(): T = apply {
			require(error == null) { "Error: ${error} / result: ${result}" }
			require(result == 0) { "Invalid result: ${result}" }
		}
	}

	sealed class ChromaRequest
	sealed class ChromaResponse(
		val result: Int,
		val error: String?,
	)

	data class InfoResponse(
		val core: String,
		val device: String,
		val version: String,
	) : ChromaResponse(0, null)

	class InitRequest(
		val title: String,
		val description: String,
		val author: InitAuthor,
		@Suppress("PropertyName", "ConstructorParameterNaming")
		val device_supported: List<InitDevice>,
		val category: String = "application"
	) : ChromaRequest() {

		class InitAuthor(
			val name: String,
			val contact: String,
		)

		@Suppress("EnumNaming", "EnumEntryName")
		enum class InitDevice {

			keyboard,
			mouse,
			headset,
			mousepad,
			keypad,
			chromalink
		}
	}

	class InitResponse(
		val sessionId: Int,
		val uri: String,
	) : ChromaResponse(0, null)

	class HeartbeatRequest() : ChromaRequest()
	class HeartbeatResponse(
		val tick: Int,
	) : ChromaResponse(0, null)

	class EffectRequest(
		val effect: String,
		val param: Any?,
	) : ChromaRequest() {

		class StaticEffectParams(
			val color: Int,
		)

		class CustomEffectParams(
			val color: Array<IntArray> = arrayOf(
				intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
			),
			val key: Array<IntArray> = arrayOf(
				intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
			),
		)
	}

	class PutEffectResponse(
		// Nothing.
	) : ChromaResponse(0, null)

	class CreateEffectResponse(
		val id: String,
	) : ChromaResponse(0, null)

	class UseCreatedEffectResponse(
		// Nothing.
	) : ChromaResponse(0, null)
}

enum class RZKEY(private val value: Int) {
	RZKEY_ESC(0x0001),                 // Esc (VK_ESCAPE)
	RZKEY_F1(0x0003),                  // F1 (VK_F1)
	RZKEY_F2(0x0004),                  // F2 (VK_F2)
	RZKEY_F3(0x0005),                  // F3 (VK_F3)
	RZKEY_F4(0x0006),                  // F4 (VK_F4)
	RZKEY_F5(0x0007),                  // F5 (VK_F5)
	RZKEY_F6(0x0008),                  // F6 (VK_F6)
	RZKEY_F7(0x0009),                  // F7 (VK_F7)
	RZKEY_F8(0x000A),                  // F8 (VK_F8)
	RZKEY_F9(0x000B),                  // F9 (VK_F9)
	RZKEY_F10(0x000C),                 // F10 (VK_F10)
	RZKEY_F11(0x000D),                 // F11 (VK_F11)
	RZKEY_F12(0x000E),                 // F12 (VK_F12)
	RZKEY_1(0x0102),                   // 1 (VK_1)
	RZKEY_2(0x0103),                   // 2 (VK_2)
	RZKEY_3(0x0104),                   // 3 (VK_3)
	RZKEY_4(0x0105),                   // 4 (VK_4)
	RZKEY_5(0x0106),                   // 5 (VK_5)
	RZKEY_6(0x0107),                   // 6 (VK_6)
	RZKEY_7(0x0108),                   // 7 (VK_7)
	RZKEY_8(0x0109),                   // 8 (VK_8)
	RZKEY_9(0x010A),                   // 9 (VK_9)
	RZKEY_0(0x010B),                   // 0 (VK_0)
	RZKEY_A(0x0302),                   // A (VK_A)
	RZKEY_B(0x0407),                   // B (VK_B)
	RZKEY_C(0x0405),                   // C (VK_C)
	RZKEY_D(0x0304),                   // D (VK_D)
	RZKEY_E(0x0204),                   // E (VK_E)
	RZKEY_F(0x0305),                   // F (VK_F)
	RZKEY_G(0x0306),                   // G (VK_G)
	RZKEY_H(0x0307),                   // H (VK_H)
	RZKEY_I(0x0209),                   // I (VK_I)
	RZKEY_J(0x0308),                   // J (VK_J)
	RZKEY_K(0x0309),                   // K (VK_K)
	RZKEY_L(0x030A),                   // L (VK_L)
	RZKEY_M(0x0409),                   // M (VK_M)
	RZKEY_N(0x0408),                   // N (VK_N)
	RZKEY_O(0x020A),                   // O (VK_O)
	RZKEY_P(0x020B),                   // P (VK_P)
	RZKEY_Q(0x0202),                   // Q (VK_Q)
	RZKEY_R(0x0205),                   // R (VK_R)
	RZKEY_S(0x0303),                   // S (VK_S)
	RZKEY_T(0x0206),                   // T (VK_T)
	RZKEY_U(0x0208),                   // U (VK_U)
	RZKEY_V(0x0406),                   // V (VK_V)
	RZKEY_W(0x0203),                   // W (VK_W)
	RZKEY_X(0x0404),                   // X (VK_X)
	RZKEY_Y(0x0207),                   // Y (VK_Y)
	RZKEY_Z(0x0403),                   // Z (VK_Z)
	RZKEY_NUMLOCK(0x0112),             // Numlock (VK_NUMLOCK)
	RZKEY_NUMPAD0(0x0513),             // Numpad 0 (VK_NUMPAD0)
	RZKEY_NUMPAD1(0x0412),             // Numpad 1 (VK_NUMPAD1)
	RZKEY_NUMPAD2(0x0413),             // Numpad 2 (VK_NUMPAD2)
	RZKEY_NUMPAD3(0x0414),             // Numpad 3 (VK_NUMPAD3)
	RZKEY_NUMPAD4(0x0312),             // Numpad 4 (VK_NUMPAD4)
	RZKEY_NUMPAD5(0x0313),             // Numpad 5 (VK_NUMPAD5)
	RZKEY_NUMPAD6(0x0314),             // Numpad 6 (VK_NUMPAD6)
	RZKEY_NUMPAD7(0x0212),             // Numpad 7 (VK_NUMPAD7)
	RZKEY_NUMPAD8(0x0213),             // Numpad 8 (VK_NUMPAD8)
	RZKEY_NUMPAD9(0x0214),             // Numpad 9 (VK_ NUMPAD
	RZKEY_NUMPAD_DIVIDE(0x0113),       // Divide (VK_DIVIDE)
	RZKEY_NUMPAD_MULTIPLY(0x0114),     // Multiply (VK_MULTIPLY)
	RZKEY_NUMPAD_SUBTRACT(0x0115),     // Subtract (VK_SUBTRACT)
	RZKEY_NUMPAD_ADD(0x0215),          // Add (VK_ADD)
	RZKEY_NUMPAD_ENTER(0x0415),        // Enter (VK_RETURN - Extended)
	RZKEY_NUMPAD_DECIMAL(0x0514),      // Decimal (VK_DECIMAL)
	RZKEY_PRINTSCREEN(0x000F),         // Print Screen (VK_PRINT)
	RZKEY_SCROLL(0x0010),              // Scroll Lock (VK_SCROLL)
	RZKEY_PAUSE(0x0011),               // Pause (VK_PAUSE)
	RZKEY_INSERT(0x010F),              // Insert (VK_INSERT)
	RZKEY_HOME(0x0110),                // Home (VK_HOME)
	RZKEY_PAGEUP(0x0111),              // Page Up (VK_PRIOR)
	RZKEY_DELETE(0x020f),              // Delete (VK_DELETE)
	RZKEY_END(0x0210),                 // End (VK_END)
	RZKEY_PAGEDOWN(0x0211),            // Page Down (VK_NEXT)
	RZKEY_UP(0x0410),                  // Up (VK_UP)
	RZKEY_LEFT(0x050F),                // Left (VK_LEFT)
	RZKEY_DOWN(0x0510),                // Down (VK_DOWN)
	RZKEY_RIGHT(0x0511),               // Right (VK_RIGHT)
	RZKEY_TAB(0x0201),                 // Tab (VK_TAB)
	RZKEY_CAPSLOCK(0x0301),            // Caps Lock(VK_CAPITAL)
	RZKEY_BACKSPACE(0x010E),           // Backspace (VK_BACK)
	RZKEY_ENTER(0x030E),               // Enter (VK_RETURN)
	RZKEY_LCTRL(0x0501),               // Left Control(VK_LCONTROL)
	RZKEY_LWIN(0x0502),                // Left Window (VK_LWIN)
	RZKEY_LALT(0x0503),                // Left Alt (VK_LMENU)
	RZKEY_SPACE(0x0507),               // Spacebar (VK_SPACE)
	RZKEY_RALT(0x050B),                // Right Alt (VK_RMENU)
	RZKEY_FN(0x050C),                  // Function key.
	RZKEY_RMENU(0x050D),               // Right Menu (VK_APPS)
	RZKEY_RCTRL(0x050E),               // Right Control (VK_RCONTROL)
	RZKEY_LSHIFT(0x0401),              // Left Shift (VK_LSHIFT)
	RZKEY_RSHIFT(0x040E),              // Right Shift (VK_RSHIFT)
	RZKEY_MACRO1(0x0100),              // Macro Key 1
	RZKEY_MACRO2(0x0200),              // Macro Key 2
	RZKEY_MACRO3(0x0300),              // Macro Key 3
	RZKEY_MACRO4(0x0400),              // Macro Key 4
	RZKEY_MACRO5(0x0500),              // Macro Key 5
	RZKEY_OEM_1(0x0101),               // ~ (tilde/半角/全角) (VK_OEM_3)
	RZKEY_OEM_2(0x010C),               // -- (minus) (VK_OEM_MINUS)
	RZKEY_OEM_3(0x010D),               // = (equal) (VK_OEM_PLUS)
	RZKEY_OEM_4(0x020C),               // [ (left sqaure bracket) (VK_OEM_4)
	RZKEY_OEM_5(0x020D),               // ] (right square bracket) (VK_OEM_6)
	RZKEY_OEM_6(0x020E),               // \ (backslash) (VK_OEM_5)
	RZKEY_OEM_7(0x030B),               // ; (semi-colon) (VK_OEM_1)
	RZKEY_OEM_8(0x030C),               // ' (apostrophe) (VK_OEM_7)
	RZKEY_OEM_9(0x040A),               // , (comma) (VK_OEM_COMMA)
	RZKEY_OEM_10(0x040B),              // . (period) (VK_OEM_PERIOD)
	RZKEY_OEM_11(0x040C),              // / (forward slash) (VK_OEM_2)
	RZKEY_EUR_1(0x030D),               // "#" (VK_OEM_5)
	RZKEY_EUR_2(0x0402),               // \ (VK_OEM_102)
	RZKEY_JPN_1(0x0015),               // ¥ (0xFF)
	RZKEY_JPN_2(0x040D),               // \ (0xC1)
	RZKEY_JPN_3(0x0504),               // 無変換 (VK_OEM_PA1)
	RZKEY_JPN_4(0x0509),               // 変換 (0xFF)
	RZKEY_JPN_5(0x050A),               // ひらがな/カタカナ (0xFF)
	RZKEY_KOR_1(0x0015),               // | (0xFF)
	RZKEY_KOR_2(0x030D),               // (VK_OEM_5)
	RZKEY_KOR_3(0x0402),               // (VK_OEM_102)
	RZKEY_KOR_4(0x040D),               // (0xC1)
	RZKEY_KOR_5(0x0504),               // (VK_OEM_PA1)
	RZKEY_KOR_6(0x0509),               // 한/영 (0xFF)
	RZKEY_KOR_7(0x050A),               // (0xFF)
	RZKEY_INVALID(0xFFFF),              // Invalid keys.
	;

	val row: Int
		get() = (value and 0xFF00) ushr 8

	val column: Int
		get() = (value and 0xFF) ushr 0

	companion object {

		fun from(row: Int, column: Int): RZKEY =
			values().singleOrNull { it.row == row && it.column == column }
				?.takeUnless { "_JPN_" in it.name || "_KOR_" in it.name || "_MACRO" in it.name }
				?: RZKEY_INVALID
	}
}

//@formatter:off
@Suppress("MaxLineLength")
object X {
	val data = ChromaSDK.EffectRequest.CustomEffectParams(
		color = arrayOf(
			intArrayOf(255, 255, 255, 255, 255, 65280, 65280, 65280, 65280, 65280, 16711680, 16711680, 16711680, 16711680, 16711680, 16776960, 16776960, 16776960, 65535, 65535, 65535, 65535),
			intArrayOf(255, 255, 255, 255, 255, 65280, 65280, 65280, 65280, 65280, 16711680, 16711680, 16711680, 16711680, 16711680, 16776960, 16776960, 16776960, 65535, 65535, 65535, 65535),
			intArrayOf(255, 255, 255, 255, 255, 65280, 65280, 65280, 65280, 65280, 16711680, 16711680, 16711680, 16711680, 16711680, 16776960, 16776960, 16776960, 65535, 65535, 65535, 65535),
			intArrayOf(255, 255, 255, 255, 255, 65280, 65280, 65280, 65280, 65280, 16711680, 16711680, 16711680, 16711680, 16711680, 16776960, 16776960, 16776960, 65535, 65535, 65535, 65535),
			intArrayOf(255, 255, 255, 255, 255, 65280, 65280, 65280, 65280, 65280, 16711680, 16711680, 16711680, 16711680, 16711680, 16776960, 16776960, 16776960, 65535, 65535, 65535, 65535),
			intArrayOf(255, 255, 255, 255, 255, 65280, 65280, 65280, 65280, 65280, 16711680, 16711680, 16711680, 16711680, 16711680, 16776960, 16776960, 16776960, 65535, 65535, 65535, 65535)
		),
		key = arrayOf(
			intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
			intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
			intArrayOf(0, 0, 0, (16777216 or 255.inv()), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
			intArrayOf(0, 0, (16777216 or 255.inv()), (16777216 or 255.inv()), (16777216 or 255.inv()), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
			intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (16777216 or 16776960.inv()), 0, 0, 0, 0, 0),
			intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (16777216 or 16776960.inv()), (16777216 or 16776960.inv()), (16777216 or 16776960.inv()), 0, 0, 0, 0)
		)
	)
}
//@formatter:on
