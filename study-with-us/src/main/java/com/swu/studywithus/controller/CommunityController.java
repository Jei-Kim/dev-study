package com.swu.studywithus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.swu.studywithus.service.CommunityService;

@Controller
public class CommunityController {

  private final CommunityService communityService;
  
  @Autowired
  public CommunityController(CommunityService communityService) {
    this.communityService = communityService;
  }
}
