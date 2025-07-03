 # 📈 StocksApp 📊

A powerful and simple **Stock Market Tracker App** built with **Kotlin** using **MVVM Architecture**, **Retrofit**, and **Room Database**.  
This app allows users to track stock prices, view top gainers & losers, add stocks to watchlists, and see detailed charts.

---

## 🚀 Demo

![giphy](https://github.com/user-attachments/assets/47db577e-f94d-43f5-a5ba-f0e5ef35c057)

---

## ✨ Features

- **Top Gainers & Losers** — Track leading market movers.
- **Search Stocks** — Instantly search for any stock by its symbol.
- **Stock Details Screen** — In-depth stock data with charts.
- **Watchlist Management** — Add or remove stocks to/from your watchlist (stored locally with Room DB).
- **Live Price Updates** — Fetch latest stock prices using Alpha Vantage API.
- **Price Charts** — View interactive stock price charts using MPAndroidChart.

---


## 🔧 Tech Stack

- **Kotlin**
- **MVVM Architecture**
- **Retrofit (API Integration)**
- **Room Database (Local Storage)**
- **MPAndroidChart (Graphing Library)**
- **Material 3 Design Components**
- **ViewBinding**

---

## 📡 API Used

- [Alpha Vantage API](https://www.alphavantage.co/)  
*(You’ll need a free API key — insert it in your `ApiClient.kt` file.)*

---

## ⚙️ Installation & Setup

1. **Clone the Repository**
```bash
git clone https://github.com/your-username/StocksApp.git
```


2. **Open in Android Studio**
Launch the project in **Android Studio** to begin development.

---

3. **Get API Key**

- Visit [Alpha Vantage](https://www.alphavantage.co/support/#api-key) to generate your free API key.
- Open your `ApiClient.kt` file and insert your API key:

```kotlin
// ApiClient.kt
val apiKey = "YOUR_API_KEY_HERE"
```

---

## 📂 Folder Structure

```
📦 StocksApp/
 ┣ 📂 app/
 ┃ ┣ 📂 src/
 ┃ ┃ ┣ 📂 main/
 ┃ ┃ ┃ ┣ 📂 java/com/kapil/stocks/   ← App source code (activities, adapters, data, viewmodels)
 ┃ ┃ ┃ ┣ 📂 res/                      ← Layouts, drawables, resources
 ┃ ┃ ┃ ┗ AndroidManifest.xml
 ┃ ┗ build.gradle.kts                 ← App module Gradle config
 ┣ build.gradle.kts                   ← Project Gradle config
 ┣ gradle.properties                  ← Gradle properties
 ┗ settings.gradle.kts                ← Gradle settings

```
---

## 🤝 Contribution

Contributions, issues, and feature requests are welcome!

Fork the repo.

Create your feature branch (git checkout -b feature/YourFeature).

Commit your changes (git commit -m 'Add Feature').

Push to the branch (git push origin feature/YourFeature).

Open a Pull Request.

---
## 📄 License
This project is licensed under the MIT License. See the LICENSE file for details.
