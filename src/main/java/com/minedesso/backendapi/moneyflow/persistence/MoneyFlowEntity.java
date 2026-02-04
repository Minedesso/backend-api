package com.minedesso.backendapi.moneyflow.persistence;

import com.minedesso.backendapi.moneyflow.domain.utils.exceptions.TransactionValidationException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "MONEY_FLOW")
public class MoneyFlowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance;
    private boolean frozen;

    public MoneyFlowEntity(double balance) {
        this.balance = balance;
        this.frozen = false;
    }

    public void increase(double amount) throws TransactionValidationException {
        if (this.frozen) {
            throw new TransactionValidationException("balance of receiver is frozen!");
        }
        this.balance += amount;
    }

    public void decrease(double amount) throws TransactionValidationException {
        if (this.frozen) {
            throw new TransactionValidationException("balance of sender is frozen!");
        }
        if (balance < amount) {
            throw new TransactionValidationException("balance too little");
        }
        this.balance -= amount;
    }

    public void setBalance(double amount) throws TransactionValidationException {
        if (amount < 0) {
            throw new TransactionValidationException("new balance mustn't be negative");
        }
        this.balance = amount;
    }
}
