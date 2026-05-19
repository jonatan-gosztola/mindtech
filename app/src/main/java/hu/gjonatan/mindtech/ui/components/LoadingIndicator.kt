package hu.gjonatan.mindtech.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.gjonatan.mindtech.ui.theme.MindtechTheme

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(
        modifier = modifier.size(150.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun LoadingIndicatorPreview() {
    MindtechTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            LoadingIndicator()
        }
    }
}
