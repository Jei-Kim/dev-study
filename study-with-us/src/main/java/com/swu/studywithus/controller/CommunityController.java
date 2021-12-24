package com.swu.studywithus.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import com.swu.studywithus.domain.Community;
import com.swu.studywithus.service.CommunityService;

@Controller
public class CommunityController {

  private final CommunityService communityService;
  
  @Autowired
  public CommunityController(CommunityService communityService) {
    this.communityService = communityService;
  }
  
  @GetMapping("/community/search")
  public ModelAndView search(String keyword, int categoryNo) throws Exception {

    Collection <Community> communityList = communityService.findByKeyword(keyword, categoryNo);

    ModelAndView mv = new ModelAndView();
    mv.addObject("communityList", communityList);
    mv.addObject("categoryNo", categoryNo);
    mv.addObject("pageTitle", "스터디위더스 : 커뮤니티검색결과");
    mv.setViewName("community/CommunityList");
    return mv;
  }
  
  @GetMapping("/community/list")
  public ModelAndView list(int categoryNo) throws Exception {

    Collection<Community> communityList = communityService.findAll(categoryNo);

    ModelAndView mv = new ModelAndView();
    mv.addObject("communityList", communityList);
    mv.addObject("pageTitle", "스터디위더스 : 커뮤니티목록");
    mv.setViewName("community/CommunityList");
    return mv;
  }
  
  @GetMapping("/community/form")
  public ModelAndView form() {
    ModelAndView mv = new ModelAndView();
    mv.addObject("pageTitle", "스터디위더스 : 커뮤니티등록");
    mv.setViewName("community/CommunityForm");
    return mv;
  }
}
