plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // 1. APLICA EL PLUGIN DE GOOGLE SERVICES
    id("com.google.gms.google-services") version "4.4.2" apply false
}

android {
    namespace = "com.example.educalink_ev2"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.educalink_ev2"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // ... (Tu bloque 'buildTypes', 'compileOptions', 'kotlinOptions', 'buildFeatures'
    //      queda exactamente igual) ...
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation("com.google.accompanist:accompanist-navigation-animation:0.32.0")
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation("androidx.compose.material:material-icons-extended:1.6.7")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    implementation("androidx.compose.material3:material3-window-size-class:1.2.1")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Kotest
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    // MockK
    testImplementation("io.mockk:mockk:1.13.10")
    // Coroutines Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    // Compose UI Test
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.2")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.2")

    // Obligatorio para usar JUnit 5
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}