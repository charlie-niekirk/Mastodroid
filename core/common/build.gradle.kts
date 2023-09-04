plugins {
    id("mastodroid.android.library")
    id("mastodroid.android.hilt")
}

android {
    namespace = "me.cniekirk.mastodroid.core.common"
}

dependencies {
    testImplementation(project(":core:testing"))
}