package Assignments.Assignment1;

import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by Rajeev Nagarwal on 1/11/2017.
 */
public class Test {
    public static void main(String[] args)
    {
        /*UniDataset.generateDataset();
        HighDataset.generateDataset();
        Records[] d1 = UniDataset.getData();
        Pair pair = HighDataset.getData();
        Records[] d2 = pair.dataset1;
        Records[] d3 = pair.dataset2;*/
        SecondaryMemory secondaryMemory = new SecondaryMemory();
        LinearHashing linearHashing = new LinearHashing();
        Records[] dummy = new Records[6];
        dummy[0] = new Records();
        dummy[0].setData(1);
        dummy[1] = new Records();
        dummy[1].setData(2);
        dummy[2] = new Records();
        dummy[2].setData(3);
        dummy[3] = new Records();
        dummy[3].setData(5);
        dummy[4] = new Records();
        dummy[4].setData(8);
        dummy[5] = new Records();
        dummy[5].setData(13);
        for(int i=0;i<dummy.length;i++)
        {
            System.out.println("Ind"+i);
            linearHashing.insert(secondaryMemory,dummy[i],linearHashing.hik);
        }

    }

}
