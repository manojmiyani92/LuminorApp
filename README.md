# Luminor demo project

## Objective
This is a demo project that provides functionality for user authentication.
It includes features for user login and registration within the app.

## Architecture
Project used clean code architecture with MVVM design pattern with compose UI

**luminorcore:** This layer exposed auth related interface to the app.

**infrastructure:** This layer contains the common utils and models.

**repository:** This layer deals with data mapping and data persisting.

![](/src/arch.png "Architecture")

## Login use-case
![](/src/login.png "Login use-case")

## User Registration use-case
![](/src/register.png "Register use-case")

## Library used in this project
* Hilt(For dependency injection)
* RoomDatabase(For local data storage)
* Jetpack Navigation(For navigation)
* Jetpack compose(For UI)

## Test cases
* [AuthViewModelTest.kt](app/src/test/java/com/luminor/interviewtest/screen/AuthViewModelTest.kt)
* [AuthProfileImplTest.kt](luminorcore/src/test/java/com/luminor/core/impl/AuthProfileImplTest.kt)

## Prerequisite
* Android Studio 2024.1.2 or above
* JAVA 17
* Minimum android api level 24 or above