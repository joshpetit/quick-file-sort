# QFS
A download manager/File organizer built with Java that makes use of file extensions to put them in user-specified locations. One use would be to use this to organize your downloads folder; set all the paths for the extensions needed, set a hotkey to run the quickfilesort jar, and every time you download something you can easily press a few keys to move it.

# Installation
All that is needed is the quickfilesort jar file. This is available under releases. The qfsmanager is optional but is a GUI based approach. Note if you are running on Ubuntu desktop or other Linux desktop machines, you may need to run these files from the command line or enable opening with Java for the file's properties

# Features/Utilization
Before using, ensure that the quickfilesort jar has a settings.conf in the folder, formatted with the needed fields. Use the qfsmanager to create these settings with a GUI, or simply make the file with this format:


```
path=[Path to Folder to be Managed/Organized]
.ext=[Path To Destination]
.ext_=[Path To Destination]
.txt=[Path To Destination]
```

Note that putting an underscore at the end of an extension will remove the extension after moving. For example, files.txt.folder1_  would be moved then renamed to the folder then renamed to files.txt
