package Assignments.Assignment2;

import jdk.nashorn.internal.ir.Block;

import java.io.*;
import java.util.ArrayList;

import static Assignment2.FileOperations.openFile;
import static Assignment2.FileOperations.openWriter;

/**
 * Created by kushagra on 2/14/2017.
 */
public class Storage {

    public static void store(ArrayList<Record> records)
    {
        int count = 0;
        ArrayList<Record> list = new ArrayList<Record>();
        int index=0;

        // for storing records
        for (int i = 0; i < records.size(); i++) {
            Record record = records.get(i);
            if (count == BlockSize.blocksize) {
                list.add(record);
                writeToFile("", list, index,null);
                list.clear();
                count = 0;
                index += 1;
            } else if (count < BlockSize.blocksize) {
                list.add(record);
                count++;
            }


        }
        if (!list.isEmpty()) {
            writeToFile("", list, index,null);
        }



    }

    public static void storeMaps(int flag){
        ArrayList<Record> records=readRecordsFromFile();
        int count=0;
        int index=0;

        if(flag==1){
            //for storing bitmap


        }


        if(flag==2) {
            //for storing bit slice
            for (int h = 0; h < BitSlice.nBits; h++) {

                BitSlice.bitSliceMap.put(h,index);
                ArrayList<Integer> list = new ArrayList<Integer>();
                int salesAmount;
                for (int i = 0; i < records.size(); i++) {
                    Record record = records.get(i);
                    salesAmount = record.getSale();

                    int[] bits = new int[BitSlice.nBits];
                    for (int j = BitSlice.nBits-1; j >= 0; j--) {
                        bits[j] = (salesAmount & (1 << j));
                        if(bits[j]!=0){
                            bits[j]=1;
                        }
                    }

                    if (count == BlockSize.blocksizebitslice) {
                        list.add(bits[h]);
                        writeToFile("bitslice", null, index,list);
                        list.clear();
                        count = 0;
                        index += 1;
                    } else if (count < BlockSize.blocksizebitslice) {
                        list.add(bits[h]);
                        count++;
                    }


                }
                if (!list.isEmpty()) {
                    writeToFile("bitslice", null, index,list);
                    count=0;
                    list.clear();
                    index+=1;
                }

            }

        }

    }


    public static void writeToFile(String filename,ArrayList<Record> list,int index, ArrayList<Integer> intList)
    {
        String filenamefinal = filename+index;
        File file = openFile(filenamefinal,"csv");
        //System.out.println(file);
        BufferedWriter writer = openWriter(file);
        if(filenamefinal.contains("bitmap")){

        }
        else if(filenamefinal.contains("bitslice")){
            try {
                int bit;
                for (int i = 0; i < intList.size(); i++) {
                    bit = intList.get(i);
                    //System.out.println(bit);
                    writer.write(bit+"" + "\n");
                }
                writer.write("next: " + (index + 1) + "\n");
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else {

            try {
                for (int i = 0; i < list.size(); i++) {
                    Record record = list.get(i);
                    writer.write(record.getID() + "," + record.getName() + "," + record.getSale() + "\n");
                }
                writer.write("next: " + (index + 1) + "\n");
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    public static ArrayList<Record> readRecordsFromFile(){
        ArrayList<Record> records=new ArrayList<Record>();

        String csvFile;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int index=0;
        int flag=0;
        while(true) {
            try {
                csvFile = "./src/Assignment2/Data/"+index + "" + ".csv";
                br = new BufferedReader(new FileReader(csvFile));
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    if(line==null || line.equals("")){
                        flag=1;
                        break;
                    }
                    if (line.startsWith("next:")) {

                        index += 1;
                        break;
                    } else {
                        String[] values = line.split(cvsSplitBy);
                        Record tempRecord = new Record(Integer.parseInt(values[0]), values[1], Integer.parseInt(values[2]));
                        records.add(tempRecord);

                    }

                }

            } catch (FileNotFoundException e) {
                flag=1;
                //e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        if(flag==1){
            break;
        }

        // end of while 1
        }

        return records;

    }



}
