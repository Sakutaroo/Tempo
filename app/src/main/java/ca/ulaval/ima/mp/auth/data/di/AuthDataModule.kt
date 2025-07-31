package ca.ulaval.ima.mp.auth.data.di

import ca.ulaval.ima.mp.auth.data.EmailPatternValidator
import ca.ulaval.ima.mp.auth.domain.PatternValidator
import ca.ulaval.ima.mp.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }

    singleOf(::UserDataValidator)
}
