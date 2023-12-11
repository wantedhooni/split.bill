package com.revy.api_server.service;

import com.revy.api_server.service.data.KakaoRoomNotiData;
import com.revy.api_server.service.data.SettlementNotiData;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Created by Revy on 2023.12.08
 */
public interface MessageEventListener {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    void noticeSettlementToUser(SettlementNotiData settlementNotiDto);

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    void noticeSettlementToKaKaoRoom(KakaoRoomNotiData kakaoRoomNotiDto);


}
