package com.luminor.interviewtest.screen.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luminor.core.model.AuthException
import com.luminor.interviewtest.R
import com.luminor.interviewtest.screen.AuthViewmodel
import com.luminor.interviewtest.theme.BlackColor
import com.luminor.interviewtest.theme.SizeFifty
import com.luminor.interviewtest.theme.SizeTen
import com.luminor.interviewtest.theme.SizeTwenty
import com.luminor.interviewtest.theme.SkyColor
import com.luminor.interviewtest.theme.TextSizeTwentyFive
import com.luminor.interviewtest.theme.TextStyle.TextRegular
import com.luminor.interviewtest.utils.AlertDialogUi
import com.luminor.interviewtest.utils.Edittext
import com.luminor.interviewtest.utils.RoundCornerButton
import com.luminor.interviewtest.utils.VerticalSpacer

@Composable
fun RegisterScreen(
    navController: NavController = NavController(LocalContext.current),
    authViewmodel: AuthViewmodel = hiltViewModel()
) {
    val errorMessage = authViewmodel.errorMessage.collectAsState().value
    val isUserRegistered = authViewmodel.isUserRegistered.collectAsState()
    if (isUserRegistered.value) {
        AlertDialogUi(
            title = stringResource(R.string.registration),
            messageText = stringResource(R.string.user_registered_successfully),
            onDismiss = {
                authViewmodel.clearState()
                navController.popBackStack()
            })
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
    RegisterView(registerButtonClicked = { email, password, rePassword ->
        authViewmodel.userRegister(email = email, password = password, rePassword = rePassword)
    }){
        navController.popBackStack()
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterView(
    registerButtonClicked: (String, String, String) -> Unit = { _, _, _ -> },
    onBackClicked: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rePassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(SizeTen)
    ) {
        Image(
            painter = painterResource(id = R.mipmap.ic_back),
            contentDescription = "back ",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(SizeFifty)
                .clickable {
                    onBackClicked()
                }
        )
        Column(
            modifier = Modifier
                .weight(0.5f)
                .align(alignment = Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(R.string.register),
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
            VerticalSpacer(SizeTwenty)
            Edittext(
                value = rePassword,
                isPasswordEnabledField = true,
                placeHolderText = stringResource(R.string.re_password)
            ) {
                rePassword = it
            }

            VerticalSpacer(SizeTwenty)
            RoundCornerButton(
                value = stringResource(R.string.register),
                backgroundColor = SkyColor,
                onclick = {
                    registerButtonClicked(email, password, rePassword)
                }
            )
        }

    }
}


