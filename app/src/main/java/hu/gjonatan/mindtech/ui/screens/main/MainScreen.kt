package hu.gjonatan.mindtech.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import hu.gjonatan.mindtech.ui.theme.MindtechTheme

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    detailsButtonClicked: () -> Unit,
) {
    MainScreenContent(
        detailsButtonClicked = detailsButtonClicked
    )
}

@Composable
private fun MainScreenContent(
    detailsButtonClicked: () -> Unit
) {
    Column() {
        Text("Main screen content")

        Button(
            onClick = detailsButtonClicked
        ) {
            Text("Details")
        }

    }


}

@Preview(showBackground = true)
@Composable
private fun MainScreenContentPreview() {
    MindtechTheme {
        MainScreenContent(
            detailsButtonClicked = {}
        )
    }
}
