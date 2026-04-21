plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.apollo)
    alias(libs.plugins.kotlinx.serialization)
}

val dataPackageName = "com.kurayami.data"

android {
    namespace = dataPackageName
    compileSdk = 37

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
        }
    }
}

apollo {
    service("service") {
        packageName.set(dataPackageName)
        introspection {
            endpointUrl.set("https://graphql.anilist.co")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Okhttp
    implementation(libs.okhttp.logging.interceptor)

    // Paging
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    // Hilt
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)

    // Datastore
    implementation(libs.androidx.datastore.preferences)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Apollo
    implementation(libs.apollo.runtime)
    implementation(libs.apollo.normalized.cache)
}
