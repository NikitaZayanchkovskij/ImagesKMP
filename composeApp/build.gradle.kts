plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidKmpLibrary)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android {
        namespace = "com.mikitazayanchkouski.imageskmp.composeapp"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        /* Explicitly enabling Android resources, that are been generated,
         * to be able to make our debug build run, on an Ios simulator.
         */
        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            freeCompilerArgs += listOf("-Xbinary=bundleId=com.mikitazayanchkouski.imageskmp")
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.bundles.koin.common)
            implementation(libs.bundles.ktor.common)

            implementation(libs.material3.adaptive)
            implementation(libs.touchlab.kermit)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.jetbrains.compose.navigation)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.ui.tooling)
}