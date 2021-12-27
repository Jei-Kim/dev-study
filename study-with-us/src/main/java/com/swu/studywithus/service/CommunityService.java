package com.swu.studywithus.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
  
  @Transactional
  public Community create(Community community) {
    
    try {
      communityRepository.insert(community);
      return community;
      
    } catch (Exception e) {
      return null;
    }
    
  }
  
  public Community findByNo(int boardNo) throws Exception {
    
    updateViewCount(boardNo);

    return communityRepository.findByNo(boardNo);
  }
  
  @Transactional
  public void update(Community community) throws Exception {
    communityRepository.update(community);
  }

  @Transactional
  public void delete(int boardNo) throws Exception {
    communityRepository.delete(boardNo);
  }
  
  private void updateViewCount(int boardNo) throws Exception {
    communityRepository.updateCount(boardNo);
  }
  
}
