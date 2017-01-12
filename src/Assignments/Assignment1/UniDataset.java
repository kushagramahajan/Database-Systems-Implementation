package Assignments.Assignment1;

import java.util.Random;

/**
 * Created by Rajeev Nagarwal on 1/11/2017.
 */
public class UniDataset {
    public static Records[] dataset;
    public static void generateDataset()
    {
        dataset = new Records[100000];
        for(int i=0;i<100000;i++)
        {
            Random rand = new Random();
            dataset[i] = new Records();
            dataset[i].setData(rand.nextInt(800000-0)+0);
        }

    }

}
