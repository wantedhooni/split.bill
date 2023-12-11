package com.revy.domain.entity.settlement.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.revy.domain.entity.settlement.QSettlement;
import com.revy.domain.entity.settlement.Settlement;
import com.revy.domain.entity.settlement.SettlementRepository;
import com.revy.domain.entity.settlement.service.SettlementReader;
import com.revy.domain.entity.settlement_user_info.QSettlementUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
public class SettlementReaderImpl implements SettlementReader {

    private final SettlementRepository repository;

    private final JPAQueryFactory jpaQueryFactory;

    private static final QSettlement SETTLEMENT = QSettlement.settlement;

    @Override
    public Optional<Settlement> findByIdAndRequestUserId(Long settlementId, Long requestUserId) {
        BooleanBuilder where = new BooleanBuilder();
        where.and(SETTLEMENT.id.eq(settlementId));
        where.and(SETTLEMENT.requestUser.id.eq(requestUserId));

        Settlement result = jpaQueryFactory.selectFrom(SETTLEMENT)
                .where(where)
                .fetchFirst();
        return Optional.ofNullable(result);
    }

    @Override
    public List<Settlement> findByUserId(Long userId, int pageNumber, int pageSize) {
        BooleanBuilder where = new BooleanBuilder();
        where.and(SETTLEMENT.requestUser.id.eq(userId));

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return jpaQueryFactory.selectFrom(SETTLEMENT)
                .where(where)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(SETTLEMENT.id.asc())
                .fetch();


    }
}
