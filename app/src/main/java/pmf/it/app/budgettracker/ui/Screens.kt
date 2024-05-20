package pmf.it.app.budgettracker.ui

sealed class Screen(val route: String, val resource: String) {
    object Home : Screen("Home", "Home")
    object Plan : Screen("Plan", "Plan")
}
