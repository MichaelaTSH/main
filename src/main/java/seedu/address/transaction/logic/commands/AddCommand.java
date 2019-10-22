package seedu.address.transaction.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_ADD_TRANSACTION;

import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;

/**
 * Adds a transaction to the transaction list.
 */
public class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    private final Transaction transaction;

    /**
     * Creates an AddCommand to add the specified {@code Transaction}
     */
    public AddCommand(Transaction transaction) {
        requireNonNull(transaction);
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        model.resetPredicate();
        model.addTransaction(transaction);
        return new CommandResult(String.format(MESSAGE_ADD_TRANSACTION, transaction));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && transaction.equals(((AddCommand) other).transaction));
    }
}
