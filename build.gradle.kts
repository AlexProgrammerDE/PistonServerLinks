plugins {
    base
}

tasks.named<UpdateDaemonJvm>("updateDaemonJvm") {
    languageVersion = JavaLanguageVersion.of(25)
}

allprojects {
    group = "net.pistonmaster"
    version = property("maven_version")!!
    description = "Custom server links plugin for Minecraft 1.21+"
}
