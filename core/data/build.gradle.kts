plugins {
    id("mastodroid.android.library")
    id("mastodroid.android.hilt")
}

android {
    namespace = "me.cniekirk.mastodroid.core.data"
}

dependencies {
    implementation(project(":core:common"))

    testImplementation(project(":core:testing"))
}