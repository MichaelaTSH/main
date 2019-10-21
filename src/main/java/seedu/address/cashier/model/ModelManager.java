package seedu.address.cashier.model;

import java.time.LocalDate;
import java.util.ArrayList;

import seedu.address.cashier.logic.exception.NoCashierFoundException;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.storage.StorageManager;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.Model;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.util.TransactionList;

/**
 * Represents the in-memory model of the sales list data.
 */
public class ModelManager implements Model {

    private static final String SALES_DESCRIPTION = "Items sold";
    private static final String SALES_CATEGORY = "Sales";
    private static ArrayList<Item> salesList = new ArrayList<Item>();
    private Person cashier = null;
    private InventoryList inventoryList;
    private StorageManager storage;
    private TransactionList transactionList;

    /**
     * Initializes a ModelManager with the given inventory list.
     */
    public ModelManager(InventoryList inventoryList) {
        this.inventoryList = inventoryList;
    }

    /**
     * Initializes a ModelManager with the given storage manager.
     */
    public ModelManager(StorageManager storage) {
        this.storage = storage;
        try {
            this.inventoryList = storage.getInventoryList();
        } catch (Exception e) {
            this.inventoryList = new InventoryList();
        }
        try {
            this.transactionList = storage.getTransactionList();
        } catch (Exception e) {
            this.transactionList = new TransactionList();
        }
    }

    /**
     * Returns a view of the {@code InventoryList}.
     * @return
     */
    public InventoryList getInventoryList() {
        return this.inventoryList;
    }

    /**
     * Returns a view of the {@code TransactionList}.
     */
    public TransactionList getTransactionList() {
        return this.transactionList;
    }

    /**
     * Updates the inventory list from the data file.
     */
    @Override
    public void readInUpdatedList() {
        try {
            this.inventoryList = storage.getInventoryList();
        } catch (Exception e) {
            this.inventoryList = new InventoryList();
        }
    }

    /**
     * Returns true if the quantity keyed in is less than or equals to the quantity available in inventory.
     * Else, return false.
     *
     * @param description of the item to check
     * @param quantity of the item to check
     * @return true if sufficient quantity in inventory
     * @throws NoSuchItemException if there is no such item in the inventory
     */
    public boolean hasSufficientQuantity(String description, int quantity) throws NoSuchItemException {
        Item originalItem = inventoryList.getOriginalItem(description);
        for (Item i : salesList) {
            if (originalItem.isSameItem(i)) {
                int initialSalesQty = i.getQuantity();
                return (originalItem.getQuantity() >= (initialSalesQty + quantity));
            }
        }
        if (originalItem.getQuantity() >= quantity) {
            return true;
        }
        return false;
    }

    /**
     * Returns the quantity of stock left for a specific item.
     * @param description of the item
     * @return an integer representing the quantity of stock left
     * @throws NoSuchItemException if the item do not exist
     */
    public int getStockLeft(String description) throws NoSuchItemException {
        return inventoryList.getOriginalItem(description).getQuantity();
    }

    /**
     * Returns true if an item with the same attributes as {@code item} exists in the Inventory List.
     */
    @Override
    public boolean hasItemInInventory(Item item) {
        try {
            for (int i = 0; i < inventoryList.size(); i++) {
                if (inventoryList.getItemByIndex(i).isSameItem(item)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * Returns true if an item with the same description as {@code item} exists in the Inventory List.
     * Else, return false.
     * @param description of the item to check
     * @return true if item exist in inventory
     */
    public boolean hasItemInInventory(String description) {
        return inventoryList.hasItem(description);
    }

    @Override
    public void addItem(Item item) {
        salesList.add(item);
    }

    /**
     * Adds the item into the Sales List.
     * @param description of the item to be added
     * @param qty of the item to be added
     * @return the item added
     * @throws NoSuchItemException if the description is invalid
     */
    public Item addItem(String description, int qty) throws NoSuchItemException {
        for (Item item : salesList) {
            if (item.getDescription().equalsIgnoreCase(description)) {
                int originalQty = item.getQuantity();
                item.setQuantity(originalQty + qty);
                return item;
            }
        }
        Item i = inventoryList.getOriginalItem(description);
        Item copy = new Item(i.getDescription(), i.getCategory(), qty, i.getCost(), i.getPrice(),
                Integer.valueOf(i.getId()));
        copy.setQuantity(qty);
        salesList.add(copy);
        return i;
    }

    @Override
    public Item findItemByIndex(int index) {
        Item i = salesList.get(index - 1);
        return i;
    }

    @Override
    public void deleteItem(int index) {
        salesList.remove(index - 1);
    }

    @Override
    public void setItem(int i, Item editedItem) throws Exception {
        inventoryList.set(i, editedItem);
    }

    @Override
    public void writeInInventoryFile() throws Exception {
        storage.writeFileToInventory(inventoryList);
    }

    /**
     * Updates the quantity in the inventory list according to the quantity sold in Sales List.
     * @throws Exception if an item is invalid
     */
    public void updateInventoryList() throws Exception {
        for (int i = 0; i < salesList.size(); i++) {
            Item item = salesList.get(i);
            int originalQty = inventoryList.getOriginalItem(item).getQuantity();
            inventoryList.getOriginalItem(item).setQuantity(originalQty - item.getQuantity());
        }
    }

    /**
     * Sets the specified {@code Person} as the cashier.
     * @param p the person to be set as cashier
     */
    public void setCashier(Person p) {
        this.cashier = p;
    }

    /**
     * Returns the cashier-in-charge.
     * @return the cashier-in-charge
     * @throws NoCashierFoundException if no cashier has been set
     */
    public Person getCashier() throws NoCashierFoundException {
        if (cashier == null) {
            throw new NoCashierFoundException(CashierMessages.NO_CASHIER);
        }
        return this.cashier;
    }

    /**
     * Returns the total amount of all the items in the Sales List.
     * @return the total amount of all the items in the Sales List
     */
    public static double getTotalAmount() {
        double total = 0;
        for (Item i : salesList) {
            total += (i.getPrice() * i.getQuantity());
        }
        return total;
    }

    /**
     * Returns the Sales List.
     * @return the Sales List
     */
    public ArrayList<Item> getSalesList() {
        return this.salesList;
    }

    /**
     * Clears all the items in the Sales List.
     */
    public void clearSalesList() {
        this.salesList = new ArrayList<Item>();
    }

    /**
     * Edits the item to update the quantity to be sold.
     * @param index of the item to be updated
     * @param qty of the item to be updated
     * @return the item edited
     */
    public Item editItem(int index, int qty) {
        salesList.get(index - 1).setQuantity(qty);
        return salesList.get(index - 1);
    }

    /**
     * Returns the subtotal of the item according to the quantity and the price.
     * @param i the item to be calculated
     * @return the subtotal of the item
     */
    public double getSubtotal(Item i) {
        return Double.parseDouble(Item.DECIMAL_FORMAT.format(i.getPrice() * i.getQuantity()));
    }

    /**
     * Returns true if the item with the specified description is available for sale. Else, returns false.
     * @param description of the item
     * @return true if available for sale
     * @throws NoSuchItemException if not for sale
     */
    public boolean isSellable(String description) throws NoSuchItemException {
        Item i = inventoryList.getOriginalItem(description);
        return i.isSellable();
    }

    /**
     * Returns a list of sales items according to their category.
     * @param category of the items
     * @return a list of sales items according to their category
     */
    public ArrayList<String> getDescriptionByCategory(String category) {
        readInUpdatedList();
        return inventoryList.getAllSalesDescriptionByCategory(category);
    }


    /**
     * Returns a list of recommended items based on the initial input description.
     * @param description of the item
     * @return a list of recommended items
     * @throws NoSuchIndexException if inventory list is invalid
     */
    public ArrayList<String> getRecommendedItems(String description) throws NoSuchIndexException {
        readInUpdatedList();
        ArrayList<String> recommendedItems = new ArrayList<>();
        for (int i = 0; i < inventoryList.size(); i++) {
            Item item = inventoryList.getItemByIndex(i);
            if (item.getDescription().startsWith(description)) {
                recommendedItems.add(item.getDescription());
                continue;
            }
            if (description.length() >= 3
                    && ((item.getDescription().contains(description)) || description.contains(item.getDescription()))) {
                recommendedItems.add(item.getDescription());
                continue;
            }
            if (item.getDescription().endsWith(description)) {
                recommendedItems.add(item.getDescription());
                continue;
            }
        }
        return recommendedItems;
    }


    /**
     * Creates a new {@code Transaction} and append it to the data file.
     * Adds the transaction to the transaction model.
     *
     * @param amount to paid by customer
     * @param person cashier who is in-charge
     * @param transactionModel the transaction model being used
     * @return the new transaction made from the sales
     * @throws Exception if the user input is invalid
     */
    public Transaction checkoutAsTransaction(double amount, Person person,
                                             seedu.address.transaction.model.Model transactionModel) throws Exception {
        Transaction transaction = new Transaction(LocalDate.now().format(Transaction.DATE_TIME_FORMATTER),
                SALES_DESCRIPTION, SALES_CATEGORY, amount, person, transactionList.size(), false);
        storage.appendToTransaction(transaction);
        transactionModel.addTransaction(transaction);
        return transaction;
    }

}



