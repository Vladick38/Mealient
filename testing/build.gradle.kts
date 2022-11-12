plugins {
    id("gq.kirmanak.mealient.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "gq.kirmanak.mealient.test"
    lint {
        abortOnError = false
        quiet = true
        checkReleaseBuilds = false
    }
}

dependencies {
    implementation(project(":logging"))

    implementation(libs.google.dagger.hiltAndroid)
    kapt(libs.google.dagger.hiltCompiler)
    kapt(libs.google.dagger.hiltAndroidCompiler)
    implementation(libs.google.dagger.hiltAndroidTesting)

    implementation(libs.jetbrains.kotlinx.coroutinesAndroid)
    implementation(libs.jetbrains.kotlinx.coroutinesTest)

    implementation(libs.androidx.test.junit)
    implementation(libs.androidx.coreTesting)

    implementation(libs.google.truth)

    implementation(libs.io.mockk)

    implementation(libs.robolectric)
}

kapt {
    correctErrorTypes = true
}
