![UI Enhancements](https://i.imgur.com/RxVBumq.png)


:tada: Enhanced Fork of Jellyfin for Android TV :rocket:

[Jellyfin for Android TV](https://github.com/jellyfin/jellyfin-androidtv)! This version brings a suite of exciting features and improvements to elevate your media streaming experience on Android TV. From sleek UI enhancements to performance boosts, this fork is designed to make your interaction smoother and more enjoyable. :star2:

:sparkles: What's Included in This Fork
- **White Borders on Focused Cards**  
  Improved visibility with clean, white borders around focused cards for a polished look.
- :crescent_moon: **Enhanced Dark Theme**  
  A refined dark theme for a more comfortable and stylish viewing experience.
- :memo: **Subtitle Weight Customization**  
  Choose between **Normal** or **Bold** subtitle weights to suit your preferences.
- :microphone: **Voice Search Integration**  
  Search by voice is now seamlessly integrated into the search page.
- :books: **Customizable Genre Rows**  
  Show or hide genre rows on the home screen for infinite scrolling. Includes support for **Collections Library** and **Playlists** for music libraries.
- :paintbrush: **Backdrop & Background Blur Customization**  
  Adjust blur to your desired percentage for better visibility and a personalized aesthetic.
- :gear: **Redesigned Buttons**  
  New **Search**, **Settings**, and **Users** buttons with intuitive, convenient placement.
- :black_square_button: **Sleek, Rounded Cards**  
  Modernized card design with smoother, more rounded edges for a contemporary feel.
- :zap: **Improved Image Caching**  
  Faster loading times with an optimized image caching method.
- :rocket: **Performance & Visual Enhancements**  
  Streamlined visuals for better performance and quicker load times.

:crystal_ball: Possible Things i might add :
- :inbox_tray: **Subtitle Download Integration**  
  Download and load subtitles directly in the player from sources like OpenSubtitles or Subdl.
- :frame_with_picture: **Auto-Sliding Banner**  
  A dynamic banner showcasing recently added media.
- :musical_note: **Default Audio Language Selection**  
  Set your preferred audio language for a seamless experience.
- :house: **Customizable Home Screen**  
  Choose which items appear in a personalized home screen row.
- :pushpin: **Long-Press Menu Options**  
  Long-press items to add them to specific collections via a mini popup menu.
- :art: **Theme Import for Android TV**  
  Import themes from web wrapper-clients to the Android TV app.

:star: Credits
Special thanks to the OLED mod fork for inspiration : [LitCastVlog/jellyfin-androidtv-OLED](https://github.com/LitCastVlog/jellyfin-androidtv-OLED).

                             Note : I made this on coffee and noodles,Small bugs might happen.
-----------------------------------------------------------------------------------------------------------------------------------------------
<h1 align="center">Jellyfin Android TV</h1>
<h3 align="center">Part of the <a href="https://jellyfin.org">Jellyfin Project</a></h3>

---

<p align="center">
<img alt="Logo banner" src="https://raw.githubusercontent.com/jellyfin/jellyfin-ux/master/branding/SVG/banner-logo-solid.svg?sanitize=true"/>
<br/><br/>
<a href="https://github.com/jellyfin/jellyfin-androidtv">
<img alt="GPL 2.0 License" src="https://img.shields.io/github/license/jellyfin/jellyfin-androidtv.svg"/>
</a>
<a href="https://github.com/jellyfin/jellyfin-androidtv/releases">
<img alt="Current Release" src="https://img.shields.io/github/release/jellyfin/jellyfin-androidtv.svg"/>
</a>
<a href="https://translate.jellyfin.org/projects/jellyfin-android/jellyfin-androidtv/">
<img alt="Translation Status" src="https://translate.jellyfin.org/widgets/jellyfin-android/-/jellyfin-androidtv/svg-badge.svg"/>
</a>
<br/>
<a href="https://opencollective.com/jellyfin">
<img alt="Donate" src="https://img.shields.io/opencollective/all/jellyfin.svg?label=backers"/>
</a>
<a href="https://features.jellyfin.org">
<img alt="Feature Requests" src="https://img.shields.io/badge/fider-vote%20on%20features-success.svg"/>
</a>
<a href="https://matrix.to/#/+jellyfin:matrix.org">
<img alt="Chat on Matrix" src="https://img.shields.io/matrix/jellyfin:matrix.org.svg?logo=matrix"/>
</a>
<a href="https://www.reddit.com/r/jellyfin">
<img alt="Join our Subreddit" src="https://img.shields.io/badge/reddit-r%2Fjellyfin-%23FF5700.svg"/>
</a>
<br/>
<a href="https://play.google.com/store/apps/details?id=org.jellyfin.androidtv">
<img width="153" alt="Jellyfin on Google Play" src="https://jellyfin.org/images/store-icons/google-play.png"/>
</a>
<a href="https://www.amazon.com/gp/aw/d/B07TX7Z725">
<img width="153" alt="Jellyfin on Amazon Appstore" src="https://jellyfin.org/images/store-icons/amazon.png"/>
</a>
<a href="https://f-droid.org/en/packages/org.jellyfin.androidtv/">
<img width="153" alt="Jellyfin on F-Droid" src="https://jellyfin.org/images/store-icons/fdroid.png"/>
</a>
<br/>
<a href="https://repo.jellyfin.org/releases/client/androidtv/">Download archive</a>
</p>

Jellyfin Android TV is a Jellyfin client for Android TV, Nvidia Shield, and Amazon Fire TV devices.
We welcome all contributions and pull requests! If you have a larger feature in mind please open an
issue so we can discuss the implementation before you start. 

## Translating

Translations can be improved very easily from our
[Weblate](https://translate.jellyfin.org/projects/jellyfin-android/jellyfin-androidtv) instance.
Look through the following graphic to see if your native language could use some work!

<a href="https://translate.jellyfin.org/engage/jellyfin-android/">
<img alt="Detailed Translation Status" src="https://translate.jellyfin.org/widgets/jellyfin-android/-/jellyfin-androidtv/multi-auto.svg"/>
</a>

## Build Process

### Dependencies

- Android Studio

### Build

1. Clone or download this repository

   ```sh
   git clone https://github.com/jellyfin/jellyfin-androidtv.git
   cd jellyfin-androidtv
   ```

2. Open the project in Android Studio and run it from there or build an APK directly through Gradle:

   ```sh
   ./gradlew assembleDebug
   ```
   
   Add the Android SDK to your PATH environment variable or create the ANDROID_SDK_ROOT variable for
   this to work.

### Deploy to device/emulator

   ```sh
   ./gradlew installDebug
   ```

*You can also replace the "Debug" with "Release" to get an optimized release binary.*
