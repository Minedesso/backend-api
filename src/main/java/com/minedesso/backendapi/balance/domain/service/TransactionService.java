package com.minedesso.backendapi.balance.domain.service;

import com.minedesso.backendapi.balance.domain.dtos.in.TransactionCommand;
import com.minedesso.backendapi.balance.domain.utils.exceptions.TransactionValidationException;
import com.minedesso.backendapi.balance.persistence.TransactionEntity;
import com.minedesso.backendapi.balance.persistence.TransactionRepository;
import com.minedesso.backendapi.minecraftplayer.persistence.MinecraftPlayerEntity;
import com.minedesso.backendapi.minecraftplayer.persistence.MinecraftPlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionService implements TransactionUseCase {
    private final TransactionRepository transactionRepository;
    private final MinecraftPlayerRepository minecraftPlayerRepository;

    @Override
    @Transactional
    public void transact(TransactionCommand command) throws TransactionValidationException {
        command.validate();

        UUID senderUuid = command.getSender();
        UUID receiverUuid = command.getReceiver();
        double amount = command.getAmount();

        MinecraftPlayerEntity sender = this.minecraftPlayerRepository.findById((senderUuid))
                .orElseThrow(() -> new IllegalStateException("sender not found!"));

        MinecraftPlayerEntity receiver = this.minecraftPlayerRepository.findById((receiverUuid))
                .orElseThrow(() -> new IllegalStateException("receiver not found!"));

        sender.increaseBalance(amount);
        receiver.decreaseBalance(amount);

        this.minecraftPlayerRepository.save(sender);
        this.minecraftPlayerRepository.save(receiver);

        TransactionEntity transactionEntity = new TransactionEntity(command);
        this.transactionRepository.save(transactionEntity);
    }
}
