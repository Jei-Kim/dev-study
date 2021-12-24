package com.swu.studywithus.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.swu.studywithus.domain.Community;
import com.swu.studywithus.repository.CommunityRepository;

@Service
public class CommunityService {
  
  private final CommunityRepository communityRepository;
  
  @Autowired
  public CommunityService(CommunityRepository communityRepository) {
    this.communityRepository = communityRepository;
  }
  
  public Collection<Community> findByKeyword(String keyword, int categoryNo) throws Exception {
    return communityRepository.findByKeyword(keyword, categoryNo);
  }
  
  public Collection<Community> findAll(int categoryNo) throws Exception {
    return communityRepository.findAll(categoryNo);
  }
}
