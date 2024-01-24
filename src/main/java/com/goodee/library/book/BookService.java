package com.goodee.library.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service    //빈으로 등록
public class BookService {
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(BookService.class);
	
	
	@Autowired
	BookDao bookDao;
	// 도서 등록 기능
	public int createdBookConfirm(BookVo vo) {	 
		return bookDao.insertBook(vo);
	}
	
	// 도서 상세 이동
	public BookVo bookDetail(int b_no) {
	      LOGGER.info("[BookService] bookDetail();");
	      BookVo vo = bookDao.selectBookOne(b_no);
	      return vo;
	   }
	public int modifyConfirm(BookVo vo) {  //int는 (update 수행결과)를 전달
		LOGGER.info("[BookService] modifyConfirm();");
		return bookDao.updateBook(vo);
	}
 

}
