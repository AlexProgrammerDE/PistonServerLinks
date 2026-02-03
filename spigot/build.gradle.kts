plugins {
    id("psl.platform-conventions")
}

dependencies {
    implementation(projects.pistonserverlinksShared)
    implementation("org.bstats:bstats-bukkit:3.1.0")
    implementation("net.kyori:adventure-text-minimessage:4.18.0")
    implementation("net.kyori:adventure-text-serializer-legacy:4.18.0")

    compileOnly("org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("io.github.miniplaceholders:miniplaceholders-api:2.2.3")
}

tasks.shadowJar {
    relocate("net.kyori", "net.pistonmaster.pistonserverlinks.shadow.kyori")
}
