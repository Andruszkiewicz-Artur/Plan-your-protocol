package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.DataInfoModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.DataInfoRealizationDate

@Composable
fun DataInfoItem(
    dataInfo: DataInfoModel,
    onClickEdit: () -> Unit,
    onClickDelete: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = "${dataInfo.idDocument} - " + when(dataInfo.state) {
                DataInfoRealizationDate.Today -> dataInfo.time
                DataInfoRealizationDate.CAD -> "${dataInfo.date} - ${dataInfo.resone}"
                DataInfoRealizationDate.PNA -> dataInfo.state.name
                DataInfoRealizationDate.CNA -> dataInfo.state.name
            },
            modifier = Modifier
                .padding(vertical = 4.dp)
        )

        Spacer(
            modifier = Modifier
                .width(8.dp)
        )

        Row {
            IconButton(
                onClick = { onClickEdit() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = null
                )
            }

            IconButton(
                onClick = { onClickDelete() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Remove,
                    contentDescription = null
                )
            }
        }
    }
}