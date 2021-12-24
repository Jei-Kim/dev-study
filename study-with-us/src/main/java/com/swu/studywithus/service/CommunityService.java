package com.swu.studywithus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.swu.studywithus.repository.CommunityRepository;

@Service
public class CommunityService {
  
  private final CommunityRepository communityRepository;
  
  @Autowired
  public CommunityService(CommunityRepository communityRepository) {
    this.communityRepository = communityRepository;
  }
}
