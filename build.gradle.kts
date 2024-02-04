import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

import java.util.*

plugins {
  `java-library`
  `maven-publish`
  idea
  id("io.papermc.paperweight.userdev") version "1.5.11"
  id("xyz.jpenilla.run-paper") version "2.2.3"
  id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

group = "me.arian"
version = "b1"
description = "Allows you to display items"

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
  mavenCentral()
  mavenLocal()

  maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
  paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
  // paperweight.foliaDevBundle("1.20.1-R0.1-SNAPSHOT")
}

tasks {
  compileJava {
    options.encoding = Charsets.UTF_8.name()

    options.release.set(17)
  }
  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }
  assemble {
    dependsOn(reobfJar)
  }
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      groupId = group.toString().lowercase(Locale.ENGLISH)
      artifactId = rootProject.name.lowercase(Locale.ENGLISH)
      version = project.version.toString()

      from(components["java"])
    }
  }
}

bukkit {
  name = rootProject.name
  version = project.version.toString()
  description = project.description.toString()
  author = "Arian01"

  main = "me.arian.itemdisplays.ItemDisplays"
  apiVersion = "1.19"
  foliaSupported = true

  load = BukkitPluginDescription.PluginLoadOrder.POSTWORLD
  prefix = project.name
  defaultPermission = BukkitPluginDescription.Permission.Default.OP

  permissions {
    register("itemdisplays.*") {
      children = listOf("displayitem.command")
      childrenMap = mapOf("displayitem.command" to true)
    }
    register("displayitem.command") {
      description = "Allows you to display an item"
      default = BukkitPluginDescription.Permission.Default.OP
    }
  }
}
