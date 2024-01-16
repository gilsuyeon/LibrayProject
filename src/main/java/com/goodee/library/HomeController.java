package com.goodee.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HomeController {
	
	private static final Logger LOGGER =
			LoggerFactory.getLogger(HomeController.class);
	
	// 겟으로 진입햇을때 어떤 작업을 실행하는가를 컨트롤에 적어줌
	// / 면서 GET 방식일때 이 URL를 타겠다
	@RequestMapping(value= {"","/"}, method=RequestMethod.GET)
	public String home() {
		LOGGER.info("[HomeController] home();");
		return "home";
	}
	}
	

