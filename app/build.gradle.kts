import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "ca.ulaval.ima.mp"
    compileSdk = 35

    defaultConfig {
        applicationId = "ca.ulaval.ima.mp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField(
                "String",
                "SUPABASE_ANON_KEY",
                "\"${
                    gradleLocalProperties(
                        rootDir,
                        rootProject.providers
                    ).getProperty("SUPABASE_ANON_KEY")
                }\""
            )
            buildConfigField(
                "String",
                "SUPABASE_URL",
                "\"${
                    gradleLocalProperties(
                        rootDir,
                        rootProject.providers
                    ).getProperty("SUPABASE_URL")
                }\""
            )
            buildConfigField(
                "String",
                "SECRET_KEY_ALIAS",
                "\"${
                    gradleLocalProperties(
                        rootDir,
                        rootProject.providers
                    ).getProperty("SECRET_KEY_ALIAS")
                }\""
            )
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "SUPABASE_ANON_KEY",
                "\"${
                    gradleLocalProperties(
                        rootDir,
                        rootProject.providers
                    ).getProperty("SUPABASE_ANON_KEY")
                }\""
            )
            buildConfigField(
                "String",
                "SUPABASE_URL",
                "\"${
                    gradleLocalProperties(
                        rootDir,
                        rootProject.providers
                    ).getProperty("SUPABASE_URL")
                }\""
            )
            buildConfigField(
                "String",
                "SECRET_KEY_ALIAS",
                "\"${
                    gradleLocalProperties(
                        rootDir,
                        rootProject.providers
                    ).getProperty("SECRET_KEY_ALIAS")
                }\""
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.media)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.bundles.koin.compose)

    implementation(libs.kotlinx.serialization.json)

    implementation(platform(libs.supabase.bom))
    implementation(libs.supabase.auth)
    implementation(libs.supabase.postgrest)

    implementation(libs.ktor.client.cio)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
