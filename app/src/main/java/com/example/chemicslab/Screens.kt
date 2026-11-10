package com.example.chemicslab.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Shared stub layout: Scaffold + TopAppBar with the screen title,
 * content placed inside innerPadding. Real UI comes in later labs.
 * Screens never see NavController - only plain lambdas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StubScaffold(
    title: String,
    onBack: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    if (onBack != null) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            content()
        }
    }
}

// ---------- Outside the main graph ----------

@Composable
fun OnboardingScreen(onStart: () -> Unit) {
    StubScaffold("Welcome") {
        Text("Short tutorial about the virtual lab (stub)")
        Spacer(Modifier.height(16.dp))
        Button(onClick = onStart) { Text("Start") }
    }
}

// ---------- Top-level: Experiments ----------

@Composable
fun CatalogScreen(onExperimentClick: (Int) -> Unit) {
    StubScaffold("Experiment Catalog") {
        Text("A real list with filters will appear in lab No. 3")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { onExperimentClick(1) }) { Text("Titration (id = 1)") }
        Button(onClick = { onExperimentClick(2) }) { Text("Pendulum (id = 2)") }
        Button(onClick = { onExperimentClick(3) }) { Text("Ohm's law (id = 3)") }
    }
}

@Composable
fun ExperimentDetailScreen(experimentId: Int, onStartSetup: () -> Unit, onBack: () -> Unit) {
    StubScaffold("Experiment Detail", onBack) {
        Text("Experiment details with id = $experimentId")
        Spacer(Modifier.height(16.dp))
        Button(onClick = onStartSetup) { Text("Set up experiment") }
        Button(onClick = onBack) { Text("Back") }
    }
}

@Composable
fun SetupScreen(experimentId: Int, onRun: () -> Unit, onBack: () -> Unit) {
    StubScaffold("Setup", onBack) {
        Text("Parameters and reagents for experiment id = $experimentId")
        Spacer(Modifier.height(16.dp))
        Button(onClick = onRun) { Text("Run simulation") }
    }
}

@Composable
fun SimulationScreen(experimentId: Int, onFinish: () -> Unit, onBack: () -> Unit) {
    StubScaffold("Simulation", onBack) {
        Text("Lab bench for experiment id = $experimentId")
        Spacer(Modifier.height(16.dp))
        Button(onClick = onFinish) { Text("Finish and see analysis") }
    }
}

// ---------- Shared: Journal / Experiments ----------

@Composable
fun AttemptDetailScreen(attemptId: Int, onRepeat: () -> Unit, onBack: () -> Unit) {
    StubScaffold("Attempt Detail", onBack) {
        Text("Attempt with id = $attemptId")
        Text("Tabs: Data / Graph / Mistake Analysis (later)")
        Spacer(Modifier.height(16.dp))
        Button(onClick = onRepeat) { Text("Repeat experiment") }
        Button(onClick = onBack) { Text("Back") }
    }
}

// ---------- Top-level: Journal ----------

@Composable
fun JournalScreen(onAttemptClick: (Int) -> Unit) {
    StubScaffold("Journal") {
        Text("History of attempts (stub)")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { onAttemptClick(101) }) { Text("Attempt 101") }
        Button(onClick = { onAttemptClick(102) }) { Text("Attempt 102") }
    }
}

// ---------- Top-level: Teacher ----------

@Composable
fun TeacherHomeScreen(onOpenEditor: () -> Unit, onOpenRules: () -> Unit, onOpenResults: () -> Unit) {
    StubScaffold("Teacher Home") {
        Button(onClick = onOpenEditor) { Text("Experiment Editor") }
        Button(onClick = onOpenRules) { Text("Mistake Rules Editor") }
        Button(onClick = onOpenResults) { Text("Class Results") }
    }
}

@Composable
fun ExperimentEditorScreen(onBack: () -> Unit) {
    StubScaffold("Experiment Editor", onBack) {
        Text("Step-by-step wizard (stub)")
        Button(onClick = onBack) { Text("Back") }
    }
}

@Composable
fun RulesEditorScreen(onBack: () -> Unit) {
    StubScaffold("Mistake Rules Editor", onBack) {
        Text("Rules and hint texts (stub)")
        Button(onClick = onBack) { Text("Back") }
    }
}

@Composable
fun ClassResultsScreen(onBack: () -> Unit) {
    StubScaffold("Class Results", onBack) {
        Text("Imported results and analytics (stub)")
        Button(onClick = onBack) { Text("Back") }
    }
}

// ---------- Top-level: Settings ----------

@Composable
fun SettingsScreen(
    teacherMode: Boolean,
    onTeacherModeChange: (Boolean) -> Unit,
    onOpenAbout: () -> Unit
) {
    StubScaffold("Settings") {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Teacher mode", modifier = Modifier.weight(1f))
            Switch(checked = teacherMode, onCheckedChange = onTeacherModeChange)
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = onOpenAbout) { Text("About / Help") }
    }
}

@Composable
fun AboutScreen(onBack: () -> Unit) {
    StubScaffold("About / Help", onBack) {
        Text("Virtual Lab v0.1")
        Button(onClick = onBack) { Text("Back") }
    }
}
