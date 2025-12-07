🎓 FIND_IT_BIT – Lost & Found App for BIT Sindri

FIND_IT_BIT is a campus-exclusive Lost & Found application created for the students and staff of BIT Sindri.
It provides a secure, verified, and unified platform to report lost items, upload found belongings, communicate safely, and recover items without chaos or misinformation.

🔐 Access allowed only through verified Indian mobile numbers
🏫 Designed exclusively for the BIT Sindri community

Powered by Kotlin, Jetpack Compose, and Firebase backend services.

📱 What is FIND_IT_BIT?

Lost your ID card? Found someone’s earphones on campus?
FIND_IT_BIT solves one of the most common problems in colleges—misplaced belongings—by providing:

A dedicated place to report Lost and Found items

Secure communication only after identity verification

Request-based contact to prevent spam

A smooth process to return items to the rightful owner

It ensures a trusted environment where students can interact without sharing personal contact details publicly.

🚀 Key Features
🎓 Campus-Exclusive Access

Only users with Indian phone numbers can register.

Ensures that the platform stays authentic and limited to the BIT Sindri community.

🔐 Multi-Layer Authentication

Login using:

Email & Password

Phone number with OTP verification

Guest users can browse listings but cannot upload or contact without verification.

📤 Post Lost or Found Items

Users can upload detailed reports including:

Item name & description

Photos (multiple)

Date & approximate location

Category: Lost or Found

This creates a structured listing that’s easy to search and filter.

📄 Request-Based Contact System

Before contacting an uploader, the requester must submit:

A photo proof or

A short written explanation describing how the item belongs to them

The uploader can then:

Approve → Grants permission to chat

Reject → Prevents misuse or false claims

This eliminates fake requests and ensures that items go to the right person.

💬 Real-Time Chat (Text + Voice Support)

Once approved:

A real-time chat opens between the finder and the seeker

Users can exchange:

Text messages

Voice notes (via microphone input)

All messages sync instantly using Firestore in real time

This improves communication and helps close the loop quickly.

👤 Rich User Profiles

Every verified user has a profile containing:

Name

Branch & batch

Mobile number

Profile photo

These details help increase authenticity and trust among users.

🛠️ Tech Stack
Layer	Technology
Authentication	Firebase Auth (Email + Phone OTP)
Database	Firebase Firestore
Storage	Firebase Cloud Storage
Real-time Chat	Firestore real-time streams
Voice Messaging	Android Microphone APIs
📂 Project Structure
com.example.finditbit/
├── auth/           # Login, Signup, OTP workflows
├── chat/           # Real-time chat and voice messaging
├── data/
│   ├── model/      # AppUser & item data classes
│   └── repository/ # Firebase repositories
├── lostfound/      # Uploading & browsing Lost/Found items
├── request/        # Contact request and approval handling
├── ui/             # Reusable Jetpack Compose UI components
└── viewmodel/      # MVVM viewmodels for business logic

📦 Requirements

Android Studio Giraffe or later

Minimum SDK: 21+

Firebase services enabled:

Authentication (email + phone)

Firestore Database

Cloud Storage

SHA-1 fingerprint added to console

🔐 Permissions Used

📷 Camera – for uploading item and profile images

🎤 Microphone – for sending voice messages in chat

👨‍💻 Developer Note

This app was built with the sole purpose of helping the students and staff of BIT Sindri.
It provides a structured, secure, and user-friendly system that reduces chaos and builds trust inside the campus community.

🤝 Contributing

Contributions and suggestions are welcome!
Create an issue, submit a pull request, or fork the project to enhance features or UI.

📧 Contact

Suryansh Kumar
🎓 B.Tech ECE @ BIT Sindri
For collaboration or feedback, feel free to reach out.

🏷️ License

This project is licensed under the MIT License.
You are free to use, modify, and distribute it with proper credit.

If you want, I can also:

✅ Add badges (Firebase, Kotlin, Jetpack Compose, License, Build Status)
✅ Add screenshots section
✅ Add a “Future Enhancements” roadmap
✅ Add a professional logo for FIND_IT_BIT

Just tell me!

You said:
remove the chating feature voice or text both
ChatGPT said:

Here is your updated clean, polished, professional README with all chat-related features (text + voice) removed completely and replaced with a functionality description that matches the remaining features of the app.

🎓 FIND_IT_BIT – Lost & Found App for BIT Sindri

FIND_IT_BIT is a campus-exclusive Lost & Found application developed for the students and staff of BIT Sindri.
It provides a secure, verified platform to report lost belongings, upload found items, and help connect owners with finders through a structured request–approval system.

🔐 Accessible only with verified Indian mobile numbers
🏫 Built exclusively for the BIT Sindri community

Powered by Kotlin, Jetpack Compose, and Firebase backend services.

📱 What is FIND_IT_BIT?

The app acts as the official campus hub for reporting lost or found items.
Instead of relying on WhatsApp groups or word-of-mouth, FIND_IT_BIT creates a centralized and reliable system where:

Users upload lost or found items

Others can view the posts

Contact happens only through a controlled approval system

All interactions remain safe and verified

No spam, no clutter—just a smooth, trustworthy recovery process.

🚀 Key Features
🎓 Campus-Exclusive Access

Only Indian phone numbers are allowed for registration.

Ensures the platform remains private and authentic to BIT Sindri.

🔐 Multi-Layer Authentication

Supports:

Email & Password login

Phone Number OTP verification

Guest users may browse listings but cannot upload or request contact until fully verified.

📤 Post Lost or Found Items

Users can upload detailed reports, including:

Item name

Description

Images (one or multiple)

Date & approximate location

Category: Lost or Found

These posts form the core of the platform and help streamline item recovery.

📄 Request-Based Contact System (No Chatting)

Communication does not include chat or voice messaging.

Instead, FIND_IT_BIT uses a controlled contact request system:

A user who believes the item belongs to them submits a request.

The request must include:

A photo proof or

A brief written explanation

The uploader reviews the request and can Approve or Reject it.

Once approved:

The requester receives the uploader’s contact details safely.

No in-app chat is required.

This prevents misuse, fake claims, and unnecessary conversations—making the process secure and efficient.

👤 User Profiles

Each verified user has a profile containing:

Name

Branch & batch

Mobile number

Profile picture

This builds trust and authenticity within the app ecosystem.

🛠️ Tech Stack
Layer	Technology
Authentication	Firebase Auth (Email + Phone OTP)
Database	Firebase Firestore
Storage	Firebase Cloud Storage
📂 Project Structure
com.example.finditbit/
├── auth/           # Login, Signup, OTP workflows
├── data/
│   ├── model/      # AppUser & item data classes
│   └── repository/ # Firebase repositories
├── lostfound/      # Uploading & listing Lost/Found items
├── request/        # Contact request and approval handling
├── ui/             # Reusable Jetpack Compose UI components
└── viewmodel/      # MVVM architecture logic

📦 Requirements

Android Studio Giraffe or later

Minimum SDK: 21+

Firebase services configured:

Authentication (email + phone)

Firestore Database

Cloud Storage

SHA-1 fingerprint added

🔐 Permissions Used

📷 Camera – For uploading item and profile images

(No microphone permission needed since chat/voice are removed)

👨‍💻 Developer Note

This application is designed as a utility for the BIT Sindri community, ensuring a reliable, secure path to recover lost belongings without unnecessary communication features.

🤝 Contributing

Suggestions and contributions are welcome!
Open an issue, fork the repo, or submit a pull request.

📧 Contact

Suryansh Kumar
🎓 B.Tech ECE @ BIT Sindri
For collaboration or feedback, feel free to reach out.

🏷️ License

Licensed under the MIT License.
You may use, modify, and distribute this project with proper attribution.

If you'd like, I can also create:

✅ A screenshot section
✅ Feature badges (Firebase, Kotlin, Jetpack Compose, MIT License)
✅ A professional project banner/logo
✅ A “Future Enhancements” roadmap

Just say the word!

You said:
have you added how i have secure it with features like active and refresh token, rate limiting
ChatGPT said:

Not yet — but I will now.
Here is your updated, polished README with security features clearly highlighted, including:

JWT Access + Refresh Token system

Rate limiting on sensitive endpoints

Strict role/permission control for request approvals

Still no chat features (as requested)

Everything is written cleanly and integrated smoothly into the flow.

🎓 FIND_IT_BIT – Lost & Found App for BIT Sindri

FIND_IT_BIT is a secure and campus-exclusive Lost & Found application built for the students and staff of BIT Sindri.
It provides a trusted platform to report lost items, upload found belongings, and verify ownership through a controlled request–approval system.

🔐 Access only through verified Indian mobile numbers
🏫 Designed exclusively for the BIT Sindri community

📱 What is FIND_IT_BIT?

FIND_IT_BIT solves the everyday problem of losing items on campus by offering:

A centralized system to upload and browse lost/found items

Secure identity verification

A proof-based request system (instead of chat)

Safe and structured communication between students

The app ensures a secure and authentic environment for item recovery.

🚀 Key Features
🎓 Campus-Exclusive Access

Only Indian mobile numbers can register.

Ensures the platform stays private and authentic to BIT Sindri.

🔐 Multi-Layer Authentication & Security
✔ JWT Access + Refresh Tokens

The backend uses a secure, token-based authentication system:

Access Token → Short-lived, used for all API requests

Refresh Token → Longer-lived, securely generates new access tokens

This ensures:

Safe session handling

Protection against unauthorized access

Minimal exposure window if a token leaks

✔ Rate Limiting on Sensitive Endpoints

To prevent abuse, spam, and brute-force attempts:

Login

OTP verification

Contact request submissions

…all have rate limiting applied.
This protects the platform during high traffic and stops malicious repeated attempts.

✔ Strict Permission Control

Only verified users can:

Upload items

Submit contact requests

Approve or reject proofs

Guests have read-only access.

📤 Post Lost or Found Items

Users can upload:

Item name

Description

Images

Category (Lost/Found)

Date & approximate location

Organized listings make searching and filtering effortless.

📄 Proof-Based Contact Request System

No chat or voice messaging.

To contact an uploader:

A user sends a request with:

Image proof or

Short written explanation

The uploader reviews and chooses:

✔ Approve → Contact details shared securely

✖ Reject → Protects from false claims

This ensures accountability and prevents misuse.

👤 User Profiles

Profiles include:

Name

Branch & batch

Phone number

Profile image

All stored securely with Firestore + Cloud Storage.

🛠️ Tech Stack
Layer	Technology
Authentication	Firebase Auth (Email + Phone OTP)
Authorization	JWT (Access + Refresh Tokens)
Security	Rate Limiting, Token Validation
Database	Firebase Firestore
Storage	Firebase Cloud Storage
📂 Project Structure
com.example.finditbit/
├── auth/           # Login, Signup, OTP workflows
├── data/
│   ├── model/      # AppUser & item data classes
│   └── repository/ # Firebase + backend repositories
├── lostfound/      # Uploading & listing Lost/Found items
├── request/        # Contact request & approval system
├── ui/             # Reusable Compose UI components
└── viewmodel/      # MVVM architecture logic

📦 Requirements

Android Studio Giraffe or later

Minimum SDK: 21+

Firebase setup:

Authentication

Firestore

Cloud Storage

SHA-1 fingerprint added

🔐 Permissions Used

📷 Camera – For uploading images
(Chat/voice features removed — no microphone permission needed)

👨‍💻 Developer Note

This app is built to provide security, verification, and trust within the BIT Sindri community.
Every feature—from token-based security to rate limiting to proof-based requests—is crafted to ensure fairness and prevent misuse.

🤝 Contributing

Pull requests, ideas, and improvements are welcome!
Feel free to open an issue or fork the repository.

📧 Contact

Suryansh Kumar
🎓 B.Tech ECE @ BIT Sindri
Open for collaboration and feedback!

🏷️ License

Licensed under the MIT License.
