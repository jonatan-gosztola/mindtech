package hu.gjonatan.mindtech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fitInside
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.WindowInsetsRulers
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import hu.gjonatan.mindtech.ui.screens.Screen
import hu.gjonatan.mindtech.ui.screens.details.DetailsScreen
import hu.gjonatan.mindtech.ui.screens.details.DetailsScreenViewModel
import hu.gjonatan.mindtech.ui.screens.main.MainScreen
import hu.gjonatan.mindtech.ui.screens.main.MainScreenViewModel
import hu.gjonatan.mindtech.ui.theme.MindtechTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindtechTheme {
                val backStack = rememberNavBackStack(Screen.Main)

                NavDisplay(
                    backStack = backStack,
                    onBack = {
                        backStack.removeLastOrNull()
                    },
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    entryProvider = { screenKey ->
                        when (screenKey) {
                            is Screen.Main -> NavEntry(screenKey) {
                                MainScreen(
                                    viewModel = koinViewModel<MainScreenViewModel>(),
                                    detailsButtonClicked = { backStack.add(Screen.Details) }
                                )
                            }

                            is Screen.Details -> NavEntry(screenKey) {
                                DetailsScreen(
                                    viewModel = koinViewModel<DetailsScreenViewModel>()
                                )
                            }

                            else -> throw NoSuchElementException("Invalid screen key")
                        }
                    },
                    modifier = Modifier
                        .fitInside(WindowInsetsRulers.SafeDrawing.current)
                        .padding(12.dp)
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MindtechTheme {
        Greeting("Android")
    }
}