package es.iesfranciscodelosrios.ryg.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

	Cloudinary cloudinary;

	private Map<String, String> valuesMap = new HashMap<String, String>();

	public CloudinaryService() {
		valuesMap.put("cloud_name", "antoniomcloud");
		valuesMap.put("api_key", "768168943981397");
		valuesMap.put("api_secret", "Hi9TuLFzXQtIqJOn7GfQkZlI6as");
		this.cloudinary = new Cloudinary(valuesMap);
	}
	
	public Map upload(MultipartFile multipartFile) throws IOException {
		File file = convert(multipartFile);
		Map resulMap = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
		file.delete();
		return resulMap;
	}
	
	public Map delete(String id) throws IOException {
		Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        return result;
	}
	
	private File convert(MultipartFile multipartFile) throws IOException{
		File file = new File(multipartFile.getOriginalFilename());
		FileOutputStream fo = new FileOutputStream(file);
		fo.write(multipartFile.getBytes());
		fo.close();
		return file;
	}

}
