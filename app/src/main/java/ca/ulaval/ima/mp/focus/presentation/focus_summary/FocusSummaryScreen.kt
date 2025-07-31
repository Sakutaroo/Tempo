package ca.ulaval.ima.mp.focus.presentation.focus_summary

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoTopBar
import ca.ulaval.ima.mp.core.presentation.ui.ObserveAsEvents
import ca.ulaval.ima.mp.focus.presentation.focus_summary.components.FocusSummaryButtons
import ca.ulaval.ima.mp.focus.presentation.focus_summary.components.FocusSummaryCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun FocusSummaryScreenRoot(
    onValidClick: () -> Unit,
    onBackClick: (() -> Unit)?,
    viewModel: FocusSummaryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            FocusSummaryEvent.SessionSaved -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.session_saved),
                    Toast.LENGTH_LONG
                ).show()
                onValidClick()
            }

            FocusSummaryEvent.SessionDeleted -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.session_deleted),
                    Toast.LENGTH_LONG
                ).show()
                onValidClick()
            }

            is FocusSummaryEvent.SessionSavedError -> {
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    FocusSummaryScreen(
        state = state,
        onAction = { action ->
            when (action) {
                FocusSummaryAction.OnBackClick -> onBackClick?.invoke()
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun FocusSummaryScreen(
    state: FocusSummaryState,
    onAction: (FocusSummaryAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Gray50)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(56.dp)
    ) {
        TempoTopBar(
            onBackClick = if (state.isEditing) {
                { onAction(FocusSummaryAction.OnBackClick) }
            } else {
                null
            }
        )
        FocusSummaryCard(
            descriptionState = state.descriptionTextField,
            sessionUi = state.session
        )
        FocusSummaryButtons(
            validLoading = state.validLoading,
            onValidClick = {
                onAction(FocusSummaryAction.OnValidClick)
            },
            deleteLoading = state.deleteLoading,
            onDeleteClick = {
                onAction(FocusSummaryAction.OnDeleteClick)
            },
            isEditing = state.isEditing
        )
    }
}

@Preview
@Composable
private fun Preview() {
    TempoTheme {
        FocusSummaryScreen(
            state = FocusSummaryState(
                isEditing = true
            ),
            onAction = {}
        )
    }
}
