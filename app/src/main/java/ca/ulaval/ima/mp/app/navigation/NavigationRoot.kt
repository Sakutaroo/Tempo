package ca.ulaval.ima.mp.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
import ca.ulaval.ima.mp.auth.presentation.intro.IntroScreenRoot
import ca.ulaval.ima.mp.auth.presentation.login.LoginScreenRoot
import ca.ulaval.ima.mp.auth.presentation.register.RegisterScreenRoot
import ca.ulaval.ima.mp.focus.domain.Session
import ca.ulaval.ima.mp.focus.presentation.active_focus.ActiveFocusScreenRoot
import ca.ulaval.ima.mp.focus.presentation.focus_overview.FocusScreenRoot
import ca.ulaval.ima.mp.focus.presentation.focus_summary.FocusSummaryScreenRoot
import ca.ulaval.ima.mp.focus.presentation.profile.ProfileScreenRoot
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.typeOf

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) {
            Screens.HomeGraph
        } else {
            Screens.AuthGraph
        },
        modifier = modifier
    ) {
        navigation<Screens.AuthGraph>(
            startDestination = Screens.IntroScreen
        ) {
            composable<Screens.IntroScreen> {
                IntroScreenRoot(
                    onLoginClick = {
                        navController.navigate(Screens.LoginScreen) {
                            popUpTo(Screens.IntroScreen) {
                                inclusive = true
                            }
                        }
                    },
                    onRegisterClick = {
                        navController.navigate(Screens.RegisterScreen) {
                            popUpTo(Screens.IntroScreen) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable<Screens.LoginScreen> {
                LoginScreenRoot(
                    onLoginSuccess = {
                        navController.navigate(Screens.HomeGraph) {
                            popUpTo(Screens.AuthGraph) {
                                inclusive = true
                            }
                        }
                    },
                    onRegisterClick = {
                        navController.navigate(Screens.RegisterScreen) {
                            popUpTo(Screens.LoginScreen) {
                                inclusive = true
                                saveState = true
                            }
                            restoreState = true
                        }
                    }
                )
            }
            composable<Screens.RegisterScreen> {
                RegisterScreenRoot(
                    onRegisterSuccess = {
                        navController.navigate(Screens.HomeGraph) {
                            popUpTo(Screens.AuthGraph) {
                                inclusive = true
                            }
                        }
                    },
                    onLoginClick = {
                        navController.navigate(Screens.LoginScreen) {
                            popUpTo(Screens.RegisterScreen) {
                                inclusive = true
                                saveState = true
                            }
                            restoreState = true
                        }
                    }
                )
            }
        }

        navigation<Screens.HomeGraph>(
            startDestination = Screens.FocusGraph
        ) {
            navigation<Screens.FocusGraph>(
                startDestination = Screens.FocusScreen
            ) {
                composable<Screens.FocusScreen> {
                    FocusScreenRoot(
                        onSessionClick = { session ->
                            navController.navigate(
                                Screens.FocusSummaryScreen(
                                    session = session,
                                    isEditing = true,
                                    from = "focus"
                                )
                            )
                        },
                        onStartSessionClick = {
                            navController.navigate(Screens.ActiveFocusScreen) {
                                popUpTo(Screens.FocusScreen) {
                                    inclusive = true
                                    saveState = false
                                }
                                restoreState = false
                            }
                        }
                    )
                }
                composable<Screens.ActiveFocusScreen>(
                    deepLinks = listOf(
                        navDeepLink {
                            uriPattern = "tempo://active_focus"
                        }
                    )
                ) {
                    ActiveFocusScreenRoot(
                        onStopFocus = { session ->
                            navController.navigate(
                                Screens.FocusSummaryScreen(
                                    session = session,
                                    isEditing = false
                                )
                            ) {
                                popUpTo(Screens.ActiveFocusScreen) {
                                    inclusive = true
                                    saveState = false
                                }
                                restoreState = false
                            }
                        }
                    )
                }
                composable<Screens.FocusSummaryScreen>(
                    typeMap = mapOf(
                        typeOf<Session>() to CustomNavTypes.SessionType
                    )
                ) {
                    val args = it.toRoute<Screens.FocusSummaryScreen>()

                    FocusSummaryScreenRoot(
                        onValidClick = {
                            navController.navigate(
                                if (!args.isEditing || args.from != "profile") Screens.FocusGraph else Screens.ProfileGraph
                            ) {
                                popUpTo(Screens.HomeGraph) {
                                    inclusive = true
                                    saveState = false
                                }
                                restoreState = false
                            }
                        },
                        viewModel = koinViewModel(
                            parameters = {
                                parametersOf(args.session, args.isEditing)
                            }
                        ),
                        onBackClick = {
                            when (args.isEditing) {
                                true -> {
                                    navController.navigateUp()
                                }

                                false -> null
                            }
                        }
                    )
                }
            }

            navigation<Screens.ProfileGraph>(
                startDestination = Screens.ProfileScreen
            ) {
                composable<Screens.ProfileScreen> {
                    ProfileScreenRoot(
                        onLogoutClick = {
                            navController.navigate(Screens.AuthGraph) {
                                popUpTo(Screens.HomeGraph) {
                                    inclusive = true
                                    saveState = false
                                }
                                restoreState = false
                            }
                        },
                        onSessionClick = { session ->
                            navController.navigate(
                                Screens.FocusSummaryScreen(
                                    session = session,
                                    isEditing = true,
                                    from = "profile"
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}
