# Screen and Mic recorder.

## Brief project description

This is a Java class named "ScreenRecorder". It uses the Java AWT (Abstract Window Toolkit) and javax libraries to capture the screen and record audio from the user's computer.

The constructor of the class takes two parameters: a File object that represents the output file where the recorded video will be saved, and a String that specifies the format of the output video file.

The class has a method called "startRecording" which does the actual screen and audio recording. The method starts by opening the audio line, creating a video writer, and creating a video output stream. It then loops for 0 seconds, capturing the screen and audio data for each frame. It writes the video frames to the output stream and the audio data to the same output stream.

After 30 seconds, the method finishes the video recording, disposes of the writer, and closes the output stream. It then saves the recorded video file to the specified output file. Finally, it stops and closes the audio line.

Overall, this class provides a simple way to capture the user's screen and record audio from the computer in Java.

And then used AWS credential to upload this generated video after compressing it.





## Prerequisites

- Java 8 or higher
- Maven 3.5 or higher

## Getting Started

These instructions will guide you through setting up the project on your local machine for development and testing purposes.

1. Clone the repository and open the folder in IntelliJ.
   #### git clone https://github.com/Malaydewangan09/recorder.git

2. Start the Application
   #### Run the DemoApplication.java file.

3. Open your web browser and navigate to http://localhost:8080/upload. 

4. Press record button to record and stop button to stop recording.

5. Video will be recorded for 10 seconds (customizable)

6. Go to your AWS account and check screenrecordingtest bucket you will see files getting uploaded there.


