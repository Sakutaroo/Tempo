package ca.ulaval.ima.mp.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesDataSource(
    private val context: Context
) {
    private val Context.dataStore: DataStore<UserPreferences> by dataStore(
        fileName = "user-preferences",
        serializer = UserPreferencesSerializer
    )

    fun getTokens(): Flow<Pair<String, String>?> = context.dataStore.data.map {
        val accessToken = it.accessToken
        val refreshToken = it.refreshToken

        if (accessToken != null && refreshToken != null) {
            accessToken to refreshToken
        } else {
            null
        }
    }

    suspend fun saveTokens(accessToken: String?, refreshToken: String?) {
        context.dataStore.updateData {
            it.copy(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }
    }

    suspend fun clear() {
        context.dataStore.updateData {
            UserPreferences()
        }
    }
}
