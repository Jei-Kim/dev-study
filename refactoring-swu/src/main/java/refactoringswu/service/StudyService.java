package refactoringswu.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import refactoringswu.domain.Study;
import refactoringswu.repository.StudyRepository;

@Service
@RequiredArgsConstructor
public class StudyService {

	@Autowired
	StudyRepository studyRepository;

	public List<Study> findAll(@Param("low") int low, @Param("high") int high) throws Exception {
		return studyRepository.findAll(low, high);

	};

	public List<Study> findAllRecruit(@Param("low") int low, @Param("high") int high) throws Exception {
		return studyRepository.findAllRecruit(low, high);
	}

	public List<Study> findAllOngoing(@Param("low") int low, @Param("high") int high) throws Exception {
		return studyRepository.findAllOngoing(low, high);
	}

	public List<Study> findAllFinish(@Param("low") int low, @Param("high") int high) throws Exception {
		return studyRepository.findAllFinish(low, high);
	}

	public List<Study> findAllByKeyword(@Param("low") int low, @Param("high") int high,
			@Param("category") String keyword) throws Exception {
		return studyRepository.findAllByKeyword(low, high, keyword);
	}

	public List<Study> findByKeyword(String keyword) throws Exception {
		return studyRepository.findByKeyword(keyword); // low&high?
	}

	public Study findByNo(int no) throws Exception {
		return studyRepository.findByNo(no);
	}

	public void insert(Study study) throws Exception {
		studyRepository.insert(study);
	};

	public void update(Study study) throws Exception {
		studyRepository.update(study);
	};

	public void updateCount(int no) throws Exception {
		studyRepository.updateCount(no);
	};

	public void delete(int no) throws Exception {
		studyRepository.delete(no);
	};

	// 나의 관심목록 스터디
	public List<Study> findAllInterest(@Param("memberNo") int memberNo, @Param("low") int low, @Param("high") int high)
			throws Exception {
		return studyRepository.findAllInterest(memberNo, low, high);
	}

	public int findMyInterest(@Param("memberNo") int memberNo, @Param("studyNo") int studyNo) throws Exception {
		return studyRepository.findMyInterest(memberNo, studyNo);
	}

	// 관심목록 추가
	public void insertInterest(@Param("memberNo") int memberNo, @Param("studyNo") int studyNo) throws Exception {
		studyRepository.insertInterest(memberNo, studyNo);
	}

	public void deleteInterest(@Param("memberNo") int memberNo, @Param("studyNo") int studyNo) throws Exception {
		studyRepository.deleteInterest(memberNo, studyNo);
	}

	public int checkLikesByMember(@Param("memberNo") int memberNo, @Param("studyNo") int studyNo) throws Exception {
		return studyRepository.checkLikesByMember(memberNo, studyNo);
	}

}
