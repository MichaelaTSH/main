package seedu.address.inventory.commands;

import seedu.address.inventory.model.ModelManager;
import seedu.address.inventory.ui.InventoryMessages;

public class SortResetCommand extends SortCommand {
    @Override
    public CommandResult execute(ModelManager model) throws Exception {
        model.sortReset();
        return new CommandResult(InventoryMessages.MESSAGE_RESET_TO_ORIGINAL_ORDER);
    }
}
