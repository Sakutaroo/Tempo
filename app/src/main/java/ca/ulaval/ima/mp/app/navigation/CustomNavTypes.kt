package ca.ulaval.ima.mp.app.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import ca.ulaval.ima.mp.focus.domain.Session
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavTypes {
    val SessionType = object : NavType<Session>(
        isNullableAllowed = false
    ) {
        val json = Json {
            ignoreUnknownKeys = true
        }

        override fun get(bundle: Bundle, key: String): Session? {
            return json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Session {
            return json.decodeFromString(Uri.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: Session) {
            bundle.putString(key, json.encodeToString(value))
        }

        override fun serializeAsValue(value: Session): String {
            return Uri.encode(json.encodeToString(value))
        }
    }
}
