package Assignments.Assignment1;

/**
 * Created by Rajeev Nagarwal on 1/11/2017.
 */
public class Bucket {
    private Records[] records;
    private Integer empty;
    private String next;
    public Bucket()
    {
     records = new Records[RecordSize.size];
     empty = RecordSize.size;
     next="";
    }
    public void addRecords(Records record)
    {
        if(this.empty!=0)
        {
            records[RecordSize.size-this.empty] = record;
            this.empty--;
        }
    }
    public void deleteRecord(Integer index)
    {
        if(index>=RecordSize.size)
        {
            records[index] = null;
        }
    }
    public void clearRecords()
    {
        for(int i=0;i<records.length;i++)
        {
            records[i] = null;
        }
    }







}
