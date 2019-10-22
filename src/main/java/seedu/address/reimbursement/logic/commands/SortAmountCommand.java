package seedu.address.reimbursement.logic.commands;

import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.reimbursement.model.Model;
import seedu.address.reimbursement.ui.ReimbursementMessages;

/**
 * Represents a command to sort by amount.
 */
public class SortAmountCommand extends SortCommand {
    private final Logger logger = LogsCenter.getLogger(getClass());

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        ReimbursementMessages reimbursementMessages = new ReimbursementMessages();
        model.sortListByAmount();
        logger.info("sort by amount");
        return new CommandResult(reimbursementMessages.SORT_BY_AMOUNT);
    }
}
