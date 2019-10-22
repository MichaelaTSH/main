package seedu.address.cashier.logic.commands;

import static seedu.address.cashier.ui.CashierMessages.MESSAGE_ADD_CASHIER;

import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.person.logic.commands.exceptions.CommandException;
import seedu.address.person.model.Model;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.exception.NoSuchPersonException;

/**
 * Sets a cashier who is in-charge of the sales made.
 */
public class SetCashierCommand extends Command {

    public static final String COMMAND_WORD = "cashier";
    private Person cashier;


    /**
     * Creates a SetCashierCommand to add the specified {@code Person}
     */
    public SetCashierCommand(Person p) {
        cashier = p;
    }

    @Override
    public CommandResult execute(ModelManager modelManager, Model personModel,
                                 seedu.address.transaction.model.Model transactionModel,
                                 seedu.address.inventory.model.Model inventoryModel)
            throws NoSuchIndexException, CommandException, NoSuchPersonException {
        modelManager.setCashier(cashier);
        return new CommandResult(String.format(MESSAGE_ADD_CASHIER, cashier.getName()));
    }

}
