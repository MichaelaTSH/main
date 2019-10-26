package seedu.address.inventory.commands;

import seedu.address.inventory.logic.commands.Command;
import seedu.address.inventory.logic.commands.CommandResult;
import seedu.address.inventory.model.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTestUtil {
    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link seedu.address.cashier.logic.commands.CommandResult}
     * matches {@code expectedCommandResult} <br>
     * - the {@code expectedModel} matches {@code inventoryModel}
     */
    public static void assertCommandSuccess(Command command, String expectedCommandResult,
                                            Model expectedModel, Model inventoryModel) {
        try {
            CommandResult result = command.execute(inventoryModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, inventoryModel);
            assertEquals(expectedModel.getInventoryList(), inventoryModel.getInventoryList());
        } catch (Exception e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }
}
