package com.revy.domain.entity.settlement_user_info;

import com.revy.core.enums.domain.SettlementStatus;
import com.revy.core.util.BigDecimalUtils;
import com.revy.domain.common.BaseAudit;
import com.revy.domain.entity.settlement.Settlement;
import com.revy.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Revy on 2023.12.05
 * 정산 유저 정보
 */

@Slf4j
@Entity
@Table(name = "settlement_user_info")
@Getter
@ToString
@NoArgsConstructor
public class SettlementUserInfo extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settlement_id")
    @ToString.Exclude
    private Settlement settlement;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SettlementStatus status;

    // 정산대상자(송금대상, fom)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id")
    @ToString.Exclude
    private User targetUser;

    // 요청자(수취대상, to)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_user_id")
    @ToString.Exclude
    private User requestUser;

    // 토탈 금액
    @Column(name = "total_amount", nullable = false, columnDefinition = "numeric(10,0) default 0")
    private BigDecimal totalAmount;

    // 미정산 금액
    @Column(name = "unsettled_amount", nullable = false, columnDefinition = "numeric(10,0) default 0")
    private BigDecimal unsettledAmount;

    // 완료시간
    @Column(name = "completed")
    private LocalDateTime completed;

    @Builder
    public SettlementUserInfo(User targetUser, User requestUser, Settlement settlement, SettlementStatus status, BigDecimal totalAmount, BigDecimal unsettledAmount) {
        this.targetUser = targetUser;
        this.requestUser = requestUser;
        this.settlement = settlement;
        this.status = status;
        this.totalAmount = totalAmount;
        this.unsettledAmount = unsettledAmount;
    }


    public void pay(BigDecimal paidAmount) {
        Assert.notNull(paidAmount, "요청금액은 null일수 없습니다.");
        Assert.isTrue(this.status != SettlementStatus.COMPLETE, "이미 완료된 정산건입니다.");
        Assert.isTrue(BigDecimalUtils.gt(paidAmount, BigDecimal.ZERO), "송금 금액은 0원 보다 커야 합니다.");
        Assert.isTrue(BigDecimalUtils.gte(this.unsettledAmount, paidAmount), "송금 금액이 정산금액보다 클수 없습니다.");

        if (BigDecimalUtils.eq(this.unsettledAmount, paidAmount)) {
            this.unsettledAmount = BigDecimal.ZERO;
            this.status = SettlementStatus.COMPLETE;
            this.completed = LocalDateTime.now();
        } else {
            this.unsettledAmount = unsettledAmount.subtract(paidAmount);
            this.status = SettlementStatus.PROCEEDING;
        }
    }

}
