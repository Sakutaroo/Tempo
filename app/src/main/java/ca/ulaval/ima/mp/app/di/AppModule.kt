package ca.ulaval.ima.mp.app.di

import ca.ulaval.ima.mp.app.MainViewModel
import ca.ulaval.ima.mp.app.TempoApp
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as TempoApp).applicationScope
    }

    viewModelOf(::MainViewModel)
}
