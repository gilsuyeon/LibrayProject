package com.goodee.library.book;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao {
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(BookDao.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	private final String namespace ="com.goodee.library.book.BookDao.";  // 얘가 mapper한테 일시키는얘 그래서 뒤에 .붙임
	
	public int insertBook(BookVo vo) {   // insert를 위해 필요한 정보는  BookVo vo다.
		LOGGER.info("[BookDao] insertBook();");
		return sqlSession.insert(namespace+"insertBook",vo);
			
		
	}
	
	
}
