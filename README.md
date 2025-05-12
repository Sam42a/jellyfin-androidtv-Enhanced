# Jellyfine - Enhanced Jellyfin Android TV Client
--------------------------------------------------------------------------------------------------------------------

[![GitHub release](https://img.shields.io/github/release/Sam42a/jellyfin-androidtv-Enhanced.svg)](https://github.com/Sam42a/jellyfin-androidtv-Enhanced/releases)
Jellyfin Android TV is a Jellyfin client for Android TV, Nvidia Shield, and Amazon Fire TV devices.
We welcome all contributions and pull requests! If you have a larger feature in mind please open an
issue so we can discuss the implementation before you start. 

<p align="center">
  <img src="https://imgur.com/DFQiJvc.jpg" alt="Centered image">
</p>

## Translating

Translations can be improved very easily from our
[Weblate](https://translate.jellyfin.org/projects/jellyfin-android/jellyfin-androidtv) instance.
Look through the following graphic to see if your native language could use some work!

<a href="https://translate.jellyfin.org/engage/jellyfin-android/">
<img alt="Detailed Translation Status" src="https://translate.jellyfin.org/widgets/jellyfin-android/-/jellyfin-androidtv/multi-auto.svg"/>
</a>

## Enhanced Features

This fork includes several visual and usability enhancements to improve the Jellyfin experience on Android TV devices:

### UI Improvements
- **White Border Around Items**: Added borders to item cards for better visual contrast
- **Bold Text**: Improved readability with bolder text throughout the interface
- **Enhanced Backgrounds**: Polished UI with improved backgrounds for login, server and user selection screens
- **Modified Toolbar**: Redesigned search/settings/users toolbar buttons for better visibility

### New Customization Options
- **Backdrop Control**: New preference settings to adjust backdrop dimming intensity
- **Blur Control**: Customizable blur effect for backdrop images
- **Genre Rows**: Added the ability to include genre-specific rows on the home screen with show/hide options
- **Subtitle Enhancement**: New preference setting to control the boldness of subtitles

### Technical Improvements
- **Side-by-side Installation**: Modified package ID (org.jellyfyn.androidtv.enhanced) allows installation alongside the original Jellyfin app
- **Fire TV Compatibility**: Fixed permission handling for better compatibility with Fire TV devices

## Build Process

### Dependencies

- Android Studio

### Build

1. Clone or download this repository

   ```sh
   git clone https://github.com/Sam42a/jellyfin-androidtv-Enhanced.git
   cd jellyfin-androidtv-Enhanced
   ```

2. Open the project in Android Studio and run it from there or build an APK directly through Gradle:

   **Standard Version:**
   ```sh
   ./gradlew assembleStandardDebug  # Debug build
   ./gradlew assembleStandardRelease  # Release build
   ```
   
   **Enhanced Version (installable alongside original Jellyfin):**
   ```sh
   ./gradlew buildEnhanced  # Custom task to build enhanced release version
   ```
   
   Add the Android SDK to your PATH environment variable or create the ANDROID_SDK_ROOT variable for
   this to work.

### Deploy to device/emulator

   ```sh
   # Install standard version
   ./gradlew installStandardDebug
   
   # Install enhanced version (can coexist with original Jellyfin)
   ./gradlew installEnhancedRelease
   ```

**Note:** The enhanced version uses package ID `org.jellyfyn.androidtv.enhanced` which allows it to be installed alongside the original Jellyfin app.

### Build System Requirements

- Java 21 or higher
- Android SDK with API level 35
