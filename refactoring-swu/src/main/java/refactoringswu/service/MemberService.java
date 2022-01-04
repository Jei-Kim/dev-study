package refactoringswu.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import refactoringswu.domain.Member;
import refactoringswu.repository.MemberRepository;
import refactoringswu.repository.Param;


@Service
public class MemberService {
  
  private final MemberRepository memberRepository;
  
  @Autowired
  public MemberService(MemberRepository memberRepository) {
	  this.memberRepository = memberRepository;
  }
  
  List<Member> findAll() throws Exception; {
	  return memberRepository.findAll();
  }
  
  Member findByNo(int no) throws Exception;
  Member findByEmail(String email) throws Exception; // 회원가입 시 아이디 중복검사
  Member findMemberByNamePhoneNumber(@Param("name") String name, @Param("phoneNumber") String phoneNumber) throws Exception; // 아이디 찾기
  Member findMember(@Param("name") String name, @Param("email") String email, @Param("phoneNumber") String phoneNumber) throws Exception; // 비밀번호 재설정
  Member findMemberByEmailPassword(@Param("email") String email, @Param("password") String password) throws Exception; // 로그인

  //11.15추가
  int emailCheck(String email) throws Exception; 

  void insert(Member member) throws Exception;
  void update(Member member) throws Exception;
  void updatePassword(Member member) throws Exception;
  void delete(int no) throws Exception;

  
  
}
