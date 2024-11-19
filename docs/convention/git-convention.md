## 브랜치 전략
![gitflow](https://github.com/user-attachments/assets/68e56531-ebcf-4f03-9120-ae68a382da08)

- **master** : 배포가 가능한 가장 메인이 되는 브랜치
- **hotfix** : 빠르게 버그를 수정해야 할 때 사용하는 브랜치
- **release** : 프로젝트 배포를 준비하기 위해 사용하는 브랜치
- **develop** : 개발 과정에서 사용하는 중심 브랜치
    - **feat** : 각 기능을 구현할 때 사용하는 브랜치
    

이슈에는 고유 넘버가 있음 (브랜치를 만들거나, 커밋을 할 때 이슈 넘버를 이용해주면 해당 브랜치 / 커밋이 어떤 이슈로 인해 진행되었는지 알 수 있어서 편하다. )

기능 별로 쪼개서 구현하도록 하자.

</aside>

## 커밋 컨벤션

| 커밋 유형 | 의미 |
| --- | --- |
| `feat` | 새로운 기능 추가 |
| `fix` | 버그 수정 |
| `docs` | 문서 수정 |
| `style` | 코드 formatting, 세미콜론 누락, 코드 자체의 변경이 없는 경우 |
| `refactor` | 코드 리팩토링 |
| `test` | 테스트 코드, 리팩토링 테스트 코드 추가 |
| `chore` | 패키지 매니저 수정, 그 외 기타 수정 ex) .gitignore |
| `design` | CSS 등 사용자 UI 디자인 변경 |
| `comment` | 필요한 주석 추가 및 변경 |
| `rename` | 파일 또는 폴더 명을 수정하거나 옮기는 작업만인 경우 |
| `remove` | 파일을 삭제하는 작업만 수행한 경우 |
| `breaking change` | 커다란 API 변경의 경우 |
| `hotfix` | 급하게 치명적인 버그를 고쳐야 하는 경우 |

<aside>
⭐

pull request merge 시 모든 팀원의 코드 리뷰와 승인 시 가능 (1명 이상)

</aside>

<aside>
⭐

merge 시 rebase를 진행함

</aside>

<aside>
⭐

커밋 타입은 소문자로 한다.

</aside>

## Branch 컨벤션

<aside>
⭐

태스크가 생기면 이슈를 판다(ex. #17)

-> 해당 이슈를 처리할 브랜치를 판다(ex. feat/#17) : 항상 default 브랜치에서 pull(default 브랜치를 보통 develop 브랜치로 네이밍하고 최신 상태를 유지함)
-> feat/#17에서 개발을 시작
-> 구현이 완료되면 PR날리고, 리뷰 후 변경 내용 반영한 후 develop 브랜치에 머지 (feat/#17 -> develop)

</aside>

<aside>
⭐

커밋 태그/이슈번호/기능명 ⇒ 이슈 트래커 브랜치 사용 시

like `feat/#23/user-service-update`

</aside>
