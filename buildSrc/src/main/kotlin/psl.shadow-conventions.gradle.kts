import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("psl.java-conventions")
    id("com.gradleup.shadow")
}

tasks {
    jar {
        archiveClassifier.set("unshaded")
        from(project.rootProject.file("LICENSE"))
    }

    shadowJar {
        exclude("META-INF/SPONGEPO.SF", "META-INF/SPONGEPO.DSA", "META-INF/SPONGEPO.RSA")
        minimize()
        configureRelocations()
    }

    build {
        dependsOn(shadowJar)
    }
}

fun ShadowJar.configureRelocations() {
    relocate("org.bstats", "net.pistonmaster.pistonserverlinks.shadow.bstats")
    relocate("de.exlll.configlib", "net.pistonmaster.pistonserverlinks.shadow.configlib")
    relocate("org.spongepowered.configurate", "net.pistonmaster.pistonserverlinks.shadow.configurate")
    relocate("io.leangen.geantyref", "net.pistonmaster.pistonserverlinks.shadow.geantyref")
    relocate("org.checkerframework", "net.pistonmaster.pistonserverlinks.shadow.checkerframework")
    relocate("org.yaml.snakeyaml", "net.pistonmaster.pistonserverlinks.shadow.snakeyaml")
    relocate("com.google.errorprone", "net.pistonmaster.pistonserverlinks.shadow.google.errorprone")
    relocate("com.google.gson", "net.pistonmaster.pistonserverlinks.shadow.gson")
    relocate("org.jetbrains.annotations", "net.pistonmaster.pistonserverlinks.shadow.annotations.jetbrains")
    relocate("net.kyori.option", "net.pistonmaster.pistonserverlinks.shadow.kyori.option")
}
