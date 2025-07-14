package org.mireiavh.finalprojectt.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.mireiavh.finalprojectt.ui.InitialView
import org.mireiavh.finalprojectt.ui.LoginView


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


