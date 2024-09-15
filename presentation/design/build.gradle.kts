plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

apply(from = "$rootDir/gradle/presentation.kts")

android {
    namespace = "com.vancsop.shelves.presentation.design"
}
