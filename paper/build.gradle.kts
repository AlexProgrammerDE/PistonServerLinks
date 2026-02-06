plugins {
    id("psl.platform-conventions")
    id("xyz.jpenilla.run-paper") version "3.0.2"
}

dependencies {
    implementation(projects.pistonserverlinksShared)
    implementation("org.bstats:bstats-bukkit:3.1.0")

    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.12.1")
    compileOnly("io.github.miniplaceholders:miniplaceholders-api:3.1.0")
}

tasks {
    runServer {
        minecraftVersion("1.21.4")
    }
}
