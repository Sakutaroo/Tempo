package ca.ulaval.ima.mp.core.data.datastore

import ca.ulaval.ima.mp.core.domain.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class UserPreferencesRepositoryImpl(
    private val userPreferences: UserPreferencesDataSource
) : UserPreferencesRepository {
    override fun getTokens(): Flow<Pair<String, String>?> = userPreferences.getTokens()

    override suspend fun saveTokens(accessToken: String?, refreshToken: String) = userPreferences.saveTokens(accessToken, refreshToken)

    override suspend fun clear() = userPreferences.clear()
}
