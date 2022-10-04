# GREEN STEP

![003](https://user-images.githubusercontent.com/108536712/190843747-b6555f56-30e2-452d-9279-c25f704570bc.png)

## 프로젝트 소개


**우리가 그리는 푸른 발자국이 지구를 구하도록** 
**하루하루 일상 속에서 지구를 지키는 한 발, 그린 스탭.**

>
> 매일매일 달라지는 환경 미션을 달성하고 환경 보호에 이바지해요!  
>
> 인증 받은 사진을 공유하고 서로에게 칭찬을 나눠요.   
>
> 소셜로그인으로 간편하게! PWA로 편하게! 함께해요!
> 
> **개발기간**
> 2022.08.26- 2022. 10.05   
> 
> 바로가기: <https://greenstepapp.com>    -> 모바일로 접속해주세요!
> 



## 👥팀원소개
- BE: 김현서, 심규현, 하지혜
- FE: 강인호, 김은혜, 우수진
- Designer: 이시은   


## 🕸️아키텍처
![그린스텝 발표자료 _ 복사본-005](https://user-images.githubusercontent.com/108536712/193769500-054076d4-ccc5-46c9-a7f9-6e6163476e25.png)



## ⚒️기술스택  

**백엔드**   
<div align='left' >
    <img src="https://img.shields.io/badge/java-007396?style=flat-square&logo=java&logoColor=white">
    <img src="https://img.shields.io/badge/spring-6DB33F?style=flat-square&logo=spring&logoColor=white">
    <img src="https://img.shields.io/badge/springboot-6DB33F?style=flat-square&logo=springboot&logoColor=black">
    <img src="https://img.shields.io/badge/gradle-02303A?style=flat-square&logo=gradle&logoColor=black">
    <img src="https://img.shields.io/badge/mysql-4479A1?style=flat-square&logo=mysql&logoColor=black">
    <img src="https://img.shields.io/badge/amazon%20aws-f7f7f7?style=flat-square&logo=amazon%20aws&logoColor=f89400">
    <img src="https://img.shields.io/badge/CodeDepoly-1F497D?style=flat-square&logo=CodeDepoly&logoColor=white">
    <img src="https://img.shields.io/badge/S3-FC5230?style=flat-square&logo=S3&logoColor=white">
    <img src="https://img.shields.io/badge/CloudFront-FF9900?style=flat-square&logo=CloudFront&logoColor=white">
    <img src="https://img.shields.io/badge/github-181717?style=flat-square&logo=github&logoColor=white">
    <img src="https://img.shields.io/badge/github%20actions-0769AD?style=flat-square&logo=github%20actions&logoColor=white">
</div> 
<br/>    <br/> 

**프론트엔드**
<div align='left'>
    <img src="https://img.shields.io/badge/html-E34F26?style=flat-square&logo=html5&logoColor=white">
    <img src="https://img.shields.io/badge/css-1572B6?style=flat-square&logo=css3&logoColor=white">
    <img src="https://img.shields.io/badge/sass-CC6699?style=flat-square&logo=sass&logoColor=white">
    <img src="https://img.shields.io/badge/javascript-F7DF1E?style=flat-square&logo=javascript&logoColor=black">
    <img src="https://img.shields.io/badge/react-61DAFB?style=flat-square&logo=react&logoColor=black">
    <img src="https://img.shields.io/badge/redux-764ABC?style=flat-square&logo=redux&logoColor=black">
    <img src="https://img.shields.io/badge/figma-F24E1E?style=flat-square&logo=figma&logoColor=black">
    <img src="https://img.shields.io/badge/aws-232F3E?style=flat-square&logo=aws&logoColor=black">
    <img src="https://img.shields.io/badge/github-white?style=flat-square&logo=github&logoColor=black">
    <img src="https://img.shields.io/badge/github%20actions-0769AD?style=flat-square&logo=github%20actions&logoColor=white">
</div> 
<br/><br/>
  

   




## 기능 선정 과정
| # | 기능 명 | 결정된 기술 | 선택한 이유 |  #issue or WIKI |
|---|---|---|---|---|
| 1 |  배포 | AWS  | AWS 하나로 모든 배포 환경을 체크 할 수 있어 편리하며 새로운 시스템을 도입하는 부담감이 적다.  |  |   
| 2 |  실시간 알람 | SSE  | 단방향 통신으로 핸드셰이크 발생량이 웹소켓보다 작고 가벼워 더 합리적이다. |  [#127](https://github.com/GreenStep99/Backend/issues/127)  |   
| 3 |  이미지 리사이징 | CDN, BufferedImage  | 촬영 후 사진이 바로 전송되기 때문에 서버에서 리사이징 하는 것이 적합하다는 의견이 나왔고 추가적으로 CDN을 이용하여 프론트의 랜더링 속도를 안정화 하기로 결정  | [#131](https://github.com/GreenStep99/Backend/issues/131) |    
| 4 |  History Table |  envers | 간단한 어노테이션으로 특정 테이블의 변경 내역을 자동 생성해주는 점에서 직접 class를 생성하는 것보다 리소스를 적게 차지한다고 판단함  |[#69](https://github.com/GreenStep99/Backend/issues/69)  |



## 핵심기능
![004](https://user-images.githubusercontent.com/108536712/190843752-6c950e4e-0f9c-49b0-8795-d70d40abcb5e.png)   


## 기능상세
| # | 페이지 | 기능 명 | 사용된 기술 | #issue|  
|---|---|---|---|---|
| 1 |  마이 페이지 | 카카오톡 소셜 로그인, 회원탈퇴  | JWT, Kakao Login REST Api |   |
| 2 |  마이 페이지 | 포스트 아카이빙 기능  | Spring data JPA |   |
| 3 |  마이 페이지 | 포스트 숨기기 기능  | Spring data JPA   |  |
| 4 |  마이 페이지 | 포인트 적립 기능 | Spring data JPA   |   |
| 5 | 미션 페이지  | 미션 리스트 기간별로 랜덤하게 제시  | Scheduler, JPQL, native query |   |
| 6 | 미션 페이지  | 미션 사진 촬영 기능  | S3, base64 decoding |   |
| 7 | 미션 페이지  | 미션 인증 수락 기능  | Spring data JPA   |   |
| 8 | 미션 페이지  | 미션 인증 피드 업로드 기능  | Spring data JPA   |   |
| 9 | 피드 페이지  | 전체 피드 기능  | Spring data JPA   |   |
| 10 | 피드 페이지  | 좋아요 기능  | Spring data JPA   |   |
| 11 | 피드 페이지  | 피드 숨기기 기능  | Spring data JPA   |   |
| 12 | 기타  | 실시간 알림  |  SSE |   |  
| 13 | 기타  | 이미지 리사이징  |  BufferedImage |    | 
| 14 | 기타  | 내역 전용 테이블 만들기 | enver |  |



## 데모영상    

[![Watch the video](https://img.youtube.com/vi/L4lZPjanLBY/hqdefault.jpg)](https://youtu.be/L4lZPjanLBY)

## 트러블 슈팅

| # | 문제상황 | 원인 | 해결한 방법 | 상세(WIKI)|   
|---|---|---|---|---|
| 1 |  update 적용 안되는 문제 | 변경감지가 제대로 작동하지 않고 시행중 데이터가 소실될 위험이 있는 문제  | @Transactional과 member을 토큰이 아닌 memberRepository에서 직접 불러옴으로서 JPA가 적용될 수 있도록 코드 변경  |[PatchMapping - update 적용 안되는 문제](https://github.com/GreenStep99/Backend/wiki/Trouble-Shooting#patchmapping-jpa "위키로 이동합니다.")|   
| 2 |  Base64파일 업로드 기능 구현 |multipartFile 변환에 익숙해져 있어 Base64형식에 대한 이해도와 변환 코드에 대한 이해도가 낮아 생긴 문제  | multipart파일로 변환하지 않고 ByteArrayStream을 사용하여 Base64를 바로 InputStream으로 변환  | [촬영한 사진 업로드](https://github.com/GreenStep99/Backend/wiki/Trouble-Shooting#%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%97%85%EB%A1%9C%EB%93%9C-%EC%A4%91-%EC%B4%AC%EC%98%81%ED%95%9C-%EC%82%AC%EC%A7%84-%EC%97%85%EB%A1%9C%EB%93%9C--base64-%ED%8C%8C%EC%9D%BC- "위키로 이동합니다.")  |
| 3 | @RequestBody로 단일 값을 받아오지 못하는 오류 | API통신, Json이해 부족  |   | [@RequestBody에서 스트링 값만 받아오는 경우](https://github.com/GreenStep99/Backend/wiki/Trouble-Shooting#requestbody%EC%97%90%EC%84%9C-%EC%8A%A4%ED%8A%B8%EB%A7%81-%EA%B0%92%EB%A7%8C-%EB%B0%9B%EC%95%84%EC%98%A4%EB%8A%94-%EA%B2%BD%EC%9A%B0 "위키로 이동합니다.") |
| 4 |  이미지 리사이징 | S3서버 부하 낮추고 랜더링 속도 개선을 목적으로 진행한 기능 구현 | BufferedImage를 사용하여 같은 비율로 최소값을 모바일 환경에 맞는 320으로  수정하여 S3에 저장, 이후 CDN을 연결하여 로딩 시간 축소  |  [이미지 리사이징](https://github.com/GreenStep99/Backend/wiki/Trouble-Shooting#%EC%9D%B4%EB%AF%B8%EC%A7%80-%EB%A6%AC%EC%82%AC%EC%9D%B4%EC%A7%95 "위키로 이동합니다.")   |
| 5 |  깃 액션을 이용한 빌드 실패 |properties 파일이 ignore 처리 되어 있어서 깃 허브 레포지토리에 존재하지 않아 생기는 문제   |deploy.yml 파일에 존재하는 깃 액션 과정에 properties 파일을 생성하는 과정을 추가   |  [깃 액션을 이용한 빌드 실패](https://github.com/GreenStep99/Backend/wiki/Trouble-Shooting#%EA%B9%83-%EC%95%A1%EC%85%98%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EB%B9%8C%EB%93%9C-%EC%8B%A4%ED%8C%A8 "위키로 이동합니다.")   |
| 6 |  ACM 인증서 발급이 계속해서 지연되는 문제 |CNAME 레코드를 통해 연결된 호스팅 영역의 NS 레코드 값과 구매한 도메인의 NS 레코드 값이 일치 하지 않아 생기는 문제   |호스팅 영역의 NS 레코드 값으로 구매한 도메인의 NS 레코드 값을 변경해줌   |  [ACM 인증서 발급이 계속해서 지연되는 문제](https://github.com/GreenStep99/Backend/wiki/Trouble-Shooting "위키로 이동합니다.")   |
| 7 |  Code Deploy를 이용한 서버 배포 실패 |health checker(상태 검사) 부분에서 계속 실패하여 발생하는 문제   |health checker의 default로 설정된 성공 코드 200을 서버 환경에 맞게 404로 변경해줌   |  [Code Deploy를 이용한 서버 배포 실패](https://github.com/GreenStep99/Backend/wiki/Trouble-Shooting#code-deploy%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%84%9C%EB%B2%84-%EB%B0%B0%ED%8F%AC-%EC%8B%A4%ED%8C%A8 "위키로 이동합니다.")   |
| 8 |  Code Deploy를 이용한 서버 배포 과정이 매우 느리게 진행되는 문제 |health checker과정에서 5분이상 소모되기에 생기는 문제   |health checker를 30초까지만 진행되게 설정을 변경해줌   |  [Code Deploy를 이용한 서버 배포 과정이 매우 느리게 진행되는 문제](https://github.com/GreenStep99/Backend/wiki/Trouble-Shooting#code-deploy%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%84%9C%EB%B2%84-%EB%B0%B0%ED%8F%AC-%EA%B3%BC%EC%A0%95%EC%9D%B4-%EB%A7%A4%EC%9A%B0-%EB%8A%90%EB%A6%AC%EA%B2%8C-%EC%A7%84%ED%96%89%EB%90%98%EB%8A%94-%EB%AC%B8%EC%A0%9C "위키로 이동합니다.")   |
| 9 |  N+1 문제 |n:1 연관관계 매핑 테이블을 조회해 발생하는 문제   |JPA로 작성한 쿼리문을 JPQL로 변경하여 Fetch Join을 적용   |  [N+1 문제](https://github.com/GreenStep99/Backend/wiki/Trouble-Shooting#n1-%EB%AC%B8%EC%A0%9C "위키로 이동합니다.")   |
| 10 |  S3 백업 | S3에 대한 이해도를 늘리고, 자료 보관과 리커버리에 대한 경각심 강화  | S3의 버전 관리를 설정하고 사전에 백업 계획을 작성하여 객체가 손실되더라도 복원할 수 있도록 준비해야하며, S3자체가 중요도가 높을 경우, 백업이 가능한 S3 옵션을 사용하거나 지역간 교차 백업, 클라우드 백업등을 이용하여 만약의 상황 준비   |  [S3 객체 복원](https://github.com/GreenStep99/Backend/wiki/Trouble-Shooting#주소 "위키로 이동합니다.")   |

