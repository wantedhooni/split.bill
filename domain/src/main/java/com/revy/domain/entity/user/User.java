package com.revy.domain.entity.user;

import com.revy.core.util.BigDecimalUtils;
import com.revy.domain.common.BaseAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * Created by Revy on 2023.12.05
 * User entity
 */

@Entity
@Table(name = "app_user")
@Getter
@NoArgsConstructor
@DynamicUpdate
public class User extends BaseAudit {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "email", length = 30)
    private String email;

    @Column(name = "balance", nullable = false, columnDefinition = "numeric(10,0) default 0")
    private BigDecimal balance = BigDecimal.ZERO;

    @Builder
    public User(Long id, String name, String email, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    /**
     * Balance를 증감한다.
     *
     * @param amount
     */
    public void addBalance(BigDecimal amount) {
        this.balance = balance.add(amount);
    }

    /**
     * Balance를 차감한다.
     *
     * @param amount
     */
    public void subtractBalance(BigDecimal amount) {
        Assert.isTrue(BigDecimalUtils.gt(this.balance, amount), "차감금액은 잔고보다 클수 없습니다.");
        this.balance = this.balance.subtract(amount);
    }

}
