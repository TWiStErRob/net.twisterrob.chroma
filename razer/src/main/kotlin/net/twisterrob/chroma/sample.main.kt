package net.twisterrob.chroma

import kotlinx.coroutines.delay
import net.twisterrob.chroma.razer.ChromaController
import net.twisterrob.chroma.razer.ChromaEffect
import kotlin.time.Duration.Companion.seconds

suspend fun main() {
	ChromaController().use { controller ->
		controller.start()
		@Suppress("MaxLineLength", "MagicNumber")
		controller.customKey(
			//@formatter:off
			colors = ChromaEffect.from(
				intArrayOf(0x0000ff, 0x0000ff, 0x0000ff, 0x0000ff, 0x0000ff, 0x00ff00, 0x00ff00, 0x00ff00, 0x00ff00, 0x00ff00, 0xff0000, 0xff0000, 0xff0000, 0xff0000, 0xff0000, 0xffff00, 0xffff00, 0xffff00, 0x00ffff, 0x00ffff, 0x00ffff, 0x00ffff),
				intArrayOf(0x0000ff, 0x0000ff, 0x0000ff, 0x0000ff, 0x0000ff, 0x00ff00, 0x00ff00, 0x00ff00, 0x00ff00, 0x00ff00, 0xff0000, 0xff0000, 0xff0000, 0xff0000, 0xff0000, 0xffff00, 0xffff00, 0xffff00, 0x00ffff, 0x00ffff, 0x00ffff, 0x00ffff),
				intArrayOf(0x0000ff, 0x0000ff, 0x0000ff, 0x0000ff, 0x0000ff, 0x00ff00, 0x00ff00, 0x00ff00, 0x00ff00, 0x00ff00, 0xff0000, 0xff0000, 0xff0000, 0xff0000, 0xff0000, 0xffff00, 0xffff00, 0xffff00, 0x00ffff, 0x00ffff, 0x00ffff, 0x00ffff),
				intArrayOf(0x0000ff, 0x0000ff, 0x0000ff, 0x0000ff, 0x0000ff, 0x00ff00, 0x00ff00, 0x00ff00, 0x00ff00, 0x00ff00, 0xff0000, 0xff0000, 0xff0000, 0xff0000, 0xff0000, 0xffff00, 0xffff00, 0xffff00, 0x00ffff, 0x00ffff, 0x00ffff, 0x00ffff),
				intArrayOf(0x0000ff, 0x0000ff, 0x0000ff, 0x0000ff, 0x0000ff, 0x00ff00, 0x00ff00, 0x00ff00, 0x00ff00, 0x00ff00, 0xff0000, 0xff0000, 0xff0000, 0xff0000, 0xff0000, 0xffff00, 0xffff00, 0xffff00, 0x00ffff, 0x00ffff, 0x00ffff, 0x00ffff),
				intArrayOf(0x0000ff, 0x0000ff, 0x0000ff, 0x0000ff, 0x0000ff, 0x00ff00, 0x00ff00, 0x00ff00, 0x00ff00, 0x00ff00, 0xff0000, 0xff0000, 0xff0000, 0xff0000, 0xff0000, 0xffff00, 0xffff00, 0xffff00, 0x00ffff, 0x00ffff, 0x00ffff, 0x00ffff)
			),
			keys = ChromaEffect.from(
				intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				intArrayOf(0, 0, 0, 0xff0000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				intArrayOf(0, 0, 0xff0000, 0xff0000, 0xff0000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0000ff, 0, 0, 0, 0, 0),
				intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0x0000ff, 0x0000ff, 0x0000ff, 0, 0, 0, 0)
			).apply { trim() },
			//@formatter:on
		)
		println("wait")
		delay(10.seconds)
	}
}
