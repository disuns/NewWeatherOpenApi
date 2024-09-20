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

val ArrowDropDownImageVector: ImageVector
    get() {
        if (_ArrowDropDownImageVector != null) {
            return _ArrowDropDownImageVector!!
        }
        _ArrowDropDownImageVector = ImageVector.Builder(
            name = "ArrowDropDown",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(7f, 10f)
                lineToRelative(5f, 5f)
                lineToRelative(5f, -5f)
                close()
            }
        }.build()

        return _ArrowDropDownImageVector!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowDropDownImageVector: ImageVector? = null

@Preview(showBackground = true)
@Composable
private fun ArrowDropDownImageVectorPreview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = ArrowDropDownImageVector, contentDescription = null)
    }
}