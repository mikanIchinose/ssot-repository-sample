plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.apollo)
}

android {
    namespace = "com.github.mikan.ssot.sample.myaccount"
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
}

dependencies {
    // di
    implementation(libs.hiltAndroid)
    ksp(libs.hiltCompiler)
    // network
    implementation(projects.core.network)
}

apollo {
    service("github") {
        packageName = "com.github.mikan.ssot.sample.myaccount"
        dependsOn(projects.core.network)
    }
}
