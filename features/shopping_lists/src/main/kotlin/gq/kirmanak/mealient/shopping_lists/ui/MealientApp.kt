package gq.kirmanak.mealient.shopping_lists.ui

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.rememberNavHostEngine

@Composable
fun MealientApp() {
    val engine = rememberNavHostEngine()
    val controller = engine.rememberNavController()

    DestinationsNavHost(
        navGraph = NavGraphs.root,
        engine = engine,
        navController = controller,
        startRoute = NavGraphs.root.startRoute,
    )
}