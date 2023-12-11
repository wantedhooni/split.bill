# 202312-rhwon1207-gmail.com


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

1. 정산하기 생성요청
POST /api/settlement/create

````
curl -X 'POST' \
  'http://localhost:8080/api/settlement/create' \
  -H 'accept: */*' \
  -H 'X-USER-ID: 1' \
  -H 'X-ROOM-ID: revy-kakao-room' \
  -H 'Content-Type: application/json' \
  -d '{
  "totalAmount": 50000,
  "settlementList": [
    {
      "userId": 1,
      "amount": 20000
    },
    {
      "userId": 2,
      "amount": 20000
    },
   {
      "userId": 3,
      "amount": 10000
    }
  ]
}'
````

2. 정산하기
- POST /api/settlement/settlement/pay

````
curl -X 'POST' \
  'http://localhost:8080/api/settlement/settlement/pay' \
  -H 'accept: */*' \
  -H 'X-USER-ID: 2' \
  -H 'Content-Type: application/json' \
  -d '{
  "settlementId": 1,
  "paidAmount": 20000
}'
````

Response
````
{
  "settlementUserInfoId": 2,
  "settlementId": 1,
  "settlementStatus": "COMPLETE",
  "totalAmount": 20000,
  "unsettledAmount": 0
}
````

3. 리마인드 요청
GET /api/settlement/settlement/remind/{settlementId}

````
curl -X 'GET' \
  'http://localhost:8080/api/settlement/settlement/remind/3' \
  -H 'accept: */*' \
  -H 'X-USER-ID: 1'
````

Response
````
{
  "message": "정상 처리 되었습니다."
}
````

4. 요청한 정산 목록
GET /api/settlement/settlement/request-list

````
curl -X 'GET' \
  'http://localhost:8080/api/settlement/settlement/request-list?page=1&size=10' \
  -H 'accept: */*' \
  -H 'X-USER-ID: 1'
````

Response
````
[
  {
    "requestUserId": 1,
    "totalAmount": 50000,
    "settledAmount": 20000,
    "settlementDetailList": [
      {
        "settlementUserInfoId": 1,
        "settlementId": 1,
        "settlementStatus": "COMPLETE",
        "totalAmount": 20000,
        "unsettledAmount": 0
      },
      {
        "settlementUserInfoId": 2,
        "settlementId": 1,
        "settlementStatus": "PREPARATION",
        "totalAmount": 20000,
        "unsettledAmount": 20000
      },
      {
        "settlementUserInfoId": 3,
        "settlementId": 1,
        "settlementStatus": "PREPARATION",
        "totalAmount": 10000,
        "unsettledAmount": 10000
      }
    ]
  },
  {
    "requestUserId": 1,
    "totalAmount": 50000,
    "settledAmount": 20000,
    "settlementDetailList": [
      {
        "settlementUserInfoId": 4,
        "settlementId": 2,
        "settlementStatus": "COMPLETE",
        "totalAmount": 20000,
        "unsettledAmount": 0
      },
      {
        "settlementUserInfoId": 5,
        "settlementId": 2,
        "settlementStatus": "PREPARATION",
        "totalAmount": 20000,
        "unsettledAmount": 20000
      },
      {
        "settlementUserInfoId": 6,
        "settlementId": 2,
        "settlementStatus": "PREPARATION",
        "totalAmount": 10000,
        "unsettledAmount": 10000
      }
    ]
  },
  {
    "requestUserId": 1,
    "totalAmount": 50000,
    "settledAmount": 20000,
    "settlementDetailList": [
      {
        "settlementUserInfoId": 7,
        "settlementId": 3,
        "settlementStatus": "COMPLETE",
        "totalAmount": 20000,
        "unsettledAmount": 0
      },
      {
        "settlementUserInfoId": 8,
        "settlementId": 3,
        "settlementStatus": "PREPARATION",
        "totalAmount": 20000,
        "unsettledAmount": 20000
      },
      {
        "settlementUserInfoId": 9,
        "settlementId": 3,
        "settlementStatus": "PREPARATION",
        "totalAmount": 10000,
        "unsettledAmount": 10000
      }
    ]
  },
  {
    "requestUserId": 1,
    "totalAmount": 50000,
    "settledAmount": 20000,
    "settlementDetailList": [
      {
        "settlementUserInfoId": 10,
        "settlementId": 4,
        "settlementStatus": "COMPLETE",
        "totalAmount": 20000,
        "unsettledAmount": 0
      },
      {
        "settlementUserInfoId": 11,
        "settlementId": 4,
        "settlementStatus": "PREPARATION",
        "totalAmount": 20000,
        "unsettledAmount": 20000
      },
      {
        "settlementUserInfoId": 12,
        "settlementId": 4,
        "settlementStatus": "PREPARATION",
        "totalAmount": 10000,
        "unsettledAmount": 10000
      }
    ]
  },
  {
    "requestUserId": 1,
    "totalAmount": 50000,
    "settledAmount": 20000,
    "settlementDetailList": [
      {
        "settlementUserInfoId": 13,
        "settlementId": 5,
        "settlementStatus": "COMPLETE",
        "totalAmount": 20000,
        "unsettledAmount": 0
      },
      {
        "settlementUserInfoId": 14,
        "settlementId": 5,
        "settlementStatus": "PREPARATION",
        "totalAmount": 20000,
        "unsettledAmount": 20000
      },
      {
        "settlementUserInfoId": 15,
        "settlementId": 5,
        "settlementStatus": "PREPARATION",
        "totalAmount": 10000,
        "unsettledAmount": 10000
      }
    ]
  }
]
````

5. 요청받은 정산 목록
GET /api/settlement/settlement/requested-list

````
curl -X 'GET' \
  'http://localhost:8080/api/settlement/settlement/requested-list?page=1&size=10' \
  -H 'accept: */*' \
  -H 'X-USER-ID: 1'
````

Response
````
[
  {
    "settlementUserInfoId": 1,
    "settlementId": 1,
    "settlementStatus": "COMPLETE",
    "totalAmount": 20000,
    "unsettledAmount": 0
  },
  {
    "settlementUserInfoId": 4,
    "settlementId": 2,
    "settlementStatus": "COMPLETE",
    "totalAmount": 20000,
    "unsettledAmount": 0
  },
  {
    "settlementUserInfoId": 7,
    "settlementId": 3,
    "settlementStatus": "COMPLETE",
    "totalAmount": 20000,
    "unsettledAmount": 0
  },
  {
    "settlementUserInfoId": 10,
    "settlementId": 4,
    "settlementStatus": "COMPLETE",
    "totalAmount": 20000,
    "unsettledAmount": 0
  },
  {
    "settlementUserInfoId": 13,
    "settlementId": 5,
    "settlementStatus": "COMPLETE",
    "totalAmount": 20000,
    "unsettledAmount": 0
  }
]
````