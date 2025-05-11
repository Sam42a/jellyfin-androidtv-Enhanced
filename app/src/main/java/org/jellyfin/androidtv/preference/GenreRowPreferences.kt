package org.jellyfin.androidtv.preference

import org.jellyfin.preference.stringPreference
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.androidtv.preference.store.DisplayPreferencesStore

class GenreRowPreferences(
    api: ApiClient
) : DisplayPreferencesStore(
    displayPreferencesId = "genererowsettings",
    api = api,
    app = "emby",
) {
    companion object {
        val homeGenreMovie = stringPreference("homeGenreMovie", "")
        val homeGenreTvShow = stringPreference("homeGenreTvShow", "")
        val homeGenreActionAdventure = stringPreference("homeGenreActionAdventure", "")
        val homeGenreFavorites = stringPreference("homeGenreFavorites", "")
    }

    val homeGenreMoviePref = homeGenreMovie
    val homeGenreTvShowPref = homeGenreTvShow
    val homeGenreActionAdventurePref = homeGenreActionAdventure
    val homeGenreFavoritesPref = homeGenreFavorites

    fun getHomeGenreMovie(): String = get(homeGenreMovie)
    fun getHomeGenreTvShow(): String = get(homeGenreTvShow)
    fun getHomeGenreActionAdventure(): String = get(homeGenreActionAdventure)
    fun getHomeGenreFavorites(): String = get(homeGenreFavorites)
}
