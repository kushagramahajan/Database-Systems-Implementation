package Assignments.Assignment1;

/**
 * Created by Rajeev Nagarwal on 1/13/2017.
 */
public class SecondaryMemory {
    public Bucket[] memory;
    public Integer overflow;
    public SecondaryMemory()
    {
        memory = new Bucket[200000];
        for(int i=0;i<memory.length;i++)
        {
            memory[i] = new Bucket();
        }
        overflow = 100001;
    }


}
