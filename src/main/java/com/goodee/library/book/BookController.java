package com.goodee.library.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.library.util.UploadFileService;

@Controller
@RequestMapping("/book")
public class BookController {

	private static final Logger LOGGER =
			LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	UploadFileService uploadFileService;
	
	@Autowired
	BookService bookService;
	
	// 도서 등록 화면 이동
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String createBookForm() {
		LOGGER.info("[BookController] createBookForm();");
		return "book/create";
	}
	
	// 도서 등록 기능
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String createdBookConfirm(BookVo vo,  //vo 안에는 정보가 있음
			@RequestParam("file") MultipartFile file) {
		LOGGER.info("[BookController] createdBookConfirm();");
		int result = -1;  // 밑에 오류가 발생해서 전역변수로 빼준다.
		// 1. 파일 파싱(UploadFileService)
		String savedFileName = uploadFileService.upload(file);
		// 2. 파일 등록
		// 비어있는 문자열과 같지 않다면
		if(savedFileName != null && "".equals(savedFileName) == false) {
			vo.setB_thumbnail(savedFileName);
			result = bookService.createdBookConfirm(vo);  // 서비스에서도 반환되어 돌아옴
			
			// 1. bookService 빈 등록
			// 2. BookController에 BookService 의존성 주입
			// 3. BookService 도서 등록 메소드
			// 4. BookDao 데이터베이스에 도서 등록
			// 5. book_mapper.xml 생성
			// 6. insert구문 작성
			// 7. 테이블 tbl_book
			
			
		// 숫자는 insert된 행의 갯수 	
		}
		// 3. 결과 화면 전환
		if(result > 0) {
		return "book/create_success";
	}else {
		return "book/create_fail";
	}
  }
}
	
	
	
	
