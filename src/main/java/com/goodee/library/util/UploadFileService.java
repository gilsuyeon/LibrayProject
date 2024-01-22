package com.goodee.library.util;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class UploadFileService {

	public String upload(MultipartFile file) {
		String result = "";
		// 파일 서버 저장
		String fileOriName = file.getOriginalFilename();
		String fileExtension = fileOriName.substring(
				fileOriName.lastIndexOf(".",fileOriName.length()));
		String uploadDir = "\\libray\\upload\\";
		
		//유일한 값 만들기
		UUID uuid = UUID.randomUUID();
		String uniqueName = uuid.toString().replaceAll("-", "");
		
		File saveFile = new File(uploadDir+"\\"+uniqueName+fileExtension);   //새롭게 만들어진 파일 껍데기 
		
		if(!saveFile.exists()) saveFile.mkdir();
			
		try {
			file.transferTo(saveFile);    // 우리가 가져온 파일을 껍데기만 있는 파일에다가 넣어줄게
			result = uniqueName+fileExtension;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;	
	}
}
