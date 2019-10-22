package seedu.address.transaction.logic.commands;

import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_DELETE_TRANSACTION;

import seedu.address.transaction.logic.parser.exception.ParseException;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.ui.TransactionMessages;

/**
 * Deletes a transaction to the transaction list according to the index shown on UI.
 */
public class DeleteIndexCommand extends DeleteCommand {
    private final int index;

    /**
     * Creates an DeleteIndexCommand to delete the specified {@code Transaction} according to index.
     */
    public DeleteIndexCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) throws ParseException {
        Transaction transaction;
        try {
            transaction = model.findTransactionInFilteredListByIndex(index);
            model.deleteTransaction(index);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(TransactionMessages.MESSAGE_NO_SUCH_TRANSACTION);
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TRANSACTION, transaction));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteIndexCommand // instanceof handles nulls
                && index == ((DeleteIndexCommand) other).index);
    }
}
