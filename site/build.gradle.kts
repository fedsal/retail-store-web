import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kotlinSerialization)
}

group = "org.fedsal.buenpuerto"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            globals.set(mapOf("title" to "Tienda Buen Puerto"))
            description.set("Powered by Kobweb")
        }
    }
}

kotlin {
    configAsKobwebApplication("buenpuerto")

    sourceSets {
        jsMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.silk.icons.fa)
            implementation(libs.boostrap)
            implementation(project(":shared"))
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.coroutines)
            implementation(libs.kstore)
            implementation(libs.kstore.storage)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.core)
        }
    }
}
