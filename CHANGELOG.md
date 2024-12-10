# Changelog

## v1.0.0

**Implementation of 22 Levels:**

- Button in the middle of the level  
- Two buttons (use both fingers)  
- Long press on a button  
- Rotate the phone to make the button appear  
- Airplane mode  
- Multiple dropdown menus  
- Use 10 fingers  
- Scroll to find the button lower down  
- Hidden button on the homepage  
- Black screen until the phone’s flashlight is turned on to reveal the button  
- Plug in the phone  
- Change the phone's language  
- Dark/Light modes  
- Button that moves when clicked, requiring the phone to be shaken to stop it  
- Screenshot  
- Step counter  
- Button hidden in the hints  
- Maze  
- Blackout (change the environment’s lighting)  
- Turn off the phone  
- Do nothing for a set amount of time  

**Removal:**  
- Removed the level requiring blowing into the microphone.

**Features:**

- Commenting the code  
- Renaming all level files and functions to replace numeric names (e.g., `Level_01.kt`) with descriptive names (e.g., `SimpleButton.kt`) to follow naming conventions and improve organization.  
- Reordering levels by difficulty  
- Designing a logo and adding it as the application logo  
- Numbering and spacing the levels on the homepage  
- Adding gameplay instructions to the homepage  
- Reformatting the repository to include a README and project license  
- Implementing haptic feedback  
- Fixing minor bugs related to hint colors and settings icon display.
- Background music  
- Audio feedback when pressing buttons, with distinct sounds for a regular press and for a press that successfully completes a level.  
- Backend implementation for settings buttons, enabling return to the menu and toggling sound and music on/off.  
- Writing hints for each level and storing them in an appropriate structure.  
- Translating the entire UI into 5 languages: English, French, Spanish, Portuguese, and Czech.  
- Storing all settings data (music, volume, completed levels) in a `PreferencesDataStore` for persistence.  
