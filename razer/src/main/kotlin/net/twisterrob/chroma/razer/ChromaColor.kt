package net.twisterrob.chroma.razer

@JvmInline
value class ChromaColor(val abgr: Int) {

	val alpha: Int
		@Suppress("MagicNumber")
		get() = (abgr shr 24) and BYTE_MASK

	val red: Int
		@Suppress("MagicNumber")
		get() = (abgr shr 16) and BYTE_MASK

	val green: Int
		@Suppress("MagicNumber")
		get() = (abgr shr 8) and BYTE_MASK

	val blue: Int
		@Suppress("MagicNumber")
		get() = (abgr shr 0) and BYTE_MASK

	override fun toString(): String =
		@Suppress("MagicNumber")
		"ChromaColor(abgr=${abgr.toString(16).padStart(8, '0')}" +
				", alpha=${alpha}, red=${red}, green=${green}, blue=${blue})"

	companion object {

		private const val BYTE_MASK = 0xff

		fun fromRGB(red: Int, green: Int, blue: Int): ChromaColor =
			fromARGB(0, red, green, blue)

		@Suppress("MagicNumber")
		fun fromARGB(alpha: Int, red: Int, green: Int, blue: Int): ChromaColor {
			val a = (alpha and BYTE_MASK) shl 24
			val r = (red and BYTE_MASK) shl 16
			val g = (green and BYTE_MASK) shl 8
			val b = (blue and BYTE_MASK) shl 0
			return ChromaColor(a or r or g or b)
		}

		val BLACK = ChromaColor(0x00_00_00)
		val WHITE = ChromaColor(0xff_ff_ff)
		val RED = ChromaColor(0x00_00_ff)
		val GREEN = ChromaColor(0x00_ff_00)
		val BLUE = ChromaColor(0xff_00_00)
		val YELLOW = ChromaColor(0x00_ff_ff)
		val CYAN = ChromaColor(0xff_ff_00)
		val MAGENTA = ChromaColor(0xff_00_ff)
	}
}
