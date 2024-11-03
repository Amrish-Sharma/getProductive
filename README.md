# GetProductive

GetProductive is an Android app designed to help users manage their tasks efficiently. The app provides a simple and intuitive interface for adding, viewing, and managing tasks.

## Features

- **Add New Tasks:** Users can add new tasks by entering the task description and clicking the "Add Task" button.
- **View Pending Tasks:** All newly added tasks are displayed in the "Pending Tasks" section.
- **Mark Tasks as Completed:** Users can mark tasks as completed by toggling the checkbox next to each task. Completed tasks are moved to the "Completed Tasks" section.
- **View Completed Tasks:** Completed tasks are displayed in the "Completed Tasks" section, allowing users to keep track of their progress.
- **Archive Completed Tasks:** Tasks that have been completed for more than 24 hours are automatically moved to the "Archived Tasks" section.
- **View Archived Tasks:** Archived tasks are displayed in a collapsible view using `ExpandableListView`, keeping the interface clean and organized.
- **Persistent Storage:** Tasks are saved using `SharedPreferences`, ensuring that tasks are retained even after the app is closed and reopened.

## Technical Details

- **Language:** Java
- **Framework:** Android
- **Minimum Android Version:** v12
- **Dependencies:** Gson for JSON serialization and deserialization

## Installation

1. Download the APK file from the release page.
2. Install the APK on your Android device.
3. Open the app and start managing your tasks.

## Feedback

We welcome feedback and suggestions for future improvements. Please report any issues or feature requests on our GitHub repository.

Thank you for using GetProductive!
