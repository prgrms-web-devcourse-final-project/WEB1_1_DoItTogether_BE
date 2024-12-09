# 두잇투게더 (Do it Together)

## 0️⃣프로젝트 소개
두잇투게더는 함께 사는 구성원들의 집안일 관리를 돕는 서비스입니다.

### 🔗 배포 링크
**[두잇투게더 바로가기](https://doit-together.vercel.app/)**
![image](https://github.com/user-attachments/assets/9ebc33ea-32fa-4d95-9df0-c3153b9a828c)

- 무제한 그룹 생성 및 인원 제한 없는 그룹 관리
- AI 기반 개인 맞춤형 집안일 할당
- 직관적인 UI로 쉽게 사용하는 집안일 관리
- 주간/월간 통계를 통한 체계적인 관리

## 🛠 주요 기능

### 1. 집안일 관리

- 간편한 집안일 등록 및 관리
- 완료/미완료 체크 기능
- 그룹원 간 칭찬하기/찌르기 상호작용

### 2. AI 기반 선호도 분석

- 4가지 질문 기반 성향 분석
- OpenAI GPT 활용 키워드 도출
- 맞춤형 집안일 할당

### 3. 통계 시스템

- 주간/월간 통계 제공
- 4단계로 완료 단계 시각화
- 잔디를 통한 월간 현황 확인
- MVP 선정 시스템

### 4. 그룹 관리

- 편리한 그룹 초대 시스템
- 커스텀 가능한 프리셋 관리
- 체계적인 권한 관리

### 프로젝트 참고 자료
* [노션](https://www.notion.so/13f74aba5e74803a8759df041286ef93)

### 개발 기간
* 2024/11/15~2024/12/10

## 1️⃣기획서
---
* [기획서](https://www.notion.so/7142dd98f2424ff4aca5626aa11b0af1)

## 2️⃣개발툴
---
### 개발 도구

[![stackticon](https://firebasestorage.googleapis.com/v0/b/stackticon-81399.appspot.com/o/images%2F1733713072747?alt=media&token=78c51470-61cd-43db-abd5-9e21da05b0e2)](https://github.com/msdio/stackticon)

### 백엔드 기술 스택
| 소프트웨어           | 비고     |
|-----------------|--------|
| Spring Boot     | 3.3.5  |
| JAVA            | 3.0.0  |
| Spring Data JPA | 3.3.5  |
| Swagger         | 5.2.0  |
| JWT             | 0.11.5 |
| Spring Security | 6.3.4  |
| JUnit5          | 5.10.5 |

![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)


## 3️⃣프로젝트 협업 규칙
---
* [Git 컨벤션](https://www.notion.so/Git-Convention-BE-5b8e90810b8f47509f8c98fa4a8f1e30)


## 4️⃣프로젝트 구현 사항
---
### 1. 데이터 베이스 캐스팅
* MySQL: 실제 운영 데이터베이스로 사용. 회원, 성향, 집안일, 프리셋, 방 정보 저장
* H2 Database: 테스트 용도로 사용. 개발 및 테스트 환경에서 사용.

### 2. Spring Security
* 간단한 인증 및 인가 구현: 회원 정보 보호를 위한 기본적인 인증 로직 사용.

### 3. 테스트
* JUnit을 이용한 유닛 테스트: 각 서비스 로직 및 컨트롤러에 대한 테스트 구현.

### 4. Swagger API 문서화
* SpringDoc을 활용하여 API 문서 자동 생성.
* [API 명세서](https://3.39.64.195.nip.io/api)

### 5. ERD
![img_1.png](img_1.png)

### 6. 프로젝트 패키지 구성
<details><summary> 패키지 구성 </summary>
    application

    ├─main
    │  ├─java
    │  │  └─com
    │  │      └─doittogether
    │  │          └─platform
    │  │              ├─application
    │  │              │  └─global
    │  │              │      ├─code
    │  │              │      ├─exception
    │  │              │      │  ├─channel
    │  │              │      │  ├─housework
    │  │              │      │  ├─personality
    │  │              │      │  ├─preset
    │  │              │      │  ├─reaction
    │  │              │      │  ├─redis
    │  │              │      │  ├─statistics
    │  │              │      │  └─user
    │  │              │      └─response
    │  │              ├─business
    │  │              │  ├─channel
    │  │              │  ├─housework
    │  │              │  ├─invite
    │  │              │  ├─openai
    │  │              │  │  ├─dto
    │  │              │  │  └─util
    │  │              │  ├─personality
    │  │              │  ├─preset
    │  │              │  ├─reaction
    │  │              │  ├─redis
    │  │              │  ├─stastics
    │  │              │  └─user
    │  │              ├─common
    │  │              │  ├─config
    │  │              │  │  └─jwt
    │  │              │  ├─exception
    │  │              │  └─oauth2
    │  │              │      ├─dto
    │  │              │      └─filter
    │  │              ├─domain
    │  │              │  ├─entity
    │  │              │  └─enumeration
    │  │              ├─infrastructure
    │  │              │  ├─handler
    │  │              │  │  └─redis
    │  │              │  └─persistence
    │  │              │      ├─channel
    │  │              │      ├─housework
    │  │              │      ├─personality
    │  │              │      ├─preset
    │  │              │      ├─reaction
    │  │              │      └─user
    │  │              └─presentation
    │  │                  ├─controller
    │  │                  │  ├─channel
    │  │                  │  ├─housework
    │  │                  │  ├─personality
    │  │                  │  ├─preset
    │  │                  │  ├─reaction
    │  │                  │  ├─stastics
    │  │                  │  └─user
    │  │                  └─dto
    │  │                      ├─channel
    │  │                      │  ├─request
    │  │                      │  └─response
    │  │                      ├─housework
    │  │                      ├─personality
    │  │                      ├─preset
    │  │                      │  ├─request
    │  │                      │  └─response
    │  │                      ├─reaction
    │  │                      ├─stastics
    │  │                      └─user
    │  │                          ├─request
    │  │                          └─response
    │  └─resources
    │      └─redis
    └─test
    └─java
    └─com
    └─doittogether
    └─platform
    ├─application
    │  └─global
    │      ├─exception
    │      └─response
    └─business
    ├─channel
    ├─invite
    └─redis

</details>

---