# **🎓 FIND_IT_BIT – Lost & Found App for BIT Sindri**

**FIND_IT_BIT** is a secure, campus-exclusive Lost & Found platform built for students and staff of **BIT Sindri**.  
It enables verified reporting of lost items, uploading found belongings, and managing requests safely—ensuring trust and authenticity across the campus.

> 🔐 **Access restricted to verified Indian mobile numbers**  
> 🏫 **Designed exclusively for BIT Sindri**

---

## **📱 What is FIND_IT_BIT?**

FIND_IT_BIT provides a structured, secure way to handle lost and found activity on campus:

- A central hub for Lost/Found item uploads  
- Verified identity-based access  
- Proof-based request system (no chat, no voice)  
- A trustworthy and organized item recovery process  

It eliminates spam, false claims, and scattered information.

---

## **🚀 Key Features**

### **🎓 Campus-Exclusive Access**
- Only verified **Indian phone numbers** can register.  
- Ensures authenticity and prevents outsiders from using the platform.

---

## **🔐 Multi-Layer Authentication & Security**

### **✔ JWT Access + Refresh Tokens**
The backend uses a robust token-based system:
- **Access Token** — Short-lived, used for API calls  
- **Refresh Token** — Long-lived, regenerates access tokens securely  

Benefits:
- Strong protection against session hijacking  
- Minimal exposure window if a token is compromised  
- Refreshing tokens keeps sessions smooth and secure  

---

### **✔ Rate Limiting on Sensitive Endpoints**
To protect the system from misuse:
- Login attempts  
- OTP verifications  
- Contact request submissions  

…are all strictly rate-limited.  
This prevents brute-force attempts, spam, and overload attacks.

---

### **✔ Strict Permission Control**
Only verified users can:
- Upload items  
- Submit contact requests  
- Approve or reject proofs  

Guest users can browse but **cannot** interact with sensitive features.

---

## **📤 Post Lost or Found Items**

Users can upload:
- Item title  
- Description  
- Images (multiple)  
- Date & approximate location  
- Category: Lost / Found  

This ensures that every listing is complete, helpful, and easy to browse.

---

## **📄 Proof-Based Contact Request System (No Chatting)**

Instead of chat or voice messaging, the app uses a **secure request workflow**:

### **How it works:**
1. A user sends a **contact request** with either:  
   - Photo proof  
   - Written explanation  
2. The uploader reviews the request.  
3. They can:  
   - **Approve** → The requester receives secure contact details  
   - **Reject** → Prevents misuse or fake claims  

This system ensures legitimacy and avoids unnecessary communication.

---

## **👤 User Profiles**

Each verified user has a structured profile:
- Name  
- Branch & batch  
- Mobile number  
- Profile image  

Stored securely using Firebase Authentication + Firestore + Cloud Storage.

---

## **🛠️ Tech Stack**

| **Layer**             | **Technology**                          |
|-----------------------|------------------------------------------|
| **Authentication**    | Firebase Auth (Email + Phone OTP)        |
| **Authorization**     | JWT (Access + Refresh Tokens)            |
| **Security**          | Rate Limiting, Token Validation          |
| **Database**          | Firebase Firestore                       |
| **Storage**           | Firebase Cloud Storage                   |

---

## **📂 Project Structure**

com.example.finditbit/
├── auth/ # Login, Signup, OTP workflows
├── data/
│ ├── model/ # AppUser & item data classes
│ └── repository/ # Firebase + backend repositories
├── lostfound/ # Uploading & listing Lost/Found items
├── request/ # Contact request & approval system
├── ui/ # Reusable Compose UI components
└── viewmodel/ # MVVM architecture logic


---

## **📦 Requirements**

- Android Studio **Giraffe** or later  
- Minimum SDK: **21+**  
- Firebase services configured:  
  - Authentication  
  - Firestore  
  - Cloud Storage  
  - SHA-1 fingerprint  

---

## **🔐 Permissions Used**

- **📷 Camera** – For uploading item & profile images  
*(No microphone needed — chat/voice features removed)*

---

## **👨‍💻 Developer Note**

This application is built with a strong focus on **security**, **verification**, and **smooth user experience** for the BIT Sindri community.  
Every feature—from JWT token flow to request approval—is designed to ensure **fairness**, **trust**, and **zero misuse**.

---

## **🤝 Contributing**

Contributions and suggestions are welcome!  
Open an issue, submit a PR, or fork the repository.

---

## **📧 Contact**

**Suryansh Kumar**  
🎓 B.Tech ECE @ BIT Sindri  

---

## **🏷️ License**

Licensed under the **MIT License**.

