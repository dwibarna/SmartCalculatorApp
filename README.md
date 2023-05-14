# SmartCalculatorApp

## Table of contents
* [General info](#general-info)
* [Features](#Features)
* [BuildVariants](#BuildVariants)
* [Technology](#Technology)

## General info
Smart Calculator App is a simple Android application that allows users to capture arithmetic expressions using the built-in camera or by selecting an image from the file system, and calculate the result. The result can be saved with SQlite database(Room) or Encrypt to local storage and viewed on a separate screen.
	
## Features
Project is created with:
* Allows users to capture arithmetic expressions using the built-in camera or by selecting an image from the file system
* Detects and calculates the result of arithmetic expressions in images
* Displays a history of calculation results performed by the user
* Can encrypt the result of the calculator and display it again in a decrypted form.

## BuildVariants
this project can built into 4 differents APK from the source code :
* app-red-filesystem.apk
* app-red-built-in-camera.apk
* app-green-filesystem.apk
* app-green-built-in-camera.apk

## Technology
this project is implementing technologies :
* Kotlin
* Architecure Commponents
* Coroutines
* ML Kit Text Recognition v2
* Dagger Hilt
* MathParser
* Security-Crypto
* Room
* Glide
* Animation
