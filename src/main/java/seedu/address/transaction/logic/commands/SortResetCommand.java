package seedu.address.transaction.logic.commands;

import seedu.address.transaction.model.Model;
import seedu.address.transaction.ui.TransactionMessages;

/**
 * Resets transactions in the transaction list to their original ordering when read from the file.
 */
public class SortResetCommand extends SortCommand {

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        model.sortReset();
        return new CommandResult(TransactionMessages.MESSAGE_RESET_TO_ORIGINAL_ORDER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortResetCommand); // instanceof handles nulls
    }
}
