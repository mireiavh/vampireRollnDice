package org.mireiavh.finalproject.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.mireiavh.finalproject.App
import org.mireiavh.finalproject.presentation.InitialView
import org.mireiavh.finalproject.presentation.LoginView

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

@Preview(showBackground = true)
@Composable
fun InitialViewPreview() {
    InitialView(
        navigateToLogin = {},
        navigateToSignUp = {}
    )
}

