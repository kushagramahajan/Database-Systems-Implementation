package Assignments.Assignment4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Created by kushagra on 4/3/2017.
 */
public class Test {
    public static void main(String[] args) throws Exception
    {
        Layout layout = new Layout();
        ArrayList<Blocks> tables = Layout.tables;

        performex(tables,"MRU","Employee","Department",1);

    }
    public static void analyse()
    {
        Layout layout = new Layout();
        ArrayList<Blocks> tables = Layout.tables;
        int[] array = new int[11];
        array[0] = 20;
        array[1] = 50;
        array[2] = 75;
        array[3] = 100;
        array[4] = 200;
        array[5] = 500;
        array[6] = 1000;
        array[7] = 1500;
        array[8] = 2000;
        array[9] = 2500;
        array[10] = 10;
        Thread[] threads = new Thread[11];
        for(int i=0;i<11;i++)
        {
            System.out.println("hello");
            final int size = array[i];
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        PrintStream out = new PrintStream(new FileOutputStream("F:/Rajeev/Academics/Third_Year/Sixth_Semester/Database_System_Implementation/Assignment/Assignment4/Q4_MRU_"+size+".txt"));
                        experiment(tables, "MRU", "Department", "Project", size, out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }});
            threads[i].start();
        }


    }

    synchronized public static void experiment(ArrayList<Blocks> tables,String policy,String R,String S,int Buffer,PrintStream out)
    {
        System.out.println("ok");
        BufferManager.initialize(Buffer);
        PageTable pageTable = new PageTable();
        Experiment.pattern.append("");
        System.setOut(out);
        Experiment.nested_join(tables,R,S,pageTable,policy);
        System.out.println("Page Fault: "+PageTable.page_fault);
        System.out.println(Buffer);
        System.out.println("Reference Pattern :"+Experiment.pattern.toString());
        System.setOut(System.out);
    }
    public static void performex(ArrayList<Blocks> tables,String policy,String R,String S,int Buffer)
    {
        BufferManager.initialize(Buffer);
        PageTable pageTable = new PageTable();
        Experiment.pattern.append("");
        Experiment.nested_join(tables,R,S,pageTable,policy);

    }



}
