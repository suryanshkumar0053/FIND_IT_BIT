# 🎓 FIND_IT_BIT - Lost & Found App for BIT Sindri

**FIND_IT_BIT** is a campus-specific lost and found Android app built exclusively for students and staff of **BIT Sindri**. It allows users to post, browse, and recover lost or found items through a secure and verified system.

> 🔐 Only accessible with verified **Indian phone numbers**.  
> 🏫 Built specifically for the **BIT Sindri community**.

Built using **Jetpack Compose** and **Kotlin**, and powered by **Firebase** for authentication, real-time database, and cloud storage.

---

## 📱 What is FIND_IT_BIT?

Every college student has experienced losing or finding things on campus—ID cards, books, headphones, etc. FIND_IT_BIT provides a dedicated platform where:

- Lost items can be reported instantly
- Found items can be uploaded to help others
- Communication is enabled only after secure request approval
- Only verified users with Indian mobile numbers can access sensitive features

---

## 🚀 Key Features

### ✅ College-Exclusive Access
- App is built for **BIT Sindri** students and staff only
- Only Indian mobile numbers are accepted for sign-up
- Authenticated access required for uploads, contact, and chat

### 🔐 Authentication
- Firebase Authentication with:
    - Email & Password
    - Indian phone number verification via OTP
- Guest access allowed (can browse listings only)

### 📤 Upload Lost/Found Items
- Users can upload:
    - Title, description, image(s)
    - Date and location
    - Category (Lost / Found)

### 📄 Contact Request & Approval
- Users must request permission before contacting the uploader
- Request must include:
    - Image proof **OR**
    - Written explanation
- Uploader approves/rejects the request

### 💬 Real-Time Chat with Voice Support
- Chat is enabled only after approval
- Includes:
    - Real-time text messaging via Firestore
    - Voice messages via microphone input

### 👤 User Profiles
- Profile includes:
    - Name, branch, batch, mobile number, profile image
- Data securely stored using Firestore & Cloud Storage

---

## 🛠️ Tech Stack

| Layer               | Technology                              |
|---------------------|------------------------------------------|
| **Frontend**        | Jetpack Compose (Kotlin) |
| **Authentication**  | Firebase Authentication (Email + Phone OTP) |
| **Database**        | Firebase Firestore |
| **Storage**         | Firebase Cloud Storage |
| **Real-time Chat**  | Firestore real-time updates |
| **Voice Messaging** | Android Microphone APIs |

---

## 📂 Project Structure

com.example.finditbit/
│
├── auth/           # Login, Signup, Password creation
├── chat/           # Chat and Voice Messaging
├── data/
│   ├── model/      # AppUser data class
│   └── repository/ # Firebase Repositories
├── lostfound/      # Upload and Listing screens
├── request/        # Request and Approval logic
├── ui/             # Jetpack Compose UI components
└── viewmodel/      # MVVM architecture logic


## 📦 Requirements

- Android Studio Giraffe or later
- Minimum SDK: 21+
- Firebase Project set up with:
    - Authentication (email + phone)
    - Firestore Database
    - Cloud Storage
    - SHA-1 fingerprint added

---

## 🔐 Permissions Used

- **📷 Camera** – Capture item and profile images
- **🎤 Microphone** – Send voice messages in chat

---

## 👨‍💻 Developer Note

This app was developed as a helpful tool **for the students of BIT Sindri**.  
It ensures secure, verified communication and makes it easier to find lost belongings on campus.

---

## 🤝 Contributing

Pull requests and suggestions are welcome!  
Open an issue or fork the project to contribute new features or design improvements.

---

## 📧 Contact

Created by **SuryanshKumar**  
👨‍🎓 B.Tech ECE @ BIT Sindri  
📬 For feedback or collaboration, feel free to reach out!

---

## 🏷️ License

This project is licensed under the **MIT License**.  
Feel free to use, adapt, and build upon it with proper credit.