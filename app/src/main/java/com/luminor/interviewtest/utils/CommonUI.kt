package com.luminor.interviewtest.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.luminor.core.R
import com.luminor.interviewtest.theme.BlackColor
import com.luminor.interviewtest.theme.GrayTextColor
import com.luminor.interviewtest.theme.SizeFifty
import com.luminor.interviewtest.theme.SizeFive
import com.luminor.interviewtest.theme.SizeOne
import com.luminor.interviewtest.theme.SizeTen
import com.luminor.interviewtest.theme.SizeTwentyFour
import com.luminor.interviewtest.theme.SizeTwo
import com.luminor.interviewtest.theme.TextSizeSixteen
import com.luminor.interviewtest.theme.TextStyle.TextBold
import com.luminor.interviewtest.theme.TextStyle.TextRegular

const val EMPTY_STRING = ""

@Preview(showBackground = true)
@Composable
fun RoundCornerButton(
    value: String = stringResource(R.string.title),
    onclick: () -> Unit = {},
    roundClipping: Dp = SizeTwentyFour,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(SizeFifty),
    textColor: Color = White,
    backgroundColor: Color = BlackColor,
    enabled: Boolean = true,
    textSize: TextUnit = TextSizeSixteen,
    textStyle: TextStyle = TextRegular.copy(fontSize = textSize),
    textDisplay: @Composable () -> Unit = {
        Text(
            text = value, style = textStyle, color = textColor
        )
    },
    buttonColor: ButtonColors = ButtonDefaults.buttonColors(backgroundColor)
) {
    Button(
        onClick = onclick,
        modifier = modifier,
        shape = RoundedCornerShape(size = roundClipping),
        colors = buttonColor,
        enabled = enabled
    ) {
        textDisplay()
    }
}


@Preview(showBackground = true)
@Composable
fun Edittext(
    value: String = "Sample text",
    placeHolderText: String = "test",
    isSingleLine: Boolean = true,
    borderColor: Color = BlackColor,
    isPasswordEnabledField: Boolean = false,
    backgroundColor: Color = White,
    onTextChanged: (String) -> Unit = {},
) {
    TextField(
        value = value,
        visualTransformation = if (isPasswordEnabledField) PasswordVisualTransformation() else VisualTransformation.None,
        onValueChange = { onTextChanged(it) },
        singleLine = isSingleLine,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgroundColor,
        ),
        placeholder = {
            Text(
                text = placeHolderText,
                color = GrayTextColor,
                fontSize = TextSizeSixteen
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(SizeTwo, borderColor), shape = RoundedCornerShape(SizeFive)
            )

    )
}

@Preview(showBackground = true)
@Composable
fun Splitter(
    value: String = "or",
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(SizeOne)
                .align(Alignment.CenterVertically)
                .padding(end = SizeTen)
                .background(BlackColor)
        )

        Text(value, color = GrayTextColor)
        Box(
            modifier = Modifier
                .weight(1f)
                .height(SizeOne)
                .align(Alignment.CenterVertically)
                .padding(start = SizeTen)
                .background(BlackColor)
        )
    }
}


@Composable
fun AlertDialogUi(
    title: String = EMPTY_STRING,
    messageText: String = EMPTY_STRING,
    positiveButtonText: String = "Ok",
    negativeButtonText: String = "Cancel",
    onDismiss: () -> Unit = {},
    onCancelClicked: () -> Unit = {},
    onOkButtonClicked: () -> Unit = {},
    onCancelButtonEnabled: Boolean = false,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            if (title.isNotEmpty()) Text(text = title, style = TextBold)
        },
        text = { Text(text = messageText, style = TextRegular) },
        confirmButton = {
            Text(text = positiveButtonText,
                modifier = Modifier
                    .padding(SizeTen)
                    .clickable {
                        onDismiss()
                        onOkButtonClicked()
                    })
        },
        dismissButton = {
            if (onCancelButtonEnabled) {
                Text(text = negativeButtonText,
                    modifier = Modifier
                        .padding(SizeTen)
                        .clickable {
                            onDismiss()
                            onCancelClicked()
                        })
            }
        }
    )
}

@Composable
fun VerticalSpacer(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}