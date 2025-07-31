package ca.ulaval.ima.mp.focus.presentation.focus_overview

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray50
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray950
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoActionButton
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoLoader
import ca.ulaval.ima.mp.focus.domain.Session
import ca.ulaval.ima.mp.focus.presentation.components.FocusGauge
import ca.ulaval.ima.mp.focus.presentation.components.FocusLabeledItems
import ca.ulaval.ima.mp.focus.presentation.focus_overview.components.FocusSessionList
import ca.ulaval.ima.mp.focus.presentation.models.SessionUi
import org.koin.androidx.compose.koinViewModel

@Composable
fun FocusScreenRoot(
    onSessionClick: (Session) -> Unit,
    onStartSessionClick: () -> Unit,
    viewModel: FocusViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FocusScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is FocusAction.OnSessionClick -> onSessionClick(action.session)
                FocusAction.OnStartSessionClick -> onStartSessionClick()
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun FocusScreen(
    state: FocusState,
    onAction: (FocusAction) -> Unit,
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
            FocusSessionList(
                sessions = state.sessions,
                onSessionClick = { session ->
                    onAction(FocusAction.OnSessionClick(session))
                }
            )
        } else if (state.areSessionsLoading == true) {
            TempoLoader(
                color = Gray950
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        TempoActionButton(
            text = stringResource(R.string.start_a_new_session),
            isLoading = false,
            onClick = {
                onAction(FocusAction.OnStartSessionClick)
            }
        )
    }
}

@Preview
@Composable
private fun Preview() {
    TempoTheme {
        FocusScreen(
            state = FocusState(
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
