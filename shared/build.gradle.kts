import com.varabyte.kobweb.gradle.library.util.configAsKobwebLibrary

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.library)
    alias(libs.plugins.kotlinSerialization)
}

group = "org.fedsal.buenpuerto"
version = "1.0-SNAPSHOT"

kotlin {
    configAsKobwebLibrary(includeServer = true)

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.coroutines)
            implementation(libs.ktor.client.core)
            implementation(project.dependencies.platform(libs.supabase.bom))
            implementation(libs.supabase.realtime)
            implementation(libs.supabase.postgrest)
        }
        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}
