# SMSS
A simple android app for "encrypting" text messages that I am building for fun.

# Current Status
On Hold

# Motivation

Due to some circumstances, I wanted to learn how to build Android apps. However, I didn't want to simply build an Android app, I wanted to mess with some things that I wouldn't have a reason to with other projects. The ideal goals of this project are:

1) Learn the basics of the Android API. It's never a bad idea to learn a new API every once in a while, especially when it's on a different platform than what I normally work on

2) Interact with features normally associated with phones that I don't usually get to use, such as the NFC features and the ability to send text messages.

3) Learn the basics for SQL for data storage. Most of it is stored in a single table with only four columns, but I still didn't know any SQL before this project.

4) Learn the basics of building and using a RESTful API. The Android API has a system called the ContentProvider inside of it, which, through a RESTful API, provides information about the app and it's services.

# How does it work?

You'll notice that I often put the word "encryption" in quotes, like I just did. This is because this encryption is easily breakable by anyone who understands how this app works and can get access to your app's internal storage. If you're looking for a full-featured difficult-to-break encryption app, I recommend something like TextSecure (https://play.google.com/store/apps/details?id=org.thoughtcrime.securesms). They actually at one point supported the same features as this app, but later removed them for various reasons that you can read on their blog.

If you're still interested, it goes like this: The app gets a sequence of random numbers from the Random.org API (https://www.random.org/clients/http), representing a mapping of an ascii character to another, forming the encryption key. The app then transfers the "encryption key" to another person via Near-Field Communication technology, the thing that allows some apps to send data between two phones by pressing them against each other for a second or two. After that, you send messages to the other person through the app, which get "encrypted" by your shared key, and translated back on the other person's side.

This app does come with some restrictions. For example, you can only have one phone number per key, and vice versa. You can't directly use contacts, though I was considering adding that functionality in the future.

# How to build

You build this app the same way you build any other Android app:

1) Download and install Android Studio (http://developer.android.com/tools/studio/)
   Alternatively, download gradle (https://gradle.org/) and the Android SDK(https://developer.android.com/sdk/installing/index.html?pkg=tools)

2) Build the Gradle project associated with this app, either using Android Studio or gradle.

3) Build the Android project associated with this app

4) You should have the app now
