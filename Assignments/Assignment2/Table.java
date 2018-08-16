package Assignments.Assignment2;

import java.util.ArrayList;

/**
 * Created by kushagra on 2/14/2017.
 */
public class Table {
    public static ArrayList<Record> records;
    public Table()
    {
        records = new ArrayList<Record>();
    }
    public void addRecord(Record record)
    {
        records.add(record);
    }


}
