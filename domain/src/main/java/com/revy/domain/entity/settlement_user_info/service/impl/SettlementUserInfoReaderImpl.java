package com.revy.domain.entity.settlement_user_info.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.revy.domain.entity.settlement.SettlementRepository;
import com.revy.domain.entity.settlement_user_info.QSettlementUserInfo;
import com.revy.domain.entity.settlement_user_info.SettlementUserInfo;
import com.revy.domain.entity.settlement_user_info.service.SettlementUserInfoReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Revy on 2023.12.08
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SettlementUserInfoReaderImpl implements SettlementUserInfoReader {

    private final SettlementRepository repository;

    private final JPAQueryFactory jpaQueryFactory;

    private static final QSettlementUserInfo SETTLEMENT_USER_INFO = QSettlementUserInfo.settlementUserInfo;

    @Override
    public Optional<SettlementUserInfo> findOneByUserIdAndSettlementId(Long userId, Long settlementId) {
        BooleanBuilder where = new BooleanBuilder();
        where.and(SETTLEMENT_USER_INFO.targetUser.id.eq(userId));
        where.and(SETTLEMENT_USER_INFO.settlement.id.eq(settlementId));
        SettlementUserInfo result = jpaQueryFactory.selectFrom(SETTLEMENT_USER_INFO)
                .where(where)
                .fetchFirst();
        return Optional.ofNullable(result);
    }

    @Override
    public List<SettlementUserInfo> findBySettlementId(Long settlementId) {
        return jpaQueryFactory.selectFrom(SETTLEMENT_USER_INFO)
                .where(SETTLEMENT_USER_INFO.settlement.id.eq(settlementId))
                .fetch();
    }

    @Override
    public List<SettlementUserInfo> findByTargetUserId(Long targetUserId, int pageNumber, int pageSize) {
        BooleanBuilder where = new BooleanBuilder();
        where.and(SETTLEMENT_USER_INFO.targetUser.id.eq(targetUserId));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return jpaQueryFactory.selectFrom(SETTLEMENT_USER_INFO)
                .where(where)
                .orderBy(SETTLEMENT_USER_INFO.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

    }
}
