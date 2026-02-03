package com.minedesso.backendapi.balance.domain.utils.wrappers;

import com.minedesso.backendapi.balance.domain.utils.enums.TransactionSource;
import com.minedesso.backendapi.balance.domain.utils.enums.TransactionType;
import lombok.Data;

@Data
public class TransactionContext {
    private TransactionType type;
    private TransactionSource source;
}
