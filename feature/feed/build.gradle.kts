plugins {
    id("mastodroid.android.feature")
    id("mastodroid.android.library.compose")
}

android {
    namespace = "me.cniekirk.mastodroid.feature.feed"
}

dependencies {
    implementation(project(":core:network"))
    implementation(libs.immutable)
    implementation(libs.paging)
    implementation(libs.paging.compose)
    implementation(libs.coil.compose)
    implementation(libs.material.icons)
}