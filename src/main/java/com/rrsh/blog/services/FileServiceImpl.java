package com.rrsh.blog.services;

import com.rrsh.blog.model.PostDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Log4j2
public class FileServiceImpl implements FileService{
    @Autowired
    private PostService postService;
    @Override
    public String uploadImage(@NotNull String path, @NotNull MultipartFile file) throws IOException {
        log.info("Uploading image");
        // file name
        @NotNull
        String fileName = file.getOriginalFilename();
        //Random name generator
        String randomId = UUID.randomUUID().toString();

        String fileNameGenerator = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));

        //full path
        String fullPath = path + File.separator+ fileNameGenerator;

        // create folder if not created
        File f = new File(path);
        if(!f.exists()) {
            f.mkdir();
        }
        // file copy
        Files.copy(file.getInputStream(), Paths.get(fullPath));
        log.info("Image uploaded successfully: " + fullPath);
        return fileNameGenerator;

    }

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {
        log.info("Getting image");
        String fullPath = path+ File.separator+filename;
       InputStream is = new FileInputStream(fullPath);

        log.info("Image retrieved successfully: " + fullPath);
        return is;
    }
}



