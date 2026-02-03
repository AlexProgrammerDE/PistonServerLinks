plugins {
    id("psl.shadow-conventions")
}

tasks.shadowJar {
    archiveFileName.set(
        "PistonServerLinks-${
            project.name.substringAfter("pistonserverlinks-").replaceFirstChar { it.uppercase() }
        }-${project.version}.jar"
    )
    destinationDirectory.set(rootProject.layout.buildDirectory.dir("libs"))
}
