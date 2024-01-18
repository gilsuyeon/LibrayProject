package com.goodee.library.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

	private static final Logger LOGGER = 
			LoggerFactory.getLogger(MemberDao.class);
	
	private final String namespace ="com.goodee.library.member.MemberDao.";
	
//	@Autowired
//	JdbcTemplate jdbcTemplate;
//	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	//private : 클래스 안에서만 쓰겠다
	private SqlSession sqlSession;
	
	// 아이디 중복 검사 - mybatis
	public boolean isMemberCheck (String m_id) {
		LOGGER.info("[MemberDao] isMemberCheck();");
		//("com.goodee.library.member.MemberDao.isMemberCheck"); memebr_mapper.xml에서 namespace과 맞춰주는것
		//결과를 하나만(md_id) 조회해라
		int result = sqlSession.selectOne(namespace+"isMemberCheck",m_id);
		if(result > 0) return true;
		else return false;
	}
	
	// 회원 정보 추가 - mybatis
	public int insertMember(MemberVo vo) {
		LOGGER.info("[MemberDao] insertMember();");
		//vo에 들어간 있는걸 getM_pw 를 다시 꺼내서 암호화 한 다음에 다시 vo에 넣어줌 
		vo.setM_pw(passwordEncoder.encode(vo.getM_pw()));
		int result = -1;
		result = sqlSession.insert(namespace+"insertMember",vo);
		return result;
	}
	
	// 로그인 - 회원정보 조회 및 비밀번호 확인
	       // 전달받은 vo에는 아이디랑 비번만 있다
			public MemberVo selectMember (MemberVo vo) {
				LOGGER.info("[MemberDao] selectMember();");
				MemberVo resultVo = new MemberVo();
			    resultVo = sqlSession.selectOne(namespace+"selectMember", vo.getM_id());
			    //전달받은 vo가 일치하는지 
			    if(passwordEncoder.matches(vo.getM_pw(), resultVo.getM_pw()) == false) {
			    	resultVo = null;
			    }
			    return resultVo;
			}
	
	
	
	
	
	
	
	
//	// 전달받ㄴ은 정보는 id와 비번 밖에 없음
//		public MemberVo selectMember (MemberVo vo) {
//			LOGGER.info("[MemberDao] selectMember ()");
//			String sql = "SELECT * FROM tbl_member WHERE m_id = ?";
//			//리스트 결과를 <MemberVo> 타입으로 받음
//			List<MemberVo> memberVos = new ArrayList<MemberVo>();
//			//쿼리 실행 결과 받아올때는 try catch문 안에 넣어야함
//			try {//로우 하나가 멤버 브이오다
//				memberVos = jdbcTemplate.query(sql, new RowMapper<MemberVo>() {
//					@Override
//					//ResultSet에 값을 받앗어 memberVo에 넘기겠다
//					public  MemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
//						MemberVo memberVo = new MemberVo();
//						memberVo.setM_no(rs.getInt("m_no"));
//						memberVo.setM_id(rs.getString("m_id"));
//						memberVo.setM_pw(rs.getString("m_pw"));
//						memberVo.setM_name(rs.getString("m_name"));
//						memberVo.setM_gender(rs.getString("m_gender"));
//						memberVo.setM_mail(rs.getString("m_mail"));
//						memberVo.setM_phone(rs.getString("m_phone"));
//						memberVo.setM_reg_date(rs.getString("m_reg_date"));
//						memberVo.setM_mod_date(rs.getString("m_mod_date"));
//						return memberVo;
//				}
//				
//				
//				},vo.getM_id());
//				// 암호화 처리 한다음에 비교
//				// 앞부분에는 사용자가 입력한 비밀번호가 들어가고 뒷부분에는 listVo에서 조회해온 얘의 비번이 들어감
//				// 앞에는 암호화를 거친애 뒤에는 암호화를 거치지 않은 얘	
//				if(passwordEncoder.matches(vo.getM_pw(), memberVos.get(0).getM_pw()) == false) {
//					memberVos.clear();
//				}
//				
//				
//			}catch(Exception e) {
////				e.printStackTrace();
//			}
//			
//			return memberVos.size() > 0 ? memberVos.get(0) : null;
//			
//		}
		public List<MemberVo> selectMemberList() {
			LOGGER.info("[MemberDao] selectMemberList();");
			// 결과를 담아와야함
			List<MemberVo> resultList = new ArrayList<MemberVo>();
			resultList = sqlSession.selectList(namespace+"selectMemberList");
			return resultList;
		}
		
//		
//		public List<MemberVo> selectMemberList() {
//			LOGGER.info("[MemberDao] selectMemberList();");
//			String sql = "SELECT * FROM tbl_member";
//			List<MemberVo> memberVos = new ArrayList<MemberVo>();
//			memberVos = jdbcTemplate.query(sql, new RowMapper<MemberVo>() {
//						@Override
//						//ResultSet에 값을 받앗어 memberVo에 넘기겠다
//						public  MemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
//							MemberVo memberVo = new MemberVo();
//							memberVo.setM_no(rs.getInt("m_no"));
//							memberVo.setM_id(rs.getString("m_id"));
//							memberVo.setM_pw(rs.getString("m_pw"));
//							memberVo.setM_name(rs.getString("m_name"));
//							memberVo.setM_gender(rs.getString("m_gender"));
//							memberVo.setM_mail(rs.getString("m_mail"));
//							memberVo.setM_phone(rs.getString("m_phone"));
//							memberVo.setM_reg_date(rs.getString("m_reg_date"));
//							memberVo.setM_mod_date(rs.getString("m_mod_date"));
//							return memberVo;
//					}
//					
//			});	
//			return memberVos;	
//		}
		
		
		public int updateMember(MemberVo vo) {
			LOGGER.info("MemberDao updateMember();");
			int result = sqlSession.update(namespace+"updateMember",vo);
			return result;
			
			
		}
		
		// 회원 단일 정보를 데이터베이스에서 조회 (m_no 조회)
		public MemberVo selectMemberOne(int m_no) {
			//m_no 이 정보를 주면서 일을 시킴
			return sqlSession.selectOne(namespace+"selectMemberOne",m_no);
		}
		// 아이디,이름,메일 기준 회원 조회
		public MemberVo selectMemberOne(MemberVo vo) {
			MemberVo memberVo= sqlSession.selectOne(namespace+"selectMemberForPasswaord",vo);
			return memberVo;
		}
		
		

		
		
}
