package seedu.address.transaction.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import seedu.address.person.model.Model;
import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.util.TransactionList;

/**
 * Manages storage of transaction data in local storage.
 */
public class StorageManager implements Storage {
    private final File file;
    private final seedu.address.person.model.Model personModel;

    public StorageManager(File file, Model personModel) {
        this.file = file;
        this.personModel = personModel;
    }

    @Override
    public TransactionList readTransactionList() {
        try {
            ArrayList<Transaction> transactionArrayList = new ArrayList<>();
            file.getAbsoluteFile().getParentFile().mkdirs();
            file.createNewFile();
            BufferedReader bfr = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = bfr.readLine()) != null) {
                Transaction t = this.readInFileLine(line, personModel);
                transactionArrayList.add(t);
            }
            return new TransactionList(transactionArrayList);
        } catch (IOException e) {
            return new TransactionList();
        }
    }

    @Override
    public void writeFile(TransactionList transactionList) throws IOException {
        FileWriter fw = new FileWriter(this.file);
        String textFileMsg = "";
        for (int i = 0; i < transactionList.size(); i++) {
            if (i == 0) {
                textFileMsg = textFileMsg + (i + 1) + ". " + transactionList.get(i).toWriteIntoFile();
            } else {
                textFileMsg = textFileMsg + System.lineSeparator() + (i + 1) + ". "
                        + transactionList.get(i).toWriteIntoFile();
            }
        }
        fw.write(textFileMsg);
        fw.close();
    }

    /**
     * Reads in a single text file line and parses it to create the {@code Transaction} object.
     * @param line Line of text.
     * @param personModel Address Book model.
     * @return Transaction created.
     */
    private static Transaction readInFileLine(String line, seedu.address.person.model.Model personModel) {
        String[] stringArr = line.split(" [|] ", 0);
        String[] dateTimeArr = stringArr[0].split(" ");
        Person person = personModel.getPersonByName(stringArr[4]);
        Transaction t = new Transaction(dateTimeArr[1], stringArr[1],
                stringArr[2], Double.parseDouble(stringArr[3]), person,
                Integer.parseInt(dateTimeArr[0].split("[.]")[0]), isReimbursed(stringArr[5]));
        return t;
    }

    private static boolean isReimbursed(String num) {
        return num.equals("1") ? true : false;
    }
}
