package Assignments.Assignment1;

import com.sun.deploy.util.ArrayUtil;
import com.sun.org.apache.regexp.internal.RE;
import jdk.nashorn.internal.ir.LiteralNode;

import java.util.*;

/**
 * Created by kushagra on 1/11/2017.
 */
public class Test {
    public static void main(String[] args)
    {
        SecondaryMemory secondaryMemory = new SecondaryMemory();
        uniaaccess(secondaryMemory);

    }
    public static void testlinearsearch(SecondaryMemory secondaryMemory,Integer flag)
    {
        if(flag==0) {
            Records[] dummy = new Records[7];
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
            dummy[6] = new Records();
            dummy[6].setData(21);
            /*dummy[7] = new Records();
            dummy[7].setData(35);
            dummy[8] = new Records();
            dummy[8].setData(55);*/
            /*for(int i=0;i<100;i++)
            {
                dummy[i]= new Records();
                dummy[i].setData(i);
            }*/
            LinearHashing linearHashing = new LinearHashing();
            for (int i = 0; i < dummy.length; i++) {
                System.out.println("Ind"+i);
                linearHashing.insert(secondaryMemory, dummy[i], linearHashing.hik, false);
                linearHashing.printTable(secondaryMemory);


            }


        }
        else if(flag==1)
        {
            UniDataset.generateDataset();
            ArrayList<Records> recordss = new ArrayList<>();
            Records[] dummy = UniDataset.getData();
            LinearHashing linearHashing = new LinearHashing();
            for(int i=0;i<dummy.length;i++)
            {
                //System.out.println("Ind"+i);
                linearHashing.insert(secondaryMemory, dummy[i], linearHashing.hik, false);



            }
            linearHashing.printTable(secondaryMemory);
            System.out.println();




        }
        else if(flag==2)
        {
            HighDataset.generateDataset();
            Pair pair = HighDataset.getData();
            ArrayList<Records> recordss = new ArrayList<>();
            ArrayList<Records> list = new ArrayList<>();
            list.addAll(Arrays.asList(pair.dataset1));
            list.addAll(Arrays.asList(pair.dataset2));
            Records[] dummy = list.toArray(new Records[]{});
            LinearHashing linearHashing = new LinearHashing();
            for(int i=0;i<dummy.length;i++)
            {
                //System.out.println("Ind"+i);
                linearHashing.insert(secondaryMemory, dummy[i], linearHashing.hik, false);

            }
            linearHashing.printTable(secondaryMemory);

        }




    }

    public static void unisearch(SecondaryMemory secondaryMemory)
    {
        UniDataset.generateDataset();
        ArrayList<Records> recordss = new ArrayList<>();
        Records[] dummy = UniDataset.getData();
        /*Records[] dummy = new Records[7];
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
        dummy[6] = new Records();
        dummy[6].setData(21);*/
        LinearHashing linearHashing = new LinearHashing();
        for(int i=0;i<dummy.length;i++)
        {
            //System.out.println("Ind"+i);
            linearHashing.insert(secondaryMemory,dummy[i],linearHashing.hik,false);
            if(i%5000==0&&i!=0)
            {
                Random random = new Random();
                for(int j=0;j<50;j++)
                {
                    int index = random.nextInt(i);
                    Records records = dummy[index];
                    recordss.add(records);
                }
                System.out.println(i+","+linearHashing.search(secondaryMemory,recordss));
                recordss.clear();

            }
        }

    }
    public static void highsearch(SecondaryMemory secondaryMemory)
    {
        HighDataset.generateDataset();
        Pair pair = HighDataset.getData();
        ArrayList<Records> recordss = new ArrayList<>();
        ArrayList<Records> list = new ArrayList<>();
        list.addAll(Arrays.asList(pair.dataset1));
        list.addAll(Arrays.asList(pair.dataset2));
        Records[] dummy = list.toArray(new Records[]{});
        LinearHashing linearHashing = new LinearHashing();
        for(int i=0;i<dummy.length;i++)
        {
            //System.out.println("Ind"+i);
            linearHashing.insert(secondaryMemory,dummy[i],linearHashing.hik,false);
            if(i%5000==0&&i!=0)
            {
                Random random = new Random();
                for(int j=0;j<50;j++)
                {
                    int index = random.nextInt(i);
                    Records records = dummy[index];
                    recordss.add(records);
                }
                System.out.println(i+","+linearHashing.search(secondaryMemory,recordss));
                recordss.clear();

            }
        }

    }
    public static void highastorage(SecondaryMemory secondaryMemory)
    {
        HighDataset.generateDataset();
        Pair pair = HighDataset.getData();
        ArrayList<Records> list = new ArrayList<>();
        list.addAll(Arrays.asList(pair.dataset1));
        list.addAll(Arrays.asList(pair.dataset2));
        Records[] dummy = list.toArray(new Records[]{});
        LinearHashing linearHashing = new LinearHashing();
        for(int i=0;i<dummy.length;i++)
        {
            //System.out.println("Ind"+dummy[i].getData());
            linearHashing.insert(secondaryMemory,dummy[i],linearHashing.hik,false);
            //linearHashing.storage(i+1,secondaryMemory);
            System.out.println(i+1+","+linearHashing.storage(i+1,secondaryMemory));
            //linearHashing.printTable(secondaryMemory);
        }
    }
    public static void highaccess(SecondaryMemory secondaryMemory)
    {
        HighDataset.generateDataset();
        Pair pair = HighDataset.getData();
        ArrayList<Records> list = new ArrayList<>();
        list.addAll(Arrays.asList(pair.dataset1));
        list.addAll(Arrays.asList(pair.dataset2));
        Records[] dummy = list.toArray(new Records[]{});
        LinearHashing linearHashing = new LinearHashing();
        for(int i=0;i<dummy.length;i++)
        {
            //System.out.println("Ind"+i);
            linearHashing.insert(secondaryMemory,dummy[i],linearHashing.hik,false);
            //linearHashing.storage(i+1,secondaryMemory);
            System.out.println(i+1+","+linearHashing.getAccess());
            //linearHashing.printTable(secondaryMemory);
        }
    }
    public static void uniastorage(SecondaryMemory secondaryMemory)
    {
        UniDataset.generateDataset();
        Records[] dummy = UniDataset.getData();
        LinearHashing linearHashing = new LinearHashing();
        for(int i=0;i<dummy.length;i++)
        {
            //System.out.println("Ind"+i);
            linearHashing.insert(secondaryMemory,dummy[i],linearHashing.hik,false);
            //linearHashing.storage(i+1,secondaryMemory);
            System.out.println(i+1+","+linearHashing.storage(i+1,secondaryMemory));
            //linearHashing.printTable(secondaryMemory);
        }
    }
    public static void uniaaccess(SecondaryMemory secondaryMemory)
    {
        UniDataset.generateDataset();
        Records[] dummy = UniDataset.getData();
        LinearHashing linearHashing = new LinearHashing();
        for(int i=0;i<dummy.length;i++)
        {
            //System.out.println("Ind"+i);
            linearHashing.insert(secondaryMemory,dummy[i],linearHashing.hik,false);
            //linearHashing.storage(i+1,secondaryMemory);
            System.out.println(i+1+","+linearHashing.getAccess());
            //linearHashing.printTable(secondaryMemory);
        }
    }


}
