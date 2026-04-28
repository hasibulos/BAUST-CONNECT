# BAUST Club Management & Academic Resource Hub 🎓

**BAUST Club Hub** is a centralized platform designed for the students and club administrations of the Bangladesh Army University of Science and Technology (BAUST). It streamlines club recruitment, event management, and provides easy access to vital academic resources.

## 🚀 Key Features

### 🔐 Multi-Tier Admin Hierarchy
The application implements a robust 3-level administrative system to ensure structured governance:
* **Super Admin:** Holds global authority to manage all users, assign administrative roles (e.g., promoting a student to an admin), and oversee all university clubs.
* **Dept Admin:** Manages club activities and approvals within a specific academic department (e.g., CSE, ME).
* **Club Admin:** Responsible for creating events, managing club-specific activities, and reviewing membership applications for their assigned club.

### 📱 Student Capabilities
* **Club Discovery:** Browse a comprehensive list of active clubs within BAUST.
* **Easy Recruitment:** Apply for club memberships directly through the app interface.
* **Academic Hub:** Quick access to university library resources, alumni information, and academic documents.
* **QR Attendance:** Integrated QR code scanning for seamless attendance verification at club events.

## 🛠️ Tech Stack
* **Language:** Kotlin
* **UI Framework:** Jetpack Compose (Modern Declarative UI)
* **Backend & Database:** Firebase (Authentication, Firestore Cloud Database)
* **Architecture:** MVVM (Model-View-ViewModel)

## 📂 Project Directory Structure
```text
com.example.baustclubh
├── ui
│   ├── screens
│   │   ├── admin (Dashboard, ManageClubs, ManageMembers, etc.)
│   │   ├── auth (Login, Registration)
│   │   └── home (Student Home, ClubDetails, Resources)
│   └── navigation (NavGraph for application routing)
├── viewmodel (AuthViewModel, AdminViewModel)
└── model (Data models for Users, Clubs, and Events)
```

## 🔧 Installation & Setup
1.  **Clone the Repository:** `git clone https://github.com/yourusername/baust-club-hub.git`
2.  **Open in Android Studio:** Ensure you have the latest version of Flamingo or newer.
3.  **Firebase Configuration:** * Create a project in the Firebase Console.
    * Add your Android app's package name (`com.example.baustclubh`).
    * Download the `google-services.json` file and place it in the `app/` directory.
4.  **Build & Run:** Sync the Gradle files and run the application on an emulator or physical device.

---

**Developed by:**
**Hasibul Hasib** B.Sc. in Computer Science and Engineering  
Bangladesh Army University of Science and Technology (BAUST)
