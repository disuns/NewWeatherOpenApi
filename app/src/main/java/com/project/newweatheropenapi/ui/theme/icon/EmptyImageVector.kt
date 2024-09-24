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

val EmptyImageVector: ImageVector
    get() {
        if (_EmptyImageVector != null) {
            return _EmptyImageVector!!
        }
        _EmptyImageVector = ImageVector.Builder(
            name = "BaselineHourglassEmpty24",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(6f, 2f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(0.01f)
                lineTo(6f, 8.01f)
                lineTo(10f, 12f)
                lineToRelative(-4f, 4f)
                lineToRelative(0.01f, 0.01f)
                lineTo(6f, 16.01f)
                lineTo(6f, 22f)
                horizontalLineToRelative(12f)
                verticalLineToRelative(-5.99f)
                horizontalLineToRelative(-0.01f)
                lineTo(18f, 16f)
                lineToRelative(-4f, -4f)
                lineToRelative(4f, -3.99f)
                lineToRelative(-0.01f, -0.01f)
                lineTo(18f, 8f)
                lineTo(18f, 2f)
                lineTo(6f, 2f)
                close()
                moveTo(16f, 16.5f)
                lineTo(16f, 20f)
                lineTo(8f, 20f)
                verticalLineToRelative(-3.5f)
                lineToRelative(4f, -4f)
                lineToRelative(4f, 4f)
                close()
                moveTo(12f, 11.5f)
                lineToRelative(-4f, -4f)
                lineTo(8f, 4f)
                horizontalLineToRelative(8f)
                verticalLineToRelative(3.5f)
                lineToRelative(-4f, 4f)
                close()
            }
        }.build()

        return _EmptyImageVector!!
    }

@Suppress("ObjectPropertyName")
private var _EmptyImageVector: ImageVector? = null

@Preview(showBackground = true)
@Composable
private fun EmptyImageVectorPreview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = EmptyImageVector, contentDescription = null)
    }
}