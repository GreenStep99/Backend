# GREEN STEP

![003](https://user-images.githubusercontent.com/108536712/190843747-b6555f56-30e2-452d-9279-c25f704570bc.png)

우리가 그리는 푸른 발자국이 지구를 구하도록 
하루하루 일상 속에서 지구를 지키는 한 발, 그린 스탭.     

바로가기: <https://greenstepapp.com>    
-> 모바일로 접속해주세요!   



**개발기간**
2022.08.26- 2022. 10.05   




## 👥팀원소개
- BE: 김현서, 심규현, 하지혜
- FE: 강인호, 김은혜, 우수진
- Designer: 이시은   



## ⚒️기술스택
- **백엔드**   


- **프론트엔드**   




## 기능 선정 과정
| # | 기능 명 | 고려한 기술 | 선택한 이유 |   
|---|---|---|---|
| 1 |  배포 | AWS  |   |   
| 2 |  실시간 알람 | SSE  |   |   
| 2 |  이미지 리사이징 | CDN, BufferedImage  |   |     



## 핵심기능
![004](https://user-images.githubusercontent.com/108536712/190843752-6c950e4e-0f9c-49b0-8795-d70d40abcb5e.png)   


## 기능상세
40
| # | 페이지 | 기능 명 | 사용된 기술 | #issue|  
|---|---|---|---|
| 1 |  마이 페이지 | 카카오톡 소셜 로그인, 회원탈퇴  |   |   |
| 2 |  마이 페이지 | 포스트 아카이빙 기능  |   |   |
| 3 |  마이 페이지 | 포스트 숨기기 기능  |   |  |
| 4 |  마이 페이지 | 포인트 적립 기능 |   |   |
| 5 | 미션 페이지  | 미션 리스트 기간별로 랜덤하게 제시  |   |   |
| 6 | 미션 페이지  | 미션 사진 촬영 기능  | S3 |   |
| 7 | 미션 페이지  | 미션 인증 수락 기능  |   |   |
| 8 | 미션 페이지  | 미션 인증 피드 업로드 기능  |   |   |
| 9 | 피드 페이지  | 전체 피드 기능  |   |   |
| 10 | 피드 페이지  | 좋아요 기능  |   |   |
| 11 | 피드 페이지  | 피드 숨기기 기능  |   |   |
| 12 | 기타  | 실시간 알람  |  SSE |   |  
| 13 | 기타  | 이미지 리사이징  |  BufferedImage |    | 
| 14 | 기타  | 내역 전용 테이블 만들기 | enver |  |



## 데모영상    




## 트러블 슈팅

| # | 문제상황 | 원인 | 해결한 방법 | 상세(WIKI)|   
|---|---|---|---|---|
| 1 |  update 적용 안되는 문제 |   |   |[PatchMapping - update 적용 안되는 문제](https://github.com/GreenStep99/Backend/wiki/Trouble-Shooting#patchmapping-jpa "위키로 이동합니다.")|   
| 2 |  Base64파일 업로드 기능 구현 | 레퍼런스와 S3에 대한 이해도 부족  |   | [촬영한 사진 업로드](https://github.com/GreenStep99/Backend/wiki/Trouble-Shooting#%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%97%85%EB%A1%9C%EB%93%9C-%EC%A4%91-%EC%B4%AC%EC%98%81%ED%95%9C-%EC%82%AC%EC%A7%84-%EC%97%85%EB%A1%9C%EB%93%9C--base64-%ED%8C%8C%EC%9D%BC-"위키로 이동합니다.")  |
| 3 | @RequestBody로 단일 값을 받아오지 못하는 오류 | API통신, Json이해 부족  |   | [@RequestBody에서 스트링 값만 받아오는 경우](https://github.com/GreenStep99/Backend/wiki/Trouble-Shooting#requestbody%EC%97%90%EC%84%9C-%EC%8A%A4%ED%8A%B8%EB%A7%81-%EA%B0%92%EB%A7%8C-%EB%B0%9B%EC%95%84%EC%98%A4%EB%8A%94-%EA%B2%BD%EC%9A%B0 "위키로 이동합니다.") |
| 4 |  이미지 리사이징 | S3서버 부하 낮추고 랜더링 속도 개선을 목적 |   |  [이미지 리사이징](https://github.com/GreenStep99/Backend/wiki/Trouble-Shooting#%EC%9D%B4%EB%AF%B8%EC%A7%80-%EB%A6%AC%EC%82%AC%EC%9D%B4%EC%A7%95 "위키로 이동합니다.")   |
| 5 |  자동배포 |   |   |  [자동배포](https://github.com/GreenStep99/Backend/wiki/Trouble-Shooting#주소 "위키로 이동합니다.")   |
| 6 |  SSE |   |   |  [SSE](https://github.com/GreenStep99/Backend/wiki/Trouble-Shooting#주소 "위키로 이동합니다.")   |
| 7 |  S3 백업 | s3에 대한 이해 부족  |   |  [S3 객체 복원](https://github.com/GreenStep99/Backend/wiki/Trouble-Shooting#주소 "위키로 이동합니다.")   |

