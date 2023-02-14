package net.twisterrob.chroma

import kotlinx.coroutines.delay
import net.twisterrob.chroma.razer.ChromaColor
import net.twisterrob.chroma.razer.ChromaController
import net.twisterrob.chroma.razer.RZKEY
import net.twisterrob.chroma.razer.rest.keyboard.KeyboardRequest
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

suspend fun main() {
	ChromaController().use { controller ->
		controller.start()
		controller.customKey(
			@Suppress("MaxLineLength", "MagicNumber")
			//@formatter:off
			KeyboardRequest.CustomEffectParams(
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
			//@formatter:on
		)
		println("wait")
		delay(10.seconds)
	}
}
