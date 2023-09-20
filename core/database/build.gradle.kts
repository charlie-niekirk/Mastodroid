plugins {
    id("mastodroid.android.library")
    id("mastodroid.android.hilt")
    id("mastodroid.android.room")
}

android {
    namespace = "me.cniekirk.mastodroid.core.database"
}

dependencies {
    implementation(project(":core:common"))

    testImplementation(project(":core:testing"))
}