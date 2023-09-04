import me.cniekirk.mastodroid.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("mastodroid.android.library")
                apply("mastodroid.android.hilt")
            }
//            extensions.configure<LibraryExtension> {
//                defaultConfig {
//                    testInstrumentationRunner =
//                        "com.google.samples.apps.nowinandroid.core.testing.NiaTestRunner"
//                }
//                configureGradleManagedDevices(this)
//            }

            dependencies {
//                add("implementation", project(":core:model"))
                add("implementation", project(":core:ui"))
//                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:data"))
//                add("implementation", project(":core:common"))
//                add("implementation", project(":core:domain"))
//                add("implementation", project(":core:analytics"))

                add("testImplementation", kotlin("test"))
                add("testImplementation", project(":core:testing"))
                add("androidTestImplementation", kotlin("test"))
                add("androidTestImplementation", project(":core:testing"))

                add("implementation", libs.findLibrary("hilt.navigation").get())
//                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
//                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())

//                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
            }
        }
    }
}