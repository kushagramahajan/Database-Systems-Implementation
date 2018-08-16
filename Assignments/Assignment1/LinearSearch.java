package Assignments.Assignment1;

import java.util.ArrayList;

/**
 * Created by kushagra on 1/23/2017.
 */
public class LinearSearch {
    public static Double search(SecondaryMemory secondaryMemory,ArrayList<Records> list)
    {
        int size = list.size();
        Integer count = 0;
        for(int i=0;i<100000;i++)
        {
            Bucket bucket = secondaryMemory.memory[i];
            if(bucket.getRecords()!=null)
            {
                count++;
                while(!bucket.next.equals(""))
                {
                    count++;
                    bucket = secondaryMemory.memory[Integer.parseInt(bucket.next)];
                }

            }
        }
        Double ans = count.doubleValue()/size;
        return ans;
    }

}
