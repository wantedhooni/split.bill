package com.revy.api_server.endpoint;

import com.revy.api_server.endpoint.req.CreateSettlementReq;
import com.revy.api_server.endpoint.req.PageReq;
import com.revy.api_server.endpoint.req.PaySettlementReq;
import com.revy.api_server.endpoint.res.CommonRes;
import com.revy.api_server.service.SettlementService;
import com.revy.api_server.service.data.CreateSettlementData;
import com.revy.api_server.service.data.PaySettlementResultData;
import com.revy.api_server.service.data.SettlementResultData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Revy on 2023.12.05
 */

@Slf4j
@RestController
@RequestMapping("/api/settlement")
@RequiredArgsConstructor
public class SettlementApi {

    private final SettlementService settlementService;

    /**
     * 정산하기를 생성한다.
     *
     * @param userId
     * @param kakaoRoomId
     * @param req
     * @return CreateSettlementRes
     */
    @PostMapping("/create")
    public SettlementResultData createSettlement(@RequestHeader(name = "X-USER-ID", required = true) Long userId, @RequestHeader(name = "X-ROOM-ID", required = false) String kakaoRoomId, @RequestBody @Valid CreateSettlementReq req) {
        log.debug("request userId : {}, kakaoRoomId: {}, req:{}", userId, kakaoRoomId, req);
        Assert.notNull(userId, "userId must not be null.");
        Assert.notNull(req, "req must not be null.");
        CreateSettlementData createSettlementData = mapCreateSettlementData(userId, kakaoRoomId, req);
        return settlementService.createSettlement(createSettlementData);
    }

    /**
     * 정산금액을 요청자에게 송금한다.
     *
     * @param userId
     * @param req
     * @return
     */
    @PostMapping("/settlement/pay")
    public PaySettlementResultData paySettlement(@RequestHeader(name = "X-USER-ID", required = true) Long userId, @RequestBody @Valid PaySettlementReq req) {

        Assert.notNull(userId, "userId must not be null.");
        Assert.notNull(req, "req must not be null.");
        return settlementService.paySettlement(userId, req.getSettlementId(), req.getPaidAmount());
    }

    /**
     * 정산객체에 정한하지 않는 인원들에게 Remind Push를 발송한다.
     *
     * @param userId
     * @param settlementId
     * @return
     */
    @GetMapping("/settlement/remind/{settlementId}")
    public CommonRes remindSettlement(@RequestHeader(name = "X-USER-ID", required = true) Long userId, @PathVariable(name = "settlementId") Long settlementId) {

        Assert.notNull(userId, "userId must not be null.");
        settlementService.remind(userId, settlementId);
        return new CommonRes("정상 처리 되었습니다.");
    }


    /**
     * 정산 요청한 목록
     *
     * @param userId
     * @param req    - 페이징 정보
     * @return
     */
    @GetMapping("/settlement/request-list")
    public List<SettlementResultData> getSettlementRequestList(@RequestHeader(name = "X-USER-ID", required = true) Long userId, @ModelAttribute @Valid PageReq req) {
        Assert.notNull(userId, "userId must not be null.");
        return settlementService.getSettlementRequestList(userId, req.getPage() - 1, req.getSize());
    }

    /**
     * 정산 요청 받은 목록
     *
     * @param userId
     * @param req
     * @return
     */
    @GetMapping("/settlement/requested-list")
    public List<PaySettlementResultData> getSettlementRequestedList(@RequestHeader(name = "X-USER-ID", required = true) Long userId, @ModelAttribute @Valid PageReq req) {
        Assert.notNull(userId, "userId must not be null.");
        return settlementService.getSettlementRequestedList(userId, req.getPage() - 1, req.getSize());
    }


    private CreateSettlementData mapCreateSettlementData(Long requestUserId, String kakaoRoomId, CreateSettlementReq req) {
        List<CreateSettlementData.CreateSettlementDetailData> detailDataList = req.getSettlementList().stream().map(data -> new CreateSettlementData.CreateSettlementDetailData(data.getUserId(), data.getAmount())).toList();
        return CreateSettlementData.builder()
                .requestUserId(requestUserId)
                .kakaoRoomId(kakaoRoomId)
                .totalAmount(req.getTotalAmount())
                .settlementList(detailDataList)
                .build();
    }

}
