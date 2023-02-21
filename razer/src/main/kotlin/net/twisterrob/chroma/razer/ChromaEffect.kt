package net.twisterrob.chroma.razer

class ChromaEffect(
	val maxHeight: Int = 6,
	val maxWidth: Int = 22
) {

	private inner class Location(val row: Int, val column: Int) {
		init {
			require(row in 0 until maxHeight) {
				"Row ${row} is out of bounds [0, ${maxHeight})."
			}
			require(column in 0 until maxWidth) {
				"Column ${column} is out of bounds [0, ${maxWidth})."
			}
		}

		override fun equals(other: Any?): Boolean {
			if (this === other) return true
			if (other !is Location) return false
			return row == other.row && column == other.column
		}

		override fun hashCode(): Int {
			var result = row
			result = 31 * result + column
			return result
		}
	}

	private val colors: MutableMap<Location, ChromaColor> = mutableMapOf()

	operator fun set(row: Int, column: Int, color: ChromaColor) {
		colors[Location(row, column)] = color
	}

	operator fun get(row: Int, column: Int): ChromaColor? =
		colors[Location(row, column)]

	fun delete(row: Int, column: Int) {
		colors.remove(Location(row, column))
	}

	operator fun set(key: RZKEY, color: ChromaColor) {
		if (key == RZKEY.RZKEY_INVALID) return
		colors[Location(key.row, key.column)] = color
	}

	operator fun get(key: RZKEY): ChromaColor? =
		if (key == RZKEY.RZKEY_INVALID)
			null
		else
			colors[Location(key.row, key.column)]

	fun delete(key: RZKEY) {
		if (key == RZKEY.RZKEY_INVALID) return
		colors.remove(Location(key.row, key.column))
	}

	val highlights: List<Pair<Pair<Int, Int>, ChromaColor>>
		get() = colors.entries.map { (it.key.row to it.key.column) to it.value }

	fun trim(ignoreColor: ChromaColor = ChromaColor.BLACK) {
		colors.values.removeIf { it == ignoreColor }
	}

	companion object {

		fun from(vararg rows: IntArray): ChromaEffect {
			val effect = ChromaEffect(rows.size, rows[0].size)
			rows.forEachIndexed row@{ rowIndex, row ->
				row.forEachIndexed column@{ columnIndex, cell ->
					effect[rowIndex, columnIndex] = ChromaColor(cell)
				}
			}
			return effect
		}
	}
}
