package com.sal.vendingmachine.dto;

import java.math.BigDecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

    public enum CoinValue {

        PENNIES(new BigDecimal(0.01)),
        NICKELS(new BigDecimal(0.05)),
        DIMES(new BigDecimal(0.1)),
        QUARTERS(new BigDecimal(0.25));

        private final BigDecimal value;

        private CoinValue(BigDecimal value) {
            this.value = value;
        }

        public BigDecimal getValue()
        {
            return value.setScale(2, RoundingMode.DOWN);
        }
    }


