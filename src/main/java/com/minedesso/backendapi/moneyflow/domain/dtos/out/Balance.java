package com.minedesso.backendapi.moneyflow.domain.dtos.out;

import com.minedesso.backendapi.moneyflow.persistence.MoneyFlowEntity;
import lombok.Data;

@Data
public class Balance {
    private double balance;

    public Balance(MoneyFlowEntity moneyFlowEntity) {
        this.balance = moneyFlowEntity.getBalance();
    }
}
