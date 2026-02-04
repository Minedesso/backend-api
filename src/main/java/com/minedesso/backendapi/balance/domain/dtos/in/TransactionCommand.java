package com.minedesso.backendapi.balance.domain.dtos.in;

import com.minedesso.backendapi.balance.domain.utils.exceptions.TransactionValidationException;
import com.minedesso.backendapi.balance.domain.utils.wrappers.TransactionContext;
import lombok.Data;

import java.util.UUID;

@Data
public class TransactionCommand {
    private UUID sender;
    private UUID receiver;
    private double amount;
    private String purposeOfUse;
    private TransactionContext context;

    public void validate() throws TransactionValidationException {
        if (this.sender == null || this.receiver == null) {
            throw new TransactionValidationException("sender or receiver mustn't be null");
        }

        if (this.sender.equals(this.receiver)) {
            throw new TransactionValidationException("sender and receiver cannot be the same");
        }

        if (this.amount < 0) {
            throw new TransactionValidationException("amount mustn't be negative");
        }

        if (this.context == null || this.context.getSource() == null || this.context.getType() == null) {
            throw new TransactionValidationException("context must be given!");
        }
    }
}
