package net.twisterrob.chroma

import kotlinx.coroutines.delay
import net.twisterrob.chroma.razer.ChromaColor
import net.twisterrob.chroma.razer.ChromaController
import net.twisterrob.chroma.razer.ChromaEffect
import net.twisterrob.chroma.razer.RZKEY
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

suspend fun main() {
	ChromaController().use { controller ->
		controller.start()
		for (i in 0..100) {
//			print("on")
			controller.customKey(ChromaEffect().apply {
				val key = RZKEY.values().filter { it != RZKEY.RZKEY_INVALID }.random()
				val color = ChromaColor((Math.random() * 0xffffff).toInt())
				print(" $key (${key.row}, ${key.column}) = ${color}")
				this[key] = color
			})
//			controller.customKey(ChromaEffect().apply {
//				val key = RZKEY.RZKEY_O
//				val color = ChromaColor(0x000000 + i * 16)
//				print(" $key (${key.row}, ${key.column}) = ${color}")
//				set(key, color)
//			})
			println(" - ")
			delay(10.milliseconds)

//			print("off")
//			controller.none()
//			delay(50.milliseconds)
//			println("!")
		}
		println("color")
		controller.static(ChromaColor.RED)
		println("wait")
		delay(1.seconds)
	}
}
