package pmf.it.app.budgettracker.ui.screens

import androidx.annotation.StringRes

sealed class Screen(val route: String, val resource: String) {
    object Profile : Screen("profile", "Profile")
    object FriendsList : Screen("friendslist", "Friends List")
}
