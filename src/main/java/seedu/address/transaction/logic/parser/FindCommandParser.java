package seedu.address.transaction.logic.parser;

import java.util.Arrays;

import seedu.address.transaction.logic.commands.FindCommand;
import seedu.address.transaction.logic.parser.exception.ParseException;
import seedu.address.transaction.model.TransactionContainsKeywordsPredicate;
import seedu.address.transaction.ui.TransactionMessages;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements IndependentCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    TransactionMessages.MESSAGE_INVALID_FIND_COMMAND_FORMAT);
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new TransactionContainsKeywordsPredicate((Arrays.asList(nameKeywords))));
    }
}
