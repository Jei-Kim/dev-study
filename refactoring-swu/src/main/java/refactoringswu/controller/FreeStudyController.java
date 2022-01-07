package refactoringswu.controller;

import java.sql.Date;
import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import refactoringswu.domain.Member;
import refactoringswu.domain.Study;
import refactoringswu.service.StudyMemberService;
import refactoringswu.service.StudyService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/freestudy")
public class FreeStudyController {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	@Autowired
	StudyService studyService;
	@Autowired
	StudyMemberService studyMemberService;

	
	 @GetMapping("/add")
	    public String addForm() {
	        return "freestudy/FreeStudyAddForm";
	    }
	 
	 @PostMapping("/add")
		public String add(Study freeStudy, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {

			Member freeMember = (Member) session.getAttribute("loginUser");
			freeStudy.setWriter(freeMember);

			studyService.insert(freeStudy);
			studyMemberService.insert(freeMember.getNo(), freeStudy.getNo(), Study.OWNER_STATUS);
			sqlSessionFactory.openSession().commit();
			
			/* 상세보기 구현 후 코드 바꾸기 
			 * 
	        redirectAttributes.addAttribute("freeStudy", freeStudy.getNo());
	        return "redirect:/freeStudy/{freeStudyNo}";
	        */
			return "redirect:../";
		}
	 
	 @GetMapping("/list")
		public String list(Model model) throws Exception {
		 
			Collection<Study> freeStudyList = studyService.findAll(0, 0);
			model.addAttribute("freeStudyList", freeStudyList);
	        return "freestudy/FreeStudyList";
		}
	 
	 @GetMapping("/{no}")
		public String detail(@PathVariable int no, HttpSession session, Model model) throws Exception { //long 

			int result; // 관심목록 추가 여부 확인용 임시 변수
			int participateResult;
			
			Member participant = null;

			Study freeStudy = studyService.findByNo(no);
			
			/* 진행상태 코드 - 시작 */
			
			if (new Date(System.currentTimeMillis()).compareTo(freeStudy.getStartDate()) == -1) {
				freeStudy.setStudyStatus(0); // 모집중

			} else if (new Date(System.currentTimeMillis()).compareTo(freeStudy.getEndDate()) == -1) {
				freeStudy.setStudyStatus(1); // 진행중

			} else {
				freeStudy.setStudyStatus(2); // 진행완료
			}

			/*  - 끝  */

			
			studyService.updateCount(no); // 조회수 코드

			/*  관심목록(좋아요) 추가 유무 확인용 코드  */
			Member freeMember = (Member) session.getAttribute("loginUser");
			// sessionConst 바꿔보기 

			if (freeMember != null) {
				result = studyService.checkLikesByMember(freeMember.getNo(), no);
				participant = studyMemberService.findByNoMember(freeMember.getNo(), no, Study.APPLICANT_STATUS);
			} else {
				result = 0;
			}

			if (participant == null) {
				participateResult = 0;

			} else {
				participateResult = 1;
			}
			/* - 끝 */

			model.addAttribute("freeStudy", freeStudy);
	        return "freestudy/FreeStudyDetail";
		}

	 @GetMapping("/{no}/update")
	 public String updateForm(@PathVariable int no, Model model) throws Exception {
		 Study freeStudy = studyService.findByNo(no);
		 model.addAttribute("freeStudy",freeStudy);
		 return "freestudy/FreeStudyUpdateForm";
	 }
	 
	 @PostMapping("/{no}/update")
		public String update(@PathVariable int no, @ModelAttribute Study freeStudy) throws Exception {
		 	freeStudy = studyService.findByNo(no);
			studyService.update(freeStudy);
			sqlSessionFactory.openSession().commit();
			return "redirect:/freestudy/{no}";
		}

		
	 // 삭제 전 확인 필요할듯 
	 @GetMapping("/delete")
		public String delete(int no) throws Exception {

			Study freeStudy = studyService.findByNo(no);
			
			if (freeStudy == null) {
				throw new Exception("해당 번호의 스터디가 없습니다.");
			}

			studyService.delete(no);
			sqlSessionFactory.openSession().commit();

			return "redirect:list";
		}
	 
	 @GetMapping("/search") // 검색화면 출력 오류 
		public String search(String keyword, Model model, HttpSession request) throws Exception {

			Collection<Study> freeStudyList = studyService.findByKeyword(keyword);
			
			model.addAttribute("freeStudyList",freeStudyList);
			//request.setAttribute()
		    return "freestudy/FreeStudyList";
		}
	 
	 /*
	@GetMapping("/freestudy/form")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageTitle", "스터디위더스 : 스터디등록");
		mv.setViewName("freestudy/FreeStudyAddForm");
		return mv;
	}

	@GetMapping("/freestudy/search")
	public ModelAndView search(String keyword) throws Exception {

		Collection<Study> freeStudyList = freeStudyDao.findByKeyword(keyword);

		ModelAndView mv = new ModelAndView();
		mv.addObject("freeStudyList", freeStudyList);
		mv.addObject("pageTitle", "스터디위더스 : 스터디검색결과");
		mv.setViewName("freestudy/FreeStudyList");
		return mv;
	}

	@GetMapping("/freestudy/findByCategory")
	public ModelAndView findByCategory(String keyword) throws Exception {

		Collection<Study> freeStudyList = freeStudyDao.findAllByKeyword(0, 0, keyword);

		ModelAndView mv = new ModelAndView();
		mv.addObject("freeStudyList", freeStudyList);
		mv.addObject("pageTitle", "스터디위더스 : 스터디검색결과");
		mv.setViewName("freestudy/FreeStudyList");
		return mv;
	}

	@PostMapping("/freestudy/add")
	public ModelAndView add(Study freeStudy, HttpSession session) throws Exception {

		Member freeMember = (Member) session.getAttribute("loginUser");
		freeStudy.setWriter(freeMember);

		freeStudyDao.insert(freeStudy);
		studyMemberDao.insert(freeMember.getNo(), freeStudy.getNo(), Study.OWNER_STATUS);
		sqlSessionFactory.openSession().commit();

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:list");
		return mv;
	}

	@GetMapping("/freestudy/list")
	public ModelAndView list() throws Exception {
		Collection<Study> freeStudyList = freeStudyDao.findAll(0, 0);

		ModelAndView mv = new ModelAndView();
		mv.addObject("freeStudyList", freeStudyList);
		mv.addObject("pageTitle", "스터디위더스 : 스터디목록");
		mv.setViewName("freestudy/FreeStudyList");
		return mv;
	}

	@GetMapping("/freestudy/detail")
	public ModelAndView detail(int no, HttpSession session) throws Exception {

		int result; // 관심목록 추가 여부 확인용 임시 변수
		int participateResult;
		Member participant = null;

		Study freeStudy = freeStudyDao.findByNo(no);

		if (freeStudy == null) {
			throw new Exception("해당 번호의 스터디가 없습니다.");
		}

		if (new Date(System.currentTimeMillis()).compareTo(freeStudy.getStartDate()) == -1) {
			freeStudy.setStudyStatus(0); // 모집중

		} else if (new Date(System.currentTimeMillis()).compareTo(freeStudy.getEndDate()) == -1) {
			freeStudy.setStudyStatus(1); // 진행중

		} else {
			freeStudy.setStudyStatus(2); // 진행완료
		}

		freeStudyDao.updateCount(no);

		Member freeMember = (Member) session.getAttribute("loginUser");

		if (freeMember != null) {
			result = freeStudyDao.checkLikesByMember(freeMember.getNo(), no);
			participant = studyMemberDao.findByNoMember(freeMember.getNo(), no, Study.APPLICANT_STATUS);
		} else {
			result = 0;
		}

		if (participant == null) {
			participateResult = 0;

		} else {
			participateResult = 1;
		}

		ModelAndView mv = new ModelAndView();
		mv.addObject("freeStudy", freeStudy);
		mv.addObject("result", result);
		mv.addObject("participateResult", participateResult);
		mv.addObject("pageTitle", "스터디위더스 : 스터디상세");
		mv.setViewName("freestudy/FreeStudyDetail");
		return mv;
	}

	@PostMapping("/freestudy/update")
	public ModelAndView update(Study freeStudy) throws Exception {

		Study oldFreeStudy = freeStudyDao.findByNo(freeStudy.getNo());

		if (oldFreeStudy == null) {
			throw new Exception("해당 번호의 스터디가 없습니다.");
		}

		freeStudyDao.update(freeStudy);
		sqlSessionFactory.openSession().commit();

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:detail?no=" + freeStudy.getNo());
		return mv;
	}

	@GetMapping("/freestudy/updateform")
	public ModelAndView updateForm(int no) throws Exception {
		Study freeStudy = freeStudyDao.findByNo(no);

		if (freeStudy == null) {
			throw new Exception("해당 번호의 스터디가 없습니다.");
		}

		ModelAndView mv = new ModelAndView();
		mv.addObject("freeStudy", freeStudy);
		mv.addObject("pageTitle", "스터디위더스 : 스터디수정");
		mv.setViewName("freestudy/FreeStudyUpdateForm");
		return mv;
	}

	@GetMapping("/freestudy/delete")
	public ModelAndView delete(int no) throws Exception {

		Study freeStudy = freeStudyDao.findByNo(no);
		if (freeStudy == null) {
			throw new Exception("해당 번호의 스터디가 없습니다.");
		}

		freeStudyDao.delete(no);
		sqlSessionFactory.openSession().commit();

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:list");
		return mv;
	}

	@GetMapping("/freestudy/interest/add")
	public ModelAndView interestAdd(int no, HttpSession session) throws Exception {

		Study oldFreeStudy = freeStudyDao.findByNo(no);

		if (oldFreeStudy == null) {
			throw new Exception("해당 번호의 스터디가 없습니다.");
		}

		int memberNo = ((Member) session.getAttribute("loginUser")).getNo();

		freeStudyDao.insertInterest(memberNo, no);
		sqlSessionFactory.openSession().commit();

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:../detail?no=" + no);
		return mv;
	}

	@GetMapping("/freestudy/apply")
	public ModelAndView apply(int no, HttpSession session) throws Exception {

		Member member = (Member) session.getAttribute("loginUser");

		studyMemberDao.insert(member.getNo(), no, Study.APPLICANT_STATUS);

		sqlSessionFactory.openSession().commit();

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:detail?no=" + no);
		return mv;
	}

	@GetMapping("/freestudy/applycancel")
	public ModelAndView applycancel(int no, HttpSession session) throws Exception {

		Member member = (Member) session.getAttribute("loginUser");

		studyMemberDao.delete(member.getNo(), no);

		sqlSessionFactory.openSession().commit();

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:detail?no=" + no);
		return mv;
	}

	@GetMapping("/freestudy/interest/delete")
	public ModelAndView interestDelete(int no, HttpSession session) throws Exception {

		Study oldFreeStudy = freeStudyDao.findByNo(no);

		if (oldFreeStudy == null) {
			throw new Exception("해당 번호의 스터디가 없습니다.");
		}

		int memberNo = ((Member) session.getAttribute("loginUser")).getNo();

		freeStudyDao.deleteInterest(memberNo, no);
		sqlSessionFactory.openSession().commit();

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:../detail?no=" + no);
		return mv;
	}

	@GetMapping("/mypage/freeregisterlist")
	public ModelAndView freeRegisterList(HttpSession session) throws Exception {

		Member freeMember = (Member) session.getAttribute("loginUser");

		Collection<Study> freeStudyList = studyMemberDao.findAllStudy(freeMember.getNo(), Study.OWNER_STATUS, 0, 0);

		ModelAndView mv = new ModelAndView();
		mv.addObject("freeStudyList", freeStudyList);
		mv.addObject("pageTitle", "스터디위더스 : 내가 생성한 스터디");
		mv.setViewName("../jsp/MyPage_freeStudy");
		return mv;
	}

	@GetMapping("/mypage/freeparticipatelist")
	public ModelAndView freeParticipateList(HttpSession session) throws Exception {

		Member freeMember = (Member) session.getAttribute("loginUser");

		Collection<Study> freeStudyList = studyMemberDao.findAllStudy(freeMember.getNo(), Study.PARTICIPANT_STATUS, 0,
				0);

		ModelAndView mv = new ModelAndView();
		mv.addObject("freeStudyList", freeStudyList);
		mv.addObject("pageTitle", "스터디위더스 : 내가 참여한 스터디");
		mv.setViewName("../jsp/MyPage_freeStudy");
		return mv;
	}

	@GetMapping("/mypage/freeinterestlist")
	public ModelAndView freeInterestList(HttpSession session) throws Exception {

		Member freeMember = (Member) session.getAttribute("loginUser");

		Collection<Study> freeStudyList = freeStudyDao.findAllInterest(freeMember.getNo(), 0, 0);

		ModelAndView mv = new ModelAndView();
		mv.addObject("freeStudyList", freeStudyList);
		mv.addObject("pageTitle", "스터디위더스 : 나의 관심목록");
		mv.setViewName("../jsp/freestudy/FreeStudyInterestList");
		return mv;
	}
*/
}
