package pmf.it.app.budgettracker.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val resource: String, val icon: ImageVector? = null) {
    data object Home : Screen("Home", "Home", Icons.Default.Home)
    data object Plan : Screen("Plan", "Plan", Icons.Default.Menu)
    data object Profile : Screen("Profile", "Profile", Icons.Default.Person)
}
