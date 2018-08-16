package Assignments.Assignment1;

/**
 * Created by kushagra on 1/14/2017.
 */
public class MainMemory {
    private Records[] records;
    public int size;

    public MainMemory(Records[] records) {
        records = new Records[1024];
        size=0;
    }
    public void addRecord(Records record){
        if(size<1024){
            records[size]=record;
            size+=1;
        }
        else{

            System.out.println("Main Memeory Full !");
            // implement what to do here

        }


    }

    public Records[] getRecords() {
        return records;
    }

    public void setRecords(Records[] records) {
        this.records = records;
    }
}
