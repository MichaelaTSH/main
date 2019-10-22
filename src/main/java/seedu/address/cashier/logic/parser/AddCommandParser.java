package seedu.address.cashier.logic.parser;

import static seedu.address.cashier.ui.CashierMessages.MESSAGE_INSUFFICIENT_STOCK;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_INVALID_ADDCOMMAND_FORMAT;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_ITEM_FOR_SALE_CASHIER;
import static seedu.address.cashier.ui.CashierMessages.QUANTITY_NOT_A_NUMBER;
import static seedu.address.cashier.ui.CashierMessages.itemsByCategory;
import static seedu.address.cashier.ui.CashierMessages.noSuchItemRecommendation;
import static seedu.address.util.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.util.CliSyntax.PREFIX_QUANTITY;

import java.util.ArrayList;
import java.util.stream.Stream;

import seedu.address.cashier.logic.commands.AddCommand;
import seedu.address.cashier.logic.commands.exception.InsufficientAmountException;
import seedu.address.cashier.logic.commands.exception.NotANumberException;
import seedu.address.cashier.logic.exception.NegativeQuantityException;
import seedu.address.cashier.logic.parser.exception.ParseException;
import seedu.address.cashier.model.Model;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.util.ArgumentMultimap;
import seedu.address.util.ArgumentTokenizer;
import seedu.address.util.Prefix;

/**
 * Parses input arguments and creates a new AddCommand object.
 */
public class AddCommandParser {

    private static ArgumentMultimap argMultimap;

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @param args to be passed in
     * @param modelManager which the command operates on
     * @throws Exception if the user input does not conform the expected format
     */
    public static AddCommand parse(String args, Model modelManager) throws Exception {
        if (!args.contains(" c/")) {
            argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_QUANTITY);

            if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_QUANTITY)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(MESSAGE_INVALID_ADDCOMMAND_FORMAT);
            }
        } else {
            argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_DESCRIPTION, PREFIX_QUANTITY);

            String category = argMultimap.getValue(PREFIX_CATEGORY).get();

            if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_QUANTITY)
                    || !argMultimap.getPreamble().isEmpty()) {
                ArrayList<String> listItems = modelManager.getDescriptionByCategory(category);
                throw new ParseException(itemsByCategory(listItems));
            }
        }

        String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();
        String quantityString = argMultimap.getValue(PREFIX_QUANTITY).get();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityString);
        } catch (Exception e) {
            throw new NotANumberException(QUANTITY_NOT_A_NUMBER);
        }

        modelManager.readInUpdatedList();
        // if the item with the specified description is not present
        if (!modelManager.hasItemInInventory(description)) {
            ArrayList<String> recommendedItems = modelManager.getRecommendedItems(description);
            throw new NoSuchItemException(noSuchItemRecommendation(recommendedItems));
        }

        // if the item with the specified description is not available for sale
        if (!modelManager.isSellable(description)) {
            throw new NoSuchItemException(NO_SUCH_ITEM_FOR_SALE_CASHIER);
        }

        if (!modelManager.hasSufficientQuantityToAdd(description, quantity)) {
            int quantityLeft = modelManager.getStockLeft(description);
            throw new InsufficientAmountException(String.format(MESSAGE_INSUFFICIENT_STOCK, quantityLeft, description));
        }
        if (quantity < 0) {
            throw new NegativeQuantityException(CashierMessages.QUANTITY_NOT_POSITIVE);
        }
        if (modelManager.hasItemInInventory(description)
                && modelManager.hasSufficientQuantityToAdd(description, quantity)) {
            AddCommand addCommand = new AddCommand(description, quantity);
            return addCommand;
        }
        return null;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

}

