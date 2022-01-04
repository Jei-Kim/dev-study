package refactoringswu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * Controller 클래스 생성 시,
 * @SpringBootApplication 어노테이션이 붙은 클래스와 동일한 위치 또는 하위에 생성해야 함 
 * Component Scan 시 Controller 클래스를 찾아서 자동으로 빈을 등록하기 때문
 */

@Controller
@RequestMapping("/test")
public class TestController {

	@GetMapping
    public String test(){

        System.out.println("테스트 컨트롤 실행됨 ");
        return "test";
    }
}