package ca.ulaval.ima.mp.core.data.network

import ca.ulaval.ima.mp.core.domain.utils.DataError
import ca.ulaval.ima.mp.core.domain.utils.Result
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

inline fun <reified T> safeCall(execute: () -> T): Result<T, DataError.Network> {
    val response = try {
        execute()
    } catch (e: HttpRequestException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION)
    } catch (e: RestException) {
        return responseToResultFromException(e)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN)
    }
    return Result.Success(response)
}

fun <T> responseToResultFromException(e: RestException): Result<T, DataError.Network> {
    return when (e.statusCode) {
        400 -> Result.Error(DataError.Network.CLIENT)
        401 -> Result.Error(DataError.Network.UNAUTHORIZED)
        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
        409 -> Result.Error(DataError.Network.CONFLICT)
        413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}
