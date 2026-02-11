package com.minedesso.backendapi.moneyflow.domain.utils.wrappers;

import com.minedesso.backendapi.moneyflow.domain.utils.enums.TransactionSource;
import com.minedesso.backendapi.moneyflow.domain.utils.enums.TransactionType;
import lombok.Data;

@Data
public class TransactionContext {
    private TransactionType type;
    private TransactionSource source;
}
