import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object Dependencies {
    const val navigation = "androidx.navigation:navigation-compose:${Versions.navigation}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltAgp = "com.google.dagger.hilt.android"
    const val hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hilt_navigation = "androidx.hilt:hilt-navigation-compose:${Versions.hilt_navigation}"
    const val hilt_navigation_compiler = "androidx.hilt:hilt-compiler:${Versions.hilt_navigation}"
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
    const val pager = "com.google.accompanist:accompanist-pager:${Versions.accompanist_version}"
    const val pager_indicators = "com.google.accompanist:accompanist-pager-indicators:${Versions.accompanist_version}"
    const val splash_api = "androidx.core:core-splashscreen:${Versions.splash_api}"
    const val datastore_preferences = "androidx.datastore:datastore-preferences:${Versions.datastore_preferences}"
    const val accompanist_permissions ="com.google.accompanist:accompanist-permissions:${Versions.accompanist_permissions}"
    const val camera_core = "androidx.camera:camera-core:${Versions.camera}"
    const val camera_camera2 = "androidx.camera:camera-core:${Versions.camera}"
    const val camera_lifecycle = "androidx.camera:camera-lifecycle:${Versions.camera}"
    const val camera_view = "androidx.camera:camera-view:${Versions.camera}"
    const val camera_extensions = "androidx.camera:camera-extensions:${Versions.camera}"
    const val okhttpBom = "com.squareup.okhttp3:okhttp-bom:${Versions.logging}"
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.logging}"
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val retrofit2_gson ="com.squareup.retrofit2:converter-gson:${Versions.gson}"
    const val coil  = "io.coil-kt:coil-compose:${Versions.coil}"
}


fun DependencyHandler.retrofit(){
    implementation(Dependencies.okhttpBom)
    implementation(Dependencies.retrofit2)
    implementation(Dependencies.retrofit2_gson)
    implementation(Dependencies.logging_interceptor)
}

fun DependencyHandler.camera(){
    implementation(Dependencies.accompanist_permissions)
    implementation(Dependencies.camera_core)
    implementation(Dependencies.camera_camera2)
    implementation(Dependencies.camera_lifecycle)
    implementation(Dependencies.camera_view)
    implementation(Dependencies.camera_extensions)
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

fun DependencyHandler.pager(){
    implementation(Dependencies.pager)
    implementation(Dependencies.pager_indicators)
}
fun DependencyHandler.splash(){
    implementation(Dependencies.splash_api)
}


fun DependencyHandler.hilt() {
    implementation(Dependencies.hilt)
    kapt(Dependencies.hilt_compiler)
    kapt(Dependencies.hilt_navigation_compiler)
    implementation(Dependencies.hilt_navigation)
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

//FEATURES SCAN
fun DependencyHandler.scanFeatureApi() {
    implementation(project(":features:scan-api"))
}
fun DependencyHandler.scanFeatureImpl() {
    implementation(project(":features:scan-impl"))
}

//FEATURES LIBRARY
fun DependencyHandler.libraryFeatureApi() {
    implementation(project(":features:library-api"))
}
fun DependencyHandler.libraryFeatureImpl() {
    implementation(project(":features:library-impl"))
}

//FEATURES SETTINGS
fun DependencyHandler.settingsFeatureApi() {
    implementation(project(":features:settings-api"))
}
fun DependencyHandler.settingsFeatureImpl() {
    implementation(project(":features:settings-impl"))
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

fun DependencyHandler.data() {
    implementation(project(":data"))
}
