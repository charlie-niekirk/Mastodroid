plugins {
    id("mastodroid.android.application")
    id("mastodroid.android.application.compose")
    id("mastodroid.android.hilt")
}

android {
    namespace = "me.cniekirk.mastodroid"

    defaultConfig {
        applicationId = "me.cniekirk.mastodroid"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    implementation(project(":feature:onboarding"))
    implementation(project(":feature:instanceselection"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

    implementation(libs.navigation.compose)

    implementation(libs.moshi)
    implementation(libs.adaptive)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    kapt(libs.hilt.compiler)

    implementation(platform(libs.firebase.bom))
    implementation(libs.analytics)
    implementation(libs.crashlytics)

    implementation(libs.androidx.material3.window.size)

    implementation(libs.immutable)

    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.logging.interceptor)

    implementation(libs.bundles.orbit)
    testImplementation(libs.orbit.test)

    implementation(libs.timber)

    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}