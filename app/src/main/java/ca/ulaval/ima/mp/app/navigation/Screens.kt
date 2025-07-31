package ca.ulaval.ima.mp.app.navigation

import ca.ulaval.ima.mp.focus.domain.Session
import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object AuthGraph : Screens

    @Serializable
    data object IntroScreen : Screens

    @Serializable
    data object LoginScreen : Screens

    @Serializable
    data object RegisterScreen : Screens

    @Serializable
    data object HomeGraph : Screens

    @Serializable
    data object FocusGraph : Screens

    @Serializable
    data object FocusScreen : Screens

    @Serializable
    data object ActiveFocusScreen : Screens

    @Serializable
    data class FocusSummaryScreen(val session: Session, val isEditing: Boolean, val from: String = "") : Screens

    @Serializable
    data object ProfileGraph : Screens

    @Serializable
    data object ProfileScreen : Screens
}
