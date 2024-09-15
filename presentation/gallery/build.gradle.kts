plugins {
    kotlin("kapt")
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
}

apply(from = "$rootDir/gradle/presentation.kts")

android {
    namespace = "com.vancsop.shelves.presentation.gallery"
}

dependencies {
    implementation(libs.coil.compose)
    implementation(projects.presentation.design)
    implementation(projects.domain.catalogue)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    kapt(libs.hilt.compiler)
}
