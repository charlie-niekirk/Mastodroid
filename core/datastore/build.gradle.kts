plugins {
    id("mastodroid.android.library")
    id("mastodroid.android.hilt")
    alias(libs.plugins.com.google.protobuf)
}

android {
    namespace = "me.cniekirk.mastodroid.core.datastore"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.datastore)
    implementation(libs.protobuf)

    testImplementation(project(":core:testing"))
}