package com.aldikitta.adaptivelayoutjetpackcompose

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Small Font",
    group = "Small Font Group",
    fontScale = 0.5f,
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
//    showSystemUi = true
)
@Preview(
    name = "Medium Font",
    group = "Medium Font Group",
    fontScale = 0.5f,
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
//    showSystemUi = true
)
annotation class FontScalePreviews

@Preview(name = "Pixel 6 Pro", group = "Devices", device = Devices.PIXEL, showSystemUi = true)
@Preview(name = "Default", group = "Devices", device = Devices.DEFAULT, showSystemUi = true)
@Preview(name = "Foldable", group = "Devices", device = Devices.FOLDABLE, showSystemUi = true)
@Preview(name = "Tablet", group = "Devices", device = Devices.TABLET, showSystemUi = true)
@Preview(name = "Desktop", group = "Devices", device = Devices.DESKTOP, showSystemUi = true)

annotation class DevicePreviews