package hu.gjonatan.mindtech.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.gjonatan.mindtech.ui.theme.MindtechTheme

@Composable
fun ErrorContent(
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Text("Something went wrong")
        Button(onClick = onRefreshClick) {
            Text("Refresh")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorContentPreview() {
    MindtechTheme {
        ErrorContent(
            onRefreshClick = {}
        )
    }
}
