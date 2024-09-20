plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

apply(from = "$rootDir/gradle/data.kts")

android {
    namespace = "com.vancsop.shelves.domain.catalogue"
}

dependencies {
    implementation(projects.data.core)
}
