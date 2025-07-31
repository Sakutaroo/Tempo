package ca.ulaval.ima.mp.auth.presentation.di

import ca.ulaval.ima.mp.auth.presentation.login.LoginViewModel
import ca.ulaval.ima.mp.auth.presentation.register.RegisterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}
