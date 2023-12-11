# 모듈 설명

---
- [core](core) - Enum / Exception / Utils
- [domain](domain) - DB Entity
- [redis](redis) - redisson 사용을 위한 모듈
- [api-server](api-server) - WEB API 서버


# 내부 인프라

---

- swagger-ui URL : http://localhost:8080/swagger-ui/index.html
- H2 DB URL: http://localhost:8080/h2-console


# SPEC

---

- JAVA 21
- H2 DB
- Redis 
- Redisson

# TABLE 구성

---

![table_info.png](img%2Ftable_info.png)



# 문제해결 위한 방어처리 / 고민사항

---
1. 동시성 이슈
- 정산 처리 및 입/출금시 동시성 방어를 위해 Redisson을 사용한 분산락을 구현하여 적용하였습니다.

2. 대용량 트래픽
- 대용량 트래픽 처리를 위해 가상스레드 사용
- WebFlux는 가독성이 좋지 않아 선호하지 않습니다. (실무에서 사용한 경험이 없습니다.)

3. 알림등과 같은 기능들은 비동기 이벤트로 처리
- 트랜잭션 시간 단축 및 트랜잭션 커밋 이후에 푸쉬 발송하도록 하였습니다.

# API 설명
---

1. 정산하기 생성요청 - POST /api/settlement/create
2. (상대방) 정산하기 - POST /api/settlement/settlement/pay
3. 리마인드 요청 - GET /api/settlement/settlement/remind/{settlementId}
4. 요청한 정산 목록  - GET /api/settlement/settlement/request-list
5. 요청받은 정산 목록 - GET /api/settlement/settlement/requested-list

