package org.polyfrost.elementa.font.data

import org.polyfrost.universal.UGraphics
import org.polyfrost.universal.utils.ReleasedDynamicTexture
import com.google.gson.JsonParser
import java.io.InputStream

class Font(
    val fontInfo: FontInfo,
    private val atlas: InputStream
) {
    private lateinit var texture: ReleasedDynamicTexture

    fun getTexture(): ReleasedDynamicTexture {
        if (!::texture.isInitialized) {
            texture = UGraphics.getTexture(atlas)
        }

        return texture
    }

    companion object {
        fun fromResource(path: String): Font {
            val json = this::class.java.getResourceAsStream("$path.json")
            val fontInfo = FontInfo.fromJson(JsonParser().parse(json.reader()).asJsonObject)

            return Font(fontInfo, this::class.java.getResourceAsStream("$path.png"))
        }
    }
}

