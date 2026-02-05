package com.minedesso.backendapi.moneyflow.domain.service;

import com.minedesso.backendapi.minecraftplayer.domain.utils.exceptions.MinecraftPlayerNotFoundException;
import com.minedesso.backendapi.minecraftplayer.persistence.MinecraftPlayerEntity;
import com.minedesso.backendapi.minecraftplayer.persistence.MinecraftPlayerRepository;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.SetMoneyFlowBalanceOnlineCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.SetMoneyFlowBalanceOfflineCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.TransactionOnlineCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.TransactionOfflineCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.out.Balance;
import com.minedesso.backendapi.moneyflow.domain.utils.exceptions.TransactionValidationException;
import com.minedesso.backendapi.moneyflow.persistence.TransactionEntity;
import com.minedesso.backendapi.moneyflow.persistence.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MoneyFlowService implements MoneyFlowUseCase {
    private final TransactionRepository transactionRepository;
    private final MinecraftPlayerRepository minecraftPlayerRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transactWithOnlinePlayer(TransactionOnlineCommand command) throws TransactionValidationException {
        command.validate();

        UUID senderUuid = command.getSender();
        UUID receiverUuid = command.getReceiver();
        double amount = command.getAmount();

        MinecraftPlayerEntity sender = this.minecraftPlayerRepository.findById((senderUuid))
                .orElseThrow(() -> new IllegalStateException("sender not found!"));

        MinecraftPlayerEntity receiver = this.minecraftPlayerRepository.findById((receiverUuid))
                .orElseThrow(() -> new IllegalStateException("receiver not found!"));

        sender.decreaseMoneyFlowBalance(amount);
        receiver.increaseMoneyFlowBalance(amount);

        TransactionEntity transactionEntity = new TransactionEntity(command);
        this.transactionRepository.save(transactionEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transactWithOfflinePlayer(TransactionOfflineCommand command) throws TransactionValidationException, MinecraftPlayerNotFoundException {
        UUID senderUuid = command.getSender();
        String receiverName = command.getReceiverName();
        double amount = command.getAmount();

        MinecraftPlayerEntity sender = this.minecraftPlayerRepository.findById((senderUuid))
                .orElseThrow(() -> new IllegalStateException("sender not found!"));

        MinecraftPlayerEntity receiver = this.minecraftPlayerRepository.findByName(receiverName)
                .orElseThrow(() -> new MinecraftPlayerNotFoundException(receiverName));

        command.validate(receiver.getUuid());

        sender.decreaseMoneyFlowBalance(amount);
        receiver.increaseMoneyFlowBalance(amount);

        TransactionEntity transactionEntity = new TransactionEntity(command, receiver.getUuid());
        this.transactionRepository.save(transactionEntity);
    }

    @Override
    public void setBalanceOfOnlinePlayer(SetMoneyFlowBalanceOnlineCommand command) throws TransactionValidationException {
        command.validate();

        UUID targetUuid = command.getTargetUuid();
        double newBalance = command.getNewBalance();

        MinecraftPlayerEntity target = this.minecraftPlayerRepository.findById((targetUuid))
                .orElseThrow(() -> new IllegalStateException("target not found!"));

        target.setMoneyFlowBalance(newBalance);
        this.minecraftPlayerRepository.save(target);

        TransactionEntity transactionEntity = new TransactionEntity(command);
        this.transactionRepository.save(transactionEntity);
    }

    @Override
    public void setBalanceOfOfflinePlayer(SetMoneyFlowBalanceOfflineCommand command) throws TransactionValidationException, MinecraftPlayerNotFoundException {
        command.validate();

        String targetName = command.getTargetName();
        double newBalance = command.getNewBalance();

        MinecraftPlayerEntity target = this.minecraftPlayerRepository.findByName(targetName)
                .orElseThrow(() -> new MinecraftPlayerNotFoundException(targetName));

        target.setMoneyFlowBalance(newBalance);
        this.minecraftPlayerRepository.save(target);

        TransactionEntity transactionEntity = new TransactionEntity(command, target.getUuid());
        this.transactionRepository.save(transactionEntity);
    }

    @Override
    public Balance getMoneyFlowBalance(UUID ownerUuid) {
        MinecraftPlayerEntity owner = this.minecraftPlayerRepository.findById(ownerUuid)
                .orElseThrow(() -> new IllegalStateException("owner not found!"));

        return new Balance(owner.getMoneyFlow());
    }
}
