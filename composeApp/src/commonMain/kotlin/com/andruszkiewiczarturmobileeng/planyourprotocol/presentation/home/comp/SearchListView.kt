package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.andruszkiewiczarturmobileeng.planyourprotocol.navigation.Screen
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.HomeEvent
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.HomeState
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.HomeViewModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.util.Constant

@Composable
fun SearchListView(
    vm: HomeViewModel,
    state: HomeState,
    navHostController: NavHostController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedVisibility(vm.state.value.searchingList.isEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Empty list",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                    )
                }
            }
        }

        items(state.searchingList) { protocol ->
            DataInfoItem(
                protocol = protocol,
                onClickDelete = { vm.onEvent(HomeEvent.DeleteProtocol(protocol)) },
                onClickEdit = {
                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                        Constant.EDIT_PROTOCOL_ARGUMENT,
                        protocol.idDocument
                    )
                    navHostController.navigate(Screen.AddEdit.route)
                },
                onClickSelect = {vm.onEvent(HomeEvent.SelectProtocol(protocol, it))}
            )

            if (state.searchingList.last() != protocol) {
                HorizontalDivider()
            }
        }
    }
}