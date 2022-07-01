package util

import java.io.FileReader
import java.io.IOException
import java.util.*

class PropertiesHelper(private val propertiesFile: String = "src/test/resources/project.properties") {
    fun getProperty(property: String, defaultValue: String = "", file: String = propertiesFile): String {
        val properties = Properties()
        try {
            properties.load(FileReader(file))
        } catch (ignored: IOException) {
        }
        return System.getProperty(property, properties.getProperty(property, defaultValue))
    }
}

val propertiesHelper = PropertiesHelper()
