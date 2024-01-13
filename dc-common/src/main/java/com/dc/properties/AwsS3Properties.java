package com.dc.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "dc.aws")
@Data
@Component
public class AwsS3Properties {
    private String accessKeyId;
    private String secretKeyId;
    private String bucketName;
    private String bucketRegion;
}
