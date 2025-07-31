package ca.ulaval.ima.mp.core.data.di

import ca.ulaval.ima.mp.core.data.datastore.UserPreferencesDataSource
import ca.ulaval.ima.mp.core.data.datastore.UserPreferencesRepositoryImpl
import ca.ulaval.ima.mp.core.data.supabase.SupaClient
import ca.ulaval.ima.mp.core.data.supabase.SupaRepositoryImpl
import ca.ulaval.ima.mp.core.domain.datastore.UserPreferencesRepository
import ca.ulaval.ima.mp.core.domain.supabase.SupaRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    singleOf(::SupaRepositoryImpl).bind<SupaRepository>()

    single {
        SupaClient().build()
    }

    single {
        get<SupabaseClient>().auth
    }
    single {
        get<SupabaseClient>().postgrest
    }

    single {
        UserPreferencesDataSource(get())
    }

    single<UserPreferencesRepository> {
        UserPreferencesRepositoryImpl(get())
    }
}
