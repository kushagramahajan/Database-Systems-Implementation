package Assignments.Assignment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by kushagra on 1/25/2017.
 */
public class TestExtendible1 {

    public static void main(String[] args)
    {
        SecondaryMemory secondaryMemory = new SecondaryMemory();
        highaccess(secondaryMemory);

    }

    public static void uniaaccess(SecondaryMemory secondaryMemory)
    {
//        UniDataset.generateDataset();
        Records[] dummy = UniDataset.getData();

//                Records[] dummy = new Records[13];
//        dummy[0] = new Records();
//        dummy[0].setData(50);
//        dummy[1] = new Records();
//        dummy[1].setData(51);
//        dummy[2] = new Records();
//        dummy[2].setData(52);
//        dummy[3] = new Records();
//        dummy[3].setData(56);
//        dummy[4] = new Records();
//        dummy[4].setData(60);
//        dummy[5] = new Records();
//        dummy[5].setData(61);
//
//        dummy[6] = new Records();
//        dummy[6].setData(53);
//
//        dummy[7] = new Records();
//        dummy[7].setData(63);
//
//        dummy[8] = new Records();
//        dummy[8].setData(62);
//
//        dummy[9] = new Records();
//        dummy[9].setData(511);
//
//        dummy[10] = new Records();
//        dummy[10].setData(510);
//
//        dummy[11] = new Records();
//        dummy[11].setData(1023);
//        dummy[12] = new Records();
//        dummy[12].setData(54);

        ArrayList<Integer> splittingCost=new ArrayList<Integer>(dummy.length);
        ExtendibleHashing extendibleHashing=new ExtendibleHashing();
        int n1=0,n2=1;

        for(int i=0;i<dummy.length;i++)
        {
            //System.out.println("Ind"+i);

            n1=n2;
            ExtendibleHashing.spHash.clear();
            ExtendibleHashing.spCost=0;
            ExtendibleHashing.glhash=0;
            extendibleHashing.insert(secondaryMemory,dummy[i],0);
            extendibleHashing.merge1(secondaryMemory);

            n2=(int)extendibleHashing.storageUtil1(secondaryMemory,i+1);


            //System.out.println("data item: "+dummy[i].getData());
            //System.out.println(ExtendibleHashing.spCost);
            if(ExtendibleHashing.spHash.size()>0){
                //extendibleHashing.printHash(secondaryMemory);
                splittingCost.add(ExtendibleHashing.spHash.size());
            }
            else{
                int diff;
                diff=n2-n1+1;
                //System.out.println("diff: "+diff);
                if(diff>1 && ExtendibleHashing.glhash>1023){
                    splittingCost.add((int)((ExtendibleHashing.glhash-1023)/(RecordSize.size/2)));
                }
                else if(diff>1)
                    splittingCost.add(diff);
                else
                    splittingCost.add(0);
            }

//            if(splittingCost.size()==3){
//                break;
//            }
            //linearHashing.storage(i+1,secondaryMemory);
            //System.out.println(i+1+","+linearHashing.getAccess());
            //linearHashing.printTable(secondaryMemory);
        }
        //extendibleHashing.printHash(secondaryMemory);
        for(int i=0;i<splittingCost.size();i++){
            System.out.println(i+1+","+splittingCost.get(i));
        }


    }


    public static void printTable(SecondaryMemory secondaryMemory)
    {
//        UniDataset.generateDataset();
        Records[] dummy = UniDataset.getData();
//        Pair pair = HighDataset.getData();
//        ArrayList<Records> list = new ArrayList<>();
//
//        list.addAll(Arrays.asList(pair.dataset1));
//        list.addAll(Arrays.asList(pair.dataset2));
//        Records[] dummy = list.toArray(new Records[]{});

        ExtendibleHashing extendibleHashing=new ExtendibleHashing();

        for(int i=0;i<dummy.length;i++)
        {
            //System.out.println("Ind"+i);
            extendibleHashing.insert(secondaryMemory,dummy[i],0);
            //linearHashing.storage(i+1,secondaryMemory);
            //System.out.println(i+1+","+linearHashing.getAccess());
            //linearHashing.printTable(secondaryMemory);
        }
        //extendibleHashing.printHash(secondaryMemory);
//        extendibleHashing.merge(secondaryMemory);
//        extendibleHashing.storageUtil(secondaryMemory,100000);
    }




    public static void highaccess(SecondaryMemory secondaryMemory)
    {
        Pair pair = HighDataset.getData();
        ArrayList<Records> list = new ArrayList<>();

        list.addAll(Arrays.asList(pair.dataset1));
        list.addAll(Arrays.asList(pair.dataset2));
        Records[] dummy = list.toArray(new Records[]{});
        ArrayList<Integer> splittingCost=new ArrayList<Integer>(dummy.length);
        ExtendibleHashing extendibleHashing=new ExtendibleHashing();

        int n1=0,n2=1;

        for(int i=0;i<dummy.length;i++)
        {
            n1=n2;
            ExtendibleHashing.spHash.clear();
            ExtendibleHashing.spCost=0;
            ExtendibleHashing.glhash=0;
            extendibleHashing.insert(secondaryMemory,dummy[i],0);
            extendibleHashing.merge1(secondaryMemory);

            n2=(int)extendibleHashing.storageUtil1(secondaryMemory,i+1);


            //System.out.println("data item: "+dummy[i].getData());
            //System.out.println(ExtendibleHashing.spCost);
            if(ExtendibleHashing.spHash.size()>0){
                //extendibleHashing.printHash(secondaryMemory);
                splittingCost.add(ExtendibleHashing.spHash.size());
            }
            else{
                int diff;
                diff=n2-n1+1;
                //System.out.println("diff: "+diff);
                if(diff>1 && ExtendibleHashing.glhash>1023){
                    splittingCost.add((int)((ExtendibleHashing.glhash-1023)/(RecordSize.size/2)));
                }
                else if(diff>1)
                    splittingCost.add(diff);
                else
                    splittingCost.add(0);
            }
            //linearHashing.storage(i+1,secondaryMemory);
            //System.out.println(i+1+","+linearHashing.getAccess());
            //linearHashing.printTable(secondaryMemory);
        }
        for(int i=0;i<splittingCost.size();i++){
            System.out.println(i+1+","+splittingCost.get(i));
        }

    }

    public static void unisearch(SecondaryMemory secondaryMemory)
    {
        //UniDataset.generateDataset();

        ArrayList<Double> searchCost=new ArrayList<Double>();

//        ArrayList<Records> recordss = new ArrayList<>();
        Records[] dummy = UniDataset.getData();
        ExtendibleHashing extendibleHashing=new ExtendibleHashing();

        for(int i=0;i<dummy.length;i++)
        {
            //System.out.println("Ind"+i);
            extendibleHashing.insert(secondaryMemory,dummy[i],0);
            if(i%5000==0&&i!=0)
            {
                double y=generateSearchCost(secondaryMemory,extendibleHashing,dummy,i);
                searchCost.add(y);
                //recordss.clear();

            }
        }

        for(int i=0;i<searchCost.size();i++){
            int x=(i+1)*5000;
            System.out.println(x+","+searchCost.get(i));
        }

    }

    public static void highsearch(SecondaryMemory secondaryMemory)
    {
        //HighDataset.generateDataset();

        ArrayList<Double> searchCost=new ArrayList<Double>();
        Pair pair = HighDataset.getData();

//        ArrayList<Records> recordss = new ArrayList<>();
        ArrayList<Records> list = new ArrayList<>();
        list.addAll(Arrays.asList(pair.dataset1));
        list.addAll(Arrays.asList(pair.dataset2));
        Records[] dummy = list.toArray(new Records[]{});
        ExtendibleHashing extendibleHashing=new ExtendibleHashing();

        for(int i=0;i<dummy.length;i++)
        {
            //System.out.println("Ind"+i);
            extendibleHashing.insert(secondaryMemory,dummy[i],0);
            if(i%5000==0&&i!=0)
            {
                double y=generateSearchCost(secondaryMemory,extendibleHashing,dummy,i);
                searchCost.add(y);
                //recordss.clear();

            }
        }

        for(int i=0;i<searchCost.size();i++){
            int x=(i+1)*5000;
            System.out.println(x+","+searchCost.get(i));
        }

    }


    public static void uniastorage(SecondaryMemory secondaryMemory)
    {
        //UniDataset.generateDataset();
        Records[] dummy = UniDataset.getData();
        ArrayList<Float> utilisationArray=new ArrayList<Float>();

        ExtendibleHashing extendibleHashing=new ExtendibleHashing();

        for(int i=0;i<dummy.length;i++)
        {
            //System.out.println("Ind"+i);
            extendibleHashing.insert(secondaryMemory,dummy[i],0);
            //System.out.println("before merge");
            extendibleHashing.merge1(secondaryMemory);
            float x=extendibleHashing.storageUtil(secondaryMemory,i+1);
            utilisationArray.add(x);
            //System.out.println("after storage");
            //linearHashing.storage(i+1,secondaryMemory);
            //System.out.println(i+1+","+linearHashing.storage(i+1,secondaryMemory));
            //linearHashing.printTable(secondaryMemory);
        }


        for(int i=0;i<utilisationArray.size();i++){
            System.out.println(i+1+","+utilisationArray.get(i));
        }

    }


    public static void highastorage(SecondaryMemory secondaryMemory)
    {

        Pair pair = HighDataset.getData();
        ArrayList<Records> list = new ArrayList<>();
        list.addAll(Arrays.asList(pair.dataset1));
        list.addAll(Arrays.asList(pair.dataset2));
        Records[] dummy = list.toArray(new Records[]{});
        ArrayList<Float> utilisationArray=new ArrayList<Float>();

        ExtendibleHashing extendibleHashing=new ExtendibleHashing();

        for(int i=0;i<dummy.length;i++)
        {
            //System.out.println("Ind"+i);
            extendibleHashing.insert(secondaryMemory,dummy[i],0);
            extendibleHashing.merge1(secondaryMemory);
            float x=extendibleHashing.storageUtil(secondaryMemory,i+1);
            utilisationArray.add(x);
            //linearHashing.storage(i+1,secondaryMemory);
            //System.out.println(i+1+","+linearHashing.storage(i+1,secondaryMemory));
            //linearHashing.printTable(secondaryMemory);
        }

        for(int i=0;i<utilisationArray.size();i++){
            System.out.println(i+1+","+utilisationArray.get(i));
        }

    }


    static double generateSearchCost(SecondaryMemory secondaryMemory,ExtendibleHashing extendibleHashing,Records[] dummy, int i){

        Random rand = new Random();
        int n;
        int numBuckets;
        int bs=0;
        //System.out.println("Value of numbuckets: ");

        for(int x=0;x<50;x++){
            n = rand.nextInt(i);
            numBuckets=extendibleHashing.costSearch(secondaryMemory,dummy[n].getData());
            //System.out.print(numBuckets+"  ");
            bs+=numBuckets;
        }

        //System.out.println("");
        return (double)bs/50;

    }

}





