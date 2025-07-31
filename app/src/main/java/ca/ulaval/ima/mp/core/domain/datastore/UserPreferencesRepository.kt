package ca.ulaval.ima.mp.core.domain.datastore

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    fun getTokens(): Flow<Pair<String, String>?>
    suspend fun saveTokens(accessToken: String?, refreshToken: String)
    suspend fun clear()
}
