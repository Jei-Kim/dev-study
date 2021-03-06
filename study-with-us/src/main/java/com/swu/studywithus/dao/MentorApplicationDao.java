package com.swu.studywithus.dao;

import java.util.List;
import com.swu.studywithus.domain.MentorApplication;

public interface MentorApplicationDao {

  void insert(MentorApplication mentorApplication) throws Exception;
  List<MentorApplication> findAll() throws Exception;
  MentorApplication findByNo(int no) throws Exception;
  void update(MentorApplication mentorApplication) throws Exception;
  void updateApproveStatus(int no) throws Exception;
  void updateRejectStatus(MentorApplication mentorApplication) throws Exception;
  void delete(int memberNo) throws Exception;
}
