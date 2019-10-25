package seedu.address.transaction.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.transaction.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.transaction.logic.commands.CommandTestUtil.showTransactionsOfPerson;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_DELETE_BY_PERSON;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_NO_SUCH_TRANSACTION_OF_PERSON;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.Model;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.ModelManager;
import seedu.address.transaction.model.Transaction;

class DeleteNameCommandTest {
    private ModelManager model = new ModelManager(TypicalTransactions.getTypicalTransactionList());
    private Model personModel = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_unFilteredList_successful() {
        DeleteNameCommand deleteNameCommand = new DeleteNameCommand(TypicalPersons.ALICE);
        String message = String.format(String.format(MESSAGE_DELETE_BY_PERSON, TypicalPersons.ALICE));
        ModelManager expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        expectedModel.deleteAllTransactionOfPerson(TypicalPersons.ALICE);
        assertCommandSuccess(deleteNameCommand, model, message,
                expectedModel, personModel);
    }

    @Test
    void execute_filteredList_successful() {
        showTransactionsOfPerson(model, TypicalPersons.ALICE.getName().toString());
        DeleteNameCommand deleteNameCommand = new DeleteNameCommand(TypicalPersons.ALICE);
        String message = String.format(String.format(MESSAGE_DELETE_BY_PERSON, TypicalPersons.ALICE));
        ModelManager expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        expectedModel.deleteAllTransactionOfPerson(TypicalPersons.ALICE);
        showNoTransaction(expectedModel);
        assertCommandSuccess(deleteNameCommand, model, message, expectedModel, personModel);
    }

    @Test
    void execute_noTransactionOfPersonSpecifiedUnfilteredList_unsuccessful() {
        DeleteNameCommand deleteNameCommand = new DeleteNameCommand(TypicalPersons.DANIEL);
        String message = String.format(String.format(MESSAGE_NO_SUCH_TRANSACTION_OF_PERSON,
                TypicalPersons.DANIEL.getName().toString()));
        assertCommandFailure(deleteNameCommand, model, message, personModel);
    }

    @Test
    void execute_noTransactionOfPersonSpecifiedFilteredListButInTransactionList_successful() {
        showTransactionsOfPerson(model, TypicalPersons.ALICE.getName().toString());
        DeleteNameCommand deleteNameCommand = new DeleteNameCommand(TypicalPersons.BENSON);
        String message = String.format(String.format(MESSAGE_DELETE_BY_PERSON, TypicalPersons.BENSON));
        ModelManager expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        showTransactionsOfPerson(expectedModel, TypicalPersons.ALICE.getName().toString());
        expectedModel.deleteAllTransactionOfPerson(TypicalPersons.BENSON);
        assertCommandSuccess(deleteNameCommand, model, message, expectedModel, personModel);
    }

    /**
     * Updates {@code model}'s filtered list to show no transaction.
     */
    private void showNoTransaction(seedu.address.transaction.model.Model model) {
        Predicate<Transaction> predicate = new Predicate<Transaction>() {
            @Override
            public boolean test(Transaction transaction) {
                return false;
            }
        };
        model.updatePredicate(predicate);
        assertTrue(model.getFilteredList().getTarrList().isEmpty());
    }

    @Test
    public void equals() {

        DeleteNameCommand delete1Command = new DeleteNameCommand(ALICE);
        DeleteNameCommand anotherDelete1Command = new DeleteNameCommand(ALICE);
        DeleteNameCommand delete2Command = new DeleteNameCommand(BENSON);

        // same object -> returns true
        assertTrue(delete1Command.equals(delete1Command));

        assertTrue(delete1Command.equals(anotherDelete1Command));

        // different types -> returns false
        assertFalse(delete1Command.equals(1));

        // null -> returns false
        assertFalse(delete1Command.equals(null));

        // different delete name command -> returns false
        assertFalse(delete1Command.equals(delete2Command));
    }
}
