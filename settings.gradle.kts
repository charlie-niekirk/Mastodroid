pluginManagement {
    includeBuild("build-logic")
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

rootProject.name = "mastodroid"
include(":app")
include(":core:common")
include(":core:network")
include(":core:ui")
include(":core:data")
include(":core:datastore")
include(":core:testing")
include(":core:database")
include(":core:model")
include(":feature:instanceselection")
include(":feature:onboarding")
include(":core:designsystem")
include(":feature:feed")
include(":feature:codereceiver")
