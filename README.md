# SoCize - File Encryption App

**Final Year Project in [Asia Pacific University](https://apspace.apu.edu.my/)**  
This project is a module for social media applications, it aims to address privacy and security concern that comes when sharing data in a social media application. It focuses on encrypting files before uploading them to a server, simulating secure file sharing and storage. The app provides an interface to encrypt/decrypt files locally and an interface to interact with a file storage server.

## Table of Contents
- [Introduction](#introduction)
    - [Features](#features)
- [Installation Guide](#installation-guide)
    - [Prerequisites](#prerequisites)
    - [Steps to run the application](#steps-to-run-the-application)
    - [Running Tests](#running-tests)
- [Contributors](#contributors)

## Introduction
This application simulates a secure file handling system for social media platforms. It enables users to:
- Encrypt a file locally before uploading it to a server.
- Decrypt an encrypted file after retrieving it from the server (Only if the user have the correct encryption key).

The goal is to highlight how data can be protected before being sent to servers for storage, ensuring that only the intended recipients are able to read the contents of a file, which doesn't include the server. This way, privacy and security concerns can be addressed as the server will not be able to understand the contents of the file.

### Features
- **File Encryption**: Select a file to encrypt and a folder to save the encrypted file and the generated encryption key.
- **File Decryption**: Decrypt an encrypted file using the correct encryption key.
- **File Storage Server Interface**: Simulate interacting with a file storage server to upload, download, and delete files.

## Installation Guide
Follow these steps to setup the project locally.

### Prerequisites
- Java 21+ (The project uses external libraries such as JavaFX and some java features)
- Maven (For managing dependencies and building the project)

Make sure you have Java 21+ installed and configured in your environment. You can check your Java version by running:
```console
java -version
```

### Steps to run the application

1. **Clone the repository**
```console
git clone https://github.com/YT-07/SoCize-Client.git
```

2. **Navigate to the project folder**
```console
cd SoCize-Client
```

3. **Build the project**  
Run the following Maven command to clean and package the application into a fat JAR.

```console
mvn clean package
```

4. **Identify the correct JAR file**
After building, Maven will generate two JAR files in the `target` folder

- One starting with `original` (this is the base JAR and does not include all dependencies).
- One without `original` in its name (this is the fat JAR and includes all the necessary dependencies).

**IMPORTANT**: **Do not** run the JAR starting with `original`. Instead, run the JAR file that does not start with `original` to ensure all dependencies are included. 

5. **Run the application**  
After building, you can run the JAR file with the following command. (Replace `{name-of-jar-file}` with the actual path to the generated JAR file)

```console
java -jar target/{name-of-jar-file}.jar
```

This will start the application.

### Running Tests
If you want to run tests (unit tests or integration tests), use the following Maven command.
```console
mvn clean test
```
This will run the tests defined in the project.

## Contributors
This project was developed as part of a university assignment. The following individuals contributed to the development of this project:

- **Yen Tze** - Main Developer  
[Github Profile](https://github.com/YT-07)

- **Shatchiin** - UI Designer  
[Github Profile](https://github.com/ItzJustPercy)