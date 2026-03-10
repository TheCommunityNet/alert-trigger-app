package wiki.comnet.alerttrigger.presentation.root

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppScreen: NavKey {
    @Serializable data object Login: AppScreen
    @Serializable data object Home: AppScreen
}