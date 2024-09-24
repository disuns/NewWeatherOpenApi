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

val ErrorImageVector: ImageVector
    get() {
        if (_ErrorImageVector != null) {
            return _ErrorImageVector!!
        }
        _ErrorImageVector = ImageVector.Builder(
            name = "ErrorImageVector",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(12f, 2f)
                curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
                reflectiveCurveToRelative(4.48f, 10f, 10f, 10f)
                reflectiveCurveToRelative(10f, -4.48f, 10f, -10f)
                reflectiveCurveTo(17.52f, 2f, 12f, 2f)
                close()
                moveTo(13f, 17f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(2f)
                close()
                moveTo(13f, 13f)
                horizontalLineToRelative(-2f)
                lineTo(11f, 7f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(6f)
                close()
            }
        }.build()

        return _ErrorImageVector!!
    }

@Suppress("ObjectPropertyName")
private var _ErrorImageVector: ImageVector? = null

@Preview(showBackground = true)
@Composable
private fun ErrorImageVectorPreview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = ErrorImageVector, contentDescription = null)
    }
}