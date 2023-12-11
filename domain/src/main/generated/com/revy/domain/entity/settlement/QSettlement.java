package com.revy.domain.entity.settlement;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSettlement is a Querydsl query type for Settlement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSettlement extends EntityPathBase<Settlement> {

    private static final long serialVersionUID = -2052964254L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSettlement settlement = new QSettlement("settlement");

    public final com.revy.domain.common.QBaseAudit _super = new com.revy.domain.common.QBaseAudit(this);

    public final DateTimePath<java.time.LocalDateTime> completed = createDateTime("completed", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath kakaoRoomId = createString("kakaoRoomId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyAt = _super.modifyAt;

    public final com.revy.domain.entity.user.QUser requestUser;

    public final NumberPath<java.math.BigDecimal> settledAmount = createNumber("settledAmount", java.math.BigDecimal.class);

    public final ListPath<com.revy.domain.entity.settlement_user_info.SettlementUserInfo, com.revy.domain.entity.settlement_user_info.QSettlementUserInfo> settlementUserInfoList = this.<com.revy.domain.entity.settlement_user_info.SettlementUserInfo, com.revy.domain.entity.settlement_user_info.QSettlementUserInfo>createList("settlementUserInfoList", com.revy.domain.entity.settlement_user_info.SettlementUserInfo.class, com.revy.domain.entity.settlement_user_info.QSettlementUserInfo.class, PathInits.DIRECT2);

    public final EnumPath<com.revy.core.enums.domain.SettlementStatus> status = createEnum("status", com.revy.core.enums.domain.SettlementStatus.class);

    public final NumberPath<java.math.BigDecimal> totalAmount = createNumber("totalAmount", java.math.BigDecimal.class);

    public QSettlement(String variable) {
        this(Settlement.class, forVariable(variable), INITS);
    }

    public QSettlement(Path<? extends Settlement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSettlement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSettlement(PathMetadata metadata, PathInits inits) {
        this(Settlement.class, metadata, inits);
    }

    public QSettlement(Class<? extends Settlement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.requestUser = inits.isInitialized("requestUser") ? new com.revy.domain.entity.user.QUser(forProperty("requestUser")) : null;
    }

}

