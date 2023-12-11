package com.revy.domain.entity.settlement;

import com.revy.core.enums.domain.SettlementStatus;
import com.revy.core.util.BigDecimalUtils;
import com.revy.domain.common.BaseAudit;
import com.revy.domain.entity.settlement_user_info.SettlementUserInfo;
import com.revy.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Revy on 2023.12.05
 * 정산 Entity
 */

@Entity
@Table(name = "settlement")
@Getter
@NoArgsConstructor
public class Settlement extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 요청자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_user_id")
    @ToString.Exclude
    private User requestUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SettlementStatus status;

    // 완료시간
    @Column(name = "completed")
    private LocalDateTime completed;

    // 토탈 금액
    @Column(name = "total_amount", nullable = false, columnDefinition = "numeric(10,0) default 0")
    private BigDecimal totalAmount;

    // 정산 된 금액
    @Column(name = "settled_amount", nullable = false, columnDefinition = "numeric(10,0) default 0")
    private BigDecimal settledAmount = BigDecimal.ZERO;

    @Column(name = "kakaoRoomId")
    private String kakaoRoomId;

    @OneToMany(mappedBy = "settlement", fetch = FetchType.LAZY)
    private List<SettlementUserInfo> settlementUserInfoList = new ArrayList<>();

    @Builder
    public Settlement(SettlementStatus status, User requestUser, BigDecimal totalAmount, BigDecimal settledAmount, String kakaoRoomId) {
        this.status = status;
        this.requestUser = requestUser;
        this.totalAmount = totalAmount;
        this.settledAmount = settledAmount;
        this.kakaoRoomId = kakaoRoomId;
    }

    public void pay(BigDecimal paidAmount) {
        this.settledAmount = this.settledAmount.add(paidAmount);

        if (BigDecimalUtils.eq(this.getTotalAmount(), this.getSettledAmount())) {
            this.status = SettlementStatus.COMPLETE;
            this.completed = LocalDateTime.now();
        } else {
            this.status = SettlementStatus.PROCEEDING;
        }
    }


}
