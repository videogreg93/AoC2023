# ProtoGaia

Visualizations of various AOC 2023 puzzles. AOC specfic code will be found [here](core/src/main/kotlin/screens/aoc), while the rest is general purpose
code from my LibGDX based engine.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3.

## Credits
- https://franuka.itch.io/rpg-snow-tileset
- https://snowypandas.itch.io/pixelart-menu-ui
- https://aamatniekss.itch.io/winter-land-fantasy-pixel-art-tileset

## Gradle

This project uses [Gradle](http://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
