package refactoringswu.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import refactoringswu.domain.Member;
import refactoringswu.repository.MemberRepository;

import org.apache.ibatis.annotations.Param; //보류 

@Service
@RequiredArgsConstructor
public class MemberService {

	@Autowired // repository(mapper)에 빈 주입
	MemberRepository memberRepository;

	/*
	 * new로 생성 시 테스트에서 또 다른 객체를 이용하게 되는 문제점 발생 -> 서비스 코드에서 DI 가능하도록 처리
	 */
	/*
	 * //private final MemberRepository memberRepository;
	 * 
	 * // @Autowired // public MemberService(MemberRepository memberRepository) { //
	 * this.memberRepository = memberRepository; // }
	 */
	
	
	// public으로 고쳐야하는 문제 ..?
	public List<Member> findAll() throws Exception {
		return memberRepository.findAll();
	}

	Member findByNo(int no) throws Exception {
		return memberRepository.findByNo(no);
	};

	// 회원가입 시 아이디 중복검사
	Member findByEmail(String email) throws Exception {
		return memberRepository.findByEmail(email);
	};

	// 아이디 찾기
	Member findMemberByNamePhoneNumber(@Param("name") String name, @Param("phoneNumber") String phoneNumber)
			throws Exception {
		return memberRepository.findMemberByNamePhoneNumber(name, phoneNumber);
	};

	// 비밀번호 재설정
	Member findMember(@Param("name") String name, @Param("email") String email,
			@Param("phoneNumber") String phoneNumber) throws Exception {
		return memberRepository.findMember(name, email, phoneNumber);
	};

	// 로그인
	public Member findMemberByEmailPassword(@Param("email") String email, @Param("password") String password)
			throws Exception {
		return memberRepository.findMemberByEmailPassword(email, password);
	};

	// 중복검사용 추가
	int emailCheck(String email) throws Exception {
		return memberRepository.emailCheck(email);
	};

	@Transactional
	public void insert(Member member) throws Exception {
		memberRepository.insert(member);
	};

	void update(Member member) throws Exception {
		memberRepository.update(member);
	};

	void updatePassword(Member member) throws Exception {
		memberRepository.updatePassword(member);
	};

	void delete(int no) throws Exception {
		memberRepository.delete(no);
	};

}
