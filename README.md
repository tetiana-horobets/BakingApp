## BakingApp
 BakingApp is the project which I made from scratch for [Android Developer Nanodegree Program](https://eu.udacity.com/course/android-developer-nanodegree-by-google--nd801). The app takes some recipes in JSON format from the web and shows ingredients, steps, videos. 
 
 Thei aim of the app is to learn how to:
 - use MediaPlayer/Exoplayer to display videos
 - handle error cases in Android
 - add a widget to your app experience
 - leverage a third-party library in your app
 - use Fragments to create a responsive design that works on phones and tablets

###  This app features or uses the following components of android

* this app handles Activity and Fragment lifeCycles
* this app maintains instanceState on configuration change (Activity and Fragment)
* passing data between activities and fragments
* fragments and re-use of the same fragment in different activities
* desiging layouts for different screen sizes(Phone and tablet) and orientation (Landscape)
* loading data from network, Loading and caching Image form network
* JSON data handling and serialization
* Widgets
* streaming video using ExoPlayer
* RecyclerViews, CardViews
* Espresso Unit Tests

### About this app

On Clicking on a recipe it shows the ingredients and steps to cook
On phone if a step is clicked it launches a new activity which displays video instruction
On tablet if a step is clicked it shows in the right panel of the screen
The widget will update ingredients when user selects a recipe in the app

<img src="https://raw.githubusercontent.com/tetiana-horobets/BakingApp/master/screens/photoeditorsdk-export.png" width="300">

### This app makes use of the following external libraries and resources

* Recipe data is provided by Udacity
* [Picasso](http://square.github.io/picasso/) - a powerful library that handles image loading and caching in the app
* [ButterKnife](http://jakewharton.github.io/butterknife/) 
* [ExoPlayer](https://github.com/google/ExoPlayer)
* [Espresso](https://developer.android.com/training/testing/espresso/)
* [Gson](https://github.com/google/gson)
* [Support Library Packages](https://developer.android.com/topic/libraries/support-library/packages)
