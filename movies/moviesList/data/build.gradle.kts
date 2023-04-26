apply {
    from("$rootDir/library-build.gradle")
}


plugins {
    kotlin(KotlinPlugins.serialization) version Kotlin.version
 }

dependencies {
    "implementation"(project(Modules.moviesListDomain))
    "implementation"(project(Modules.constants))
    "implementation"(project(Modules.core))

    "implementation"(Ktor.core)
    "implementation"(Ktor.clientSerialization)
    "implementation"(Ktor.android)
}