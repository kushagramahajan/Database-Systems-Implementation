package Assignments.Assignment2;

import java.util.Random;

/**
 * Created by kushagra on 2/14/2017.
 */
public class Dataset {
    public static Table table = new Table();
    public static void generate()
    {
        for(int i=0;i<TableSize.tablesize;i++)
        {
            Integer id = i+1;
            Random random = new Random();
            Random random1 = new Random();
            String name = getRandomString(random1);
            Integer sale = getRandomInteger(random);
            Record record = new Record(id,name,sale);
            table.addRecord(record);
        }
        Storage.store(table.records);


    }

    public static Integer getRandomInteger(Random r)
    {
        return r.nextInt((2500))+1;
    }
    public static String getRandomString(Random r)
    {
        char[] array = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

        return ""+array[r.nextInt(26)]+""+array[r.nextInt(26)]+""+array[r.nextInt(26)]+"";
    }
}
