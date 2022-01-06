package refactoringswu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "mapper") //-> 테스트 해보기 
public class RefactoringSwuApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefactoringSwuApplication.class, args);
	}

}
