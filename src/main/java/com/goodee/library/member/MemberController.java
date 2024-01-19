package com.goodee.library.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/member")
public class MemberController {
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(MemberController.class);
	@Autowired
	MemberService memberService;
	
	// 로그민 화면 이동
		@RequestMapping(value="/login", method=RequestMethod.GET)
		public String opendLoginForm() {
			LOGGER.info("[MemberController] openLoginForm();");
			return "member/login_form";
		}
		
		// 로그인 기능
		@RequestMapping(value="/login", method=RequestMethod.POST)
		//HttpSession session : 얘를 쓰겠다 의존성주입
		public String loginMember(MemberVo vo, HttpSession session)  {
			LOGGER.info("[MemberController] loginMember();");
			// 기본적으로는 성공화면 실패했을때는 fail화면으로 이동
			String nextPage = "member/login_success";
			//  아이디와 비번 정보를 저장해옴
			MemberVo loginedMember = memberService.loginMember(vo);
			if(loginedMember == null) {
				nextPage = "member/login_fail";
			} else {
				//(key,value)
				session.setAttribute("loginMember", loginedMember);
				// 로그인을 얼마만큼 유지할지
				session.setMaxInactiveInterval(60*30);
			}
			return nextPage;
		}
		
		// 로그아웃 기능
		@RequestMapping(value="/logout", method=RequestMethod.GET)
		public String logoutMember(HttpSession session) {
			LOGGER.info("[MemberController] logoutMember();");
			//만들었던 세션정보를 해제
			session.invalidate();
			// home으로 감
			return "redirect:/";
		}
//		
//		// 회원 목록 이동    -- ModelAndView (1)
//		// new로 modelandview 생성
////		@RequestMapping(method=RequestMethod.GET)
////		/*string 화면만
////		 * model 정보만
////		 * modelandview 화면과 정보
////		*/
////		public ModelAndView listupMember() {
////			LOGGER.info("[MemberController] listupMember ();");
////			// 1. 목록 정보 조회
////			List<MemberVo> memberVos = memberService.listupMember();
////			// 2. 목록 전달
////			ModelAndView mav = new ModelAndView();
////			mav.addObject("memberVos" ,memberVos);
////			// 3. 뷰 선택
////			mav.setViewName("member/listup");
////			return mav;
////		}
//		// 회원 목록 이동    -- ModelAndView (2)
//		// modelandview 매개변수를 객체 주입
////		@RequestMapping(method=RequestMethod.GET)
////		public ModelAndView listupMember(ModelAndView mav) {
////			LOGGER.info("[MemberController] listupMember ();");
////			// 1. 목록 정보 조회
////			List<MemberVo> memberVos = memberService.listupMember();
////			// 2. 목록 전달
////			
////			mav.addObject("memberVos" ,memberVos);
////			// 3. 뷰 선택
////			mav.setViewName("member/listup");
////			return mav;
////        }
		@RequestMapping(method=RequestMethod.GET)
		// model를 쓸꺼면 반환을 String 으로 해줘야됨
		// 이 메소드안에서 쓰겠다
		public String listupMember(Model model) {
			LOGGER.info("[MemberController] listupMember ();");
			// 1. 목록 정보 조회
			List<MemberVo> memberVos = memberService.listupMember();
			// 2. 목록 전달
			//model 이랑 modelandview둘다 어떤정보를 전달해 줄건지 어디로 전달해줄건지
			model.addAttribute("memberVos", memberVos);
			return "member/listup";
		}	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// 회원 정보 수정 화면 이동
		@RequestMapping(value="/{m_no}", method=RequestMethod.GET)
		public String modifyMember(@PathVariable int m_no, HttpSession session) {
				LOGGER.info("[MemberController] modifyMember();");
//				// 다른 사람의 정보 수정 o
//				// 1. url에 있는 m_no 기준 select 
//				// 2. 수정 화면 이동
//				
//				// 내 정보 수정하고 싶으면 로그인 해야 됨 로그인 해야된다는건 session를 확인해야됨
//				// 내 정보만 수정 o
//				// 1. 세션에 있는 m_no 기준
//				// 2. 수정 화면 
//				MemberVo loginedMemberVo  = (MemberVo)session.getAttribute("loginMember");
//				String nextPage = "";
//				// 로그인 정보만 비어있으면
//				if(loginedMemberVo == null) {
//					// 로그인 화면 이동
//					// redirect: 다시 연결
//					nextPage = "redirect:/member/login";
//				} else {
//					// 수정 화면 이동
//					nextPage = "member/modify_form";
//				}
//				
				
				return "member/modify_form";
			
		}
		
		
		// 회원 정보 수정 기능
		@RequestMapping(value="/{m_no}", method=RequestMethod.POST)
		public String modifyMemberConfirm(MemberVo vo,HttpSession session) {//MemberVo vo 정보를 가져옴
			LOGGER.info("[MemberController] modifyMemberConfirm();");
			// 1. 회원 정보 수정 (DB)
			
			// 3. 수정 결과 화면 이동
			// vo를 보내서 나에게 알려줘
			int result = memberService.modifyMember(vo);
			// 성공
			if(result > 0) {
				// 2. 세션 정보 변경
				//MemberVo loginedMemberVo = new MemberVo(); 바구니
				// 바뀐 정보가 세션에 저장
				MemberVo loginedMemberVo = new MemberVo();
				loginedMemberVo = memberService.getLoginedMemberVo(vo.getM_no());
				session.setAttribute("loginMember", loginedMemberVo);
				session.setMaxInactiveInterval(60*30);
				// 3. 성공 화면 이동
				return "member/modify_success";
			}else {	
				// 3. 실패 화면 이동
				return "member/modify_fail";
			}
		}
		
		// 비밀번호 설정 화면 이동
		@RequestMapping(value="/findPassword", method=RequestMethod.GET)
		public String findPasswordForm() {
			LOGGER.info("[MemberController] findPasswordForm();");
			return "member/find_password_form";
		}
		
		// 비밀번호 설정 기능
		@RequestMapping(value="/findPassword", method=RequestMethod.POST)
		public String findPasswordConfirm(MemberVo vo) {  // MemberVo vo : 일을 시키려면 정보를 주면서 시켜야됨
			LOGGER.info("[MemberController] findPasswordConfirm();");			
			int result = memberService.findPasswordConfirm(vo);
			if(result <= 0) {
				return "member/find_password_fail";
			} else {
				return "member/find_password_success";
			}
		}
		
		
		
		
		
		
		
}
