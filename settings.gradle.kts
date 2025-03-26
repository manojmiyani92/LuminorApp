pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven ( "https://plugins.gradle.org/m2/")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven ( "https://plugins.gradle.org/m2/")
    }
}
rootProject.name = "LuminorApp"
include(":app")
include(":infrastructure")
include(":repository")
include(":luminorcore")
