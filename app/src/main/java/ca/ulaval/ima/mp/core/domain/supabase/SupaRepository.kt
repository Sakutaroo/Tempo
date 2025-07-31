package ca.ulaval.ima.mp.core.domain.supabase

import ca.ulaval.ima.mp.core.domain.supabase.models.SessionInsertRequest
import ca.ulaval.ima.mp.core.domain.supabase.models.SessionResponse
import ca.ulaval.ima.mp.core.domain.supabase.models.SessionUpdateRequest
import ca.ulaval.ima.mp.core.domain.utils.DataError
import ca.ulaval.ima.mp.core.domain.utils.EmptyResult
import ca.ulaval.ima.mp.core.domain.utils.Result

interface SupaRepository {
    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun saveSession(session: SessionInsertRequest): EmptyResult<DataError.Network>
    suspend fun updateSession(session: SessionUpdateRequest): EmptyResult<DataError.Network>
    suspend fun getSessions(): Result<List<SessionResponse>, DataError.Network>
    suspend fun tryRestoreSession(): Result<Boolean, DataError.Network>
    suspend fun logout(): EmptyResult<DataError.Network>
    suspend fun deleteSession(sessionId: String): EmptyResult<DataError.Network>
}
