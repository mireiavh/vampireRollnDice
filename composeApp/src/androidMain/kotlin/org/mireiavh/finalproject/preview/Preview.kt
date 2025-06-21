package org.mireiavh.finalproject.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.mireiavh.finalproject.presentation.InitialView
import org.mireiavh.finalproject.presentation.LoginView


@Preview(showBackground = true)
@Composable
fun InitialViewPreview() {
    InitialView(
        navigateToLogin = {},
        navigateToSignUp = {}
    )
}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    LoginView(
        navigateToInitial = {}
    )
}
