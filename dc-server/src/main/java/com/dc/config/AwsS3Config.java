package com.dc.config;

import com.dc.properties.AwsS3Properties;
import com.dc.utils.AwsS3Util;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {
    @Bean
    @ConditionalOnMissingBean
    public AwsS3Util awsS3Util(AwsS3Properties awsS3Properties) {
        AwsS3Util awsS3Util = new AwsS3Util(awsS3Properties.getAccessKeyId(), awsS3Properties.getSecretKeyId(),
                awsS3Properties.getBucketRegion(), awsS3Properties.getBucketName());
        return awsS3Util;
    }
}
