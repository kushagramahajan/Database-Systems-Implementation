package Assignments.Assignment1;

import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by Rajeev Nagarwal on 1/11/2017.
 */
public class Test {
    public static void main(String[] args)
    {
        UniDataset.generateDataset();
        HighDataset.generateDataset();
        Records[] d1 = UniDataset.getData();
        for(int i=0;i<d1.length;i++)
        {
            System.out.println(d1[i].getData());
            System.out.println("index"+i);
        }
        Pair pair = HighDataset.getData();
        Records[] d2 = pair.dataset1;
        Records[] d3 = pair.dataset2;
        for(int i=0;i<d1.length;i++)
        {
            if(i>=70000)
            {
                System.out.println(d3[i-70000].getData());
                System.out.println("index"+(i-70000));

            }
            else
            {
                System.out.println(d2[i].getData());
                System.out.println("index"+i);
            }
        }
    }

}
