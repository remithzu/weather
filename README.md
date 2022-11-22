# weather

Weather is an Android apps writing in kotlin to show a weather condition based on [openweathermap](https://openweathermap.org/) API.

In this project using some of best practice in Android Development.

Includeing :
* ViewModel
* Kotlin Corotine
* Retrofit
* Room

Other Library:
* Google gson
* glide
* okHttp3
* viewPager2

## How to run this app
1. Clone the app from repository
``` 
git clone https://github.com/remithzu/weather-app.git
```

2. Load the project to and Android Studio<br>
Select `Build` -> `Build Bundle(s)/APK(s)` -> `Build APK(s)` or `Build Bundle(s)`<br>
or you can run this project by click run on the panel of Android Studio

## Explaination
### ViewModel
ViewModel is a mechanism of business logic on the screen level, this library used for manage data before displaying to UI. 
### Corotine
Coroutine is a design pettern to simplify code that executing asunchronously. coroutines help to manage long-running tasks that might otherwise block the main thread 
### Retrofit
Retrofit is type-safe REST client which aims to make it easier to consume RESTful web services.
### Room
Room is modern database that provides an abstraction layer over SQLite to allow for more robust database access.
