package com.swu.studywithus;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.swu.studywithus.domain.Community;
import com.swu.studywithus.domain.Member;
import com.swu.studywithus.repository.CommunityRepository;
import com.swu.studywithus.repository.MemberDao;

@SpringBootTest
public class CommunityServiceTest {
  
  @Autowired
  CommunityRepository repo;
  @Autowired
  MemberDao memberdao;
  
  Member member = new Member();
  Community cmnt = new Community();
  
  @BeforeEach
  public void beforeEach() throws Exception {
  
   member.setNo(1);
   member.setName("test");
   member.setEmail("test@test.com");
   member.setPassword("1111");
   member.setPhoneNumber("01011111111");
   member.setNickname("gks");
   memberdao.insert(member);
   Member session = memberdao.findByNo(member.getNo());
 }

 @AfterEach
 public void afterEach() throws Exception {
   repo.delete(cmnt.getNo());
    memberdao.delete(member.getNo());
  }
 
  @Test
  @Transactional
  void create() throws Exception { // insert, findByNo, delete 확
    member = memberdao.findByNo(8);
    
    cmnt.setCategory(0);
    cmnt.setTitle("test");
   cmnt.setContent("test");
   cmnt.setWriter(member);
   
   repo.insert(cmnt);
   Community result = repo.findByNo(9);
   System.out.println(result);
   Assertions.assertThat(result.getNo()).isEqualTo(9);
  
   repo.delete(cmnt.getNo());
 }
  
  @Test
  void findAll() throws Exception {
    member = memberdao.findByNo(8);
    
    Community c = new Community();
    
    c.setCategory(0);
    c.setTitle("test");
    c.setContent("test");
    c.setWriter(member);
    
    repo.insert(c);
    
    List<Community> cmnts = repo.findAll(0);
    
    int result = cmnts.size();
    
    Assertions.assertThat(result).isEqualTo(2);
    }
  
  @Test
  void update() throws Exception {
    Community temp = repo.findByNo(9);
    
    System.out.println(temp.getTitle());
    
    Assertions.assertThat(temp.getTitle()).isEqualTo("test3");
    temp.setTitle("test4");
    
    repo.update(temp);
    
    Community temp2 = repo.findByNo(9);
    
    System.out.println(temp2.getTitle());
    
    Assertions.assertThat(temp2.getTitle()).isEqualTo("test4");
  }
  
  @Test
  void findByKeyword() throws Exception {
    List<Community> cmnt = repo.findByKeyword("te", 0);
    
    Assertions.assertThat(cmnt.size()).isEqualTo(2);
  }
}
