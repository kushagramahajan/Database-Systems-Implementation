package Assignments.Assignment1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

/**
 * Created by Rajeev Nagarwal on 1/11/2017.
 */
public class UniDataset {
    public static Records[] dataset;
    public static Records[] getData()
    {
        dataset = new Records[100000];
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("./src/Assignments/Assignment1/UniDataset.txt"));
            String result;
            int i = 0;
            while((result=reader.readLine())!=null)
            {
                Integer num = Integer.parseInt(result);
                dataset[i] = new Records();
                dataset[i].setData(num);
                i++;

            }
            reader.close();
        }
        catch(Exception e)
        {

        }
        return dataset;
    }

    public static void generateDataset()
    {


        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./src/Assignments/Assignment1/UniDataset.txt"));
            for (int i = 0; i < 100000; i++) {
                Random rand = new Random();
                writer.write(new Integer(rand.nextInt(800000 - 0) + 0).toString());
                writer.newLine();
            }
            writer.close();
        }
        catch(Exception e)
            {
             e.printStackTrace();
            }
            finally{

        }

    }

}
