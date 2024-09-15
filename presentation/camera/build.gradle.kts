plugins {
    kotlin("kapt")
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
}

apply(from = "$rootDir/gradle/presentation.kts")

android {
    namespace = "com.vancsop.shelves.presentation.camera"
}

dependencies {
    implementation(projects.presentation.design)
    implementation(projects.domain.catalogue)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.video)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.extensions)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    kapt(libs.hilt.compiler)
}
