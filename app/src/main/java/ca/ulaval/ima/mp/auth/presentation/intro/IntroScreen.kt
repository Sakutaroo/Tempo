package ca.ulaval.ima.mp.auth.presentation.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.ulaval.ima.mp.auth.domain.intro.Quote
import ca.ulaval.ima.mp.auth.domain.intro.introQuotes
import ca.ulaval.ima.mp.auth.presentation.intro.components.AuthButtons
import ca.ulaval.ima.mp.auth.presentation.intro.components.QuoteText
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray50
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoSphere
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoTopBar

@Composable
fun IntroScreenRoot(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    IntroScreen(
        onAction = { action ->
            when (action) {
                IntroAction.OnLoginClick -> onLoginClick()
                IntroAction.OnRegisterClick -> onRegisterClick()
            }
        }
    )
}

@Composable
fun IntroScreen(
    onAction: (IntroAction) -> Unit,
) {
    val quote: Quote = introQuotes[(0..(introQuotes.size - 1)).random()]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray50)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(60.dp)
    ) {
        TempoTopBar()
        TempoSphere()
        QuoteText(
            quote = quote.quote,
            author = quote.author
        )
        AuthButtons(
            onLoginClick = {
                onAction(IntroAction.OnLoginClick)
            },
            onRegisterClick = {
                onAction(IntroAction.OnRegisterClick)
            }
        )
    }
}

@Preview
@Composable
private fun Preview() {
    TempoTheme {
        IntroScreen(
            onAction = { }
        )
    }
}
