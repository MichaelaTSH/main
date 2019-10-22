package seedu.address.inventory.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.inventory.ui.InventoryMessages;

/**
 * Wraps all data of the inventory into a list.
 * Duplicates are allowed but are considered the same item when commands are done.
 */
public class InventoryList {
    private static ArrayList<Item> iList;
    private static Integer count;

    public InventoryList() {
        this.iList = new ArrayList<Item>();
        this.count = 0;
    }

    public InventoryList(ArrayList<Item> list) {
        this.iList = list;
        this.count = 0;
    }

    public static Integer getCount() {
        return count;
    }

    public static void increaseCount() {
        count += 1;
    }

    public static Item getItemByIndex(int index) throws NoSuchIndexException {
        if (index >= iList.size()) {
            throw new NoSuchIndexException(InventoryMessages.NO_SUCH_INDEX_INVENTORY);
        } else {
            return iList.get(index);
        }
    }

    public int getIndex(String description) throws NoSuchItemException {
        for (int i = 0; i < iList.size(); i++) {
            if (iList.get(i).getDescription().equalsIgnoreCase(description)) {
                return i;
            }
        }
        throw new NoSuchItemException(InventoryMessages.NO_SUCH_ITEM_INVENTORY);
    }

    public static Item getOriginalItem(String description) throws
            seedu.address.inventory.model.exception.NoSuchItemException {
        for (int i = 0; i < iList.size(); i++) {
            if (iList.get(i).getDescription().equalsIgnoreCase(description)) {
                return iList.get(i);
            }
        }
        throw new seedu.address.inventory.model.exception.NoSuchItemException(InventoryMessages.NO_SUCH_ITEM_INVENTORY);
    }

    public static Item getOriginalItem(Item item) throws seedu.address.inventory.model.exception.NoSuchItemException {
        for (int i = 0; i < iList.size(); i++) {
            if (iList.get(i).isSameItem(item)) {
                return iList.get(i);
            }
        }
        throw new seedu.address.inventory.model.exception.NoSuchItemException(InventoryMessages.NO_SUCH_ITEM_INVENTORY);
    }

    public void add(Item item) {
        iList.add(item);
    }

    public void delete(int index) {
        iList.remove(index);
    }

    public static int size() {
        return iList.size();
    }

    public void set(int i, Item item) {
        iList.set(i, item);
    }

    public void sortByDescription() {
        Collections.sort(iList, new SortByDescription());
    }

    public void sortByCategory() {
        Collections.sort(iList, new SortByCategory());
    }

    public void sortByQuantity() {
        Collections.sort(iList, new SortByQuantity());
    }

    public Stream<Item> stream() {
        return this.iList.stream();
    }

    public Item get(int i) {
        return iList.get(i);
    }

    public void sortReset() {
        Collections.sort(iList, new ResetSort());
    }

    /**
     * Comparator to compare by the name in item.
     */
    class SortByDescription implements Comparator<Item> {
        // Used for sorting in ascending order
        @Override
        public int compare(Item a, Item b) {
            return a.getDescription().compareTo(b.getDescription());
        }
    }

    /**
     * Comparator to compare by quantity in item.
     */
    class SortByQuantity implements Comparator<Item> {
        // Used for sorting in descending order
        @Override
        public int compare(Item a, Item b) {
            if (a.getQuantity() < b.getQuantity()) {
                return -1;
            } else if (a.getQuantity() == b.getQuantity()) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    /**
     * Comparator to compare by category in item.
     */
    class SortByCategory implements Comparator<Item> {
        // Used for sorting in ascending order
        @Override
        public int compare(Item a, Item b) {
            return a.getCategory().compareTo(b.getCategory());
        }
    }

    /**
     * Comparator to compare by trueId in item.
     */
    class ResetSort implements Comparator<Item> {
        @Override
        public int compare(Item a, Item b) {
            if (a.getTrueId() < b.getTrueId()) {
                return -1;
            } else if (a.getTrueId() == b.getTrueId()) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
