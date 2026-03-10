package wiki.comnet.alerttrigger.presentation.root

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.navigation3.runtime.*
import androidx.navigation3.ui.NavDisplay
import org.koin.androidx.compose.koinViewModel
import wiki.comnet.alerttrigger.presentation.home.HomeScreen
import wiki.comnet.alerttrigger.presentation.login.LoginScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootNavigationGraph(
    viewModel: RootViewModel = koinViewModel(),
) {
    val backStack = rememberNavBackStack(AppScreen.Home)
    val isSetupFinished by viewModel.isSetupFinished.collectAsState()
    val isAuthenticated by viewModel.isLogin.collectAsState()

    if (!isSetupFinished) {
        return
    }

    val activeBackStack = remember(backStack.toList(), isAuthenticated) {
        if (!isAuthenticated) {
            listOf(AppScreen.Login) // Override everything with Login
        } else {
            backStack.toList()
        }
    }

    NavDisplay(
        backStack = activeBackStack,
        onBack = {
            if (isAuthenticated) backStack.removeLastOrNull()
         },
        entryProvider = entryProvider {
            entry<AppScreen.Login> {
                LoginScreen(
                    onLoginSuccess = {
                        backStack.clear()
                        backStack.add(AppScreen.Home)
                    }
                )
            }
            entry<AppScreen.Home> {
                HomeScreen()
            }
        }
    )
}