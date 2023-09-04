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
    }
}

rootProject.name = "Mastodroid"
include(":app")
include(":core:network")
include(":core:ui")
include(":core:data")
include(":core:datastore")
include(":build-logic:convention")
