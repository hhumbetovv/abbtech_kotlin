plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "AbbTechKotlin"
include("src:main:module_example")
findProject(":src:main:module_example")?.name = "module_example"
