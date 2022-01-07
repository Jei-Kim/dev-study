package refactoringswu.domain;

import lombok.Data;

@Data
public class Content {
  private int no; // 게시글 번호
  private Member writer; // 게시글 작성자
  private String title; // 게시글 제목
  private String content; // 게시글 내용

}
