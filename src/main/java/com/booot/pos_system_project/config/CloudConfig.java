package com.booot.pos_system_project.config;


import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
@Configuration("cloudinaryConfiguration")
public class CloudConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;
    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Bean("cloudinaryClient")
    public Cloudinary cloudinary() {
        Map<String, Object> cloud = new HashMap<>();
        cloud.put("cloud_name", cloudName);
        cloud.put("api_key", apiKey);
        cloud.put("api_secret", apiSecret);

        return new Cloudinary(cloud);
    }

}
