## Diary-Api 
> 스타트업이 간단한 게시판 형태의 일기 공유 mvp를 출시하고, 새로운 요구사항이 추가 되거나 트래픽이 늘어난다는 가정을 가지고 확장해나가는 개인 학습 프로젝트입니다.
<br>

## 기술 스택

- Kotlin (Jdk 17)
- Spring boot
- Spring Data Jpa & Line kotlin jdsl
- Gradle
- Junit5
- postgresql
- Redis   

<br>

## V2 (진행중)

- [x] 요구사항: back office를 만들어주세요! => 멀티 모듈 구축
- [ ] scale-out 환경 고려

<br>

## V1
- MVP 단계
- 1 프로세스, 1db를 가정한다.
- 조회 성능 개선은 페이징과 로컬 캐시로 해결

  <br>

### 유저
- 내 정보 조회
- 유저 정보 조회 
- 로그인 / 회원가입
#### 일기
- 내 일기 조회
- 일기 단건 조회
- 공개 일기 목록 조회
- 일기 작성/수정/삭제/공개/숨김
#### 댓글
- 댓글 작성/수정/삭제
- 댓글 조회
#### 친구
- 내 친구 목록 조회
- 친구 요청 목록 조회
- 친구 요청
- 요청 수락
#### 알림
- 전체 알림 조회
- 알림 읽음 처리
#### 신고
- 공개 일기 단건 신고

   
<br>

## 고민 포인트 (정리 진행중)

- 패키지 설계
- 연관 관계 설정
<br>

<!--
> @SpringbootTest가 아닌, 슬라이스 테스트(단위 테스트)를 처음 접해보면서 고민했던 내용들입니다. 

- [스프링 시큐리티 테스트](https://jjay2222.tistory.com/104) 
- [외부 API 테스트 방법](https://jjay2222.tistory.com/110)
- [QueryDSL 태스트 방법](https://jjay2222.tistory.com/112)
-->
    

<!--
### TO-DO 1차 개발 (DONE)
> 06~07월 개발 내용이며 나열한 것으로 대부분 순서대로 진행하였습니다.  
- [x] 도메인 모델 설계

- [x] Spring rest docs 의존성 추가

- [x] 로그인 (0605-06)
    - [x] Google : Oauth2 -> jwt -> jwt 기준 권한 관리
    - [x] Jwt Converter , Bearer Intercepter 구현
        - [x] Github, Google 공통 분모를 통해서 (id + organization) 을 통한 토큰 생성
        - [x] id + organization 을 통한 jwt 토큰 분해를 통한 Intercepter, MethodArgumentResolver 구현
    - [x] 유저 권한 부여 
        - [x] 관리자 : 전체 사이트에 대한 통계 정보를 확인할 수 있다. (관리자용 MethodArgumentResolver를 두면 될꺼같음.)
        - [x] 유저 : 대부분의 기능을 이용할 수 있음.
        - [x] 손님 : 가입 승인이 되기전의 사용자
        
- [x] LoginMember (토큰으로 로그인 한 사용자)
    - [x] read, update
    - [x] read, update 테스트 추가
    - [x] 권한 -> AllowRole 사용
    
- [x] 상품 (0607-0608)   
    - [x] 멀티 이미지 업로드
    - [x] 상품 crud
    - [x] 페이징
    
- [x] 댓글(0609-0610)
    - [x] 댓글 crud (PostId, Member를 기준으로)
    - [x] 사용자 Id를 기반으로 한 댓글 조회 기능
    - [x] Post 상세화면에서, 관련 댓글 함께 보여주기

- [x] 좋아요(0611-0613)
    - [x] 좋아요 crud (PostId, MemberId를 기준으로)
    - [x] 사용자 Id를 기반으로 좋아요 조회
    - [x] 인기 게시물 출력하기
   
- [x] 패키지 구조 변경

- [ ] 테스트 추가
    - [ ] Acceptance Tests
    - [ ] Layer & Domain Tests 
    - [x] S3 없이, 임의로 데이터 삽입 후 테스트
    - [x] S3 연동이후 임의로 추가한 데이터 변경
    - [ ] 테스트 코드 리팩토링

- [x] 검색 및 정렬
    - 정렬
        - [x] 카테고리를 기준으로 조회할 수 있다. -> category 
        - [x] 인기 상품을 조회할 수 있다. -> like
        - [x] 최근 작성일을 기준으로 조회할 수 있다. -> post 
        - [x] 특정 카테고리 및 최저 가격을 기준으로 조회할 수 있다. -> category & post 
 
    - 검색
        - [x] 글 제목의 키워드를 기준으로 검색할 수 있다.
        - [x] 작성자의 닉네임을 기준으로 검색할 수 있다.
         

- [ ] 관리자
    - [ ] 통계성 데이터 화면
    - [ ] 권한 관리(Guest, User, Admin)
    -->
