package com.revy.domain.entity.settlement_user_info;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSettlementUserInfo is a Querydsl query type for SettlementUserInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSettlementUserInfo extends EntityPathBase<SettlementUserInfo> {

    private static final long serialVersionUID = -259908648L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSettlementUserInfo settlementUserInfo = new QSettlementUserInfo("settlementUserInfo");

    public final com.revy.domain.common.QBaseAudit _super = new com.revy.domain.common.QBaseAudit(this);

    public final DateTimePath<java.time.LocalDateTime> completed = createDateTime("completed", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyAt = _super.modifyAt;

    public final com.revy.domain.entity.user.QUser requestUser;

    public final com.revy.domain.entity.settlement.QSettlement settlement;

    public final EnumPath<com.revy.core.enums.domain.SettlementStatus> status = createEnum("status", com.revy.core.enums.domain.SettlementStatus.class);

    public final com.revy.domain.entity.user.QUser targetUser;

    public final NumberPath<java.math.BigDecimal> totalAmount = createNumber("totalAmount", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> unsettledAmount = createNumber("unsettledAmount", java.math.BigDecimal.class);

    public QSettlementUserInfo(String variable) {
        this(SettlementUserInfo.class, forVariable(variable), INITS);
    }

    public QSettlementUserInfo(Path<? extends SettlementUserInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSettlementUserInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSettlementUserInfo(PathMetadata metadata, PathInits inits) {
        this(SettlementUserInfo.class, metadata, inits);
    }

    public QSettlementUserInfo(Class<? extends SettlementUserInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.requestUser = inits.isInitialized("requestUser") ? new com.revy.domain.entity.user.QUser(forProperty("requestUser")) : null;
        this.settlement = inits.isInitialized("settlement") ? new com.revy.domain.entity.settlement.QSettlement(forProperty("settlement"), inits.get("settlement")) : null;
        this.targetUser = inits.isInitialized("targetUser") ? new com.revy.domain.entity.user.QUser(forProperty("targetUser")) : null;
    }

}

