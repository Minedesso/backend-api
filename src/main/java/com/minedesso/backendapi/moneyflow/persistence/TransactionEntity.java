package com.minedesso.backendapi.moneyflow.persistence;

import com.minedesso.backendapi.moneyflow.domain.dtos.in.*;
import com.minedesso.backendapi.moneyflow.domain.utils.enums.TransactionSource;
import com.minedesso.backendapi.moneyflow.domain.utils.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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

    @CreationTimestamp
    private LocalDateTime transactionTime;

    public TransactionEntity(TransactionOnlineCommand command) {
        mapFromAbstractTransaction(command, command.getReceiver());
    }

    public TransactionEntity(TransactionOfflineCommand command, UUID receiverUuid) {
        mapFromAbstractTransaction(command, receiverUuid);
    }

    public TransactionEntity(SetMoneyFlowBalanceOnlineCommand command) {
        mapFromAbstractSetBalance(command, command.getTargetUuid());
    }

    public TransactionEntity(SetMoneyFlowBalanceOfflineCommand command, UUID receiverUuid) {
        mapFromAbstractSetBalance(command, receiverUuid);
    }

    private void mapFromAbstractTransaction(AbstractTransactionCommand command, UUID receiverUuid) {
        this.senderUuid = command.getSender();
        this.recieverUuid = receiverUuid;
        this.amount = command.getAmount();
        this.purposeOfUse = command.getPurposeOfUse();
        this.type = command.getContext().getType();
        this.source = command.getContext().getSource();
    }

    private void mapFromAbstractSetBalance(AbstractSetMoneyFlowBalanceCommand command, UUID receiverUuid) {
        this.senderUuid = command.getAdminUuid();
        this.recieverUuid = receiverUuid;
        this.amount = command.getNewBalance();
        this.purposeOfUse = "Set balance to " + command.getNewBalance();
        this.type = command.getTransactionContext().getType();
        this.source = command.getTransactionContext().getSource();
    }
}
