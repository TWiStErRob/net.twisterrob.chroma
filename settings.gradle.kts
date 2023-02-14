pluginManagement {
	resolutionStrategy {
		eachPlugin {
			when (requested.id.id) {
				"net.twisterrob.settings" -> {
					useModule("net.twisterrob.gradle:twister-convention-settings:${requested.version}")
				}
			}
		}
	}
}

plugins {
	id("net.twisterrob.settings") version "0.15"
}
