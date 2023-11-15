plugins {
    id("mastodroid.android.library")
    id("mastodroid.android.library.compose")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "me.cniekirk.mastodroid.core.ui"
}

dependencies {
    api(libs.core.ktx)
    api(libs.ui)
    api(libs.ui.graphics)
    api(libs.ui.tooling)
    api(libs.ui.tooling.preview)
    api(libs.material3)
    api(libs.coil.compose)

    implementation(project(":core:model"))

    androidTestImplementation(project(":core:testing"))
}