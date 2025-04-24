# 🩺 Clean Docdoc

**clean.docdoc** is a fully rebuilt version of my previous Docdoc app — a **doctor appointment management app** that helps users find doctors, view their profiles, and book appointments easily.

This version reflects everything I’ve learned over the past 7 months — from real freelance work and mentorship — and showcases clean code, modular architecture, and modern Android development practices.

## ⚒️ Tech Stack

- **Jetpack Compose** – for modern declarative UI  
- **Kotlin**  
- **Retrofit** – for API communication  
- **Dagger-Hilt** – for dependency injection  
- **Navigation Component** – with type-safe routes  
- **GitHub Actions** – CI/CD pipeline for release builds  
- **Firebase App Distribution** – for distributing APKs  

## 💡 Core Features

- 📝 Register & login with email  
- 🧑‍⚕️ View recommended doctors across specialties  
- 🔍 Search and filter doctors by specialization  
- 👤 View detailed doctor profiles with contact info  
- 📅 Book appointments directly in-app  
- 👥 Browse as Guest – explore doctors without an account (even though the backend doesn’t support it 😉)  
- 🔗 Share doctor profiles using deep links  
- 🚀 Automatically release signed APKs to Firebase and GitHub Releases via GitHub Actions

![Docdoc Overview](https://github.com/user-attachments/assets/12205fd4-6773-4a73-a922-8ecfc5661cd4)
  

## ✅ What’s New (Compared to the Original Docdoc)

- 🧼 **Refactored Clean Architecture** – now truly clean, scalable, and maintainable  
- 🔁 **Switched to Retrofit** – easier API management than Ktor for this case  
- 🧠 **Structured State Management** – with [`StateViewModel`](https://github.com/MohammadMarwan2005/clean.docdoc/blob/8f7178dec07cc282951bceca5649ff4d137c0c58/app/src/main/java/com/alaishat/mohammad/clean/docdoc/presentation/common/ViewModel.kt#L17) and [`EventViewModel`](https://github.com/MohammadMarwan2005/clean.docdoc/blob/8f7178dec07cc282951bceca5649ff4d137c0c58/app/src/main/java/com/alaishat/mohammad/clean/docdoc/presentation/common/ViewModel.kt#L22)  
- ❌ **Robust Error Handling** – using [`SafeAPICaller.kt`](https://github.com/MohammadMarwan2005/clean.docdoc/blob/master/app/src/main/java/com/alaishat/mohammad/clean/docdoc/data/SafeAPICaller.kt), [`ErrorHandler.kt`](https://github.com/MohammadMarwan2005/clean.docdoc/blob/master/app/src/main/java/com/alaishat/mohammad/clean/docdoc/data/ErrorHandlerImpl.kt), [`DomainError.kt`](https://github.com/MohammadMarwan2005/clean.docdoc/blob/master/app/src/main/java/com/alaishat/mohammad/clean/docdoc/domain/model/core/DomainError.kt), and [`Resource.kt`](https://github.com/MohammadMarwan2005/clean.docdoc/blob/master/app/src/main/java/com/alaishat/mohammad/clean/docdoc/domain/Resource.kt)  
- 🧭 **Type-Safe Jetpack Navigation** – clean and modular using [`NavigationRoute.kt`](https://github.com/MohammadMarwan2005/clean.docdoc/blob/master/app/src/main/java/com/alaishat/mohammad/clean/docdoc/presentation/navigation/NavigationRoute.kt)
- 👥 **Guest Mode** – use app without signing in  
- 🔗 **Deep Linking** – share doctor profiles via smart links  
- 🛠️ **CI/CD Workflow** – auto-build and deploy with GitHub Actions  

## 📦 APK Download

[**Download the latest APK**](https://github.com/MohammadMarwan2005/clean.docdoc/releases/latest/download/app-release.apk)  
_This link always points to the latest public APK uploaded to GitHub Releases._  

You can also check out **[all releases](https://github.com/MohammadMarwan2005/clean.docdoc/releases)**.


## 📚 What I’ve Learned

- How to properly implement clean architecture in a production-level app  
- Best practices for state and error handling in Compose apps  
- Setting up CI/CD pipelines with GitHub Actions  
- Writing testable, maintainable, and scalable codebases  

## 🙏 Acknowledgments

Big thanks to my brother and mentor [@ahmed](https://github.com/ahmedalaishat) — your support and guidance shaped every part of this rebuild.

## ⚠️ Archived Original Version

The original Docdoc app is no longer maintained.  
Check out the [archived version here](https://github.com/MohammadMarwan2005/docdoc) if you’re curious to compare the progress!
