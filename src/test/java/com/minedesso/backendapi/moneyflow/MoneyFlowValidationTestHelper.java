package com.minedesso.backendapi.moneyflow;

import com.minedesso.backendapi.moneyflow.domain.dtos.in.SetMoneyFlowBalanceOfflineCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.SetMoneyFlowBalanceOnlineCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.TransactionOfflineCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.TransactionOnlineCommand;
import com.minedesso.backendapi.moneyflow.domain.utils.enums.TransactionSource;
import com.minedesso.backendapi.moneyflow.domain.utils.enums.TransactionType;
import com.minedesso.backendapi.moneyflow.domain.utils.wrappers.TransactionContext;

import java.util.UUID;

public class MoneyFlowValidationTestHelper {
    public static TransactionOnlineCommand createOnlineTransaction(
            TransactionSource source,
            TransactionType type,
            UUID sender,
            UUID receiver,
            double amount
    ) {
        TransactionOnlineCommand command = new TransactionOnlineCommand();
        command.setSender(sender);
        command.setReceiver(receiver);
        command.setAmount(amount);
        command.setContext(createContext(source, type));
        return command;
    }

    public static TransactionOfflineCommand createOfflineTransaction(
            TransactionSource source,
            TransactionType type,
            UUID sender,
            double amount
    ) {
        TransactionOfflineCommand command = new TransactionOfflineCommand();
        command.setSender(sender);
        command.setAmount(amount);
        command.setContext(createContext(source, type));
        return command;
    }

    public static SetMoneyFlowBalanceOnlineCommand createOnlineBalanceCommand(
            TransactionSource source,
            TransactionType type,
            UUID targetUuid,
            double newBalance
    ) {
        SetMoneyFlowBalanceOnlineCommand command = new SetMoneyFlowBalanceOnlineCommand();
        command.setTargetUuid(targetUuid);
        command.setTransactionContext(createContext(source, type));
        command.setNewBalance(newBalance);
        command.setAdminUuid(UUID.randomUUID());
        return command;
    }

    public static SetMoneyFlowBalanceOfflineCommand createOfflineBalanceCommand(
            TransactionSource source,
            TransactionType type,
            String targetName,
            double newBalance
    ) {
        SetMoneyFlowBalanceOfflineCommand command = new SetMoneyFlowBalanceOfflineCommand();
        command.setTargetName(targetName);
        command.setTransactionContext(createContext(source, type));
        command.setNewBalance(newBalance);
        command.setAdminUuid(UUID.randomUUID());
        return command;
    }

    public static TransactionContext createContext(TransactionSource source, TransactionType type) {
        TransactionContext context = new TransactionContext();
        context.setSource(source);
        context.setType(type);
        return context;
    }
}
