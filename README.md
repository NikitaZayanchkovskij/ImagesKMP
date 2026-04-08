# Inspirational Images

### App's description: 📱
🌅 🏞️ Find inspirations, according to your mood, so you can relax, or create better. 😌

Used API: [Pexels API docs](https://www.pexels.com/api/documentation/)

👨‍💻 Created by Nikita Zayanchkovskij, using Kotlin and Compose Multiplatform, for Android and Ios.\
📧 Email: zayanchkovskij.nikita@gmail.com

P.S. Work is still in progress, this is not the final state of the app. I will add more functionality, tests, etc.\
P.P.S. If you want to launch and test the app - you need an API key. 🔑

### Video demo: [watch on YouTube](https://youtu.be/I_tUvr1YWic?si=ka8YtYP3AW9XA9PJ) 👀
### Screenshots are bellow: [App's screenshots](#apps-screenshots)

## Technologies, used in the project: 💻
1) Kotlin Multiplatform for the shared business logic.
2) Compose Multiplatform and Material 3 for the UI.
3) Gradle 9.
4) Clean Code and Clean Architecture.
5) ViewModel and MVI pattern, with actions and events.
6) Navigation Compose.
7) Ktor client for networking.
8) Kotlin Serialization for converting Kotlin objects, to data formats, like JSON and vice-versa.
9) Koin for dependency injection.
10) Kotlin Coroutines and Flows.
11) Room for the database.
12) Coil image loading library.
13) Kermit for multiplatform logging.
14) Light and dark theme support.
15) English and Russian localization.

## Project structure: 
I don't use multi-modules here, because this is a relatively simple app, and it would be an overkill for this project.\
The whole app is a single module. (P.S. But I have an experience with multi-modules.)

Instead - I have specified packages, as if they were modules.\
So, if at some point, I will decide, that I need multi-module - then, it will be much easier to migrate.

<ins>With the real multi-module I would use a "Feature-Modules" approach:</ins>
1) "core" root package, with 3 modules inside: data, domain and presentation.
2) "features" root package, with, for example, "authorization" and "listAndDetails" packages inside.
   - Then "authorization" package would contain 3 modules inside: data, domain and presentation.
   - And "listAndDetails" package would contain 3 modules inside: data, domain and presentation.
3) And so on...

At the screenshot bellow you can see, what I'm talking about:

<img width="425" height="808" alt="ProjectStructure" src="https://github.com/user-attachments/assets/15518bb4-7b1c-4469-bfb1-4167e335d2c9" />

## App's screenshots:

#### Main Home screen:

<img width="796" height="821" alt="1MainHomeTabCurated" src="https://github.com/user-attachments/assets/484e2fd6-077c-45f1-8e88-5713e3a81908" />

<img width="796" height="821" alt="2NatureTab" src="https://github.com/user-attachments/assets/b3f0a8b5-c5e1-43d7-b977-bec6d47e0f11" />

<img width="796" height="821" alt="3OceanTab" src="https://github.com/user-attachments/assets/3ec6c844-9c9a-4977-863f-18b6e6854668" />

#### Details screen:

<img width="796" height="821" alt="4DetailsScreen" src="https://github.com/user-attachments/assets/a4859980-d49d-4324-b822-e82253c9f22e" />

#### Search screen:

<img width="796" height="821" alt="5SearchScreen" src="https://github.com/user-attachments/assets/5ec97370-bcb2-4608-b291-adfad70908e6" />

#### Bookmarks screen:

<img width="796" height="821" alt="6BookmarksScreen" src="https://github.com/user-attachments/assets/7a811440-ef7b-437d-afaf-eb796a8e4d3f" />\

Thank you, for your review.

📧 Email: zayanchkovskij.nikita@gmail.com
