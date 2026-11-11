# FisioXtrem

Native Android app for a physiotherapy clinic chain: browse centres and services, book an appointment and check promotions. Built with Kotlin and Jetpack Compose using MVVM. The interface is in Catalan.

> This is a UI and architecture project. Data lives in memory and the login is simulated (see [Limitations](#limitations)).

## Features

- **Login screen** with form validation (simulated authentication).
- **Centres:** list of clinics with address and phone.
- **Services:** list with live search by treatment name and price badges.
- **Book an appointment:** choose centre, then a professional from that centre, then a date (past dates are disabled) and a time. The form validates every field before confirming.
- **My appointments:** confirmed appointments are listed with date, time, centre and professional.
- **Promotions:** current offers with their codes.
- Adaptive navigation with a Material 3 `NavigationRail`, shown only after login.

## Tech stack

Kotlin · Jetpack Compose · Material 3 · Navigation Compose · ViewModel · Gradle (Kotlin DSL, version catalog) · JUnit

`minSdk 25` · `compileSdk 36`

## Architecture

```
app/src/main/java/com/example/fisioxtrem/
├── MainActivity.kt          # Activity, navigation rail and NavHost
├── model/Models.kt          # Centre, Servei, Professional, Promocio, Usuari, Cita
├── viewmodel/               # FisioViewModel: state and business rules
└── ui/
    ├── screens/             # One composable per screen
    ├── theme/               # Material 3 theme
    └── Formatters.kt        # Date and time formatting
```

- A single `FisioViewModel` holds the state and exposes it to the screens. Screens are stateless about business data and only keep local UI state (dialogs, form fields).
- Navigation routes are centralised in one place, and the login screen is removed from the back stack after signing in.
- The appointment form resets the selected professional when the centre changes, so an invalid centre and professional pair cannot be booked.

## Run it

1. Open the project in Android Studio (recent stable version, JDK 21).
2. Run the `app` configuration on an emulator or device with Android 7.1 (API 25) or higher.
3. To sign in, use any email together with the demo password `1234`.

Unit tests (login rules, logout, appointment storage and data consistency):

```bash
./gradlew test
```

## Limitations

- **Simulated login.** Any non-empty email plus the demo password is accepted. There is no backend and no real authentication.
- **No persistence.** Appointments are kept in memory and disappear when the app process ends.
- **Demo data only.** Centres, professionals and promotions are fictional.

## Roadmap

- [ ] Persist appointments locally with Room.
- [ ] Replace the simulated login with a real backend (REST API, token storage).
- [ ] Appointment cancellation and conflict detection (same professional, same time slot).
- [ ] UI tests for the booking flow.

## Author

David Sanabria — Android / software developer. [LinkedIn](https://linkedin.com/in/davidsanabriahierro) · [GitHub](https://github.com/dsanabria01)
