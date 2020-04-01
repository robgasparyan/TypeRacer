# TypeRace Application Overview
The goal of **TypeRace** application is develop users' typing skills. It calculates the WPM (word per minute) on each game and a user can improve his/her skills on each game, see his/her latest results history, share own results and why not compete with other users.

# Project Structure
- language - **kotlin**
- architecture - **designed by MVVM data binding**
- dependency injection - **Koin**
- authentification service - **Google Firebase standard email/password authentication**

```
app
├── TypeRacerApplication.kt
├── activity
├── adapter
├── binding
├── di
├── fragment
├── model
├── repo
│   └── impl
│       ├── RaceRepoImpl
│       ├── TypeRepoImpl
│   └── model
│       ├── models
│   └── prefs
│       ├── Preferences
│   └── rest
│       ├── ApiService
│       ├── RetrofitClient
|    |   RaceRepo
|    |   TypeRepo
├── util
├── view
├── viewModel
```

# Screenshots
 
 Login             |  Start Game         | Correct typing
:-------------------------:|:-------------------------:|:-------------------------:
![](https://raw.githubusercontent.com/Armen101/TypeRacer/master/screenshots/typerace_login.jpg)  |  ![](https://raw.githubusercontent.com/Armen101/TypeRacer/master/screenshots/typerace_timer.jpg)  |  ![](https://raw.githubusercontent.com/Armen101/TypeRacer/master/screenshots/typerace_correct.jpg)

 Incorrect typing             |   Finish Game      |  History
:-------------------------:|:-------------------------:|:-------------------------:
![](https://raw.githubusercontent.com/Armen101/TypeRacer/master/screenshots/typerace_incorrect.jpg)  |  ![](https://raw.githubusercontent.com/Armen101/TypeRacer/master/screenshots/typerace_dialog.jpg)  |  ![](https://raw.githubusercontent.com/Armen101/TypeRacer/master/screenshots/typerace_history.jpg)
 
# Recourses

- Get word public API:            https://baconipsum.com/api
- Save and update json data API:  https://api.myjson.com


