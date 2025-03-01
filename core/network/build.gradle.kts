import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.apollo)
    alias(libs.plugins.secretGradlePlugin)
}

android {
    namespace = "com.github.mikan.ssot.sample.core.network"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // di
    implementation(libs.hiltAndroid)
    ksp(libs.hiltCompiler)
    // network
    api(libs.apolloRuntime)
    implementation(libs.okhttp3LoggingInterceptor)
}

apollo {
    service("github") {
        packageName = "com.github.mikan.ssot.sample.core.network"
        introspection {
            val secretProperties = Properties()
            secretProperties.load(rootProject.file("local.properties").inputStream())
            endpointUrl = "https://api.github.com/graphql"
            headers = mapOf(
                "Authorization" to "Bearer ${secretProperties.getProperty("GITHUB_PAT")}"
            )
            schemaFile = file("src/main/graphql/schema.graphqls")
        }
    }
}