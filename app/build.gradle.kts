plugins {
	id("com.android.application")
	kotlin("android")
	alias(libs.plugins.kotlin.serialization)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.aboutlibraries)
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

android {
	namespace = "org.jellyfin.androidtv"
	compileSdk = libs.versions.android.compileSdk.get().toInt()

	defaultConfig {
		minSdk = libs.versions.android.minSdk.get().toInt()
		targetSdk = libs.versions.android.targetSdk.get().toInt()

		// Release version
		applicationId = namespace
		versionName = project.getVersionName()
		versionCode = getVersionCode(versionName!!)
		setProperty("archivesBaseName", "jellyfin-androidtv-v$versionName")
	}

	buildFeatures {
		buildConfig = true
		viewBinding = true
		compose = true
		dataBinding = true
	}

	compileOptions {
		isCoreLibraryDesugaringEnabled = true
		sourceCompatibility = JavaVersion.VERSION_21
		targetCompatibility = JavaVersion.VERSION_21
	}

	// Using older kotlinOptions syntax to maintain compatibility
	// but with @Suppress to hide the deprecation warning
	@Suppress("DEPRECATION")
	kotlinOptions {
		jvmTarget = "21"
		freeCompilerArgs += listOf("-Xjvm-default=all")
	}

	tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		@Suppress("DEPRECATION")
		kotlinOptions {
			jvmTarget = "21"
		}
	}

	buildTypes {
		val release by getting {
			isMinifyEnabled = false

			// Set flavored application name
			resValue("string", "app_name", "@string/app_name_release")

			buildConfigField("boolean", "DEVELOPMENT", "false")
		}

		val debug by getting {
			// Use different application id to run release and debug at the same time
			applicationIdSuffix = ".debug"

			// Set flavored application name
			resValue("string", "app_name", "@string/app_name_debug")

			buildConfigField("boolean", "DEVELOPMENT", (defaultConfig.versionCode!! < 100).toString())
		}
	}

	// Define a build flavor for the enhanced version that can be installed alongside the original
	flavorDimensions += listOf("variant")
	productFlavors {
		create("standard") {
			dimension = "variant"
			// Uses default applicationId
		}
		
		create("enhanced") {
			dimension = "variant"
			applicationId = "org.jellyfyn.androidtv.enhanced"
			
			// Set specific version name for enhanced variant
			versionName = "0.18.8"
			
			// Set app name for the enhanced version
			resValue("string", "app_name_release", "Jellyfine")
			
			// Add required string resources that are referenced in XML files
			resValue("string", "app_id", applicationId!!)
			resValue("string", "app_search_suggest_authority", "${applicationId}.content")
			resValue("string", "app_search_suggest_intent_data", "content://${applicationId}.content/intent")
			
			// Set custom APK filename
			setProperty("archivesBaseName", "jellyfin-androidtv-v0.18.8-enhanced-release")
		}
	}

	lint {
		lintConfig = file("$rootDir/android-lint.xml")
		abortOnError = false
		sarifReport = true
		checkDependencies = true
	}

	testOptions.unitTests.all {
		it.useJUnitPlatform()
	}
}

val versionTxt by tasks.registering {
	val path = layout.buildDirectory.asFile.get().resolve("version.txt")

	doLast {
		val versionString = "v${android.defaultConfig.versionName}=${android.defaultConfig.versionCode}"
		logger.info("Writing [$versionString] to $path")
		path.writeText("$versionString\n")
	}
}

// Simple task to build the enhanced version
tasks.register("buildEnhanced") {
	group = "build"
	description = "Builds the enhanced version with package ID: org.jellyfyn.androidtv.enhanced"
	dependsOn("assembleEnhancedRelease")
	doLast {
		println("\nBuilding Enhanced version with:")
		println("Package ID: org.jellyfyn.androidtv.enhanced")
		println("Version: 0.18.8")
		println("App Name: Jellyfine")
		println("Filename: jellyfin-androidtv-v0.18.8-enhanced-release.apk")
		println("The APK will be available in: app/build/outputs/apk/enhanced/release/")
	}
}



dependencies {
    implementation("androidx.compose.material:material:1.5.0")
	// Jellyfin
	implementation(projects.playback.core)
	implementation(projects.playback.jellyfin)
	implementation(projects.playback.media3.exoplayer)
	implementation(projects.playback.media3.session)
	implementation(projects.preference)
	implementation(libs.jellyfin.sdk) {
		// Change version if desired
		val sdkVersion = findProperty("sdk.version")?.toString()
		when (sdkVersion) {
			"local" -> version { strictly("latest-SNAPSHOT") }
			"snapshot" -> version { strictly("master-SNAPSHOT") }
			"unstable-snapshot" -> version { strictly("openapi-unstable-SNAPSHOT") }
		}
	}

	// Kotlin
	implementation(libs.kotlinx.coroutines)
	implementation(libs.kotlinx.serialization.json)

	// Android(x)
	implementation(libs.androidx.core)
	implementation(libs.androidx.activity)
	implementation(libs.androidx.fragment)
	implementation(libs.androidx.fragment.compose)
	implementation(libs.androidx.leanback.core)
	implementation(libs.androidx.leanback.preference)
	implementation(libs.androidx.preference)
	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.tvprovider)
	implementation(libs.androidx.constraintlayout)
	implementation(libs.androidx.recyclerview)
	implementation(libs.androidx.work.runtime)
	implementation(libs.bundles.androidx.lifecycle)
	implementation(libs.androidx.window)
	implementation(libs.androidx.cardview)
	implementation(libs.androidx.startup)
	implementation(libs.bundles.androidx.compose)

	// Dependency Injection
	implementation(libs.bundles.koin)

	// Media players
	implementation(libs.androidx.media3.exoplayer)
	implementation(libs.androidx.media3.datasource.okhttp)
	implementation(libs.androidx.media3.exoplayer.hls)
	implementation(libs.androidx.media3.ui)
	implementation(libs.jellyfin.androidx.media3.ffmpeg.decoder)

	// Markdown
	implementation(libs.bundles.markwon)

	// Image utility
	implementation(libs.bundles.coil)
	implementation("com.github.bumptech.glide:glide:4.16.0")
	implementation("androidx.viewpager2:viewpager2:1.0.0")

	// Crash Reporting
	implementation(libs.bundles.acra)

	// Licenses
	implementation(libs.aboutlibraries)

	// Logging
	implementation(libs.timber)
	implementation(libs.slf4j.timber)

	// Compatibility (desugaring)
	coreLibraryDesugaring(libs.android.desugar)

	// Testing
	testImplementation(libs.kotest.runner.junit5)
	testImplementation(libs.kotest.assertions)
	testImplementation(libs.mockk)
}
