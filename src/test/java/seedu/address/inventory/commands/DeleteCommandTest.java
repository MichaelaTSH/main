package seedu.address.inventory.commands;

import static seedu.address.inventory.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;
import seedu.address.inventory.logic.commands.CommandResult;
import seedu.address.inventory.logic.commands.DeleteCommand;
import seedu.address.inventory.model.ModelManager;
import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.ui.InventoryMessages;
import seedu.address.inventory.util.InventoryList;
import seedu.address.testutil.TypicalItem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DeleteCommandTest {

    @Test
    void execute_nonEmptyInventoryList_successful() throws NoSuchIndexException {
        DeleteCommand deleteCommand = new DeleteCommand(Integer.parseInt(TypicalItem.BLACK_SHIRT.getId()));
        String message = String.format(String.format(InventoryMessages.MESSAGE_DELETED_ITEM, TypicalItem.BLACK_SHIRT));
        InventoryList inventoryList = new InventoryList(TypicalItem.getTypicalInventoryList().getiArrayList());
        ModelManager expectedModel = new ModelManager(inventoryList);
        expectedModel.deleteItem(Integer.parseInt(TypicalItem.BLACK_SHIRT.getId()));
        assertCommandSuccess(deleteCommand, message, expectedModel);
    }

    @Test
    void execute_noItemAtIndexSpecified_unsuccessful() throws NoSuchIndexException {
        InventoryList inventoryList = new InventoryList(TypicalItem.getTypicalInventoryList().getiArrayList());
        ModelManager expectedModel = new ModelManager(inventoryList);
        DeleteCommand deleteCommand = new DeleteCommand(7);
        CommandResult commandResult = deleteCommand.execute(expectedModel);
        assertEquals(InventoryMessages.NO_SUCH_INDEX_INVENTORY, commandResult.getFeedbackToUser());
    }

    @Test
    void execute_noTransactionOfPersonSpecifiedFilteredListButInTransactionList_successful() {
        showTransactionsOfPerson(model, TypicalPersons.ALICE.getName().toString());
        DeleteNameCommand deleteNameCommand = new DeleteNameCommand(TypicalPersons.BENSON);
        String message = String.format(String.format(MESSAGE_DELETE_BY_PERSON, TypicalPersons.BENSON));
        ModelManager expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        showTransactionsOfPerson(expectedModel, TypicalPersons.ALICE.getName().toString());
        expectedModel.deleteAllTransactionOfPerson(TypicalPersons.BENSON);
        assertCommandSuccess(deleteNameCommand, model, message, expectedModel, personModel);
    }
}
