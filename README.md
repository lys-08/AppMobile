# ClickMe (if you can)

![ClickMe logo](./res/ClickMe.png)

## Table of contents

- [ClickMe (if you can)](#clickme-if-you-can)
  - [Table of contents](#table-of-contents)
  - [Project presentation](#project-presentation)
    - [What is it ?](#what-is-it-)
    - [Future improvements and contributions](#future-improvements-and-contributions)
    - [Licence](#licence)
  - [How to play ?](#how-to-play-)
  - [Project structure](#project-structure)
  - [Credits](#credits)


## Project presentation

### What is it ?

ClickMe is a simple game, designed for Android platforms, composed of successive levels, where the player must on a button, that becomes progressively hidden and harder to find, in really different ways.

ClickMe is entirely developed in Kotlin, following the Jetpack Compose framework, on Android Studio.

This was developed as part of a project for the Android Studio course at the University of Québec at Chicoutimi.

### Future improvements and contributions

This project will probably not undergo any further development, except if we magically find some interest in it or find some particularly interesting feature to add. 

But feel free to contribute to it, if you find it interesting. No particular contribution guidelines are set, but please respect the Kotlin code style and the Jetpack Compose framework.

### Licence

This project is under the MIT licence. You can find the full licence in the `LICENCE` file.

## How to play ?

// TODO

You can download it on the Android Play store [here](https://play.google.com/store/apps/details?id=com.example.clickme).

Or you can build it yourself by cloning this repository and opening it in Android Studio. 

## Project structure

Here is the important project structure, stripped from the test and build files :

```
.
└── ClickMe
    ├── app
    │   └── src
    │       └── main
    │           ├── java
    │           │   └── com
    │           │       └── progmobile
    │           │           └── clickme
    │           │               ├── data
    │           │               └── ui
    │           │                   ├── levels
    │           │                   └── theme
    │           └── res
    │               ├── drawable
    │               ├── layout
    │               ├── mipmap-anydpi-v26
    │               ├── mipmap-hdpi
    │               ├── mipmap-mdpi
    │               ├── mipmap-xhdpi
    │               ├── mipmap-xxhdpi
    │               ├── mipmap-xxxhdpi
    │               ├── raw
    │               ├── values
    │               ├── values-b+cz+CZ
    │               ├── values-b+es+ES
    │               ├── values-b+fr+FR
    │               ├── values-b+pt+PT
    │               └── xml
    └── gradle
        └── wrapper
```

## Credits