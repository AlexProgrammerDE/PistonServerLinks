plugins {
  id("psl.java-conventions")
}

dependencies {
  implementation(project(":pistonserverlinks-paper", "shadow"))
  implementation(project(":pistonserverlinks-spigot", "shadow"))
  implementation(project(":pistonserverlinks-velocity", "shadow"))
}

tasks {
  jar {
    archiveClassifier.set("")
    archiveFileName.set("PistonServerLinks-${rootProject.version}.jar")
    destinationDirectory.set(rootProject.projectDir.resolve("build/libs"))

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(configurations.runtimeClasspath)
    from({ configurations.runtimeClasspath.get().map { zipTree(it) } })
  }
}
