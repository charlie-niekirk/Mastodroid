plugins {
    id("mastodroid.android.library")
    id("mastodroid.android.hilt")
}

android {
    namespace = "me.cniekirk.mastodroid.core.testing"
}

dependencies {

    testImplementation(libs.junit.junit)
    testImplementation(libs.mockk)
}