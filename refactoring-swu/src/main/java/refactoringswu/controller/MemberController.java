package refactoringswu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import refactoringswu.domain.Member;
import refactoringswu.service.MemberService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private final MemberService memberService;
    
    @PostMapping("/join")
    public String join(@ModelAttribute Member member, BindingResult result) throws Exception {
//        if (result.hasErrors()) {
//            return "Error";
//}
        memberService.insert(member);
        return "redirect:/";
    }

}

