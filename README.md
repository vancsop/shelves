# shelves
- Capture, organize, and analyze snapshots of store shelves.
- The app is using efficientnet-lite0-fp32.tflite TensorFlow model
- Target your camera to the item and the app will automatically identify it
- Pressing the plus button will add it to the database
- You can view your entries by pressing the Catalogue button
- You can export your database in csv by pressing the Export button
- The csv files are created in the Download folder
- The images are created in the Pictures/shelves folder

### Instalation
Requires at least Android 9
```
./gradlew installDebug
```

### Architecture
- [Built with Clean Architecture](https://developer.android.com/topic/architecture)
- [The UI is built entirely with Jetpack Compose](https://developer.android.com/compose)
- [The database is built with Room](https://developer.android.com/training/data-storage/room)

![Architecture](https://developer.android.com/static/topic/libraries/architecture/images/mad-arch-overview.png)
