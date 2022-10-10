package com.aldikitta.adaptivelayoutjetpackcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldikitta.adaptivelayoutjetpackcompose.data.repository_impl.EmailsRepositoryImpl
import com.aldikitta.adaptivelayoutjetpackcompose.domain.model.Email
import com.aldikitta.adaptivelayoutjetpackcompose.domain.repository.EmailsRepository
import com.aldikitta.adaptivelayoutjetpackcompose.presentation.utils.ReplyContentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AdaptiveViewModel(private val emailsRepository: EmailsRepository = EmailsRepositoryImpl()) :
    ViewModel() {
    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(AdaptiveHomeUIState(loading = true))
    val uiState: StateFlow<AdaptiveHomeUIState> = _uiState
//    val uiState = _uiState.asStateFlow()

    init {
        observeEmails()
    }

    private fun observeEmails(){
        viewModelScope.launch {
            emailsRepository.getAllEmails()
                .catch { ex ->
                    _uiState.value = AdaptiveHomeUIState(error = ex.message)
                }
                .collect{emails ->
                    /**
                     * We set first email selected by default for first App launch in large-screens
                     */
                    _uiState.value = AdaptiveHomeUIState(
                        emails = emails,
                        selectedEmail = emails.first()
                    )
                }
        }
    }

    fun setSelectedEmail(emailId: Long, contentType: ReplyContentType){
        /**
         * We only set isDetailOnlyOpen to true when it's only single pane layout
         */
        val email = uiState.value.emails.find { it.id == emailId }
        _uiState.value = _uiState.value.copy(
            selectedEmail = email,
            isDetailOnlyOpen = contentType == ReplyContentType.SINGLE_PANE
        )
    }
    fun closeDetailScreen() {
        _uiState.value = _uiState
            .value.copy(
                isDetailOnlyOpen = false,
                selectedEmail = _uiState.value.emails.first()
            )
    }
}

data class AdaptiveHomeUIState(
    val emails: List<Email> = emptyList(),
    val selectedEmail: Email? = null,
    val isDetailOnlyOpen: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)