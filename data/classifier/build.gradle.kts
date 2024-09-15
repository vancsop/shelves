plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

apply(from = "$rootDir/gradle/data.kts")

android {
    namespace = "com.vancsop.shelves.data.classifier"
}

dependencies {
    implementation(projects.data.core)
    implementation(libs.androidx.camera.core)
    implementation(libs.tensorflow.lite.task.vision)
    implementation(libs.tensorflow.lite.gpu.delegate.plugin)
    implementation(libs.tensorflow.lite.gpu)
}
