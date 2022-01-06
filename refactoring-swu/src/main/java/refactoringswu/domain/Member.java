package refactoringswu.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class Member {
  public static final int GENERAL_MEMBER = 0;
  public static final int MENTOR = 1;
  public static final int ADMIN = 2;

  private int no; // 회원 번호
  private String name; // 회원 이름
  private String email; // 회원 이메일
  private String password; // 회원 비밀번호
  private String phoneNumber; // 회원 휴대폰 번호
  private Date registeredDate; // 회원 가입일
  private int status; // 회원 상태
  private Date lastDate; // 회원 마지막 접속일
  private int userAccessLevel; // 권한
  private String nickname;

  //private AttachmentFile memberFile;
}
