package com.minedesso.backendapi.balance.persistence;

import com.minedesso.backendapi.balance.domain.dtos.in.TransactionCommand;
import com.minedesso.backendapi.balance.domain.utils.enums.TransactionSource;
import com.minedesso.backendapi.balance.domain.utils.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "TRANSACTION")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID senderUuid;
    private UUID recieverUuid;
    private double amount;
    private String purposeOfUse;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionSource source;

    public TransactionEntity(TransactionCommand command) {
        this.senderUuid = command.getSender();
        this.recieverUuid = command.getReceiver();
        this.amount = command.getAmount();
        this.purposeOfUse = command.getPurposeOfUse();
        this.type = command.getContext().getType();
        this.source = command.getContext().getSource();
    }
}
