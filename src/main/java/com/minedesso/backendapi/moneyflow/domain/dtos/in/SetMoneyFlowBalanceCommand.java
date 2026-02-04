package com.minedesso.backendapi.moneyflow.domain.dtos.in;

import com.minedesso.backendapi.moneyflow.domain.utils.exceptions.TransactionValidationException;
import com.minedesso.backendapi.moneyflow.domain.utils.validation.ValidationUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class SetMoneyFlowBalanceCommand extends AbstractSetMoneyFlowBalanceCommand {
    private UUID targetUuid;

    public void validate() throws TransactionValidationException {
        ValidationUtil.validateSetMoneyFlowBalanceCommand(this);
    }

}
