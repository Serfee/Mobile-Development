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

## API Documentation

```kotlin
interface ApiService {

    /**
     * Registers a new user.
     *
     * @param request The registration request body.
     * @return The base response containing the result of the registration.
     */
    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): BaseResponse


    /**
     * Logs in a user.
     *
     * @param request The login request body.
     * @return The login response containing the user's authentication token.
     */
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    /**
     * Retrieves a list of stories based on the specified page, size, and category.
     *
     * @param token The user's authentication token.
     * @param page The page number to retrieve.
     * @param size The number of stories to retrieve per page.
     * @param category The category of stories to retrieve.
     * @return The response containing the list of stories.
     */
    @GET("tasks")
    suspend fun getStory(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("category") category: String
    ): StoryResponse

    /**
     * Retrieves a list of stories based on the user's current location.
     *
     * @param token The user's authentication token.
     * @param location The user's current location.
     * @return The response containing the list of stories.
     */
    @GET("tasks")
    suspend fun getStoryLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int = 1,
    ): StoryResponse

    /**
     * Retrieves a list of available categories.
     *
     * @param token The user's authentication token.
     * @return The response containing the list of categories.
     */
    @GET("tasks/category")
    suspend fun getCategories(
        @Header("Authorization") token: String
    ): CategoryResponse

    /**
     * Adds a new story.
     *
     * @param token The user's authentication token.
     * @param file The file representing the story (e.g., image).
     * @param description The description of the story.
     * @param lat The latitude of the story location.
     * @param lon The longitude of the story location.
     * @param category The category of the story.
     * @return The base response containing the result of the story addition.
     */
    @Multipart
    @POST("tasks/request")
    suspend fun addStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody? = null,
        @Part("lon") lon: RequestBody? = null,
        @Part("category") category: RequestBody,
    ): BaseResponse

    /**
     * Applies for a job request.
     *
     * @param token The user's authentication token.
     * @param request_id The ID of the job request to apply for.
     * @return The base response containing the result of the job application.
     */
    @FormUrlEncoded
    @POST("tasks/response")
    suspend fun applyJob(
        @Header("Authorization") token: String,
        @Field("request_id") request_id: Int,
    ): BaseResponse
}
```


## Model
### Story

```kotlin
@Parcelize
data class Story(
    @SerializedName("request_id")
    val id: Int,
    @SerializedName("full_name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("image_url")
    val photoUrl: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("location_latitude")
    val lat: Double,
    @SerializedName("location_longitude")
    val lon: Double
): Parcelable
```

This model represents a story in the application.

Properties:
- `id`: The ID of the story.
- `name`: The full name of the user who created the story.
- `description`: The description of the story.
- `photoUrl`: The URL of the story's image.
- `createdAt`: The creation date of the story.
- `lat`: The latitude of the story's location.
- `lon`: The longitude of the story's location.

---

### Login

```kotlin
data class Login(
    @SerializedName("userId")
    val userId: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("token")
    val token: String?
)
```

This model represents the login response from the API.

Properties:
- `userId`: The ID of the logged-in user.
- `name`: The name of the logged-in user.
- `token`: The authentication token for the logged-in user.

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