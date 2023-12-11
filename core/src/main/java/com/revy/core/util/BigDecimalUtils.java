package com.revy.core.util;

import java.math.BigDecimal;

/**
 * Created by Revy on 2023.12.08
 */
public class BigDecimalUtils {

    private BigDecimalUtils() {
    }

    private static final int ZERO = 0;

    /**
     * left == right = true
     * @param left
     * @param right
     * @return
     */
    public static boolean eq(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) == ZERO;
    }


    /**
     * left <> right = true
     * @param left
     * @param right
     * @return
     */
    public static boolean notEq(BigDecimal left, BigDecimal right) {
        return !eq(left, right);
    }

    /**
     * left > right = true
     * @param left
     * @param right
     * @return boolean
     */
    public static boolean gt(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) > ZERO;
    }

    /**
     * left >= right = true
     * @param left
     * @param right
     * @return boolean
     */
    public static boolean gte(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) >= ZERO;
    }

    /**
     * left < right = true
     * @param left
     * @param right
     * @return boolean
     */
    public static boolean lt(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) < ZERO;
    }

    /**
     * left <= right = true
     * @param left
     * @param right
     * @return boolean
     */
    public static boolean lte(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) <= ZERO;
    }

}
