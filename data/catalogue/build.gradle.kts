plugins {
    kotlin("kapt")
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
}

apply(from = "$rootDir/gradle/data.kts")

android {
    namespace = "com.vancsop.shelves.data.catalogue"
}

dependencies {
    implementation(projects.data.core)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    kapt(libs.hilt.compiler)
}
