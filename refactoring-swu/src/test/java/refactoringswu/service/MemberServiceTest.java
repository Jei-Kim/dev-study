package refactoringswu.service;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import refactoringswu.domain.Member;
import refactoringswu.repository.MemberRepository;

public class MemberServiceTest {

	MemberService memberService;
	MemberRepository memberRepository;
	
	@BeforeEach
	public void beforeEach() {
		memberRepository = new MemberRepository();
		memberService = new MemberService(memberRepository);
	}
	
	/*
	@AfterEach
	public void afterEach() {
		memberRepository.clearStore());
	}
	*/
	
	@Test
	void insert() {
		//given
		Member member = new Member();
		member.setName("Kim");
		
		//when
		Long id = memberService.insert(member);
		
		//then
		Member findMember = memberService.findByEmail(id).get();
		Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
	}
	
	
	@Test
	public void insert_exception() {
		//given
		Member member1 = new Member();
		member1.setName("Kim");
		
		Member member2 = new Member();
		member2.setName("Kim");
		
		//when
		memberService.insert(member1);
		
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.insert(member2));
		
		Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
		
		//then
	}
}
