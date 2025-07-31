package ca.ulaval.ima.mp.focus.presentation.focus_overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray950
import ca.ulaval.ima.mp.focus.domain.Session
import ca.ulaval.ima.mp.focus.presentation.focus_overview.components.session.SessionList
import ca.ulaval.ima.mp.focus.presentation.models.SessionUi

@Composable
fun FocusSessionList(
    sessions: List<SessionUi>,
    onSessionClick: (Session) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.my_latest_sessions),
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = Gray950
        )
        SessionList(
            sessions = sessions,
            onSessionClick = { session ->
                onSessionClick(session)
            }
        )
    }
}
