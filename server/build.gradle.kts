plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
    alias(libs.plugins.serialization)
}

group = "org.flynndevs.com"
version = "1.0.0"
application {
    mainClass.set("org.flynndevs.com.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.content.negotiation)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
}