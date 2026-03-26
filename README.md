# AlertTrigger

Android companion app for the **ComNet emergency alert system** (Burmese UI: *ComNet အရေးပေါ်သတိပေးစနစ်*). It is published on-device as **Notify Coordinator** (`wiki.comnet.alerttrigger`).

Authenticated users can view broadcast messages from the service and trigger or manage alerts tied to **Shelly** device groups (categories). The app talks to the ComNet backend over HTTPS and caches data locally for faster startup when offline or slow networks.

## Features

- **OTP login** — Verify a one-time token against the API and persist the session.
- **User message** — Fetches and shows the latest user/broadcast message from the server (cached in Room).
- **Category triggers** — Loads Shelly categories from the API; each category is a button that calls the backend to trigger alerts for that group.
- **Toggle all** — Sends a request to enable/open alerts across the board (server-defined behavior).
- **Local cache** — Categories and messages are stored with Room so the home screen can show last-known data immediately.

## Tech stack

- **Kotlin**, **Jetpack Compose** (Material 3), **Android Navigation 3** (alpha)
- **Koin** — dependency injection
- **Ktor** — HTTP client (JSON + logging)
- **Room** — local database (KSP)
- **Minimum SDK** 24, **target/compile** SDK 36

## Backend

The client is configured to use:

`https://websocket.comnet.wiki`

Main API paths include `/api/v1/auth/verify_otp`, `/api/v1/user/message`, `/api/v1/shellies`, `/api/v1/shellies/categories`, and alert actions under `/api/v1/alert/…`. Details live in `app/src/main/java/wiki/comnet/alerttrigger/data/remote/ApiService.kt`.

## Building and running

Requirements: **Android Studio** (or compatible IDE) with a recent **Android Gradle Plugin** and **JDK 11+** as set in the project.

1. Clone the repository and open the project root in Android Studio.
2. Let Gradle sync; then run the **app** configuration on an emulator or device with internet access.
3. Use a valid OTP from your ComNet/backend flow to sign in.

Unit and instrumented test stubs live under `app/src/test` and `app/src/androidTest`.

## Project layout (high level)

- `app/src/main/java/wiki/comnet/alerttrigger/presentation/` — Compose UI (login, home, navigation, theme)
- `app/src/main/java/wiki/comnet/alerttrigger/data/` — Ktor API, DTOs, Room entities/DAOs, repository implementations
- `app/src/main/java/wiki/comnet/alerttrigger/domain/` — repository interfaces and domain models
- `app/src/main/java/wiki/comnet/alerttrigger/di/` — Koin modules

## License

No license file is included in this repository; add one if you intend to distribute or open-source the project.
