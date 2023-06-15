# Serfee App

Serfee is an Android application that connects individuals in need with local, kind helpers. The app aims to create a fair and trusted community by facilitating the exchange of paid services. Users can easily request assistance, broadcast their location, and receive offers from nearby helpers. Through the conversation window, users can negotiate prices, reach agreements, and complete transactions. Additionally, the app allows users to provide feedback for the helpers, contributing to the community's growth and improvement.


## Features
- **Login/Logout**: Users can securely log in and log out of their accounts.
- **Register**: New users can create an account to access the app's features.
- **Multi-Language Support**: The app supports English and Indonesian languages for a wider user base.
- **Animations**: Animations are implemented on various pages to enhance the user experience.
- **Task Listing**: Users can view all tasks they have created.
- **Map Integration**: The app includes a map feature that allows users to visualize tasks and their locations.
- **Task Creation**: Users can add tasks by providing a description, image, live location, date, and category of the job.

### Under Development
- **Apply & Accept Job Requests** : Users will be able to apply for job requests and accept offers from helpers.
- **Chat** : Users will be able to communicate with helpers and discuss job details through a chat feature.
- **Feedback** : Users will have the ability to provide feedback on completed tasks, helping the community maintain trust and transparency.

## Screenshots


## Installation

1. Clone the repository: `git clone https://github.com/Serfee/Mobile-Development.git`
2. Open the project in Android Studio.
3. Build and run the application on your Android device or emulator.

## Dependencies
```
dependencies {

    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.8.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'


    implementation "com.squareup.retrofit2:retrofit:2.6.2"
    implementation "com.squareup.retrofit2:converter-gson:2.6.2"
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    implementation "com.github.bumptech.glide:glide:4.11.0"
    implementation 'com.google.android.material:material:1.0.0'


    def roomVersion = '2.4.3'
    implementation "androidx.room:room-runtime:$roomVersion"
    annotationProcessor "androidx.room:room-compiler:$roomVersion"
    androidTestImplementation "androidx.room:room-testing:$roomVersion"
    implementation "androidx.room:room-ktx:$roomVersion"
    kapt "androidx.room:room-compiler:$roomVersion"

    //couroutine
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"


    //Live cycle
    implementation "androidx.datastore:datastore-preferences:1.0.0"
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1'
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.5.1"

    //lottie
    implementation "com.airbnb.android:lottie:3.4.0"
}
```

## Contributing

We welcome contributions from the community! If you would like to contribute to Serfee, please follow these guidelines:

1. Fork the repository and create your branch: `git checkout -b feature/your-feature`
2. Make your changes and test thoroughly.
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature/your-feature`
5. Create a new Pull Request.

Please make sure to adhere to our code of conduct and maintain a respectful and inclusive environment.

## Author
[Akha Balasi](https://github.com/Akha99)