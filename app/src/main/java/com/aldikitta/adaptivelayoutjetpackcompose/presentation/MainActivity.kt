package com.aldikitta.adaptivelayoutjetpackcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import com.aldikitta.adaptivelayoutjetpackcompose.presentation.utils.DevicePosture
import com.aldikitta.adaptivelayoutjetpackcompose.presentation.utils.isBookPosture
import com.aldikitta.adaptivelayoutjetpackcompose.presentation.utils.isSeparating
import com.aldikitta.adaptivelayoutjetpackcompose.ui.theme.AdaptiveLayoutJetpackComposeTheme
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainActivity : ComponentActivity() {
    private val viewModel: AdaptiveViewModel by viewModels()
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Flow of [DevicePosture] that emits every time there's a change in the windowLayoutInfo
         */
        val devicePostureFlow = WindowInfoTracker.getOrCreate(this).windowLayoutInfo(this)
            .flowWithLifecycle(this.lifecycle)
            .map { layoutInfo ->
                val foldingFeature =
                    layoutInfo.displayFeatures
                        .filterIsInstance<FoldingFeature>()
                        .firstOrNull()
                when {
                    isBookPosture(foldingFeature) ->
                        DevicePosture.BookPosture(foldingFeature.bounds)

                    isSeparating(foldingFeature) ->
                        DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

                    else -> DevicePosture.NormalPosture
                }
            }
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = DevicePosture.NormalPosture
            )
        setContent {
            AdaptiveLayoutJetpackComposeTheme {
                val windowSize = calculateWindowSizeClass(this)
                val devicePosture by devicePostureFlow.collectAsState()
                val uiState by viewModel.uiState.collectAsState()

                AdaptiveApp(
                    windowSize = windowSize,
                    foldingDevicePosture = devicePosture,
                    replyHomeUIState = uiState,
                    closeDetailScreen = {
                        viewModel.closeDetailScreen()
                    },
                    navigateToDetail = { emailId, pane ->
                        viewModel.setSelectedEmail(emailId, pane)
                    }
                )
            }
        }
    }
}

//@Preview
//@DevicePreviews
//@Composable
//fun DefaultPreview() {
//    AdaptiveLayoutJetpackComposeTheme {
//        AdaptiveApp(windowSize = , foldingDevicePosture = , replyHomeUIState = )
//    }
//}
