package ca.ulaval.ima.mp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ca.ulaval.ima.mp.app.navigation.NavigationRoot
import ca.ulaval.ima.mp.app.navigation.Screens
import ca.ulaval.ima.mp.app.navigation.TempoBottomNavigation
import ca.ulaval.ima.mp.app.navigation.bottomBarScreens
import ca.ulaval.ima.mp.app.navigation.bottomNavigationItems
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray50
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme
import ca.ulaval.ima.mp.focus.domain.motion.MotionState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.value.isLoading || viewModel.state.value.isCheckingAuth
            }
        }
        enableEdgeToEdge()
        setContent {
            KoinContext {
                TempoTheme {
                    val state by viewModel.state.collectAsStateWithLifecycle()

                    if (state.isCheckingAuth || state.isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Gray50)
                        )
                        return@TempoTheme
                    }

                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    val shouldShowBottomBar = bottomBarScreens.any {
                        it::class.qualifiedName == currentRoute
                    }

                    LaunchedEffect(state.motionState) {
                        if (state.motionState != MotionState.STOPPED && currentRoute != Screens.ActiveFocusScreen::class.qualifiedName) {
                            navController.navigate(Screens.ActiveFocusScreen) {
                                popUpTo(Screens.FocusScreen) {
                                    inclusive = true
                                    saveState = false
                                }
                                restoreState = false
                            }
                        }
                    }

                    Scaffold(
                        bottomBar = {
                            if (shouldShowBottomBar) {
                                TempoBottomNavigation(
                                    items = bottomNavigationItems,
                                    navController = navController,
                                    currentRoute = currentRoute
                                )
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        NavigationRoot(
                            navController = navController,
                            isLoggedIn = state.isLoggedIn,
                            modifier = Modifier
                                .padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
