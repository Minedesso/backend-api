package com.minedesso.backendapi.moneyflow.domain.dtos.in;

import com.minedesso.backendapi.moneyflow.domain.utils.wrappers.TransactionContext;
import lombok.Data;

import java.util.UUID;

@Data
public abstract class AbstractTransactionCommand {
    private UUID sender;
    private double amount;
    private String purposeOfUse;
    private TransactionContext context;
}
