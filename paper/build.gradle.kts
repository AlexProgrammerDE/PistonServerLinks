plugins {
    id("psl.platform-conventions")
    id("xyz.jpenilla.run-paper") version "3.0.2"
}

dependencies {
    implementation(projects.pistonserverlinksShared)
    implementation("org.bstats:bstats-bukkit:3.1.0")

    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("io.github.miniplaceholders:miniplaceholders-api:2.2.3")
}

tasks {
    runServer {
        minecraftVersion("1.21.4")
    }
}
