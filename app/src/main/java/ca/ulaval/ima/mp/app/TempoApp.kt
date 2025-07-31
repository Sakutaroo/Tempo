package ca.ulaval.ima.mp.app

import android.app.Application
import ca.ulaval.ima.mp.app.di.appModule
import ca.ulaval.ima.mp.auth.data.di.authDataModule
import ca.ulaval.ima.mp.auth.presentation.di.authPresentationModule
import ca.ulaval.ima.mp.core.data.di.coreDataModule
import ca.ulaval.ima.mp.focus.data.di.focusDataModule
import ca.ulaval.ima.mp.focus.presentation.di.focusPresentationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TempoApp: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TempoApp)
            modules(
                appModule,
                coreDataModule,
                authDataModule,
                authPresentationModule,
                focusDataModule,
                focusPresentationModule
            )
        }
    }
}
