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

public class DeleteCommandTest {
    private ModelManager model =
            new ModelManager(new InventoryList(TypicalItem.getTypicalInventoryList().getiArrayList()));


    @Test
    void execute_nonEmptyInventoryList_successful() throws NoSuchIndexException {
        DeleteCommand deleteCommand = new DeleteCommand(Integer.parseInt(TypicalItem.BLACK_SHIRT.getId()));
        String message = String.format(String.format(InventoryMessages.MESSAGE_DELETED_ITEM, TypicalItem.BLACK_SHIRT));
        InventoryList inventoryList = new InventoryList(TypicalItem.getTypicalInventoryList().getiArrayList());
        ModelManager expectedModel = new ModelManager(inventoryList);
        expectedModel.deleteItem(Integer.parseInt(TypicalItem.BLACK_SHIRT.getId()));
        assertCommandSuccess(deleteCommand, message, expectedModel, model);
    }

    @Test
    void execute_noItemAtIndexSpecified_unsuccessful() throws NoSuchIndexException {
        InventoryList inventoryList = new InventoryList(TypicalItem.getTypicalInventoryList().getiArrayList());
        ModelManager expectedModel = new ModelManager(inventoryList);
        DeleteCommand deleteCommand = new DeleteCommand(7);
        CommandResult commandResult = deleteCommand.execute(expectedModel);
        assertEquals(InventoryMessages.NO_SUCH_INDEX_INVENTORY, commandResult.getFeedbackToUser());
    }
}
