package com.luminor.interviewtest.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luminor.core.model.UserDetail
import com.luminor.interviewtest.R
import com.luminor.interviewtest.navigation.NavigationRoute
import com.luminor.interviewtest.screen.AuthViewmodel
import com.luminor.interviewtest.theme.BlackColor
import com.luminor.interviewtest.theme.SizeTen
import com.luminor.interviewtest.theme.SizeTwenty
import com.luminor.interviewtest.theme.TextSizeTwentyFive
import com.luminor.interviewtest.theme.TextStyle.TextRegular
import com.luminor.interviewtest.utils.RoundCornerButton
import com.luminor.interviewtest.utils.VerticalSpacer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DashBoardScreen(
    navController: NavController = NavController(LocalContext.current),
    authViewmodel: AuthViewmodel = hiltViewModel(),
    uid: Int
) {
    val logoutSuccess = authViewmodel.isUserLogoutSuccessfully.collectAsState()
    if (logoutSuccess.value) {
        navController.navigate(NavigationRoute.Login().destination) {
            popUpTo(NavigationRoute.DashBoard().destination) { inclusive = true }
        }
    }
    LaunchedEffect(Unit) {
        authViewmodel.getUserById(id = uid)
    }
    DashBoardScreenView(userDetailFlow = authViewmodel.userDetail, logoutClicked = {
        authViewmodel.logout()
    })
}

@Preview(showBackground = true)
@Composable
fun DashBoardScreenView(
    userDetailFlow: StateFlow<UserDetail?> = MutableStateFlow(null),
    logoutClicked: () -> Unit = {}
) {
    val userDetail = userDetailFlow.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(SizeTen)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = userDetail.value?.email ?: "",
                style = TextRegular,
                color = BlackColor,
                fontSize = TextSizeTwentyFive,

                )
            VerticalSpacer(SizeTwenty)
            RoundCornerButton(
                value = stringResource(R.string.logout),
                onclick = {
                    logoutClicked()
                }
            )
        }
    }
}


