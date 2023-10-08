plugins {
    id("mastodroid.android.feature")
    id("mastodroid.android.library.compose")
}

android {
    namespace = "me.cniekirk.mastodroid.feature.post"
}

dependencies {
    implementation(libs.immutable)
    implementation(libs.coil.compose)
    implementation(libs.material.icons)
}