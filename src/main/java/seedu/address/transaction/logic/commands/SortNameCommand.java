package seedu.address.transaction.logic.commands;

import seedu.address.transaction.model.Model;
import seedu.address.transaction.ui.TransactionMessages;

/**
 * Sorts transactions in the transaction list by their name.
 * Sorts it in alphabetical order of name.
 */
public class SortNameCommand extends SortCommand {

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        model.sortByName();
        return new CommandResult(TransactionMessages.MESSAGE_SORTED_BY_NAME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortNameCommand); // instanceof handles nulls
    }
}
