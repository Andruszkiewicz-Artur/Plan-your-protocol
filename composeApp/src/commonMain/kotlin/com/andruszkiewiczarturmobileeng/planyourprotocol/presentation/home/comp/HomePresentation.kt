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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavHostController
import com.andruszkiewiczarturmobileeng.planyourprotocol.navigation.Screen
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.AddEditEvent
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.comp.PopUpOfCalendar
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.HomeEvent
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.HomeViewModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.util.Constant
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun HomePresentation(
    prefs: DataStore<Preferences>,
    vm: HomeViewModel = koinViewModel(),
    navHostController: NavHostController
) {
    val state = vm.state.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        floatingActionButton = {
            AnimatedVisibility(!state.isSearchValue) {
                FloatingActionButton(
                    onClick = {
                        navHostController.navigate(Screen.AddEdit.route)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                }
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = state.searchValue,
                            onValueChange = {
                                vm.onEvent(HomeEvent.ChangeSearchValue(it))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = null
                                )
                            },
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                }
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                capitalization = KeyboardCapitalization.None,
                                imeAction = ImeAction.Done
                            ),
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .onFocusChanged {
                                    if (it.isFocused) vm.onEvent(HomeEvent.ChangeSearchState(true))
                                }
                                .weight(1f)
                                .padding(horizontal = 16.dp)
                        )

                        androidx.compose.animation.AnimatedVisibility (state.isSearchValue) {
                            TextButton(
                                onClick = {
                                    vm.onEvent(HomeEvent.ChangeSearchState(false))
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                },
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(
                                    text = "Cancel"
                                )
                            }
                        }
                    }

                },
                actions = {
                    AnimatedVisibility(!state.isSearchValue) {
                        IconButton(
                            onClick = { vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfSettings(true)) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        AnimatedContent(
            state.isSearchValue,
            modifier = Modifier
                .padding(padding)
        ) { isSearching ->
            if (isSearching) {
                SearchListView(
                    vm = vm,
                    state = state,
                    navHostController = navHostController
                )
            } else {
                ProtocolListView(
                    vm = vm,
                    state = state,
                    navHostController = navHostController
                )
            }
        }
    }

    AnimatedVisibility(state.isPresentedCalendar) {
        PopUpOfCalendar(
            onDismiss = { vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfCalendar(false)) },
            onSave = {
                vm.onEvent(HomeEvent.SetDate(it))
                vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfCalendar(false))
            }
        )
    }

    AnimatedVisibility(state.isPresentedSettings) {
        PopUpOfSettings(
            onDismiss = { vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfSettings(false)) },
            onClickButton = { themType ->
                scope.launch {
                    prefs.edit { dataStore ->
                        dataStore[stringPreferencesKey(Constant.THEM_PREFS)] = themType.name
                    }
                }
            }
        )
    }
}