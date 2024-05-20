package pmf.it.app.budgettracker.ui

sealed class Screen(val route: String, val resource: String) {
    object Profile : Screen("Home", "Home")
    object FriendsList : Screen("Plan", "Plan")
}
