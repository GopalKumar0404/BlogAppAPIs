package com.gopal.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gopal.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		
		//Read File Name
		String name = file.getOriginalFilename();
		
		String randomFileName = UUID.randomUUID().toString().concat(name.substring(name.lastIndexOf('.')));
		
		// Full path
		String filePath = path+ File.separator + randomFileName;
		
		// createFolder if not created
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		// file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
	
		
		return randomFileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws IOException {
		String fullPath = path+File.separator+fileName;
		InputStream input = new FileInputStream(fullPath);
		return input;
	}

}
