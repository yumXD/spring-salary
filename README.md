# 💰 월급 관리 시스템

## 📌 목차

- [💲 프로젝트 소개](#-프로젝트-소개)
- [🧾 이 프로젝트를 시작하게 된 계기](#-이-프로젝트를-시작하게-된-계기)
- [🔥 프로젝트의 주요 특징](#-프로젝트의-주요-특징)
- [📅 설치 및 시작 방법](#-설치-및-시작-방법)
- [🔧 사용된 기술 스택](#-사용된-기술-스택)
- [📚 엔티티 특징 및 연관관계](#-엔티티-특징-및-연관관계)
- [✨ 주요 기능](#-주요-기능)
- [💭 프로젝트 후기](#-프로젝트-후기)
- [🤝 기여 및 참여](#-기여-및-참여)
- [🙏 감사의 말](#-감사의-말)
- [📝 License](#-License)

## 💲 프로젝트 소개

월급 관리 프로젝트는 근로자와 기업의 근무 관련 데이터를 체계적으로 관리하고 분석하는 웹 기반 어플리케이션입니다. 관리자가 근로자의 일일 근무 기록을 입력하여 자동으로 계산하여 일일 급여 및 총 급여를 정확하게 산출하는 것을 목표로 합니다.

## 🧾 이 프로젝트를 시작하게 된 계기

현대 사회에서 근로자와 기업 모두 근무 시간과 관련된 정확한 데이터 관리의 중요성이 갈수록 커지고 있습니다. 하지만 대부분의 기업에서는 아직 수기로 근무 기록을 관리하거나 복잡한 시스템에 의존하고 있습니다. 월급 관리 프로젝트는 이러한 문제점을 해결하고, 근로자와 기업 모두에게 투명하고 정확한 급여 계산 서비스를 제공하기 위해 시작되었습니다.

## 🔥 프로젝트의 주요 특징

### 1. 🧮 자동화된 급여 계산:

- 월급 관리의 핵심입니다! 각 직원의 근무 시간에 따라 자동으로 급여를 계산하여, 사람의 실수 없이 정확한 급여를 지급할 수 있게 해줍니다.

### 2. 📊 데이터 통합 관리:

- 모든 중요한 정보, 직원의 정보부터 근무기록까지 한 곳에서 관리. 데이터 일관성 및 관리의 효율성을 극대화합니다.

### 3. 🛠️ 확장성 높은 구조:

- 변화하는 요구 사항에 유연하게 대응할 수 있는 구조로 설계되었습니다. 새로운 기능의 추가나 변형이 아주 간편합니다.

### 4. 👁️ 직관적인 사용자 인터페이스:

- 부트스트랩을 활용하여 제작된 사용자 친화적인 디자인! 누구나 쉽고 빠르게 정보에 접근하거나 작업을 수행할 수 있습니다.

### 5. 🔐 다양한 권한 관리:

- 스프링 시큐리티로 강력한 보안! 사용자별 권한 설정과 관리로 시스템의 안전성을 한 단계 더 높였습니다.

## 📅 설치 및 시작 방법

### 1. 컴퓨터에 Mysql 설치합니다.

### 2. Mysql 실행하여 shop 이라는 스키마를 생성합니다.

```sql
create database salary default character set utf8 collate utf8_general_ci;
```

### 3. 저장소를 클론합니다.

```bash
git clone https://github.com/yumXD/spring-salary.git
```

### 4. 인텔리제이 혹은 다른 IDE를 실행하여 build.gradle로 엽니다.

### 4-1. 🔥 Build 실패시 아래의 방법으로 해결합니다.

```plaintext
Build, Exeution, Deployment → Build Tools → Build and run using → Intellij IDEA 변경
Build, Exeution, Deployment → Build Tools → Run tests using → Intellij IDEA 변경
Build, Exeution, Deployment → Build Tools → Gradle JVM → Java 17 이상 변경
Project Structure → SDK → Java 17 이상 변경
```

### 5. 환경설정

```plaintext
setting → Enable annotation processing
setting → Optimize imports on the fly (java)
setting → Editor → File Encoding → Transparent native-to-ascii conversion
```

### 6. 의존성 설치

```plaintext
build
```

### 7. 애플리케이션 시작!

```plaintext
Run ShopApplication
```

### 8. 웹 실행주소!

http://localhost:8080/

## 🔧 사용된 기술 스택

- **언어**: Java 17
- **버전 관리**: Git
- **IDE**: 인텔리제이 (IntelliJ IDEA)
- **프레임워크**: 스프링부트 3.1.4
- **데이터베이스**: MySQL
- **ORM**: JPA
- **빌드 도구**: Gradle
- **템플릿 엔진**: 타임리프 (Thymeleaf)
- **프론트엔드**: 부트스트랩 (Bootstrap)
- **백엔드**: Spring Restful
- **테스트**: JUnit

## 📚 엔티티 특징 및 연관관계

### 1. Employee (직원)

### 👥 주요 특징:

- 직원의 핵심으로서, 모든 근무 관련 정보의 출발점이자 기준입니다.
- 각 직원은 자신만의 고유한 근무표를 가짐으로써 개별적인 근무 상황을 체계적으로 관리할 수 있습니다.

### 🔗 연관관계:

- **WorkDetail**와 One-to-One 연관관계: 각 직원은 하나의 근무표를 소유합니다.

### 2. WorkDetail (근무표)

### 📅 주요 특징:

- 직원의 근무 관련 상세 정보를 집중적으로 관리합니다.
- 시급, 근무 총 급여, 근무 총 횟수 등 핵심 근무 정보를 중심으로 기록 및 관리됩니다.
- 다양한 근무 기록을 포괄적으로 관리함으로써, 정확한 급여 계산의 기준점이 됩니다.

### 🔗 연관관계:

- **WorkLog**와 One-to-Many 연관관계: 하나의 근무표에는 여러 근무 기록이 연결될 수 있습니다.
- **Employee**와 One-to-One 연관관계: 각 근무표는 특정 직원에게만 속합니다.

### 3. WorkLog (근무 기록)

### ⏰ 주요 특징:

- 직원의 실제 근무 활동을 구체적으로 기록합니다.
- 시작 시간부터 종료 시간까지의 근무 내용을 정밀하게 추적하여, 근무표의 정보를 실질적으로 채워나갑니다.
- 각 근무 기록은 개별적인 근무 상황을 반영하여, 근무표의 전체적인 근무 패턴을 이해하는 데 중요한 역할을 합니다.

- **WorkDetail**와 Many-to-One 연관관계: 여러 근무 기록은 하나의 근무표 아래에서 관리됩니다.

### 4. User (회원)

- 사용자 정보를 포함합니다.

## ✨ 주요 기능

### 1. 🔗 Spring Restful API:

- 직원 CRUD: 직원 정보의 생성, 조회, 수정, 삭제 기능 제공.
- 근무표 CRUD: 근무 일정과 관련된 정보의 관리.
- 근무기록 CRUD: 각 직원의 근무 시작 및 종료 시간 등 근무 관련 기록 관리.
- 회원 CRUD: 사용자 계정 정보 관리 기능.

### 2. 🔐 스프링 시큐리티를 이용한 보안 기능:

- 로그인/로그아웃: 안전하게 사용자 인증 및 세션 관리.
- 권한 관리: 다양한 사용자 역할에 따른 접근 권한 설정.

### 3. 🧪 단위 테스트:

- 각 API에 대한 테스트: API의 안정성 보장 및 오류 방지.

### 4. 🎨 부트스트랩을 이용한 반응형 웹 디자인:

- 다양한 디바이스에서의 최적화된 뷰 제공. 효율적이고 사용자 친화적인 인터페이스.

### 5. 📊 MySQL & JPA 데이터베이스 관리:

- MySQL: 안정적인 관계형 데이터베이스를 사용하여 데이터 저장.
- JPA: 객체와 테이블 간의 매핑을 통한 효율적인 데이터베이스 관리와 CRUD 작업 수행.

## 💭 프로젝트 후기

- 📊 이 프로젝트를 통해 월급 및 근무 기록 관리의 중요성을 더욱 깨닫게 되었습니다.
- 🌱 간단한 아이디어에서 시작하여 완성된 월급 관리 시스템을 보며, 개발의 즐거움을 느끼게 되었습니다.
- 🚀 초기 아이디어부터 구현까지의 여정은 물론 힘들었지만, 그 과정에서 많은 것을 배우게 되었습니다.
- 💼 각 기능의 구현 과정에서 많은 도전과 문제를 마주치게 되었지만, 그렇기에 얻는 만족감도 컸습니다.
- 🚴‍♂️ 많은 사용자의 편의를 고려하여 최적화 된 서비스를 제공하려는 노력을 지속적으로 했습니다.
- 🛠 이 월급 관리 프로젝트를 통해 기술적인 능력 뿐만 아니라 문제 해결 능력, 그리고 사용자 중심의 개발 방향성에 대해 더욱 깊이 이해하게 되었습니다.

- 🔥🔥 마지막으로, 이 모든 과정을 거치며 얻은 것은 단순한 코드나 기능을 넘어, 개발에 대한 무한한 열정과 끊임없는 성장을 추구하는 제 자신이었습니다. 이 경험은 앞으로의 개발자로서의 제 길에 큰 힘이 될 것이며, 언제나 더 나은 코드, 더 나은 서비스를 향한 무한한 도전의 열기를 잃지 않을 것입니다.

## 🤝 기여 및 참여

이 프로젝트는 🌱 오픈소스로 운영되며, 모든 기여와 피드백을 💖 환영합니다! 🚀 이슈 제출, 📥 풀 리퀘스트 및 💡 코드 리뷰를 통해 함께 더 나은 월급 관리 시스템을 만들어나갑시다.

프로젝트에 기여하고 싶다면 아래의 단계를 따라주세요:

1. 🍴 프로젝트를 포크합니다.
2. 🌿 새로운 branch를 생성합니다 (`git checkout -b feature/AmazingFeature`).
3. ✏️ 변경 사항을 커밋합니다 (`git commit -m 'Add some AmazingFeature'`).
4. 📤 branch에 변경 사항을 푸시합니다 (`git push origin feature/AmazingFeature`).
5. 🔀 Pull Request를 엽니다.

## 🙏 감사의 말

- 모든 오픈 소스 라이브러리와 도구를 제작하신 분들에게 감사드립니다.

## 📝 License

This project is [MIT](https://github.com/yumXD/spring-salary/blob/main/LICENSE) licensed.