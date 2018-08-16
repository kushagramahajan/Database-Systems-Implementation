package Assignments.Assignment2;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by kushagra on 2/14/2017.
 */
public class Test {
    public static void main(String[] args)
    {
        //createData();
        experiment(1000);
    }
    public static void createData()
    {
        generateData();
        ArrayList<Record> records = fetchData();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                createBitMap(records);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                createBitMapRow(records);
            }
        });
        t1.start();
        t2.start();

    }
    public static ArrayList<Record> fetchData()
    {
        ArrayList<Record> records = Storage.readRecordsFromFile();
        return records;
    }
    public static void createBitMapRow(ArrayList<Record> records)
    {
     BitMapRow bitMapRow = new BitMapRow();
     bitMapRow.populateIndex(records,0);
    }
    public static void createBitMap(ArrayList<Record> records)
    {
        BitMap bitmap = new BitMap();
        bitmap.populateIndex(records,0);
    }

    public static void generateData()
    {
        Dataset.generate();
    }
    public static void experiment(int no_of_ones)
    {
        BitSet bf = new BitSet(TableSize.tablesize);
        HashSet<Integer> set = new HashSet<>();
        while(set.size()!=no_of_ones)
        {
            Random random = new Random();
            int index = random.nextInt(TableSize.tablesize-1)+1;
            if(!set.contains(index)) {
                set.add(index);
                bf.set(index-1);
            }
        }
       /* //System.out.println("size:"+set.size());
        HashMap<Boolean,Integer> map = new HashMap<Boolean,Integer>();
        for(int i=1;i<bf.size();i++)
        {
            System.out.println(bf.get(i));
        }*/
       ArrayList<Record> records = fetchData();
       Thread t1 = new Thread(new Runnable() {
           @Override
           public void run() {
               countblockBitMap(bf,records);

           }
       });
       Thread t2 = new Thread(new Runnable() {
           @Override
           public void run() {
               countblockBitMapRow(bf,records);

           }
       });
       t1.start();
       t2.start();


    }
    public static void countblockBitMap(BitSet set,ArrayList<Record> records)
    {
        BitMap bitMap = new BitMap();
        bitMap.populateIndex(records,1);
        System.out.println(bitMap.no_of_blocks(set));
    }
    public static void countblockBitMapRow(BitSet set,ArrayList<Record> records)
    {
        BitMapRow bitMapRow = new BitMapRow();
        bitMapRow.populateIndex(records,1);
        System.out.println(bitMapRow.no_of_blocks(set));
    }





}
