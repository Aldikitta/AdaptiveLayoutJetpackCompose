package com.aldikitta.adaptivelayoutjetpackcompose.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aldikitta.adaptivelayoutjetpackcompose.R
import com.aldikitta.adaptivelayoutjetpackcompose.domain.model.Email
import com.aldikitta.adaptivelayoutjetpackcompose.presentation.components.AdaptiveAppBars
import com.aldikitta.adaptivelayoutjetpackcompose.presentation.components.EmailDetailAppBar
import com.aldikitta.adaptivelayoutjetpackcompose.presentation.components.ReplyEmailListItem
import com.aldikitta.adaptivelayoutjetpackcompose.presentation.components.ReplyEmailThreadItem
import com.aldikitta.adaptivelayoutjetpackcompose.presentation.utils.ReplyContentType
import com.aldikitta.adaptivelayoutjetpackcompose.presentation.utils.ReplyNavigationType

@Composable
fun AdaptiveListContent(
    contentType: ReplyContentType,
    replyHomeUIState: AdaptiveHomeUIState,
    navigationType: ReplyNavigationType,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, ReplyContentType) -> Unit,
    modifier: Modifier = Modifier
) {
    /**
     * When moving from LIST_AND_DETAIL page to LIST page clear the selection
     * and user should see LIST screen.
     */
    LaunchedEffect(key1 = contentType) {
        if (contentType == ReplyContentType.SINGLE_PANE && !replyHomeUIState.isDetailOnlyOpen) {
            closeDetailScreen()
        }
    }

    val emailLazyListState = rememberLazyListState()

    if (contentType == ReplyContentType.DUAL_PANE) {
        ReplyDualPaneContent(
            replyHomeUIState = replyHomeUIState,
            emailLazyListState = emailLazyListState,
            modifier = modifier.fillMaxSize(),
            navigateToDetail = navigateToDetail
        )
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            ReplySinglePaneContent(
                replyHomeUIState = replyHomeUIState,
                emailLazyListState = emailLazyListState,
                modifier = Modifier.fillMaxSize(),
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = navigateToDetail
            )
            // When we have bottom navigation we show FAB at the bottom end.
            if (navigationType == ReplyNavigationType.BOTTOM_NAVIGATION) {
                LargeFloatingActionButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.edit),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ReplySinglePaneContent(
    replyHomeUIState: AdaptiveHomeUIState,
    emailLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, ReplyContentType) -> Unit
) {
    if (replyHomeUIState.selectedEmail != null && replyHomeUIState.isDetailOnlyOpen) {
        BackHandler {
            closeDetailScreen()
        }
        ReplyEmailDetail(email = replyHomeUIState.selectedEmail) {
            closeDetailScreen()
        }
    } else {
        LazyColumn(modifier = modifier, state = emailLazyListState) {
            item {
                AdaptiveAppBars(modifier = Modifier.fillMaxWidth())
            }
            items(items = replyHomeUIState.emails, key = { it.id }) { email ->
                ReplyEmailListItem(email = email) { emailId ->
                    navigateToDetail(emailId, ReplyContentType.SINGLE_PANE)
                }
            }
        }
    }
}

@Composable
fun ReplyDualPaneContent(
    replyHomeUIState: AdaptiveHomeUIState,
    emailLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long, ReplyContentType) -> Unit
) {
    Row(modifier = modifier) {
        LazyColumn(modifier = modifier.weight(1f), state = emailLazyListState) {
            item {
                AdaptiveAppBars(modifier = Modifier.fillMaxWidth())
            }
            items(items = replyHomeUIState.emails, key = { it.id }) { email ->
                ReplyEmailListItem(
                    email = email,
                    isSelectable = true,
                    isSelected = replyHomeUIState.selectedEmail?.id == email.id
                ) {
                    navigateToDetail(it, ReplyContentType.DUAL_PANE)
                }
            }
        }
        ReplyEmailDetail(
            modifier = Modifier.weight(1f),
            isFullScreen = false,
            email = replyHomeUIState.selectedEmail ?: replyHomeUIState.emails.first()
        )
    }
}

@Composable
fun ReplyEmailDetail(
    email: Email,
    isFullScreen: Boolean = true,
    modifier: Modifier = Modifier.fillMaxSize(),
    onBackPressed: () -> Unit = {}
) {
    LazyColumn(modifier = modifier.background(MaterialTheme.colorScheme.inverseOnSurface)) {
        item {
            EmailDetailAppBar(email, isFullScreen) {
                onBackPressed()
            }
        }
        items(items = email.threads, key = { it.id }) { email ->
            ReplyEmailThreadItem(email = email)
        }
    }
}