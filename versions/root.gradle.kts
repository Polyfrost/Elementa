plugins {
    id("org.polyfrost.multi-version.root")
    id("org.polyfrost.multi-version.api-validation")
}

version = 560

preprocess {
    val forge11802 = createNode("1.18.2-forge", 11802, "srg")
    val fabric11802 = createNode("1.18.2-fabric", 11802, "yarn")
    val forge11701 = createNode("1.17.1-forge", 11701, "srg")
    val fabric11701 = createNode("1.17.1-fabric", 11701, "yarn")
    val fabric11605 = createNode("1.16.5-fabric", 11605, "yarn")
    val forge11605 = createNode("1.16.5-forge", 11605, "srg")
    val forge11202 = createNode("1.12.2-forge", 11202, "srg")
    val fabric11202 = createNode("1.12.2-fabric", 11202, "yarn")
    val fabric10809 = createNode("1.8.9-fabric", 10809, "yarn")
    val forge10809 = createNode("1.8.9-forge", 10809, "srg")

    forge11802.link(fabric11802)
    fabric11802.link(fabric11701)
    forge11701.link(fabric11701)
    fabric11701.link(fabric11605)
    fabric11605.link(forge11605)
    forge11605.link(forge11202, file("1.15.2-1.12.2.txt"))
    fabric11202.link(forge11202)
    forge11202.link(forge10809, file("1.12.2-1.8.9.txt"))
    fabric10809.link(forge10809)
}

apiValidation {
    ignoredProjects.addAll(subprojects.map { it.name })
    ignoredPackages.add("com.example")
    nonPublicMarkers.add("org.jetbrains.annotations.ApiStatus\$Internal")
}
