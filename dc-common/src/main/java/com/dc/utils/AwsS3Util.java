package com.dc.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dc.properties.AwsS3Properties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@AllArgsConstructor
@Data
public class AwsS3Util {
    private String accessKeyId;
    private String secretKeyId;
    private String bucketRegion;
    private String bucketName;

    private AmazonS3 getS3Client() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                accessKeyId,
                secretKeyId
        );
        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.US_WEST_2)
                .build();
        return s3Client;
    }

    private String getKey(String folderName, String originalFileName) {
        return String.format("%s/%s-%s", folderName, UUID.randomUUID().toString(), originalFileName);
    }

    private String getS3Url(String key) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, bucketRegion, key);
    }

    public String upload(String folderName, MultipartFile multipartFile) throws IOException{
        // Write multipartFile to file
        File file = new File("./uploads/" + multipartFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());

        // Upload to s3
        AmazonS3 s3Client = getS3Client();
        String key = getKey(folderName, multipartFile.getOriginalFilename());
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        s3Client.putObject(putObjectRequest);

        // Delete after upload
        file.delete();
        return getS3Url(key);
    }
}
