package ca.ulaval.ima.mp.core.data.supabase

import ca.ulaval.ima.mp.core.data.network.safeCall
import ca.ulaval.ima.mp.core.domain.datastore.UserPreferencesRepository
import ca.ulaval.ima.mp.core.domain.supabase.SupaRepository
import ca.ulaval.ima.mp.core.domain.supabase.models.SessionInsertRequest
import ca.ulaval.ima.mp.core.domain.supabase.models.SessionResponse
import ca.ulaval.ima.mp.core.domain.supabase.models.SessionUpdateRequest
import ca.ulaval.ima.mp.core.domain.utils.DataError
import ca.ulaval.ima.mp.core.domain.utils.EmptyResult
import ca.ulaval.ima.mp.core.domain.utils.Result
import ca.ulaval.ima.mp.focus.domain.Session
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserSession
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.flow.firstOrNull

class SupaRepositoryImpl(
    private val auth: Auth,
    private val postgrest: Postgrest,
    private val userPreferences: UserPreferencesRepository
) : SupaRepository {
    override suspend fun register(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        return safeCall {
            auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }

            val session = auth.currentSessionOrNull()
            session?.let {
                userPreferences.saveTokens(
                    accessToken = it.accessToken,
                    refreshToken = it.refreshToken
                )
            }
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        return safeCall {
            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            val session = auth.currentSessionOrNull()
            session?.let {
                userPreferences.saveTokens(
                    accessToken = it.accessToken,
                    refreshToken = it.refreshToken
                )
            }
        }
    }

    override suspend fun saveSession(session: SessionInsertRequest): EmptyResult<DataError.Network> {
        return safeCall {
            postgrest.from(SESSION_TABLE).insert(session)
        }
    }

    override suspend fun updateSession(session: SessionUpdateRequest): EmptyResult<DataError.Network> {
        return safeCall {
            postgrest.from(SESSION_TABLE).update(session) {
                filter {
                    Session::id eq session.id
                }
            }
        }
    }

    override suspend fun getSessions(): Result<List<SessionResponse>, DataError.Network> {
        return safeCall {
            postgrest.from(SESSION_TABLE).select().decodeList<SessionResponse>()
        }
    }

    override suspend fun tryRestoreSession(): Result<Boolean, DataError.Network> {
        val session = userPreferences.getTokens()
            .firstOrNull() ?: return Result.Success(false)
        val (accessToken, refreshToken) = session
        val userSession = UserSession(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresIn = 3600,
            tokenType = "Bearer",
            user = null
        )

        auth.importSession(
            session = userSession
        )
        return Result.Success(true)
    }

    override suspend fun logout(): EmptyResult<DataError.Network> {
        return safeCall {
            userPreferences.clear()
            auth.signOut()
        }
    }

    override suspend fun deleteSession(sessionId: String): EmptyResult<DataError.Network> {
        return safeCall {
            postgrest.from(SESSION_TABLE).delete {
                filter {
                    Session::id eq sessionId
                }
            }
        }
    }

    companion object {
        const val SESSION_TABLE = "session"
    }
}
