package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sound.sampled.LineUnavailableException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.UUID;

@Controller
public class UploadController {

    @GetMapping("/upload")
    public String getUploadPage() {
        return "upload.html";
    }

    @PostMapping("/startRecording")
    @ResponseBody
    public ResponseEntity<String> handleVideoUpload() throws LineUnavailableException, AWTException, IOException {
        // Handle the video upload here
        System.setProperty("java.awt.headless", "false");
        System.out.println(GraphicsEnvironment.getLocalGraphicsEnvironment());

        System.out.println(GraphicsEnvironment.isHeadless());
        String name = UUID.randomUUID().toString();

        File file = new File(name);

        DemoApplication1 demoApplication = new DemoApplication1();

        ScreenRecorder recorder = new ScreenRecorder(file, "mp4");
        recorder.startRecording();
        System.out.println("Video uploaded!");
        return ResponseEntity.ok("Video uploaded successfully");
    }
        @PostMapping("/stopRecording")
        @ResponseBody
        public ResponseEntity<String> handleVideostop() throws LineUnavailableException, AWTException, IOException {
            // Handle the video upload here






        System.out.println("Video stopped!");
        return ResponseEntity.ok("Video stopped successfully");
    }
}
