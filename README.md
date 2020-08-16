# Packetty [![License](https://img.shields.io/github/license/Krymonota/packetty.svg?style=flat-square)](https://github.com/Krymonota/packetty/LICENSE.txt) 
Packetty is a simple, packet-based and event-driven [Netty](https://github.com/netty/netty) wrapper that saves you a lot of work.

After implementing several projects with Netty, I noticed that I had to write the same boilerplate code over and over again. Since I didn't feel like doing this anymore and wanted to reduce maintenance effort as well as use my time more efficiently, I developed Packetty.

## Table of Contents
* [Including the wrapper in your project](#including-the-wrapper-in-your-project)
   * [No build or dependency management tool](#no-build-or-dependency-management-tool)
   * [Using build or dependency management tool](#using-build-or-dependency-management-tool)
* [Simple example project](#simple-example-project)
* [Used dependencies and tools](#used-dependencies-and-tools)
* [License](#license)

## Including the wrapper in your project
#### No build or dependency management tool
You can [download the latest version from the releases page](https://github.com/Krymonota/packetty/releases/latest) and add the `.jar` files to your project libraries. We recommend using a build and dependency management tool though.

#### Using build or dependency management tool
<details>
  <summary>As Maven dependency</summary>

```xml
<!-- Add required repository -->
<repositories>
    <repository>
        <id>packetty</id>
        <url>https://mymavenrepo.com/repo/v3i97KuHAZF1V0yF9mn0/</url>
    </repository>
</repositories>

<!-- Add common as dependency -->
<dependencies>
    <dependency>
        <groupId>id.niklas</groupId>
        <artifactId>packetty-common</artifactId>
        <version>1.1.3</version>
    </dependency>
</dependencies>

<!-- Add client as dependency -->
<dependencies>
    <dependency>
        <groupId>id.niklas</groupId>
        <artifactId>packetty-client</artifactId>
        <version>1.1.3</version>
    </dependency>
</dependencies>

<!-- Add server as dependency -->
<dependencies>
    <dependency>
        <groupId>id.niklas</groupId>
        <artifactId>packetty-server</artifactId>
        <version>1.1.3</version>
    </dependency>
</dependencies>
```
</details>
<details>
  <summary>As Gradle dependency</summary>

```gradle
// Add required repository
allprojects {
    repositories {
        maven { url 'https://mymavenrepo.com/repo/v3i97KuHAZF1V0yF9mn0/' }
    }
}

// Add common as dependency
dependencies {
    implementation 'id.niklas:packetty-common:1.1.3'
}

// Add client as dependency
dependencies {
    implementation 'id.niklas:packetty-client:1.1.3'
}

// Add server as dependency
dependencies {
    implementation 'id.niklas:packetty-server:1.1.3'
}
```
</details>
<details>
  <summary>As SBT dependency</summary>

```scala
// Add required repository
resolvers += "packetty" at "https://mymavenrepo.com/repo/v3i97KuHAZF1V0yF9mn0"

// Add common as dependency
libraryDependencies += "id.niklas" % "packetty-common" % "1.1.3"

// Add client as dependency
libraryDependencies += "id.niklas" % "packetty-client" % "1.1.3"

// Add server as dependency
libraryDependencies += "id.niklas" % "packetty-server" % "1.1.3"
```
</details>

## Simple example project
Check out [Packetty Ping Pong](https://github.com/Krymonota/packetty-pingpong) for a simple example project that demonstrates the use of Packetty.

## Used dependencies and tools
Don't worry, we already take care of our dependencies so that you don't have to deal with them! However, we wanted to draw some attention to the libraries and tools we use for this project.
- [Netty](https://netty.io/)
- [Jackson](https://github.com/FasterXML/jackson)
- [Moonwlker](https://github.com/bertilmuth/moonwlker)
- [Guava](https://github.com/google/guava)
- [Log4j](https://logging.apache.org/log4j/)
- [Project Lombok](https://projectlombok.org/)
- [JUnit](https://junit.org/junit5/)

## License
This project is licensed under the MIT License. See the [LICENSE.txt](https://github.com/Krymonota/packetty/blob/master/LICENSE.txt) file for details.