plugins {
    id("mastodroid.android.library")
    id("mastodroid.android.hilt")
    id("com.google.devtools.ksp")
}

android {
    namespace = "me.cniekirk.mastodroid.core.network"
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.core.ktx)

    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.logging.interceptor)
    implementation(libs.moshi)
    ksp(libs.moshi.codegen)

    testImplementation(project(":core:testing"))
}