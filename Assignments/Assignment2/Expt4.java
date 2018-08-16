package Assignments.Assignment2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kushagra on 2/26/2017.
 */
public class Expt4 {

    public void createbf(){
        Query.bf=new ArrayList<>();
        for(int i=0;i<TableSize.tablesize;i++){
            Query.bf.add(0);
        }
        Random rand = new Random();
        int  n;
        for(int i=0;i<500;i++){
            n= rand.nextInt(TableSize.tablesize-1) + 0;
            if(Query.bf.get(n)==1){
                Query.bf.set(n/2, 1);
            }
            else {
                Query.bf.set(n, 1);
            }
        }
    }

    public int executenoindex(){

        ArrayList<Record> records=Storage.readRecordsFromFile();
        int salesum=0;

        int file;
        BufferedReader br = null;
        String line = "";
        int index;

        String csvFile;
        int num=0;

        try {
            for (int i = 0; i < records.size(); i++) {
                int sale;
                int counter=0;
                if (Query.bf.get(i) == 1) {
                    file = i / BlockSize.blocksize;
                    index = i % BlockSize.blocksize;

                    csvFile = "./src/Assignment2/Data/" + file + "" + ".csv";
                    br = new BufferedReader(new FileReader(csvFile));
                    while ((line = br.readLine()) != null) {
                        counter+=1;
                        if(counter==index){
                            sale=Integer.parseInt(line.split(",")[2]);
                            salesum+=sale;
                        }

                    }

                    num+=1;

                }


            }

        }
        catch(FileNotFoundException e){
            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return num;

    }


    public int executebitslice(){

        int num=0;
        int salesum=0;
        ArrayList<Integer> bv=new ArrayList<Integer>();
        ArrayList<Integer> andResult=new ArrayList<Integer>();

        BufferedReader br=null;
        String csvFile="";
        String line="";
        int flag=0;

        int counter=0;
        int fileindex=0;

        try {
            for (int i = 0; i < BitSlice.bitSliceMap.size(); i++) {
                counter=0;
                flag = 0;
                bv.clear();
                //System.out.println("bit: "+i);
                fileindex = BitSlice.bitSliceMap.get(i);

                while(true) {
                    csvFile="./src/Assignment2/Databitslice/bitslice" + fileindex + "" + ".csv";

                    br = new BufferedReader(new FileReader(csvFile));
                    while ((line = br.readLine()) != null) {

                        // use comma as separator
                        if (line == null || line.equals("")) {
                            flag = 1;
                            break;
                        }
                        if (line.startsWith("next:")) {
                            num += 1;
                            fileindex+=1;
                            break;
                        } else {
                            counter += 1;
                            bv.add(Integer.parseInt(line));
                            if(counter==TableSize.tablesize){
                                flag=1;
                                num+=1;
                                break;
                            }
                        }

                    }

                    if(flag==1){
                        break;
                    }

                }
                int countOnes=0;
                for(int k=0;k<bv.size();k++){
                    if(bv.get(k)==0 || Query.bf.get(k)==0)
                        andResult.add(0);
                    else {
                        andResult.add(1);
                        countOnes+=1;
                    }
                }
                salesum=salesum+(2^i*(countOnes));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return num;

    }



}
