package com.swu.studywithus;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.swu.studywithus.domain.Community;
import com.swu.studywithus.repository.CommunityRepository;

@SpringBootTest
public class CommunityServiceTest {
  
  @Autowired
  CommunityRepository repo;
  
  @Test
  void create() throws Exception {
    Community cmnt = new Community();
    cmnt.setNo(1);
    cmnt.setCategory(0);
    
    repo.insert(cmnt);
    Community result = repo.findByNo(cmnt.getNo());
    Assertions.assertThat(result).isEqualTo(cmnt);
  }
}
