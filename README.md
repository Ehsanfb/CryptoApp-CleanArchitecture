# Crypto Info App - Clean Architecture

This Android app displays a list of cryptocurrencies and provides detailed information about each coin, built using modern Android development best practices such as **Clean Architecture**, **MVVM**, **Kotlin Coroutines**, **Flow**, and **Jetpack Compose**. It includes the following features:

- **List of Cryptos**: Displays a list of cryptocurrencies with essential details.
- **Coin Detail Screen**: Tapping on a coin from the list shows detailed information, including:
  - Coin name
  - Symbol
  - Active status
  - Description
  - Related tags
  - Team members and their roles

---

## Architecture

The app follows the **Clean Architecture** pattern with a clear separation of concerns into the following layers:

1. **Presentation Layer (UI)**: 
   - **Jetpack Compose** is used for UI development.
   - ViewModel manages UI-related data and communicates with the domain layer via use cases.

2. **Domain Layer**: 
   - Contains business logic and use cases.
   - The `GetCoinsUseCase` and `GetCoinDetailUseCase` handle fetching and processing the data.

3. **Data Layer**:
   - The data layer is responsible for handling data from remote or local sources.
   - It includes a repository (`CoinRepository`) that interacts with an API or database to fetch coin data.

---

## Tech Stack

- **Kotlin**: Programming language.
- **Jetpack Compose**: UI toolkit for building native Android UIs.
- **MVVM**: Architecture pattern for separating concerns.
- **Flow**: For handling asynchronous data streams.
- **Coroutines**: For background processing.
- **Hilt**: Dependency Injection.
- **Retrofit**: For making network requests (if you're fetching data from an API).
- **Resource Wrappers**: Handling success, error, and loading states.

---

## Setup

### Prerequisites

- Android Studio (latest stable version)
- Kotlin 1.5 or higher
- Gradle 7.0 or higher
