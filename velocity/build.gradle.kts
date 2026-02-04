plugins {
    id("psl.platform-conventions")
    id("xyz.jpenilla.run-velocity") version "3.0.2"
}

dependencies {
    implementation(projects.pistonserverlinksShared)
    implementation("org.bstats:bstats-velocity:3.1.0")

    compileOnly("com.velocitypowered:velocity-api:3.4.0")
    compileOnly("io.github.miniplaceholders:miniplaceholders-api:3.1.0")
}

tasks {
    runVelocity {
        velocityVersion("3.4.0-SNAPSHOT")
    }
}
