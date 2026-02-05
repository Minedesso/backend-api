package com.minedesso.backendapi.moneyflow.domain.dtos.in;

import com.minedesso.backendapi.moneyflow.domain.utils.exceptions.TransactionValidationException;
import com.minedesso.backendapi.moneyflow.domain.utils.validation.MoneyFlowValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransactionOfflineCommand extends AbstractTransactionCommand {
    private String receiverName;

    public void validate(UUID receiverUuid) throws TransactionValidationException {
        MoneyFlowValidationUtils.validateTransactionCommand(this, receiverUuid);
    }
}
