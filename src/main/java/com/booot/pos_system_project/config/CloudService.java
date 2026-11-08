package com.booot.pos_system_project.config;

import com.booot.pos_system_project.exception.ResourceNotFoundException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CloudService {
    private final Cloudinary cloudinary;
    public CloudService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map<?,?> uploadFile(MultipartFile file, String folder) {
        try{
            return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder",folder,
                    "resource_type", "image"
            ));

        }catch (IOException e){
            throw new ResourceNotFoundException("File not found" + e.getMessage());
        }
    }

    public String uploadAndGetUrl(MultipartFile file, String folder) {
        return (String) uploadFile(file, folder).get("secure_url");
    }
    public List<String> uploadMany(List<MultipartFile> files, String folder) {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            urls.add(uploadAndGetUrl(file, folder));
        }
        return urls;
    }

    public void delete(String publicId){
        try{
            cloudinary.uploader().destroy(publicId,  ObjectUtils.asMap());
        }catch (IOException e){
            throw new ResourceNotFoundException("File not found" + e.getMessage());
        }
    }

    public void validate(MultipartFile file){
        if(file.isEmpty() || file == null){
            throw new IllegalArgumentException("File cannot be empty");
        }
        String type = file.getContentType();
        if(type == null || !type.startsWith("image/")){
            throw new IllegalArgumentException("Invalid file type");
        }
    }
}
