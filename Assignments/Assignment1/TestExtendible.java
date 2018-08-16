package Assignments.Assignment1;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kushagra on 1/13/2017.
 */
public class TestExtendible {

    public static void main(String args[]) {

//        UniDataset.generateDataset();
//        HighDataset.generateDataset();
        Records[] dummy = UniDataset.getData();
//        System.out.println("length of dummy: "+dummy.length);

        /*Pair pair = HighDataset.getData();
        Records[] d2 = pair.dataset1;
        Records[] d3 = pair.dataset2;*/


        SecondaryMemory secondaryMemory = new SecondaryMemory();
        ExtendibleHashing extendibleHashing = new ExtendibleHashing();
//        Records[] dummy = new Records[12];
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



        ArrayList<Float> utilisationArray=new ArrayList<Float>(dummy.length);
        ArrayList<Integer> splittingCost=new ArrayList<Integer>(dummy.length);
        ArrayList<Double> searchCost=new ArrayList<Double>();


        for (int i = 0; i < dummy.length; i++) {
            //System.out.println(dummy[i].getData());
            ExtendibleHashing.spCost=0;
            extendibleHashing.insert(secondaryMemory, dummy[i], 0);
            //System.out.println("Splitting cost for "+dummy[i].getData()+" is : "+ExtendibleHashing.spCost);
//            splittingCost.add(ExtendibleHashing.spCost);

//            if(i%5000==0 && i>0){
//                double y=generateSearchCost(secondaryMemory,extendibleHashing,dummy,i);
//            searchCost.add(y);


            if(ExtendibleHashing.spCost!=0){
                splittingCost.add(ExtendibleHashing.spCost);
            }

            int numBuckets=extendibleHashing.costSearch(secondaryMemory,dummy[i].getData());
            searchCost.add((double)numBuckets);
            //          }

            //        float x=extendibleHashing.storageUtil(secondaryMemory,i+1);
//
//        System.out.println("Utilisation: "+x);
//            utilisationArray.add(x);
        }

        System.out.println("Values inserted !!!");
        extendibleHashing.printHash(secondaryMemory);
        float x=extendibleHashing.storageUtil(secondaryMemory,100000);
        System.out.println("Utilisation: "+x);

        System.out.println("Average Search Cost ArrayList: \n");

        for(int j=0;j<searchCost.size();j++){
            System.out.print(searchCost.get(j)+"  ");
        }

        System.out.println("\nSplitting Cost ArrayList: \n");

        for(int j=0;j<splittingCost.size();j++){
            System.out.print(splittingCost.get(j)+"  ");
        }



    }


    static double generateSearchCost(SecondaryMemory secondaryMemory,ExtendibleHashing extendibleHashing,Records[] dummy, int i){

        Random rand = new Random();
        int n;
        int numBuckets;
        int bs=0;
        System.out.println("Value of numbuckets: ");

        for(int x=0;x<50;x++){
            n = rand.nextInt(i);
            numBuckets=extendibleHashing.costSearch(secondaryMemory,dummy[n].getData());
            System.out.print(numBuckets+"  ");
            bs+=numBuckets;
        }

        System.out.println("");
        return (double)bs/50;

    }

}
