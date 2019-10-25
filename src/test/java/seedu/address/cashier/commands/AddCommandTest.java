package seedu.address.cashier.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.cashier.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_ADDED_ITEM;
import static seedu.address.cashier.ui.CashierMessages.MESSAGE_INSUFFICIENT_STOCK;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_ITEM_FOR_SALE_CASHIER;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.logic.commands.AddCommand;
import seedu.address.cashier.logic.commands.CommandResult;
import seedu.address.cashier.logic.commands.exception.InsufficientAmountException;
import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.inventory.model.Item;
import seedu.address.person.model.UserPrefs;
import seedu.address.stubs.CashierModelStubAcceptingItemAdded;
import seedu.address.stubs.InventoryModelStubAcceptingItemAdded;
import seedu.address.testutil.TypicalItem;
import seedu.address.testutil.TypicalTransactions;

public class AddCommandTest {


    private ModelManager model = new ModelManager(TypicalItem.getTypicalInventoryList(),
            TypicalTransactions.getTypicalTransactionList());

    private seedu.address.person.model.Model personModel =
            new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullDescription_throwAssertionException() {
        assertThrows(AssertionError.class, () -> new AddCommand(null, 6));
    }

    @Test
    public void constructor_negativeQuantity_throwAssertionException() {
        assertThrows(AssertionError.class, () -> new AddCommand(TypicalItem.FISH_BURGER.getDescription(), -5));
    }

    @Test
    public void execute_itemAcceptedByModel_addSuccessful() throws NoSuchItemException, InsufficientAmountException {
        InventoryModelStubAcceptingItemAdded inventoryModelStubWithItem = new InventoryModelStubAcceptingItemAdded();
        inventoryModelStubWithItem.addItem(TypicalItem.FISH_BURGER);

        CashierModelStubAcceptingItemAdded modelStubWithItem = new CashierModelStubAcceptingItemAdded();
        modelStubWithItem.setInventoryModelStub(inventoryModelStubWithItem);

        Item anotherItem = TypicalItem.FISH_BURGER;
        AddCommand addCommand = new AddCommand(anotherItem.getDescription(),
                anotherItem.getQuantity());
        CommandResult commandResult = addCommand.execute(modelStubWithItem, personModel);

        assertEquals(String.format(MESSAGE_ADDED_ITEM, anotherItem.getDescription()),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(anotherItem), modelStubWithItem.getItemsAdded());

    }

    @Test
    public void execute_nonExistingItemInInventory_throwsAssertionException() {
        InventoryModelStubAcceptingItemAdded inventoryModelStubWithItem = new InventoryModelStubAcceptingItemAdded();

        CashierModelStubAcceptingItemAdded modelStubWithItem = new CashierModelStubAcceptingItemAdded();
        modelStubWithItem.setInventoryModelStub(inventoryModelStubWithItem);

        Item anotherItem = TypicalItem.BLACK_SHIRT;
        AddCommand addCommand = new AddCommand(anotherItem.getDescription(),
                anotherItem.getQuantity());

        String expectedMessage = CashierMessages.NO_SUCH_ITEM_CASHIER;
        assertCommandFailure(addCommand, modelStubWithItem, expectedMessage, personModel);

    }

    @Test
    public void execute_invalidQuantity_throwInsufficientStockException() {

        InventoryModelStubAcceptingItemAdded inventoryModelStubWithItem = new InventoryModelStubAcceptingItemAdded();
        inventoryModelStubWithItem.addItem(TypicalItem.FISH_BURGER);

        Item anotherItem = TypicalItem.FISH_BURGER;
        AddCommand addCommand = new AddCommand(anotherItem.getDescription(),
                anotherItem.getQuantity() + 70);

        String message = String.format(MESSAGE_INSUFFICIENT_STOCK,
                anotherItem.getQuantity(), anotherItem.getDescription());
        assertCommandFailure(addCommand, model, message, personModel);
    }

    @Test
    public void execute_notAvailableForSaleItem_throwsNoSuchItemForSaleException() {
        InventoryModelStubAcceptingItemAdded inventoryModelStubWithItem = new InventoryModelStubAcceptingItemAdded();
        inventoryModelStubWithItem.addItem(TypicalItem.PHONE_CASE);

        CashierModelStubAcceptingItemAdded modelStubWithItem = new CashierModelStubAcceptingItemAdded();
        modelStubWithItem.setInventoryModelStub(inventoryModelStubWithItem);

        Item anotherItem = TypicalItem.PHONE_CASE;
        AddCommand addCommand = new AddCommand(anotherItem.getDescription(),
                anotherItem.getQuantity());
        String expectedMessage = NO_SUCH_ITEM_FOR_SALE_CASHIER;

        assertCommandFailure(addCommand, modelStubWithItem, expectedMessage, personModel);
    }

}

