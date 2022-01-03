package com.swu.studywithus.controller;

import java.util.Collection;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.swu.studywithus.domain.Community;
import com.swu.studywithus.domain.Member;
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
  
  @PostMapping("/community/add")
  public ModelAndView add(Community community, HttpSession session) throws Exception {

    community.setWriter((Member) session.getAttribute("loginUser"));
    
    Community result = communityService.create(community);
    
    ModelAndView mv = new ModelAndView();

    if(result == null) {
     mv.setViewName("/error");

    } else {
     mv.setViewName("redirect:list?no=" + community.getCategory());     
   }
    
    return mv;
  } 
  
  @GetMapping("/community/detail")
  public ModelAndView detail(int boardNo) throws Exception {
    
    Community community = communityService.findByNo(boardNo);

    if (community == null) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    }

    ModelAndView mv = new ModelAndView();
    mv.addObject("community", community);
    mv.addObject("pageTitle", "스터디위더스 : 커뮤니티상세보기");
    mv.setViewName("community/CommunityDetail");
    return mv;
  }

  @GetMapping("/community/updateform")
  public ModelAndView updateForm(int boardNo) throws Exception {
    
    Community community = communityService.findByNo(boardNo);

    if (community == null) {
      throw new Exception("해당 번호의 스터디가 없습니다.");
    }

    ModelAndView mv = new ModelAndView();
    mv.addObject("community", community);
    mv.addObject("pageTitle", "스터디위더스 : 커뮤니티수정");
    mv.setViewName("community/CommunityUpdateForm");
    return mv;
  }

  @PostMapping("/community/update")
  public ModelAndView update(Community community) throws Exception {

    Community oldCommunity = communityService.findByNo(community.getNo());
    
    
    if (oldCommunity == null) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    } 

    communityService.update(community);

    ModelAndView mv = new ModelAndView();
    mv.setViewName("redirect:detail?no=" + community.getNo());
    return mv;
  }

  @GetMapping("/community/delete")
  public ModelAndView delete(int no) throws Exception {

    Community community = communityService.findByNo(no);

    if (community == null) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    }

    communityService.delete(no);

    ModelAndView mv = new ModelAndView();
    mv.setViewName("redirect:list?no=" + community.getCategory() + "&pageNo=1");
    return mv;
  }
  
  @GetMapping("/community/interest/add")
  public ModelAndView interestAdd(int memberNo, int communityNo, HttpSession session) throws Exception {

    Community oldCommunity= communityService.findByNo(communityNo);

    if (oldCommunity == null) {
      throw new Exception("해당 번호의 커뮤니티가 없습니다.");
    } 

    memberNo = ((Member) session.getAttribute("loginUser")).getNo();

    communityService.addLikes(memberNo, communityNo);

    ModelAndView mv = new ModelAndView();
    mv.setViewName("redirect:../detail?no=" + communityNo);
    return mv;
  }

  @GetMapping("/community/interest/delete")
  public ModelAndView interestDelete(int memberNo, int communityNo, HttpSession session) throws Exception {

    Community oldCommunity= communityService.findByNo(communityNo);

    if (oldCommunity == null) {
      throw new Exception("해당 번호의 커뮤니티가 없습니다.");
    } 

    memberNo = ((Member) session.getAttribute("loginUser")).getNo();

    communityService.deleteLikes(memberNo, communityNo);

    ModelAndView mv = new ModelAndView();
    mv.setViewName("redirect:../detail?no=" + communityNo);
    return mv;
  }
}
