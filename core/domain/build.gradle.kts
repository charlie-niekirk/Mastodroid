plugins {
    id("mastodroid.android.library")
    id("mastodroid.android.hilt")
}

android {
    namespace = "me.cniekirk.mastodroid.core.domain"
}

dependencies {
    implementation(libs.immutable)
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    testImplementation(project(":core:testing"))
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
}