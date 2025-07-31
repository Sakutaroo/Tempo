package ca.ulaval.ima.mp.focus.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray50
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray950
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoLoader
import ca.ulaval.ima.mp.core.presentation.ui.ObserveAsEvents
import ca.ulaval.ima.mp.focus.domain.Session
import ca.ulaval.ima.mp.focus.presentation.components.FocusGauge
import ca.ulaval.ima.mp.focus.presentation.components.FocusLabeledItems
import ca.ulaval.ima.mp.focus.presentation.models.SessionUi
import ca.ulaval.ima.mp.focus.presentation.profile.components.ProfileLogout
import ca.ulaval.ima.mp.focus.presentation.profile.components.ProfileSessionList
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreenRoot(
    onLogoutClick: () -> Unit,
    onSessionClick: (Session) -> Unit,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            ProfileEvent.UserLogout -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.you_are_disconnected),
                    Toast.LENGTH_LONG
                ).show()
                onLogoutClick()
            }
        }
    }

    ProfileScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ProfileAction.OnSessionClick -> {
                    onSessionClick(action.session)
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun ProfileScreen(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Gray50)
            .padding(16.dp)
    ) {
        FocusGauge(
            focusScoreAvg = state.focusScoreAvg
        )
        FocusLabeledItems(
            focusedTimeAvg = state.focusedTimeAvg,
            bestFocusScore = state.bestFocusScore,
            bestTime = state.bestTime
        )
        Spacer(modifier = Modifier.height(48.dp))
        if (state.sessions.isNotEmpty()) {
            ProfileSessionList(
                sessions = state.sessions,
                onSessionClick = { session ->
                    onAction(ProfileAction.OnSessionClick(session))
                },
                modifier = Modifier.weight(3f)
            )
        } else if (state.areSessionsLoading == true) {
            TempoLoader(
                color = Gray950,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        ProfileLogout(
            logoutLoading = state.logoutLoading,
            onLogoutClick = {
                onAction(ProfileAction.OnLogoutClick)
            }
        )
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    TempoTheme {
        ProfileScreen(
            state = ProfileState(
                sessions = listOf(
                    SessionUi(
                        id = "1",
                        duration = "00:15",
                        description = "Top"
                    ),
                    SessionUi(
                        id = "2",
                        duration = "01:30",
                        description = "Super"
                    )
                ),
                focusScoreAvg = "89",
                focusedTimeAvg = "00:15",
                bestFocusScore = "92",
                bestTime = "00:20"
            ),
            onAction = {}
        )
    }
}
