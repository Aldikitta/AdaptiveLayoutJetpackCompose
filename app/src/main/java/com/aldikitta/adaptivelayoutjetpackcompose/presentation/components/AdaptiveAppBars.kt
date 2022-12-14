package com.aldikitta.adaptivelayoutjetpackcompose.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aldikitta.adaptivelayoutjetpackcompose.R
import com.aldikitta.adaptivelayoutjetpackcompose.domain.model.Email
import com.aldikitta.adaptivelayoutjetpackcompose.ui.theme.spacing

@Composable
fun AdaptiveAppBars(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = MaterialTheme.spacing.medium24,
                bottom = MaterialTheme.spacing.extraMedium16,
                start = MaterialTheme.spacing.extraMedium16,
                end = MaterialTheme.spacing.extraMedium16
            )
            .background(MaterialTheme.colorScheme.surface, CircleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(id = R.string.search),
            modifier = Modifier.padding(start = MaterialTheme.spacing.extraMedium16),
            tint = MaterialTheme.colorScheme.outline
        )
        Text(
            text = stringResource(id = R.string.search_replies),
            modifier = Modifier
                .weight(1f)
                .padding(MaterialTheme.spacing.extraMedium16),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
        ReplyProfileImage(
            drawableResource = R.drawable.avatar_6,
            description = stringResource(id = R.string.profile),
            modifier = Modifier
                .padding(12.dp)
                .size(32.dp)
        )
    }
}

@Composable
fun EmailDetailAppBar(
    email: Email,
    isFullScreen: Boolean,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (isFullScreen) {
            FilledIconButton(
                onClick = onBackPressed,
                modifier = Modifier.padding(8.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_button),
                    modifier = Modifier.size(14.dp)
                )
            }
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = if (isFullScreen)
                Alignment.CenterHorizontally else Alignment.Start
        ) {
            Text(
                text = email.subject,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = "${email.threads.size} ${stringResource(id = R.string.messages)}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
        IconButton(
            onClick = { /*TODO*/ },
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(id = R.string.more_options_button),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
@Preview()
@Composable
fun PreviewsAppBar() {
    AdaptiveAppBars()
}