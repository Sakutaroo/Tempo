package ca.ulaval.ima.mp.auth.presentation.intro

sealed interface IntroAction {
    data object OnLoginClick : IntroAction
    data object OnRegisterClick : IntroAction
}
