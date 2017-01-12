package Assignments.Assignment1;

import java.util.Random;

/**
 * Created by Rajeev Nagarwal on 1/11/2017.
 */
public class HighDataset {
    public static Records[] dataset1;
    public static Records[] dataset2;
    public static void generateDataset()
    {
        dataset1 = new Records[70000];
        dataset2 = new Records[30000];
        for(int i=0;i<70000;i++)
        {
            Random rand = new Random();
            dataset1[i] = new Records();
            dataset1[i].setData(rand.nextInt(800000-700000)+700000);
        }
        for(int i=0;i<30000;i++)
        {
            Random rand = new Random();
            dataset2[i] = new Records();
            dataset2[i].setData(rand.nextInt(700000-0)+0);
        }

    }

}
