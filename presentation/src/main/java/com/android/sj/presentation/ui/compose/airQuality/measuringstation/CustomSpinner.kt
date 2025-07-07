package com.android.sj.presentation.ui.compose.airQuality.measuringstation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import com.android.sj.presentation.R
import com.android.sj.presentation.ui.theme.Color_ffd700

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSpinner(selectOption: (String) -> Unit) {
    var expandStatus by remember { mutableStateOf(false) }
    val rltmData = stringArrayResource(R.array.rltmData)
    var selectedOption by remember { mutableStateOf(rltmData[0]) }


    ExposedDropdownMenuBox(
        expanded = expandStatus,
        onExpandedChange = { expandStatus = !expandStatus },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimensionResource(R.dimen.ItemCornerShape)))
            .background(
                color = Color_ffd700,
                shape = RoundedCornerShape(dimensionResource(R.dimen.ItemCornerShape))
            )
    ) {
        TextField(
            readOnly = true,
            value = selectedOption,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandStatus) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        ExposedDropdownMenu(
            shape = RoundedCornerShape(dimensionResource(R.dimen.ItemCornerShape)),
            containerColor = Color_ffd700,
            expanded = expandStatus,
            onDismissRequest = { expandStatus = false }) {
            rltmData.forEachIndexed { _, data ->
                DropdownMenuItem(
                    text = { Text(text = data) },
                    onClick = {
                        selectedOption = data
                        selectOption(selectedOption)
                        expandStatus = false
                    }
                )
            }
        }
    }
}