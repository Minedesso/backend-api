package com.minedesso.backendapi.moneyflow;

import com.minedesso.backendapi.moneyflow.domain.dtos.in.SetMoneyFlowBalanceOfflineCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.SetMoneyFlowBalanceOnlineCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.TransactionOfflineCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.TransactionOnlineCommand;
import com.minedesso.backendapi.moneyflow.domain.utils.enums.TransactionSource;
import com.minedesso.backendapi.moneyflow.domain.utils.enums.TransactionType;
import com.minedesso.backendapi.moneyflow.domain.utils.exceptions.TransactionValidationException;
import com.minedesso.backendapi.moneyflow.domain.utils.validation.MoneyFlowValidationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MoneyFlowValidationUtilsTest {
    @Test
    void validateTransactionCommand_online_shouldAcceptValidCommand() {
        UUID sender = UUID.randomUUID();
        UUID receiver = UUID.randomUUID();

        TransactionOnlineCommand command = MoneyFlowValidationTestHelper.createOnlineTransaction(
                TransactionSource.PLAYER,
                TransactionType.PAY,
                sender,
                receiver,
                10.0
        );

        assertDoesNotThrow(() -> MoneyFlowValidationUtils.validateTransactionCommand(command));
    }

    @Test
    void validateTransactionCommand_online_shouldRejectNullCommand() {

        TransactionValidationException exception = assertThrows(
                TransactionValidationException.class,
                () -> MoneyFlowValidationUtils.validateTransactionCommand(null)
        );

        assertEquals("command must not be null", exception.getMessage());
    }

    @Test
    void validateTransactionCommand_online_shouldRejectNullContext() {
        TransactionOnlineCommand command = MoneyFlowValidationTestHelper.createOnlineTransaction(
                null,
                TransactionType.PAY,
                UUID.randomUUID(),
                UUID.randomUUID(),
                10.0
        );


        assertValidationMessage("context must be given!",
                () -> MoneyFlowValidationUtils.validateTransactionCommand(command));
    }

    @Test
    void validateTransactionCommand_online_shouldRejectWrongTransactionType() {
        TransactionOnlineCommand command = MoneyFlowValidationTestHelper.createOnlineTransaction(
                TransactionSource.PLAYER,
                TransactionType.SET,
                UUID.randomUUID(),
                UUID.randomUUID(),
                10.0
        );

        assertValidationMessage("transaction type must be " + TransactionType.PAY,
                () -> MoneyFlowValidationUtils.validateTransactionCommand(command)
        );
    }

    @ParameterizedTest
    @EnumSource(
            value = TransactionSource.class,
            mode = EnumSource.Mode.EXCLUDE,
            names = {"ADMIN_COMMAND", "PLAYER"}
    )
    void validateTransactionCommand_online_shouldRejectInvalidSource(TransactionSource source) {
        TransactionOnlineCommand command = MoneyFlowValidationTestHelper.createOnlineTransaction(
                source,
                TransactionType.PAY,
                UUID.randomUUID(),
                UUID.randomUUID(),
                10.0
        );

        assertValidationMessage("transaction source must be one of " + "[ADMIN_COMMAND, PLAYER]",
                () -> MoneyFlowValidationUtils.validateTransactionCommand(command)
        );
    }

    @Test
    void validateTransactionCommand_online_shouldAcceptAdminCommandSource() {
        TransactionOnlineCommand command = MoneyFlowValidationTestHelper.createOnlineTransaction(
                TransactionSource.ADMIN_COMMAND,
                TransactionType.PAY,
                UUID.randomUUID(),
                UUID.randomUUID(),
                10.0
        );

        assertDoesNotThrow(() -> MoneyFlowValidationUtils.validateTransactionCommand(command));
    }

    @Test
    void validateTransactionCommand_online_shouldRejectNullSender() {
        TransactionOnlineCommand command = MoneyFlowValidationTestHelper.createOnlineTransaction(
                TransactionSource.PLAYER,
                TransactionType.PAY,
                null,
                UUID.randomUUID(),
                10.0
        );

        assertValidationMessage("uuid must not be null",
                () -> MoneyFlowValidationUtils.validateTransactionCommand(command)
        );
    }

    @Test
    void validateTransactionCommand_online_shouldRejectNullReceiver() {
        TransactionOnlineCommand command = MoneyFlowValidationTestHelper.createOnlineTransaction(
                TransactionSource.PLAYER,
                TransactionType.PAY,
                UUID.randomUUID(),
                null,
                10.0
        );

        assertValidationMessage("uuid must not be null",
                () -> MoneyFlowValidationUtils.validateTransactionCommand(command)
        );
    }

    @ParameterizedTest
    @ValueSource(doubles = {
            0.0,
            -1.0,
            -100.0,
            Double.NaN,
            Double.POSITIVE_INFINITY,
            Double.NEGATIVE_INFINITY
    })
    void validateTransactionCommand_online_shouldRejectInvalidAmount(double amount) {
        TransactionOnlineCommand command = MoneyFlowValidationTestHelper.createOnlineTransaction(
                TransactionSource.PLAYER,
                TransactionType.PAY,
                UUID.randomUUID(),
                UUID.randomUUID(),
                amount
        );

        assertValidationMessage("amount must be a positive finite number",
                () -> MoneyFlowValidationUtils.validateTransactionCommand(command)
        );
    }

    @Test
    void validateTransactionCommand_online_shouldRejectSameSenderAndReceiver() {
        UUID uuid = UUID.randomUUID();

        TransactionOnlineCommand command = MoneyFlowValidationTestHelper.createOnlineTransaction(
                TransactionSource.PLAYER,
                TransactionType.PAY,
                uuid,
                uuid,
                10.0
        );

        assertValidationMessage("sender and receiver cannot be the same",
                () -> MoneyFlowValidationUtils.validateTransactionCommand(command)
        );
    }

    @Test
    void validateTransactionCommand_offline_shouldAcceptValidCommand() {
        UUID sender = UUID.randomUUID();
        UUID receiver = UUID.randomUUID();

        TransactionOfflineCommand command = MoneyFlowValidationTestHelper.createOfflineTransaction(
                TransactionSource.PLAYER,
                TransactionType.PAY,
                sender,
                10.0
        );

        assertDoesNotThrow(() -> MoneyFlowValidationUtils.validateTransactionCommand(command, receiver));
    }

    @Test
    void validateTransactionCommand_offline_shouldRejectNullCommand() {
        assertValidationMessage("command must not be null",
                () -> MoneyFlowValidationUtils.validateTransactionCommand(null, UUID.randomUUID())
        );
    }

    @Test
    void validateTransactionCommand_offline_shouldRejectNullReceiver() {
        TransactionOfflineCommand command = MoneyFlowValidationTestHelper.createOfflineTransaction(
                TransactionSource.PLAYER,
                TransactionType.PAY,
                UUID.randomUUID(),
                10.0
        );

        assertValidationMessage("uuid must not be null",
                () -> MoneyFlowValidationUtils.validateTransactionCommand(command, null)
        );
    }

    @Test
    void validateTransactionCommand_offline_shouldRejectSameSenderAndReceiver() {
        UUID uuid = UUID.randomUUID();

        TransactionOfflineCommand command = MoneyFlowValidationTestHelper.createOfflineTransaction(
                TransactionSource.PLAYER,
                TransactionType.PAY,
                uuid,
                10.0
        );

        assertValidationMessage("sender and receiver cannot be the same",
                () -> MoneyFlowValidationUtils.validateTransactionCommand(command, uuid)
        );
    }


    @Test
    void validateSetMoneyFlowBalanceCommand_online_shouldAcceptValidCommand() {
        SetMoneyFlowBalanceOnlineCommand command = MoneyFlowValidationTestHelper.createOnlineBalanceCommand(
                TransactionSource.PLAYER,
                TransactionType.SET,
                UUID.randomUUID(),
                100.0
        );

        assertDoesNotThrow(() -> MoneyFlowValidationUtils.validateSetMoneyFlowBalanceCommand(command));
    }

    @Test
    void validateSetMoneyFlowBalanceCommand_online_shouldRejectNullCommand() {
        assertValidationMessage("command must not be null",
                () -> MoneyFlowValidationUtils.validateSetMoneyFlowBalanceCommand((SetMoneyFlowBalanceOnlineCommand) null)
        );
    }

    @Test
    void validateSetMoneyFlowBalanceCommand_online_shouldRejectWrongType() {
        SetMoneyFlowBalanceOnlineCommand command = MoneyFlowValidationTestHelper.createOnlineBalanceCommand(
                TransactionSource.PLAYER,
                TransactionType.PAY,
                UUID.randomUUID(),
                100.0
        );

        assertValidationMessage("transaction type must be " + TransactionType.SET,
                () -> MoneyFlowValidationUtils.validateSetMoneyFlowBalanceCommand(command)
        );
    }

    @ParameterizedTest
    @ValueSource(doubles = {
            -1.0,
            -100.0,
            Double.NaN,
            Double.POSITIVE_INFINITY,
            Double.NEGATIVE_INFINITY
    })
    void validateSetMoneyFlowBalanceCommand_online_shouldRejectInvalidBalance(double balance) {
        SetMoneyFlowBalanceOnlineCommand command = MoneyFlowValidationTestHelper.createOnlineBalanceCommand(
                TransactionSource.PLAYER,
                TransactionType.SET,
                UUID.randomUUID(),
                balance
        );

        assertValidationMessage("amount must be a non-negative finite number",
                () -> MoneyFlowValidationUtils.validateSetMoneyFlowBalanceCommand(command)
        );
    }

    @Test
    void validateSetMoneyFlowBalanceCommand_online_shouldAcceptZeroBalance() {
        SetMoneyFlowBalanceOnlineCommand command = MoneyFlowValidationTestHelper.createOnlineBalanceCommand(
                TransactionSource.PLAYER,
                TransactionType.SET,
                UUID.randomUUID(),
                0.0
        );

        assertDoesNotThrow(() -> MoneyFlowValidationUtils.validateSetMoneyFlowBalanceCommand(command));
    }

    @Test
    void validateSetMoneyFlowBalanceCommand_online_shouldRejectNullTargetUuid() {
        SetMoneyFlowBalanceOnlineCommand command = MoneyFlowValidationTestHelper.createOnlineBalanceCommand(
                TransactionSource.PLAYER,
                TransactionType.SET,
                null,
                100.0
        );
        assertValidationMessage("uuid must not be null",
                () -> MoneyFlowValidationUtils.validateSetMoneyFlowBalanceCommand(command)
        );
    }

    @Test
    void validateSetMoneyFlowBalanceCommand_offline_shouldAcceptValidCommand() {
        SetMoneyFlowBalanceOfflineCommand command = MoneyFlowValidationTestHelper.createOfflineBalanceCommand(
                TransactionSource.PLAYER,
                TransactionType.SET,
                "John",
                100.0
        );

        assertDoesNotThrow(() -> MoneyFlowValidationUtils.validateSetMoneyFlowBalanceCommand(command));
    }

    @Test
    void validateSetMoneyFlowBalanceCommand_offline_shouldRejectNullCommand() {
        assertValidationMessage("command must not be null",
                () -> MoneyFlowValidationUtils.validateSetMoneyFlowBalanceCommand((SetMoneyFlowBalanceOfflineCommand) null)
        );
    }

    @Test
    void validateSetMoneyFlowBalanceCommand_offline_shouldRejectNullName() {
        SetMoneyFlowBalanceOfflineCommand command = MoneyFlowValidationTestHelper.createOfflineBalanceCommand(
                TransactionSource.PLAYER,
                TransactionType.SET,
                null,
                100.0
        );

        assertValidationMessage("name must not be null",
                () -> MoneyFlowValidationUtils.validateSetMoneyFlowBalanceCommand(command)
        );
    }

    @Test
    void validateSetMoneyFlowBalanceCommand_offline_shouldRejectInvalidBalance() {
        SetMoneyFlowBalanceOfflineCommand command = MoneyFlowValidationTestHelper.createOfflineBalanceCommand(
                TransactionSource.PLAYER,
                TransactionType.SET,
                "John",
                -1.0
        );

        assertValidationMessage("amount must be a non-negative finite number",
                () -> MoneyFlowValidationUtils.validateSetMoneyFlowBalanceCommand(command)
        );
    }

    private void assertValidationMessage(String expectedMessage, Executable executable) {
        TransactionValidationException exception = assertThrows(TransactionValidationException.class, executable);

        assertEquals(expectedMessage, exception.getMessage());
    }
}