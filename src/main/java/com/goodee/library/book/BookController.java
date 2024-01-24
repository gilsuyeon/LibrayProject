package com.goodee.library.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	// 도서 수정 이동
	@RequestMapping(value="/modify/{b_no}", method=RequestMethod.GET)
	public String modifyBookForm(@PathVariable int b_no , Model model) {  // 수정화면을 위해서는 정보전달이 필요
		// 1. 기존 정보 조회
		BookVo vo = bookService.bookDetail(b_no);
		// 2. 화면 전환 + 정보 전달
		model.addAttribute("bookVo",vo);
		return "book/modify";
		//book/modify.css
	}
	// 도서 수정 기능
	@RequestMapping(value="/modify/{b_no}", method=RequestMethod.POST)
	public String modifyBookConfirm(BookVo vo, @RequestParam("file") MultipartFile file) {   //BookVo vo로 필요한 정보 받아올거임 , file정보는 따로 받아옴
		LOGGER.info("[BookController] modifyBookConfirm()");
		// 1. 만약에 새로운 파일 o -> 파일 업로드
		if(file.getOriginalFilename().equals("") == false) { //파일이 있으면
			String savedFileName = uploadFileService.upload(file);
			if(savedFileName != null) {
				vo.setB_thumbnail(savedFileName);  // 비어있지 않다면 B_thumbnail(savedFileName)를 해준다
			}
		}
		// 2. 도서 정보 수정  // update의 결과는 항상 int
				int result = bookService.modifyConfirm(vo);
		
		
		// (1) BookService에 modifyConfirm 메소드생성
		// (2) BookDao에 updateBook 메소드 생성
		// (3) BookService의 modifyConfirm이 BookDao의 updateBook으로부터
		// int(update 수행결과)를 전달
		// (4) book_mapper에 updateBook 쿼리 생성ㅇ
		// -> 파라미터가 BookVo
		// -> tbl_book을 UPDATE
		// -> b_name, b_author, b_pulisher, b_publish_year, b_mod_date
		// -> 만약에 b_thumnail이 null이 아니면서 빈 스트링이 아니라면 b_thumnail도 수정
				
				
		// 3. 결과 화면 이동
		if(result <= 0) {
				return "book/modify_fail";
	      } else {
	    	  	 return "book/modify_success";
			
		}
	}
	
	// 도서 삭제
}		 
		
	
	
	
	
	
	

	
	
	
	
