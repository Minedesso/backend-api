package com.minedesso.backendapi.balance.persistence;

import com.minedesso.backendapi.balance.domain.utils.exceptions.TransactionValidationException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "BALANCE")
public class BalanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance;
    private boolean frozen;

    public BalanceEntity(double balance) {
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
}
