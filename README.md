# Softwareentwurf

## Quick Start Guide

Follow these steps to run the project after cloning the repository:

### Copy the example game settings

- Navigate to the `FHMaze` directory.
- Copy `gameSettings.example.json` and rename the copy to `gameSettings.json`.
- On Windows (PowerShell):

  ```sh
  cp FHMaze/gameSettings.example.json FHMaze/gameSettings.json
   ```

### Build the project

- Run the following command in the project root:

   ```sh
   ./gradlew.bat clean build
   ```

### Run the game

- From the project root, run:

   ```sh
   cd ./FHMaze/ && ./FHMaze.bat
   ```

The game should now start with the default settings.
