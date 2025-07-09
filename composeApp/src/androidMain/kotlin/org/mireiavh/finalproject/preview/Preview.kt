package org.mireiavh.finalproject.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.mireiavh.finalproject.application.InitialView
import org.mireiavh.finalproject.application.LoginView


@Preview(showBackground = true)
@Composable
fun InitialViewPreview() {
    InitialView(
        navigateToLogin = {},
        navigateToSignUp = {},
        onGoogleLoginClick = {}
    )
}


@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    LoginView(
        navigateToInitial = {},
        navigateToHome = {},
        auth = TODO()
    )
}


