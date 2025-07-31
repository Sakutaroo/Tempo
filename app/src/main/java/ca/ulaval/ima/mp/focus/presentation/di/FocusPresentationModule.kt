package ca.ulaval.ima.mp.focus.presentation.di

import ca.ulaval.ima.mp.focus.domain.Session
import ca.ulaval.ima.mp.focus.presentation.active_focus.ActiveFocusViewModel
import ca.ulaval.ima.mp.focus.presentation.focus_overview.FocusViewModel
import ca.ulaval.ima.mp.focus.presentation.focus_summary.FocusSummaryViewModel
import ca.ulaval.ima.mp.focus.presentation.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val focusPresentationModule = module {
    viewModelOf(::FocusViewModel)
    viewModelOf(::ActiveFocusViewModel)
    viewModel { (session: Session, isEditing: Boolean) ->
        FocusSummaryViewModel(
            session = session,
            isEditing = isEditing,
            supaRepository = get()
        )
    }
    viewModelOf(::ProfileViewModel)
}
