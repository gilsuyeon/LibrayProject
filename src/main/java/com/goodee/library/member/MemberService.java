package com.goodee.library.member;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

	private static final Logger LOGGER = 
			LoggerFactory.getLogger(MemberService.class);
	
	@Autowired
	MemberDao dao;
	
	// 아이디를 기준으로 정보를 조회
		// 아이디 줄테니까 정보좀 가지고 와라
		// (MemberVo vo) 컨트롤러에게 아이디랑 비번 정보를 전달 받는거임
		public MemberVo loginMember(MemberVo vo) {
			LOGGER.info("[MemberService] loginMember();");
		// 비번이 맞는 지 확인해서 
		MemberVo loginedMember = dao.selectMember(vo);
		return loginedMember;
		
		}
		
		public List<MemberVo> listupMember () {
			LOGGER.info("[MemberService] listupMember();");
			// dao야 정보 조회해서 나한테 보여줘
			return dao.selectMemberList();
			
			
			
		}
		
		public int modifyMember (MemberVo vo) {
			LOGGER.info("[MemberService] modifyMember();");
			return dao.updateMember(vo);
		}
		public String modifyMemberConfirm() {
			LOGGER.info("[MemberService]  modifyMemberConfirm();");
			
		}
}
