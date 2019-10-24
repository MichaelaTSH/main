package seedu.address.inventory.model;

import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.util.InventoryList;

/**
 * The API of the Model component.
 */
public interface Model {
    InventoryList getInventoryList();

    void addItem(Item item);

    Item findItemByIndex(int index) throws NoSuchIndexException;

    void deleteItem(int index);

    void writeInInventoryFile() throws Exception;

    void setItem(int i, Item editedItem) throws Exception;

    boolean hasItemInInventory(Item item);

    void sortByDescription();

    void sortByCategory();

    void sortByQuantity();

    void sortReset();

    void readInUpdatedList();

}
