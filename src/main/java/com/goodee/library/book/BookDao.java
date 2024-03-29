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
	// 도서 상세 조회
	  public BookVo selectBookOne(int b_no) {
	      LOGGER.info("[BookDao] selectBookOne();");
	      BookVo vo = sqlSession.selectOne(namespace+"selectBookOne",b_no);
	      return vo;
	   }
	  
	  public int updateBook(BookVo vo) {
		  LOGGER.info("[BookDao] updateBook();");  //namespace+"updateBook"  updateBook는 쿼리 이름
		  return sqlSession.update(namespace+"updateBook",vo); //sql 쿼리를 날려줘 정보주면서
	  }
	//도서 삭제 기능    //int b_no 라는 정보를 받아서 일처리를 함
	  public int deleteBook(int b_no) {  
		  LOGGER.info("[BookDao] deleteBook();");
		  int result = sqlSession.delete(namespace+"deleteBook",b_no);
		  return result;
	  }
	
}
