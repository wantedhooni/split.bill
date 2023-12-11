package com.revy.domain.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseAudit is a Querydsl query type for BaseAudit
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseAudit extends EntityPathBase<BaseAudit> {

    private static final long serialVersionUID = -746564954L;

    public static final QBaseAudit baseAudit = new QBaseAudit("baseAudit");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> modifyAt = createDateTime("modifyAt", java.time.LocalDateTime.class);

    public QBaseAudit(String variable) {
        super(BaseAudit.class, forVariable(variable));
    }

    public QBaseAudit(Path<? extends BaseAudit> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseAudit(PathMetadata metadata) {
        super(BaseAudit.class, metadata);
    }

}

