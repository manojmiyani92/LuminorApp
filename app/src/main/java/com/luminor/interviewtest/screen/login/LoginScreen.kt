package com.luminor.interviewtest.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luminor.core.model.AuthException
import com.luminor.interviewtest.R
import com.luminor.interviewtest.navigation.NavigationRoute
import com.luminor.interviewtest.screen.AuthViewmodel
import com.luminor.interviewtest.theme.BlackColor
import com.luminor.interviewtest.theme.SizeTen
import com.luminor.interviewtest.theme.SizeThirty
import com.luminor.interviewtest.theme.SizeTwenty
import com.luminor.interviewtest.theme.SkyColor
import com.luminor.interviewtest.theme.TextSizeTwentyFive
import com.luminor.interviewtest.theme.TextStyle.TextRegular
import com.luminor.interviewtest.utils.AlertDialogUi
import com.luminor.interviewtest.utils.Edittext
import com.luminor.interviewtest.utils.RoundCornerButton
import com.luminor.interviewtest.utils.Splitter
import com.luminor.interviewtest.utils.VerticalSpacer

@Composable
fun LoginScreen(
    navController: NavController = NavController(LocalContext.current),
    authViewmodel: AuthViewmodel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        authViewmodel.autoLoginByValidSession()
    }

    val userDetail = authViewmodel.userDetail.collectAsState()
    val errorMessage = authViewmodel.errorMessage.collectAsState().value
    userDetail.value?.let {
        navController.navigate(
            NavigationRoute.DashBoard().destination.replace(
                "{id}",
                it.id.toString()
            )
        ) {
            popUpTo(NavigationRoute.Login().destination) { inclusive = true }
        }
    }

    when (errorMessage) {
        is AuthException.InvalidEmail -> {
            AlertDialogUi(
                title = stringResource(R.string.invalid_email),
                messageText = errorMessage.msg,
                onDismiss = {
                    authViewmodel.clearState()
                })
        }

        is AuthException.PasswordBlank, is AuthException.EmptyEmail,
        is AuthException.PasswordComplexity, is AuthException.PasswordMismatched -> {
            AlertDialogUi(
                title = stringResource(R.string.password_error),
                messageText = errorMessage.message.toString(),
                onDismiss = {
                    authViewmodel.clearState()
                })
        }

        is AuthException.UnknownException -> {
            AlertDialogUi(
                title = stringResource(R.string.warning),
                messageText = errorMessage.msg,
                onDismiss = {
                    authViewmodel.clearState()
                })
        }

        is AuthException.Nothing -> {

        }
    }


    LoginView(loginButtonClicked = { email, password ->
        authViewmodel.userLogin(email = email, password = password)
    }, registerButtonClicked = {
        navController.navigate(NavigationRoute.Register().destination)
    })

}

@Preview(showBackground = true)
@Composable
fun LoginView(
    loginButtonClicked: (String, String) -> Unit = { _, _ -> },
    registerButtonClicked: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(SizeTen)

    ) {
        Column(
            modifier = Modifier
                .weight(0.5f)
                .align(alignment = Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_icon),
                contentDescription = stringResource(R.string.app_name),
            )
            Text(
                text = stringResource(R.string.authentication),
                style = TextRegular,
                color = BlackColor,
                fontSize = TextSizeTwentyFive
            )
        }


        Column(
            modifier = Modifier
                .weight(0.5f)
        ) {

            VerticalSpacer(SizeTen)
            Edittext(email, stringResource(R.string.e_mail)) {
                email = it
            }
            VerticalSpacer(SizeTen)
            Edittext(
                value = password,
                isPasswordEnabledField = true,
                placeHolderText = stringResource(R.string.password)
            ) {
                password = it
            }
            VerticalSpacer(SizeThirty)
            RoundCornerButton(stringResource(R.string.login),
                onclick = { loginButtonClicked(email, password) })
            VerticalSpacer(SizeTwenty)
            Splitter()
            VerticalSpacer(SizeTwenty)
            RoundCornerButton(
                value = stringResource(R.string.register),
                backgroundColor = SkyColor,
                onclick = {
                    registerButtonClicked()
                }
            )
        }

    }

}


