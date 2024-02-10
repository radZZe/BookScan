pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BookScan"
include(":app")
include(":features:onboarding-api")
include(":features:onboarding-impl")
include(":core")
include(":features:auth-impl")
include(":features:auth-api")
