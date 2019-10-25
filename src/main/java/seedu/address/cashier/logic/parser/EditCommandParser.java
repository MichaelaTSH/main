package seedu.address.cashier.logic.parser;

import static seedu.address.cashier.logic.parser.AddCommandParser.arePrefixesPresent;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_INSUFFICIENT_STOCK;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_INDEX_CASHIER;
import static seedu.address.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.util.CliSyntax.PREFIX_INDEX;
import static seedu.address.util.CliSyntax.PREFIX_QUANTITY;

import seedu.address.cashier.logic.commands.EditCommand;
import seedu.address.cashier.logic.commands.exception.InsufficientAmountException;
import seedu.address.cashier.logic.commands.exception.NegativeQuantityException;
import seedu.address.cashier.logic.commands.exception.NotANumberException;
import seedu.address.cashier.logic.parser.exception.ParseException;
import seedu.address.cashier.model.Model;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.util.ArgumentMultimap;
import seedu.address.util.ArgumentTokenizer;
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws NotANumberException if the user input is not a number
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args, Model modelManager)
            throws NotANumberException, ParseException, NoSuchItemException,
            InsufficientAmountException, NegativeQuantityException, NoSuchIndexException {
        int index;
        int quantity;
        ArgumentMultimap argMultimap;

        if (args.contains(" i/")) {
            argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_QUANTITY);

            if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_QUANTITY)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(CashierMessages.MESSAGE_INVALID_EDITCOMMAND_FORMAT);
            }

            String indexString = argMultimap.getValue(PREFIX_INDEX).get();
            try {
                index = Integer.parseInt(indexString);
            } catch (Exception e) {
                throw new NotANumberException(CashierMessages.INDEX_NOT_A_NUMBER);
            }

            try {
                modelManager.findItemByIndex(index);
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchIndexException(NO_SUCH_INDEX_CASHIER);
            }

        } else {
            argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_QUANTITY);

            if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_QUANTITY)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(CashierMessages.MESSAGE_INVALID_EDITCOMMAND_FORMAT);
            }
            String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();

            index = modelManager.findIndexByDescription(description) + 1;
        }

        String quantityString = argMultimap.getValue(PREFIX_QUANTITY).get();
        try {
            quantity = Integer.parseInt(quantityString);
        } catch (Exception e) {
            throw new NotANumberException(CashierMessages.QUANTITY_NOT_A_NUMBER);
        }
        if (quantity < 0) {
            throw new NegativeQuantityException(CashierMessages.QUANTITY_NOT_POSITIVE);
        }
        if (!modelManager.hasSufficientQuantityToEdit(index, quantity)) {
            String description = modelManager.findItemByIndex(index).getDescription();
            int quantityLeft = modelManager.getStockLeft(description);
            throw new InsufficientAmountException(String.format(MESSAGE_INSUFFICIENT_STOCK, quantityLeft, description));
        }

        return new EditCommand(index, quantity);
    }

}
