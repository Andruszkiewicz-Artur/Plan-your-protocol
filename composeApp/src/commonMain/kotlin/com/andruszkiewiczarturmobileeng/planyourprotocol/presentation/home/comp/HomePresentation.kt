package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.CalendarOption
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.HomeEvent
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.HomeViewModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.unit.convertLongToStringDate
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun HomePresentation(
    vm: HomeViewModel = koinViewModel()
) {
    val state = vm.state.collectAsState().value
    val clipboardManager = LocalClipboardManager.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {

    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Plan Your day"
                    )
                },
                actions = {
                    AnimatedContent(state.isPresentedAddNewProtocol) { isPresented ->
                        if(isPresented) {
                            IconButton(
                                onClick = { vm.onEvent(HomeEvent.ClickPresentAddNewValue) }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowUpward,
                                    contentDescription = null
                                )
                            }
                        } else {
                            IconButton(
                                onClick = { vm.onEvent(HomeEvent.ClickPresentAddNewValue) }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowDownward,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 8.dp)
        ) {
            item {
                AnimatedVisibility(state.isPresentedAddNewProtocol) {
                    AddingNewValueView(
                        dataInfo = state.currentProtocol,
                        onClickShowPopUpOfReason = { vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfReason(it)) },
                        onClickShowPopUpOfTimer = { vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfTimer(it)) },
                        onClickShowPopUpOfDate = { vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfCalendar(it, CalendarOption.CadDate))  },
                        onClickAdd = { vm.onEvent(HomeEvent.SetProtocol()) },
                        onChangeValueIdDocument = { vm.onEvent(HomeEvent.SetIdOfProtocol(it)) },
                        onClickDateOfRealization = { vm.onEvent(HomeEvent.SetTypeOfPlaningProtocol(it)) },
                        isEditing = state.isEditing
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Protocols date",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { vm.onEvent(HomeEvent.ChangeDateListOfProtocols(true)) }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ChevronLeft,
                                    contentDescription = null
                                )
                            }

                            Text(
                                text = state.currentDatePresenting.convertLongToStringDate(),
                                style = MaterialTheme.typography.titleMedium
                            )

                            IconButton(
                                onClick = { vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfCalendar(true, CalendarOption.PresentingCurrentListDate)) }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CalendarMonth,
                                    contentDescription = null
                                )
                            }

                            IconButton(
                                onClick = { vm.onEvent(HomeEvent.ChangeDateListOfProtocols(false)) }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ChevronRight,
                                    contentDescription = null
                                )
                            }
                        }
                    }

                    Spacer(Modifier.width(16.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                    ) {
                        Text(
                            text = "This month",
                            style = MaterialTheme.typography.labelLarge
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = "${state.protocolsInThisMonth}",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }

                AnimatedVisibility(
                    state.protocolsList.isNotEmpty(),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        TextButton(
                            onClick = { vm.onEvent(HomeEvent.ChangeAllSelection(!state.isAllSelected)) }
                        ) {
                            AnimatedContent(state.isAllSelected) { isSelected ->
                                if (isSelected) {
                                    Text(
                                        text = "Unselect All"
                                    )
                                } else {
                                    Text(
                                        text = "Select All"
                                    )
                                }
                            }
                        }


                        IconButton(
                            onClick = {
                                vm.onEvent(HomeEvent.ClickCopyData(clipboardManager))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = null
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                AnimatedVisibility(state.protocolsList.isEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Today's list is empty",
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                        )
                    }
                }
            }

            items(state.protocolsList) { protocol ->
                DataInfoItem(
                    protocol = protocol,
                    onClickDelete = { vm.onEvent(HomeEvent.DeleteProtocol(protocol)) },
                    onClickEdit = { vm.onEvent(HomeEvent.ChooseProtocol(protocol)) },
                    onClickSelect = {vm.onEvent(HomeEvent.SelectProtocol(protocol, it))}
                )

                if (state.protocolsList.last() != protocol) {
                    HorizontalDivider()
                }
            }
        }
    }

    AnimatedVisibility(state.isPresentedReasons) {
        PopUpOfReasonDialog(
            onDismiss = { vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfReason(false)) },
            onSave = {
                vm.onEvent(HomeEvent.SetReasonOfProtocol(it))
                vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfReason(false))
            }
        )
    }

    AnimatedVisibility(state.isPresentedTimer) {
        PopUpOfTimer(
            onDismiss = { vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfTimer(false)) },
            onSave = {
                vm.onEvent(HomeEvent.SetTimeOfProtocol(it))
                vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfTimer(false))
            }
        )
    }

    AnimatedVisibility(state.isPresentedCalendar) {
        PopUpOfCalendar(
            onDismiss = { vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfCalendar(false, null)) },
            onSave = {
                vm.onEvent(HomeEvent.SetDate(it))
                vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfCalendar(false, null))
            }
        )
    }
}