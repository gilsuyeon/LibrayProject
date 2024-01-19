package com.goodee.library.member;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

	private static final Logger LOGGER = 
			LoggerFactory.getLogger(MemberService.class);
	
	@Autowired
	MemberDao dao;
	
	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;
	
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
		// // 회원 단일 정보 조회
		// MemberVo : 정보를 담을 바구니 
		//getLoginedMemberVo : 일하는 사람
		public MemberVo getLoginedMemberVo(int m_no) {
			return dao.selectMemberOne(m_no);
		}
		//MemberVo vo : 사용자가 입력한 정보 
		public int findPasswordConfirm(MemberVo vo) {
			// 1. 입력한 정보와 일치하는 사용자가 있는지 확인
			MemberVo selectedMember = dao.selectMemberOne(vo);
			int result = 0;
			if(selectedMember != null) {
				// 2. 새로운 비밀번호 생성
				String newPassword = createNewPassword();
				// 3. 생성된 비밀번호 업데이트
				//dao에게 매개변수 2개 넘기기 vo.getM_id(),newPassword
				// vo.getM_id(),newPassword 사용자가 입력한 아이디, 새롭게 생성한 비밀번호
					result = dao.updatePassword(vo.getM_id(),newPassword);
					// 데이터 베이스가 업데이트 된후에 메일보내기를 할수 있음
				if(result > 0) {
					// 4. 메일 보내기
					sendNewPasswordByMail(vo.getM_mail(),newPassword);   //newPassword는 변수값
				}
			}
			return result;
		}
		
		// 메일로 비밀번호 보내기
		// 반환값 없어도 되니까 void
		private void sendNewPasswordByMail(String toMailAddr, String newPw) {  //newpw 는 사용자한테 받아온값
			LOGGER.info("[MemberService] sendNewPasswordByMail();");
			
			final MimeMessagePreparator mime = new MimeMessagePreparator() {
				
				@Override
				public void prepare(MimeMessage mimeMessage) throws Exception{
					final MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, true,"UTF-8");
					mimeHelper.setTo(toMailAddr);
					mimeHelper.setSubject("[구디도서관]새로운 비밀번호 안내입니다.");
					mimeHelper.setText("새 비밀번호 : "+newPw,true);
				}
			};			
			javaMailSenderImpl.send(mime);
		}
		

		
		
		
		
		
		
		// 비밀번호 생성
		// 일꾼의 산출물은 string형태로 나온다
		private String createNewPassword() {
			LOGGER.info("[MemberService] createNewPassword();");
			char[] chars = new char[] {
			         '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			         'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
			         'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
			         'u', 'v', 'w', 'x', 'y', 'z'
			         };
			StringBuffer sb = new StringBuffer();
			SecureRandom sr = new SecureRandom();
			sr.setSeed(new Date().getTime());
			//인덱스 값 설정
			int index = 0;
			//배열의 길이
			int length = chars.length;
			for(int i = 0 ; i < 8 ; i++) {
				index = sr.nextInt(length);
				if(index % 2 == 0) {
					//sb.append(chars[index]);
					//스트리으로 바꿔줌
					//sb.append(String.valueOf(chars[index]));
					// 짝수일때 대문자
					sb.append(String.valueOf(chars[index]).toUpperCase());
				} else {
					//홀수일때 소문자
					sb.append(String.valueOf(chars[index]).toLowerCase());
				}
			}
			return sb.toString();
		}
		
		
}
