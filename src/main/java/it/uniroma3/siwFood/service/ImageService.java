package it.uniroma3.siwFood.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
	
	public String saveImage(MultipartFile image, String directory) throws IOException{
		
    	if (!image.isEmpty()) { // Verifica se il file è vuoto

            Path fileNameAndPath = Paths.get(directory, image.getOriginalFilename());
            Files.write(fileNameAndPath, image.getBytes());
            
            return image.getOriginalFilename();
        }
		return null;
	}
}
