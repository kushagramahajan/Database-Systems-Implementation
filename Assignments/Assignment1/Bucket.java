package Assignments.Assignment1;

/**
 * Created by kushagra on 1/11/2017.
 */
public class Bucket {
    private Records[] records;
    private Integer empty;
    public String next;
    public Bucket()
    {
        records = new Records[RecordSize.size];
        empty = RecordSize.size;
        next="";
    }
    public Boolean hasSpace()
    {
        //System.out.println(empty);
        if(this.empty!=0)
        {
            return true;
        }
        else
            return false;
    }

    public Integer getEmpty() {
        return empty;
    }

    public String getNext() {
        return next;
    }

    public void setEmpty(Integer empty) {
        this.empty = empty;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public void setRecords(Records[] records) {
        this.records = records.clone();
    }

    public void addRecords(Records record)
    {
        if(this.hasSpace())
        {
            records[RecordSize.size-this.empty] = record;
            this.empty--;
        }
    }

    public void addRecordArray(Records[] records)
    {
        for(int i=0;i<records.length;i++){
            records[RecordSize.size-this.empty] = records[i];
            this.empty--;
        }


    }

    public void deleteRecord(Integer index)
    {
        //System.out.println("index"+index+"size"+RecordSize.size);
        if(index<=RecordSize.size)
        {
            records[index] = null;
            this.empty++;
        }
    }
    public void clearRecords()
    {
        for(int i=0;i<records.length;i++)
        {
            records[i] = null;
            this.empty++;
        }
    }

    public Records[] getRecords()
    {
        return this.records;
    }


    public int getSize(){
        return RecordSize.size-empty;
    }







}
