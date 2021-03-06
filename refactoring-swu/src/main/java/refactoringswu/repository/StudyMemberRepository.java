package refactoringswu.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import refactoringswu.domain.Member;
import refactoringswu.domain.Study;

@Mapper
@Repository
public interface StudyMemberRepository {
	List<Study> findAllStudy(@Param("memberNo") int memberNo, @Param("myStatus") int statusNo, @Param("low") int low,
			@Param("high") int high) throws Exception;

	Study findByNoStudy(@Param("memberNo") int memberNo, @Param("studyNo") int studyNo, @Param("myStatus") int statusNo)
			throws Exception;

	List<Member> findAllMember(@Param("studyNo") int studyNo, @Param("myStatus") int statusNo) throws Exception;

	Member findByNoMember(@Param("memberNo") int memberNo, @Param("studyNo") int studyNo,
			@Param("myStatus") int statusNo) throws Exception;

	Map<String, Object> findByNoStatus(@Param("memberNo") int memberNo, @Param("studyNo") int studyNo) throws Exception;

	void insert(@Param("memberNo") int memberNo, @Param("studyNo") int studyNo, @Param("myStatus") int statusNo)
			throws Exception;

	void update(@Param("memberNo") int memberNo, @Param("studyNo") int studyNo, @Param("myStatus") int statusNo)
			throws Exception;

	void delete(@Param("memberNo") int memberNo, @Param("studyNo") int studyNo) throws Exception;
}
