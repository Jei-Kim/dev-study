package com.swu.studywithus.dao;

import java.util.List;
import com.swu.studywithus.domain.Comment;

public interface CommentDao {

  void insert(Comment comment) throws Exception;

  Comment findByNo(int no) throws Exception;

  void delete(int no) throws Exception;

  List<Comment> findAll(int communityNo) throws Exception;
}
