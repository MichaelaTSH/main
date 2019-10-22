package seedu.address.transaction.logic.commands;

import seedu.address.util.OverallCommandResult;

/**
 * Represents the result of a command execution.
 */
public class CommandResult extends OverallCommandResult {

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }

    public CommandResult(String feedbackToUser, boolean exit) {
        super(feedbackToUser, exit);
    }
}
