package com.swu.studywithus.controller;

import java.util.Collection;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.studywithus.domain.Comment;
import com.studywithus.domain.Member;
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
  
  @PostMapping("/community/add")
  public ModelAndView add(Community community, HttpSession session) throws Exception {

    communityService.add
    
    community.setWriter((Member) session.getAttribute("loginUser"));

    communityDao.insert(community);
    sqlSessionFactory.openSession().commit();

    ModelAndView mv = new ModelAndView();
    mv.setViewName("redirect:list?no=" + community.getCategory() + "&pageNo=1");
    return mv;
  } 
  
  @GetMapping("/community/detail")
  public ModelAndView detail(int no) throws Exception {
    Community community = communityDao.findByNo(no);

    Collection<Comment> commentList = commentDao.findAll(no);

    if (community == null) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    }

    communityDao.updateCount(no);

    ModelAndView mv = new ModelAndView();
    mv.addObject("comments", commentList);
    mv.addObject("community", community);
    mv.addObject("pageTitle", "스터디위더스 : 커뮤니티상세보기");
    mv.setViewName("community/CommunityDetail");
    return mv;
  }

  @GetMapping("/community/updateform")
  public ModelAndView updateForm(int no) throws Exception {
    Community community = communityDao.findByNo(no);

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

    Community oldCommunity = communityDao.findByNo(community.getNo());
    if (oldCommunity == null) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    } 

    communityDao.update(community);
    sqlSessionFactory.openSession().commit();

    ModelAndView mv = new ModelAndView();
    mv.setViewName("redirect:detail?no=" + community.getNo());
    return mv;
  }

  @GetMapping("/community/delete")
  public ModelAndView delete(int no) throws Exception {

    Community community = communityDao.findByNo(no);

    if (community == null) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    }

    communityDao.delete(no);
    sqlSessionFactory.openSession().commit();

    ModelAndView mv = new ModelAndView();
    mv.setViewName("redirect:list?no=" + community.getCategory() + "&pageNo=1");
    return mv;
  }
}
