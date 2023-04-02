package com.example.demo;

import javax.imageio.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.awt.GraphicsEnvironment;
public class ScreenRecorder {

    public Robot robot;
    public Rectangle screenRect;
    public AudioFormat audioFormat;
    public TargetDataLine audioLine;
    public File outputFile;
    public String outputVideoFormat;

    public ScreenRecorder(File outputFile, String outputVideoFormat) throws AWTException, LineUnavailableException {
        this.robot = new Robot();
        this.screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        this.audioFormat = new AudioFormat(44100, 16, 2, true, true);
        this.audioLine = AudioSystem.getTargetDataLine(audioFormat);
        this.outputFile = outputFile;
        this.outputVideoFormat = outputVideoFormat;
    }

    public void startRecording() throws IOException, LineUnavailableException {
        // Start audio recording
        audioLine.open(audioFormat);
        audioLine.start();

        // Create video writer
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
        if (!writers.hasNext()) {
            throw new IllegalStateException("No writers found");
        }
        ImageWriter writer = writers.next();
        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writeParam.setCompressionQuality(0.7f);

        // Create video output stream
        ByteArrayOutputStream videoOutputStream = new ByteArrayOutputStream();
        writer.setOutput(ImageIO.createImageOutputStream(videoOutputStream));
        writer.prepareWriteSequence(null);

        // Record video and audio for 30 seconds
        long startMillis = System.currentTimeMillis();
        while (System.currentTimeMillis() - startMillis <= 10000) {
            // Capture screen
            BufferedImage image = robot.createScreenCapture(screenRect);

            // Write video frame to output stream
            IIOImage iioImage = new IIOImage(image, null, null);
            writer.writeToSequence(iioImage, writeParam);

            // Capture audio
            byte[] audioData = new byte[audioLine.getBufferSize() / 5];
            int bytesRead = audioLine.read(audioData, 0, audioData.length);

            // Write audio data to output stream
            videoOutputStream.write(audioData, 0, bytesRead);
        }

        // Finish video recording
        writer.endWriteSequence();
        writer.dispose();
        videoOutputStream.close();

        // Save output file
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        fileOutputStream.write(videoOutputStream.toByteArray());
        fileOutputStream.close();

        // Stop audio recording
        audioLine.stop();
        audioLine.close();
    }



    }


