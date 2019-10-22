package seedu.address.cashier.util;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.inventory.model.Item;

/**
 * Wraps all data of the inventory into a list.
 * Duplicates are allowed but are considered the same item when commands are done.
 */
public class InventoryList {

    private static ArrayList<Item> iArrayList;
    private static ObservableList<Item> iList;

    /**
     * Initialises the inventory list when there are no prior items inputted.
     */
    public InventoryList() {
        iArrayList = new ArrayList<>();
        this.iList = FXCollections.observableList(iArrayList);
    }

    /**
     * Initialises the inventory list when there are prior inputted items.
     * @param iArrayList Array list of the items saved.
     */
    public InventoryList(ArrayList<Item> iArrayList) {
        this.iArrayList = iArrayList;
        this.iList = FXCollections.observableList(iArrayList);
    }

    public ArrayList<Item> getiArrayList() {
        return iArrayList;
    }

    public ObservableList<Item> getiList() {
        return iList;
    }

    /**
     * Returns the item of given description.
     * @param description of the item
     * @return Item specified
     * @throws NoSuchItemException if the description is invalid.
     */
    public int getIndex(String description) throws NoSuchItemException {
        for (int i = 0; i < iList.size(); i++) {
            if (iList.get(i).getDescription().equalsIgnoreCase(description)) {
                return i;
            }
        }
        throw new NoSuchItemException(CashierMessages.NO_SUCH_ITEM_CASHIER);
    }

    /**
     * Retrieve the original item in the inventory list according to the specified description.
     * @param description of the item to find
     * @return original item in the inventory list
     * @throws NoSuchItemException if the description is invalid
     */
    public static Item getOriginalItem(String description) throws NoSuchItemException {
        for (int i = 0; i < iList.size(); i++) {
            if (iList.get(i).getDescription().equalsIgnoreCase(description)) {
                return iList.get(i);
            }
        }
        throw new NoSuchItemException(CashierMessages.NO_SUCH_ITEM_CASHIER);
    }

    /**
     * Retrieve the original item in the inventory list according to the specified item.
     * @param item to find
     * @return original item in the inventory list
     * @throws NoSuchItemException if the item is invalid
     */
    public static Item getOriginalItem(Item item) throws NoSuchItemException {
        for (int i = 0; i < iList.size(); i++) {
            if (iList.get(i).isSameItem(item)) {
                return iList.get(i);
            }
        }
        throw new NoSuchItemException(CashierMessages.NO_SUCH_ITEM_CASHIER);
    }

    /**
     * Returns the item of given index.
     * @param index of the item
     * @return Item specified
     * @throws NoSuchIndexException if the index is invalid.
     */
    public static Item getItemByIndex(int index) throws NoSuchIndexException {
        if (index >= iList.size()) {
            throw new NoSuchIndexException(CashierMessages.NO_SUCH_INDEX_CASHIER);
        } else {
            return iList.get(index);
        }
    }

    /**
     * Checks if the item exists in the inventory list according to the description.
     * @param description of the item to check
     * @return true if the list contains the item. Else, return false
     */
    public static boolean hasItem(String description) {
        for (int i = 0; i < iList.size(); i++) {
            if (iList.get(i).getDescription().equalsIgnoreCase(description)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the size of the inventory list.
     * @return the size of the inventory list
     */
    public static int size() {
        return iList.size();
    }

    /**
     * Sets a new item according to the index.
     * @param i the index of the item to be replaced
     * @param item the new item to substitute the existing one
     */
    public void set(int i, Item item) {
        iList.set(i, item);
    }

    /**
     * Returns a list of description of items according to category
     * @param category of the items
     * @return a list of description of items according to category
     */
    public ArrayList<String> getAllSalesDescriptionByCategory(String category) {
        ArrayList<String> categoryItems = new ArrayList<>();
        for (Item i : iList) {
            if (i.getCategory().equalsIgnoreCase(category) && i.isSellable()) {
                categoryItems.add(i.getDescription());
            }
        }
        return categoryItems;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InventoryList // instanceof handles nulls
                && iArrayList.equals(((InventoryList) other).getiArrayList())
                && iList.equals(((InventoryList) other).getiList()));
    }

}

