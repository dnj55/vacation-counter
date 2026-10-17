# Vacation Counter

My first Android app built with AI.

This is an experimental, offline-native Android app that counts button taps and saves the count locally between launches.

## Features
- **Count one +** increments the counter.
- **Reset counter** asks for confirmation before clearing it.
- Local persistence with Android `SharedPreferences`; no network, ads, sign-in, or special permissions.

## Build and install from an Android phone
1. Open this repository's **Actions** tab.
2. Select **Build Android APK**, then open its latest successful run. If none appears, tap **Run workflow**.
3. Under **Artifacts**, download `vacation-counter-debug-apk` (a ZIP file).
4. Extract the ZIP in Samsung **My Files**.
5. Tap the enclosed `app-debug.apk` to install it. You may need to permit installations from this source or adjust Samsung Auto Blocker to allow sideloading.

A first run can take several minutes while it downloads Android tools and build dependencies.

## Acceptance test
1. Tap **Count one +** ten times.
2. Close and reopen the app: the count should still be 10.
3. Tap **Reset counter** and then **Cancel**: it should remain 10.
4. Confirm a reset: the count should return to 0.

## Build details
- Native Kotlin Activity using Android framework widgets (no third-party UI dependencies)
- Android Gradle Plugin 9.4.0, Gradle 9.6.0, Android API 36 build SDK
- Java 17
- Target SDK 35, minimum SDK 26
- GitHub Actions builds with `gradle --no-daemon :app:assembleDebug`

**Note:** GitHub-hosted debug builds may have different debug signing keys between runs. If Android refuses a subsequent APK update because the signing key is different, uninstalling the earlier app before installing the new APK will usually resolve it, but will erase the locally saved count. For reliable updates, a persistent signing key would be needed.

This is an initial experiment; the APK must first be compiled by GitHub Actions, not from this chat.
