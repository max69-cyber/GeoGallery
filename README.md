# GeoGallery

GeoGallery is an Android learning project built with Kotlin and Jetpack Compose. The planned app will capture photos, group them automatically by place, and display those places on a map.

## Run locally

### Requirements

- Android Studio with support for the Android Gradle Plugin version in the [version catalog](gradle/libs.versions.toml).
- Android SDK Platform **37** for compilation.
- A device or emulator running **Android 8.0 (API 26)** or newer.
- **JDK 25** for the Gradle daemon. Gradle can provision it, or you can make it available locally.

### Android Studio

1. Clone or download the repository and open its root directory in Android Studio.
2. Let Gradle sync and install any required SDK components.
3. Select the `app` run configuration and a device or emulator, then click **Run**.

The starter app runs without API keys, server configuration, or credentials.

### Command line

Run these commands from the repository root. Set `JAVA_HOME` or make `java` available on your `PATH` so the Gradle wrapper can start. Configure the Android SDK through `ANDROID_HOME` or a local `local.properties` file containing `sdk.dir`.

Build a debug APK.

```sh
./gradlew :app:assembleDebug
```

The APK is written to `app/build/outputs/apk/debug/app-debug.apk`.

Start an emulator or connect a device, then install the app. On a physical device, enable USB debugging and authorize your computer. Use `adb devices` to check the connection. The `adb` command comes with Android SDK Platform-Tools.

```sh
./gradlew :app:installDebug
```

Open GeoGallery from the app launcher or start it from the terminal.

```sh
adb shell am start -n com.rayxaus.geogallery/.MainActivity
```

On Windows, use `gradlew.bat` in place of `./gradlew`.
