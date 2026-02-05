package com.minedesso.backendapi.moneyflow.domain.utils.validation;

import com.minedesso.backendapi.moneyflow.domain.dtos.in.*;
import com.minedesso.backendapi.moneyflow.domain.utils.enums.TransactionSource;
import com.minedesso.backendapi.moneyflow.domain.utils.enums.TransactionType;
import com.minedesso.backendapi.moneyflow.domain.utils.exceptions.TransactionValidationException;
import com.minedesso.backendapi.moneyflow.domain.utils.wrappers.TransactionContext;

import java.util.EnumSet;
import java.util.UUID;

public final class MoneyFlowValidationUtils {

    private static final EnumSet<TransactionSource> SOURCES_REQUIRING_PLAYER = EnumSet.of(
            TransactionSource.ADMIN_COMMAND,
            TransactionSource.PLAYER
    );

    public static void validateSetMoneyFlowBalanceCommand(SetMoneyFlowBalanceCommand command) throws TransactionValidationException {
        validateSetMoneyFlowBalanceAbstract(command);
        validateUuid(command.getTargetUuid());
    }

    public static void validateSetMoneyFlowBalanceCommand(SetMoneyFlowBalanceOfflineCommand command) throws TransactionValidationException {
        validateSetMoneyFlowBalanceAbstract(command);
        validateName(command.getTargetName());
    }

    public static void validateTransactionCommand(TransactionOnlineCommand command) throws TransactionValidationException {
        validateTransactionAbstract(command);

        validateUuid(command.getReceiver());
        validateUuidsNotEqual(command.getSender(), command.getReceiver());
    }

    public static void validateTransactionCommand(TransactionOfflineCommand command, UUID receiverUuid) throws TransactionValidationException {
        validateTransactionAbstract(command);

        validateUuid(receiverUuid);
        validateUuidsNotEqual(command.getSender(), receiverUuid);
    }

    private static void validateSetMoneyFlowBalanceAbstract(AbstractSetMoneyFlowBalanceCommand command) throws TransactionValidationException {
        requireNonNull(command, "command");
        validateTransactionContext(command.getTransactionContext());
        validateType(command.getTransactionContext(), TransactionType.SET);

        validateAdminUuid(command.getAdminUuid(), command.getTransactionContext());

        validateAmountGreaterOrEqualZero(command.getNewBalance());
    }

    private static void validateTransactionAbstract(AbstractTransactionCommand command) throws TransactionValidationException {
        requireNonNull(command, "command");
        validateTransactionContext(command.getContext());
        validateType(command.getContext(), TransactionType.PAY);
        validateSourceMustBePlayer(command.getContext());

        validateUuid(command.getSender());
        validateAmountGreaterThanZero(command.getAmount());
    }

    private static void validateTransactionContext(TransactionContext context) throws TransactionValidationException {
        if (context == null || context.getSource() == null || context.getType() == null) {
            throw new TransactionValidationException("context must be given!");
        }
    }

    private static void validateAdminUuid(UUID adminUuid, TransactionContext context) throws TransactionValidationException {
        boolean playerRequired = context != null && SOURCES_REQUIRING_PLAYER.contains(context.getSource());
        if (playerRequired && adminUuid == null) {
            throw new TransactionValidationException("admin-uuid must not be null when source is " + context.getSource());
        }
    }

    private static void validateType(TransactionContext context, TransactionType type) throws TransactionValidationException {
        if (context == null || context.getType() != type) {
            throw new TransactionValidationException("transaction type must be " + type);
        }
    }

    private static void validateSourceMustBePlayer(TransactionContext context) throws TransactionValidationException {
        boolean playerRequired = context != null && SOURCES_REQUIRING_PLAYER.contains(context.getSource());
        if (!playerRequired) {
            throw new TransactionValidationException("transaction source must be one of " + SOURCES_REQUIRING_PLAYER);
        }
    }

    private static void validateAmountGreaterThanZero(double amount) throws TransactionValidationException {
        if (Double.isNaN(amount) || Double.isInfinite(amount) || amount <= 0) {
            throw new TransactionValidationException("amount must be a positive finite number");
        }
    }

    private static void validateAmountGreaterOrEqualZero(double amount) throws TransactionValidationException {
        if (Double.isNaN(amount) || Double.isInfinite(amount) || amount < 0) {
            throw new TransactionValidationException("amount must be a non-negative finite number");
        }
    }

    private static void validateUuidsNotEqual(UUID uuid1, UUID uuid2) throws TransactionValidationException {
        if (uuid1.equals(uuid2)) {
            throw new TransactionValidationException("sender and receiver cannot be the same");
        }
    }

    private static void validateUuid(UUID uuid) throws TransactionValidationException {
        requireNonNull(uuid, "uuid");
    }

    private static void validateName(String name) throws TransactionValidationException {
        requireNonNull(name, "name");
    }

    private static void requireNonNull(Object value, String name) throws TransactionValidationException {
        if (value == null) {
            throw new TransactionValidationException(name + " must not be null");
        }
    }
}
