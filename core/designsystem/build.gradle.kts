plugins {
    id("mastodroid.android.library")
    id("mastodroid.android.library.compose")
}

android {
    namespace = "me.cniekirk.mastodroid.core.designsystem"
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.core.ktx)

    api(libs.immutable)
    api(libs.material.icons)
    api(libs.coil.compose)
    api(libs.ui)
    api(libs.ui.graphics)
    api(libs.ui.tooling)
    api(libs.ui.tooling.preview)
    api(libs.material3)

    testImplementation(project(":core:testing"))
}