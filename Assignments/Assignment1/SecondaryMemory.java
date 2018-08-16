package Assignments.Assignment1;

/**
 * Created by kushagra on 1/13/2017.
 */
public class SecondaryMemory {
    public Bucket[] memory;
    public Integer overflow;
    public int mainMemoryOverflow;
    public SecondaryMemory()
    {
        memory = new Bucket[1800000];
        for(int i=0;i<memory.length;i++)
        {
            memory[i] = new Bucket();
        }
        overflow = 1600001;
        mainMemoryOverflow=1400001;
    }


}
