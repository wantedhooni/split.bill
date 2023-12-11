package com.revy.api_server.service.data;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Revy on 2023.12.08
 */

@Getter
@NoArgsConstructor
public class KakaoRoomNotiData {

    private String kakaoRoomId;

    private String message;

    public KakaoRoomNotiData(String kakaoRoomId, String message) {
        this.kakaoRoomId = kakaoRoomId;
        this.message = message;
    }
}
