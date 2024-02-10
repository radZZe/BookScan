import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object Dependencies {
    const val navigation = "androidx.navigation:navigation-compose:${Versions.navigation}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltAgp = "com.google.dagger.hilt.android"
    const val hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val lifecycle_runtime_ktx =  "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_runtime_ktx}"
    const val activity_compose = "androidx.activity:activity-compose:${Versions.activity_compose}"
    const val compose_bom = "androidx.compose:compose-bom:${Versions.compose_bom}"
    const val compose_ui = "androidx.compose.ui:ui"
    const val compose_ui_graphics = "androidx.compose.ui:ui-graphics"
    const val compose_ui_tooling_preview = "androidx.compose.ui:ui-tooling-preview"
    const val compose_material3 = "androidx.compose.material3:material3"
    const val junit = "junit:junit:${Versions.junit}"
    const val ext_junit = "androidx.test.ext:junit:${Versions.ext_junit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val compose_junit  = "androidx.compose.ui:ui-test-junit4"
    const val compose_ui_tooling = "androidx.compose.ui:ui-tooling"
    const val compose_ui_test = "androidx.compose.ui:ui-test-manifest"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val android_material = "com.google.android.material:material:${Versions.material}"
}

fun DependencyHandler.compose() {
    implementation(Dependencies.activity_compose)
    implementation(platform(Dependencies.compose_bom))
    implementation(Dependencies.compose_ui)
    implementation(Dependencies.compose_ui_graphics)
    implementation(Dependencies.compose_material3)
    debugImplementation(Dependencies.compose_ui_tooling)
    debugImplementation(Dependencies.compose_ui_test)
    implementation(Dependencies.compose_ui_tooling_preview)
}




fun DependencyHandler.hilt() {
    implementation(Dependencies.hilt)
    kapt(Dependencies.hilt_compiler)
}
//FEATURES ONBOARDING
fun DependencyHandler.onboardingFeatureApi() {
    implementation(project(":features:onboarding-api"))
}
fun DependencyHandler.onboardingFeatureImpl() {
    implementation(project(":features:onboarding-impl"))
}
// FEATURES AUTH
fun DependencyHandler.authFeatureApi() {
    implementation(project(":features:auth-api"))
}
fun DependencyHandler.authFeatureImpl() {
    implementation(project(":features:auth-impl"))
}
fun DependencyHandler.core() {
    implementation(project(":core"))
}
fun DependencyHandler.navigation() {
    implementation(Dependencies.navigation)
}