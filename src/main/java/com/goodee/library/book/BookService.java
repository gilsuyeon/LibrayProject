package com.goodee.library.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service    //빈으로 등록
public class BookService {
	
	@Autowired
	BookDao bookDao;
	
	public int createdBookConfirm(BookVo vo) {	 
		return bookDao.insertBook(vo);
	}
	

}
