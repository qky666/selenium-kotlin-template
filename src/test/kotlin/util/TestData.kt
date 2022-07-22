package util

const val defaultDataPropertiesFileName = "data/default.properties"

class TestData(propertiesFiles: List<String>) {
    constructor(env: String) : this(listOf(defaultDataPropertiesFileName, "data/$env.properties"))

    val input = PropertiesHelper(propertiesFiles)
    val output = mutableMapOf<String, Any>()
}
