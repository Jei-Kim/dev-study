package refactoringswu.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import refactoringswu.domain.Member;
import refactoringswu.service.MemberService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private final MemberService memberService;

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@PostMapping("/join")
	public String join(@ModelAttribute Member member, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			return "Error";
		}
		memberService.insert(member);
		log.info(member.getEmail() + "회원가입 성공"); //확인용 
		
		sqlSessionFactory.openSession().commit();
		return "redirect:../";
	}
	
	@PostMapping("/login")
	  public String login(@RequestParam String email, @RequestParam String password, HttpSession request) throws Exception {

	    Member loginUser = memberService.findMemberByEmailPassword(email, password);
	    
	    if (loginUser != null) {
	    	request.setAttribute("loginUser", loginUser); //jsp로 변수 넘기기 위해서 필요함 
	    	log.info(loginUser.getName() + "님 로그인 성공");
	    	
	    	return "index";
	    }
	    
	    return "redirect:../";
	  }

	/* 매퍼파일 연결됐는지 확인용 
	@GetMapping("/members")
	public List<Member> findAll() throws Exception {
		log.info("allmember = {}", memberService.findAll());
		return memberService.findAll();
	} */
	
}
