package com.revy.api_server.service.impl;

import com.revy.api_server.service.MessageEventListener;
import com.revy.api_server.service.data.KakaoRoomNotiData;
import com.revy.api_server.service.data.SettlementNotiData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by Revy on 2023.12.08
 */

@Slf4j
@Service
public class MessageEventListenerImpl implements MessageEventListener {


    @Override
    public void noticeSettlementToKaKaoRoom(KakaoRoomNotiData kakaoRoomNotiDto) {
        if (kakaoRoomNotiDto == null) {
            return;
        }
        log.info("[noticeKaKaoRoom] kakaoRoomId:{}, message:{}", kakaoRoomNotiDto.getKakaoRoomId(), kakaoRoomNotiDto.getMessage());
    }

    @Override
    public void noticeSettlementToUser(SettlementNotiData settlementNotiDto) {
        if (settlementNotiDto == null) {
            return;
        }
        log.info("[noticeSettlementToUser] NoticeMessage: {}", settlementNotiDto.getMessage());
    }
}
