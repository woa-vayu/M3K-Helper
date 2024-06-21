@file:Suppress(
    "UnstableApiUsage", "UnstableApiUsage", "UnstableApiUsage",
    "UnstableApiUsage"
)

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "M3K Helper"
include(":app")
