package com.example.chemicslab.navigation

/** All route strings in one place, so there are no typos in navigate(...) calls. */
object Routes {
    // Outside the main graph
    const val ONBOARDING = "onboarding"

    // Top-level destinations (bottom bar)
    const val CATALOG = "catalog"
    const val JOURNAL = "journal"
    const val TEACHER = "teacher"
    const val SETTINGS = "settings"

    // Flow destinations with an argument
    const val DETAIL = "experiment/{id}"
    const val SETUP = "setup/{id}"
    const val SIMULATION = "simulation/{id}"
    const val ATTEMPT = "attempt/{id}"

    // Flow destinations without arguments
    const val EDITOR = "teacher/editor"
    const val RULES = "teacher/rules"
    const val RESULTS = "teacher/results"
    const val ABOUT = "settings/about"

    // Helpers that build a concrete route from an id
    fun detail(id: Int) = "experiment/$id"
    fun setup(id: Int) = "setup/$id"
    fun simulation(id: Int) = "simulation/$id"
    fun attempt(id: Int) = "attempt/$id"

    val topLevel = setOf(CATALOG, JOURNAL, TEACHER, SETTINGS)
}
