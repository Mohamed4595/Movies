apply {
    from("$rootDir/library-build.gradle")
}

plugins {
    kotlin(KotlinPlugins.serialization) version Kotlin.version
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.moviesListData))
    "implementation"(project(Modules.moviesListDomain))

    "implementation"(Kotlinx.coroutinesCore) // need for flows

    "testImplementation"(Junit.junit4)
    "testImplementation"(Ktor.ktorClientMock)
    "testImplementation"(Ktor.clientSerialization)
}