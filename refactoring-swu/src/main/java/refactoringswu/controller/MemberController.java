package refactoringswu.controller;

import javax.servlet.http.HttpServletRequest;
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import refactoringswu.SessionConst;
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
			return "index";//
		}
		memberService.insert(member);
		log.info(member.getEmail() + "회원가입 성공"); //확인용 
		
		sqlSessionFactory.openSession().commit();
		return "redirect:../";
	}
	
	@PostMapping("/login")
	  public String login(@RequestParam String email, @RequestParam String password, HttpSession sessionRequest, HttpServletRequest servletRequest) throws Exception {

		/*if (result.hasErrors()) {
            return "index";//
        }*/
		
		Member loginUser = memberService.findMemberByEmailPassword(email, password);
		
		if (loginUser == null) {
			//result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "index";
        }
		
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = servletRequest.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);
        sessionRequest.setAttribute("loginUser", loginUser); //jsp로 변수 넘기기 위해서 필요함 
    	log.info(loginUser.getName() + "님 로그인 성공");

        return "redirect:../";
	  }
	
	@GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
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
