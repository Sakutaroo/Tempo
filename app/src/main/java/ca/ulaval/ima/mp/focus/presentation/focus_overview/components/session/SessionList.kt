package ca.ulaval.ima.mp.focus.presentation.focus_overview.components.session

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme
import ca.ulaval.ima.mp.focus.domain.Session
import ca.ulaval.ima.mp.focus.presentation.mappers.toSession
import ca.ulaval.ima.mp.focus.presentation.models.SessionUi

@Composable
fun SessionList(
    sessions: List<SessionUi>,
    onSessionClick: (Session) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        items (
            items = sessions,
        ) { session ->
            if (session.id != null) {
                SessionItem(
                    title = session.description,
                    duration = session.duration,
                    onSessionClick = {
                        onSessionClick(session.toSession())
                    }
                )
                HorizontalDivider()
            }
        }
    }
}

@Preview
@Composable
private fun SessionListPreview() {
    TempoTheme { 
        SessionList(
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
            onSessionClick = {}
        )
    }
}

