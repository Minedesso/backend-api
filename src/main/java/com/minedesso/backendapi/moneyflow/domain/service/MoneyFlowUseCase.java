package com.minedesso.backendapi.moneyflow.domain.service;

import com.minedesso.backendapi.minecraftplayer.domain.utils.exceptions.MinecraftPlayerNotFoundException;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.SetMoneyFlowBalanceOnlineCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.SetMoneyFlowBalanceOfflineCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.TransactionOnlineCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.TransactionOfflineCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.out.Balance;
import com.minedesso.backendapi.moneyflow.domain.utils.exceptions.TransactionValidationException;

import java.util.UUID;

public interface MoneyFlowUseCase {
    void transactWithOnlinePlayer(TransactionOnlineCommand command) throws TransactionValidationException;
    void transactWithOfflinePlayer(TransactionOfflineCommand command) throws TransactionValidationException, MinecraftPlayerNotFoundException;
    void setBalanceOfOnlinePlayer(SetMoneyFlowBalanceOnlineCommand command) throws TransactionValidationException;
    void setBalanceOfOfflinePlayer(SetMoneyFlowBalanceOfflineCommand command) throws TransactionValidationException, MinecraftPlayerNotFoundException;
    Balance getMoneyFlowBalance(UUID ownerUuid);
}
