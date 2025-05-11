plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.app.eventmingle"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.app.eventmingle"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation (libs.firebase.auth)
    implementation (libs.google.firebase.auth)
    implementation (libs.firebase.firestore)
    implementation (libs.firebase.storage)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.firebase.database)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.google.firebase.firestore)
    implementation (libs.google.firebase.storage)
    implementation (libs.github.glide)
    annotationProcessor (libs.github.compiler)
    implementation(libs.com.google.firebase.firebase.storage.v2030.x2)

}