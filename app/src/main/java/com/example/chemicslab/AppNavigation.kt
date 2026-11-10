package com.example.chemicslab.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chemicslab.ui.*

private data class TopLevelTab(val route: String, val label: String, val icon: ImageVector)

private val studentTabs = listOf(
    TopLevelTab(Routes.CATALOG, "Experiments", Icons.Default.Home),
    TopLevelTab(Routes.JOURNAL, "Journal", Icons.Default.List),
    TopLevelTab(Routes.SETTINGS, "Settings", Icons.Default.Settings),
)
private val teacherTab = TopLevelTab(Routes.TEACHER, "Teacher", Icons.Default.Person)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Temporary in-memory flag; becomes a DataStore preference in a later lab.
    var teacherMode by rememberSaveable { mutableStateOf(false) }

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showBottomBar = currentRoute in Routes.topLevel

    // Order from the architecture: Experiments -> Journal -> Teacher -> Settings
    val tabs = if (teacherMode) {
        listOf(studentTabs[0], studentTabs[1], teacherTab, studentTabs[2])
    } else studentTabs

    Scaffold(
        // Inner screens have their own Scaffold/TopAppBar, so avoid double insets here.
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    tabs.forEach { tab ->
                        NavigationBarItem(
                            selected = currentRoute == tab.route,
                            onClick = { navController.navigateToTab(tab.route) },
                            icon = { Icon(tab.icon, contentDescription = tab.label) },
                            label = { Text(tab.label) }
                        )
                    }
                }
            }
        }
    ) { outerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.ONBOARDING,
            modifier = Modifier.padding(bottom = outerPadding.calculateBottomPadding())
        ) {
            composable(Routes.ONBOARDING) {
                OnboardingScreen(
                    onStart = {
                        navController.navigate(Routes.CATALOG) {
                            popUpTo(Routes.ONBOARDING) { inclusive = true } // not reachable via Back
                        }
                    }
                )
            }

            // ----- Experiments tab -----
            composable(Routes.CATALOG) {
                CatalogScreen(onExperimentClick = { id -> navController.navigate(Routes.detail(id)) })
            }
            composable(
                route = Routes.DETAIL,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                ExperimentDetailScreen(
                    experimentId = id,
                    onStartSetup = { navController.navigate(Routes.setup(id)) },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(
                route = Routes.SETUP,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                SetupScreen(
                    experimentId = id,
                    onRun = { navController.navigate(Routes.simulation(id)) },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(
                route = Routes.SIMULATION,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                SimulationScreen(
                    experimentId = id,
                    onFinish = {
                        // Stub: the new attempt id is just id + 100.
                        // Remove detail/setup/simulation from the stack, so Back from the
                        // analysis goes to the Catalog.
                        navController.navigate(Routes.attempt(id + 100)) {
                            popUpTo(Routes.CATALOG)
                        }
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            // ----- Journal tab -----
            composable(Routes.JOURNAL) {
                JournalScreen(onAttemptClick = { id -> navController.navigate(Routes.attempt(id)) })
            }

            // ----- Shared by Experiments and Journal -----
            composable(
                route = Routes.ATTEMPT,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                AttemptDetailScreen(
                    attemptId = id,
                    onRepeat = { navController.navigate(Routes.setup(id % 100)) },
                    onBack = { navController.popBackStack() }
                )
            }

            // ----- Teacher tab -----
            composable(Routes.TEACHER) {
                TeacherHomeScreen(
                    onOpenEditor = { navController.navigate(Routes.EDITOR) },
                    onOpenRules = { navController.navigate(Routes.RULES) },
                    onOpenResults = { navController.navigate(Routes.RESULTS) }
                )
            }
            composable(Routes.EDITOR) { ExperimentEditorScreen(onBack = { navController.popBackStack() }) }
            composable(Routes.RULES) { RulesEditorScreen(onBack = { navController.popBackStack() }) }
            composable(Routes.RESULTS) { ClassResultsScreen(onBack = { navController.popBackStack() }) }

            // ----- Settings tab -----
            composable(Routes.SETTINGS) {
                SettingsScreen(
                    teacherMode = teacherMode,
                    onTeacherModeChange = { teacherMode = it },
                    onOpenAbout = { navController.navigate(Routes.ABOUT) }
                )
            }
            composable(Routes.ABOUT) { AboutScreen(onBack = { navController.popBackStack() }) }
        }
    }
}

/** Standard bottom-bar navigation: one copy of each tab, state of each tab is preserved. */
private fun NavController.navigateToTab(route: String) {
    navigate(route) {
        popUpTo(Routes.CATALOG) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
