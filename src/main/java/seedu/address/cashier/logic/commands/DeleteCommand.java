package seedu.address.cashier.logic.commands;

import static seedu.address.cashier.ui.CashierMessages.MESSAGE_DELETED_ITEM;

import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.inventory.model.Item;
import seedu.address.person.model.Model;

/**
 * Deletes an item identified using its displayed index from the sales list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    private int index;

    /**
     * Creates a DeleteCommand to delete an item
     * @param index of the item in the sales list to be deleted
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(ModelManager modelManager, Model personModel,
                                 seedu.address.transaction.model.Model transactionModel,
                                 seedu.address.inventory.model.Model inventoryModel) throws NoSuchIndexException {
        Item item = modelManager.findItemByIndex(index);
        modelManager.deleteItem(index);
        return new CommandResult(String.format(MESSAGE_DELETED_ITEM, item));
    }
}
