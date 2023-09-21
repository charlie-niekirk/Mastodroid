plugins {
    id("mastodroid.android.feature")
    id("mastodroid.android.library.compose")
}

android {
    namespace = "me.cniekirk.mastodroid.feature.instanceselection"
}

dependencies {
    implementation(libs.immutable)
}