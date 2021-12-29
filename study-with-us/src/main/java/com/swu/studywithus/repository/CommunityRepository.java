package com.swu.studywithus.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.swu.studywithus.domain.Community;

// 역할
// - 커뮤니티 데이터를 처리하는 객체 사용법을 정의한다.
//
@Repository
@Mapper
public interface CommunityRepository {
  void insert(Community community) throws Exception;

  void insertLikes (@Param("memberNo") int memberNo, @Param("cmntNo") int communityNo) throws Exception;

  List<Community> findAll(int categoryNo) throws Exception;

  List<Community> findByKeyword(@Param("keyword") String keyword,@Param("categoryNo") int categoryNo) throws Exception;

  Community findByNo(int no) throws Exception;

  int communityCount(int categoryNo) throws Exception;

  void update(Community community) throws Exception;

  void delete(int no) throws Exception;

  void deleteLikes (@Param("memberNo") int memberNo, @Param("cmntNo") int communityNo) throws Exception;

  void updateCount(int no) throws Exception;

  int checkLikesByMember (@Param("memberNo") int memberNo, @Param("cmntNo") int communityNo) throws Exception;
}
