package refactoringswu.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import refactoringswu.domain.Member;
import refactoringswu.domain.Study;
import refactoringswu.repository.StudyMemberRepository;

@Service
@RequiredArgsConstructor
public class StudyMemberService {
	
	@Autowired 
	StudyMemberRepository studyMemberRepository;
	
		List<Study> findAllStudy(@Param("memberNo") int memberNo, @Param("myStatus") int statusNo, @Param("low") int low,
				@Param("high") int high) throws Exception {
			return studyMemberRepository.findAllStudy(memberNo, statusNo, low, high);
		}

		Study findByNoStudy(@Param("memberNo") int memberNo, @Param("studyNo") int studyNo, @Param("myStatus") int statusNo)
				throws Exception {
			return studyMemberRepository.findByNoStudy(memberNo, studyNo, statusNo);
		}

		List<Member> findAllMember(@Param("studyNo") int studyNo, @Param("myStatus") int statusNo) throws Exception {
			return studyMemberRepository.findAllMember(studyNo, statusNo);
		}

		Member findByNoMember(@Param("memberNo") int memberNo, @Param("studyNo") int studyNo,
				@Param("myStatus") int statusNo) throws Exception {
			return studyMemberRepository.findByNoMember(memberNo, studyNo, statusNo);
		}

		Map<String, Object> findByNoStatus(@Param("memberNo") int memberNo, @Param("studyNo") int studyNo) throws Exception {
			return studyMemberRepository.findByNoStatus(memberNo, studyNo);
		}

		public void insert(@Param("memberNo") int memberNo, @Param("studyNo") int studyNo, @Param("myStatus") int statusNo)
				throws Exception {
			studyMemberRepository.insert(memberNo, studyNo, statusNo);
		}

		void update(@Param("memberNo") int memberNo, @Param("studyNo") int studyNo, @Param("myStatus") int statusNo)
				throws Exception {
			studyMemberRepository.update(memberNo, studyNo, statusNo);
		}

		void delete(@Param("memberNo") int memberNo, @Param("studyNo") int studyNo) throws Exception {
			studyMemberRepository.delete(memberNo, studyNo);
		}
	

}
