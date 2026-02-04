package com.minedesso.backendapi.moneyflow.domain.dtos.in;

import com.minedesso.backendapi.moneyflow.domain.utils.exceptions.TransactionValidationException;
import com.minedesso.backendapi.moneyflow.domain.utils.validation.ValidationUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SetMoneyFlowBalanceOfflineCommand extends AbstractSetMoneyFlowBalanceCommand {
    private String targetName;

    public void validate() throws TransactionValidationException {
        ValidationUtil.validateSetMoneyFlowBalanceCommand(this);
    }
}
