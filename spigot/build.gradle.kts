plugins {
    id("psl.platform-conventions")
}

dependencies {
    implementation(projects.pistonserverlinksShared)
    implementation("org.bstats:bstats-bukkit:3.1.0")
    implementation("net.kyori:adventure-text-minimessage:4.26.1")
    implementation("net.kyori:adventure-text-serializer-legacy:4.26.1")

    compileOnly("org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.12.2")
    compileOnly("io.github.miniplaceholders:miniplaceholders-api:3.1.0")
}

tasks.shadowJar {
    relocate("net.kyori", "net.pistonmaster.pistonserverlinks.shadow.kyori")
}
