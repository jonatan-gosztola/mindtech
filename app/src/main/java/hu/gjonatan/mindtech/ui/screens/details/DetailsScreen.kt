package hu.gjonatan.mindtech.ui.screens.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import hu.gjonatan.mindtech.ui.theme.MindtechTheme

@Composable
fun DetailsScreen(viewModel: DetailsScreenViewModel) {
    DetailsScreenContent()
}

@Composable
private fun DetailsScreenContent() {
    Text("Details screen")
}

@Preview(showBackground = true)
@Composable
private fun DetailsScreenContentPreview() {
    MindtechTheme {
        DetailsScreenContent()
    }
}
