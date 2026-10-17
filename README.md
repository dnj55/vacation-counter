# Vacation Counter

A native offline Android app developed with ChatGPT and built on GitHub Actions.

## Version 1.1
- Increment the count with **+ One**.
- Decrement with **− One** (stops at zero, and the button is disabled at zero).
- Reset with confirmation.
- Switch between **light and dark modes**. First launch defaults to the system preference, but your manual choice is remembered.
- Save both counter and theme preferences between launches, offline, with no special permissions.

## Build and sideload
1. Open the repository **Actions** tab and choose the latest successful **Build Android APK** run.
2. Under **Artifacts**, download **vacation-counter-debug-apk**.
3. Extract the ZIP in Samsung My Files and open **app-debug.apk**.
4. Authorize the installation source if Android asks. Re-enable Samsung Auto Blocker afterward.

**Upgrading from v1.0:** GitHub-hosted builds may be signed by different temporary Android debug keys. If Android refuses to install v1.1 over v1.0, uninstall v1.0 first. Uninstalling deletes saved counter data. Future builds can use a persistent signing key to allow reliable in-place upgrades; do not commit private signing keys to this public repository.

## Test
1. Increment to 7, decrement twice, and verify 5.
2. At 0, verify decrement is disabled.
3. Switch themes and verify text, background, buttons, and reset confirmation dialog change.
4. Close and reopen; check the count and theme are retained.
5. Cancel reset (unchanged); confirm reset (zero).

## Build environment
Kotlin, native Android widgets, Android Gradle Plugin 9.4.0, Gradle 9.6.0, Android API 36, JDK 17.
