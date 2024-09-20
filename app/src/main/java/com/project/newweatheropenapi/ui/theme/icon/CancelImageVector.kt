package com.project.newweatheropenapi.ui.theme.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.Suppress

val CancelImageVector: ImageVector
    get() {
        if (_CancelImageVector != null) {
            return _CancelImageVector!!
        }
        _CancelImageVector = ImageVector.Builder(
            name = "Cancel",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(12f, 2f)
                curveTo(6.47f, 2f, 2f, 6.47f, 2f, 12f)
                reflectiveCurveToRelative(4.47f, 10f, 10f, 10f)
                reflectiveCurveToRelative(10f, -4.47f, 10f, -10f)
                reflectiveCurveTo(17.53f, 2f, 12f, 2f)
                close()
                moveTo(17f, 15.59f)
                lineTo(15.59f, 17f)
                lineTo(12f, 13.41f)
                lineTo(8.41f, 17f)
                lineTo(7f, 15.59f)
                lineTo(10.59f, 12f)
                lineTo(7f, 8.41f)
                lineTo(8.41f, 7f)
                lineTo(12f, 10.59f)
                lineTo(15.59f, 7f)
                lineTo(17f, 8.41f)
                lineTo(13.41f, 12f)
                lineTo(17f, 15.59f)
                close()
            }
        }.build()

        return _CancelImageVector!!
    }

@Suppress("ObjectPropertyName")
private var _CancelImageVector: ImageVector? = null

@Preview(showBackground = true)
@Composable
private fun CancelImageVectorPreview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = CancelImageVector, contentDescription = null)
    }
}
