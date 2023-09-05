plugins {
    id("mastodroid.android.library")
    id("mastodroid.android.hilt")
}

android {
    namespace = "me.cniekirk.network"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.core.ktx)

    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.logging.interceptor)

    testImplementation(project(":core:testing"))
}