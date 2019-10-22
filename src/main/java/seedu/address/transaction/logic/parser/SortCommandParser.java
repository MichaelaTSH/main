package seedu.address.transaction.logic.parser;

import seedu.address.transaction.logic.commands.SortAmountCommand;
import seedu.address.transaction.logic.commands.SortCommand;
import seedu.address.transaction.logic.commands.SortDateCommand;
import seedu.address.transaction.logic.commands.SortNameCommand;
import seedu.address.transaction.logic.commands.SortResetCommand;
import seedu.address.transaction.logic.commands.exception.NoSuchSortException;
import seedu.address.transaction.ui.TransactionMessages;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements IndependentCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws NoSuchSortException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws NoSuchSortException {
        /*String[] argsArr = args.trim().split(" ");
        if (argsArr.length > 1 && argsArr[1].equals("date")) {
            return new SortDateCommand();
        } else if (argsArr.length > 1 && argsArr[1].equals("name")) {
            return new SortNameCommand();
        } else if (argsArr.length > 1 && argsArr[1].equals("amount")) {
            return new SortAmountCommand();
        } else if (argsArr.length > 1 && argsArr[1].equals("reset")) {
            return new SortResetCommand();
        } else {
            throw new NoSuchSortException(TransactionMessages.MESSAGE_NO_SUCH_SORT_COMMAND);
        }*/

        String argsArr = args.trim();
        if (argsArr.equals("date")) {
            return new SortDateCommand();
        } else if (argsArr.equals("name")) {
            return new SortNameCommand();
        } else if (argsArr.equals("amount")) {
            return new SortAmountCommand();
        } else if (argsArr.equals("reset")) {
            return new SortResetCommand();
        } else {
            throw new NoSuchSortException(TransactionMessages.MESSAGE_NO_SUCH_SORT_COMMAND);
        }
    }
}
