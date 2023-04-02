package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class DemoApplication1 {
    private static final String AWS_ACCESS_KEY = "AKIAQ2P7JUNICA55YKVH";
    private static final String AWS_SECRET_KEY = "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";
    private static final String BUCKET_NAME = "screenrecordingtest";
    //private static final String FILE_NAME = "src/main/resources/test.mp4";


    public void upload(File file) throws IOException {
        String FILE_NAME = file.getName();
        String COMPRESSED_FILE_NAME = FILE_NAME+".avi";

        // Compress the file
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(COMPRESSED_FILE_NAME));
        // FileOutputStream fos = new FileOutputStream(outputDirectoryPath + File.separator + inputFile.getName().replace(".avi", ".mp4"));

        FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
        zipOutputStream.putNextEntry(new ZipEntry(FILE_NAME));
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fileInputStream.read(bytes)) >= 0) {
            zipOutputStream.write(bytes, 0, length);
        }
        zipOutputStream.close();
        fileInputStream.close();


        // Upload the compressed file to S3
        BasicAWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://s3.us-east-2.amazonaws.com", "us-east-2"))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
        File compressedFile = new File(COMPRESSED_FILE_NAME);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(compressedFile.length());
        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, COMPRESSED_FILE_NAME, compressedFile);
        request.setMetadata(metadata);
        s3Client.putObject(request);
        compressedFile.delete();
        System.out.println("File uploaded successfully!");



    }
}
