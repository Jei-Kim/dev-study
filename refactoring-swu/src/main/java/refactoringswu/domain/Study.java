package refactoringswu.domain;

import java.sql.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false) // 해당 클래스의 필드값만 고려  
public class Study extends Content {

  public static final int APPLICANT_STATUS = 0;
  public static final int PARTICIPANT_STATUS = 1;
  public static final int OWNER_STATUS = 2;

  private String area; // 지역
  private int onOffLine; // 오프라인 = 0 , 온라인 = 1 
  private Date registeredDate; // 스터디 등록일
  private int viewCount; // 조회수
  private int members; // 스터디 현재 모집인원 수
  private int maxMembers; // 스터디 최대 모집인원 수
  private Date startDate; // 스터디 시작일
  private Date endDate; // 스터디 종료일
  private int studyStatus; // 모집중 = 0, 진행중 = 1, 진행완료 = 2
  private int likes; // 스터디 관심목록
  // 기본값 = 0, 
  // 삭제요청(유료) = 1 
  private int deleteStatus;
  private int price; // 유료 스터디 가격
  private String category;

}
