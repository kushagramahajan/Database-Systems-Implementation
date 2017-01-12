package Assignments.Assignment1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

/**
 * Created by Rajeev Nagarwal on 1/11/2017.
 */
public class HighDataset {
    public static Records[] dataset1;
    public static Records[] dataset2;
    public static Pair getData()
    {
        Pair pair = new Pair();
        dataset1 = new Records[70000];
        dataset2 = new Records[30000];
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("./src/Assignments/Assignment1/HighDataset.txt"));
            String result;
            int i=0;
            while((result=reader.readLine())!=null)
            {
                Integer num = Integer.parseInt(result);
                if(i>=70000)
                {

                    dataset2[i-70000] = new Records();
                    dataset2[i-70000].setData(num);
                }
                else
                {
                    dataset1[i] = new Records();
                    dataset1[i].setData(num);

                }
                i++;

            }
            pair.dataset1 = dataset1;
            pair.dataset2 = dataset2;

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return pair;

    }

    public static void generateDataset()
    {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./src/Assignments/Assignment1/HighDataset.txt"));
            for (int i = 0; i < 70000; i++) {
                Random rand = new Random();
                writer.write(new Integer(rand.nextInt(800000 - 700000) + 700000).toString());
                writer.newLine();
            }
            for (int i = 0; i < 30000; i++) {
                Random rand = new Random();
                writer.write(new Integer(rand.nextInt(700000 - 0) + 0).toString());
                writer.newLine();
            }
            writer.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

}
