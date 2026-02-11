package com.minedesso.backendapi.moneyflow.domain.dtos.in;

import com.minedesso.backendapi.moneyflow.domain.utils.wrappers.TransactionContext;
import lombok.Data;

import java.util.UUID;

@Data
public abstract class AbstractSetMoneyFlowBalanceCommand {
    private UUID adminUuid;
    private double newBalance;
    private TransactionContext transactionContext;
}
