# My-Movies🎬
🎬Application that displays a list of more than 10.000 Movies and TV shows using Modern Android Application Development tools and API's

The App Makes a request to TMDB's [API](https://www.themoviedb.org/documentation/api) to get:
  
- A list of the current [`popular`](https://developers.themoviedb.org/3/movies/get-popular-movies) and [`top_rated`](https://developers.themoviedb.org/3/movies/get-top-rated-movies) movies on TMDb. `/movie/{category}`
  - List of official genres for movies. [`/genre/movie/list`](https://developers.themoviedb.org/3/genres/get-movie-list)
  - List of user reviews for a movie. [`/movie/{movie_id}/reviews`](https://developers.themoviedb.org/3/movies/get-movie-reviews)
  - The cast and crew for a movie. [`/movie/{movie_id}/credits`](https://developers.themoviedb.org/3/movies/get-movie-credits)
  - The videos(trailers, behind the Scenes, & bloopers) that have been added to a movie. [`/movie/{movie_id}/videos`](https://developers.themoviedb.org/3/movies/get-movie-videos)
  - A specific movie. [`/search/movie`](https://developers.themoviedb.org/3/search/search-movies)
  
- A list of the current [`popular`](https://developers.themoviedb.org/3/tv/get-popular-tv-shows) and [`top_rated`](https://developers.themoviedb.org/3/tv/get-top-rated-tv) TV shows on TMDb. `/tv/{category}`
  - List of official genres for TV shows. [`/genre/tv/list`](https://developers.themoviedb.org/3/genres/get-tv-list)
  - List of user reviews for a TV show. [`/tv/{tv_id}/reviews`](https://developers.themoviedb.org/3/tv/get-tv-reviews)
  - The cast and crew for a TV show. [`/tv/{tv_id}/credits`](https://developers.themoviedb.org/3/tv/get-tv-credits)
  - The videos(trailers, behind the Scenes, & bloopers) that have been added to a TV Show. [`/tv/{tv_id}/videos`](https://developers.themoviedb.org/3/tv/get-tv-videos)
  - A specific TV show. [`/search/tv`](https://developers.themoviedb.org/3/search/search-tv-shows)

Base URL = `"https://api.themoviedb.org/3/"`

**PS:** To test this code you will need to:
1. Get an API Key from [TMDB](https://www.themoviedb.org/documentation/api)
2. Create a file on the Utils package with: 
`const val SECRET_KEY = "PLACE YOUR API KEY HERE"`

![one](https://user-images.githubusercontent.com/38020305/92302032-1c158b80-ef69-11ea-9441-725334e2a3fd.jpg)
![two](https://user-images.githubusercontent.com/38020305/92302035-1f107c00-ef69-11ea-947c-58402ebb3e51.jpg)



## Built With

* [Android Jetpack](https://developer.android.com/jetpack/?gclid=Cj0KCQjwhJrqBRDZARIsALhp1WQBmjQ4WUpnRT4ETGGR1T_rQG8VU3Ta_kVwiznZASR5y4fgPDRYFqkaAhtfEALw_wcB) - Suite of libraries, tools, and guidance to help developers write high-quality apps easier.
  * [Android KTX](https://developer.android.com/kotlin/ktx)
  * [Databinding](https://developer.android.com/jetpack/androidx/releases/databinding)
  * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
  * [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation)
  * [Paging](https://developer.android.com/jetpack/androidx/releases/paging)
  * [Preference](https://developer.android.com/jetpack/androidx/releases/preference)
  * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [CircleImageView](https://github.com/hdodenhof/CircleImageView) - A fast circular ImageView perfect for profile images.
* [Glide](https://github.com/bumptech/glide) - A fast and efficient open source media management and image loading framework for Android.
* [GSON](https://github.com/google/gson) - Java library that can be used to convert Java Objects into their JSON representation.
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Library that provides a standard way to incorporate Dagger dependency injection into an Android application.
* [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) - Concurrency design pattern used on Android to simplify code that executes asynchronously.
* [Retrofit 2](https://github.com/square/retrofit) - A type-safe HTTP client for Android and Java.
* [Timber](https://github.com/JakeWharton/timber) - Logger with a small, extensible API which provides utility on top of Android's normal Log class.



## Author

* **Doilio A. Matsinhe**  
- *Contact me on*
- [Twitter](https://twitter.com/DoilioMatsinhe)
- [Instagram](https://www.instagram.com/doiliomatsinhe/)
- [LinkedIn](https://www.linkedin.com/in/doilio-matsinhe)


## License

      Copyright 2020 Doilio Abel Matsinhe

      Licensed under the Apache License, Version 2.0 (the "License");
      you may not use this file except in compliance with the License.
      You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

      Unless required by applicable law or agreed to in writing, software
      distributed under the License is distributed on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      See the License for the specific language governing permissions and
      limitations under the License.

