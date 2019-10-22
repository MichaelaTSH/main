package seedu.address.cashier.logic.parser;

import seedu.address.cashier.logic.commands.ClearCommand;

/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * and returns an ClearCommand object for execution.
     */
    public static ClearCommand parse() {
        return new ClearCommand();
    }
}
