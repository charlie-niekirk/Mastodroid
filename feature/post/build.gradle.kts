plugins {
    id("mastodroid.android.feature")
    id("mastodroid.android.library.compose")
}

android {
    namespace = "me.cniekirk.mastodroid.feature.post"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(libs.immutable)
    implementation(libs.coil.compose)
    implementation(libs.material.icons)

    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk)

    androidTestImplementation(libs.ui.test.junit4)
}