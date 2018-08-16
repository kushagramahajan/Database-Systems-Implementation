package Assignments.Assignment2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.*;

import static Assignment2.FileOperations.openFile;
import static Assignment2.FileOperations.openReader;
import static Assignment2.FileOperations.openWriter;

/**
 * Created by kushagra on 3/2/2017.
 */
public class BitMap {
    public HashMap<Integer,BitSet> index;
    public BitMap()
    {
        index = new HashMap<Integer,BitSet>();
    }
    public void populateIndex(ArrayList<Record> records, int flag)
    {
        //System.out.println("Size: "+records.size());
        try {
            for (int i = 0; i < records.size(); i++) {
                Record record = records.get(i);
//            System.out.println(index.size());
                if (index.containsKey(record.getSale())) {
                    //System.out.println(index.get(record.getSale()));
                    index.get(record.getSale()).set(record.getID()-1,true);
                } else {
                    index.put(record.getSale(),new BitSet(TableSize.tablesize));
                    index.get(record.getSale()).set(record.getID()-1,true);
                    //System.out.println(index.get(record.getSale()));
                }
            }
            records = null;
            if(flag==0) {
                storemap();
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    public HashMap<Integer,String> secondaryIndex()
    {
        HashMap<Integer,String> secondaryIndex = new HashMap<Integer,String>();
        Iterator it = index.keySet().iterator();
        Integer key = 0;
        String value = "";
        while(it.hasNext())
        {
            key = (Integer)it.next();
            value = key+"_0";
            secondaryIndex.put(key,value);

        }
        return secondaryIndex;
    }
    public Integer no_of_blocks(BitSet set)
    {
        int no_of_blocks = 0;
        HashMap<Integer,String> secondaryIndex = secondaryIndex();
        Iterator it = secondaryIndex.keySet().iterator();
        Integer key = 0;
        String value = "";
        int sum = 0;
        while(it.hasNext())
        {
            key  = (Integer)it.next();
            value = secondaryIndex.get(key);
            File file = openFile(value,"txt",0);
            BufferedReader reader = openReader(file);
            Boolean flag = false;
            int stop = 0;
            int index = 0;
            BitSet Bv = new BitSet(TableSize.tablesize);
            String sen = "";
            while(true) {
                //System.out.println(value);
                stop = 0;
                if(flag)
                    break;
                try {
                    no_of_blocks++;
                    while ((sen = reader.readLine())!=null) {

                        //System.out.println(sen);
                        if(!sen.contains("next"))
                        {
                            if(sen.contains("1")) {
                                Bv.set(index+1);
                            }
                            index++;
                        }
                        else{

                            flag = false;
                            stop = 1;
                            file = null;
                            reader.close();
                            reader = null;
                            value = value.split("_")[0]+"_"+sen.split(": ")[1];
                            //System.out.println("Hello"+value);
                            file = openFile(value,"txt",0);
                            reader = openReader(file);
                            break;
                        }

                    }
                    if(stop==0)
                        flag = true;
                    else
                        flag = false;
                   /* if(sen.contains("next"))
                    {

                    }
                    else
                    {
                        flag = true;
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
            //System.out.println(set);
            //System.out.println(Bv);
            int c = COUNT(set,Bv);
            sum = sum + key*c;
            //System.out.println(key+":"+c);
        }
        System.out.println("SUM: "+sum);
        return no_of_blocks;

    }
    public int COUNT(BitSet bf,BitSet bv)
    {
        BitSet set = new BitSet(bf.size());
        set.or(bf);
        set.and(bv);
        return set.cardinality();
    }


    public StringBuilder getBitArray()
    {
        String bits = "";
        for(int i=0;i<TableSize.tablesize;i++)
        {
            bits = bits+"0";
        }
        StringBuilder builder = new StringBuilder(bits);
        return builder;

    }
    public void storemap()
    {
        Iterator it = index.keySet().iterator();
        while (it.hasNext()) {
            Integer key = (Integer)it.next();
            BitSet set = index.get((key));
            int count = 0;
           // ArrayList<Integer> list = new ArrayList<Integer>();
            int index = 0;
            String filename = key+"_"+index;
            File file = openFile(filename, "txt", 0);
            BufferedWriter writer = openWriter(file);
            try {
                int ind = 0;
                for (int i = 0; i < TableSize.tablesize; i++) {
                    if (set.get(i)) {
                        ind = 1;
                    }
                    else
                    {
                        ind = 0;
                    }
                    if (count == BlockSize.blocksizebitmap) {
                        writer.write(ind+"\n");
                        writer.write("next: "+(index+1));
                        writer.close();
                        writer = null;
                        file = null;
                        index = index + 1;
                        count = 0;
                        filename = key+"_"+index;
                        file = openFile(filename,"txt",0);
                        writer = openWriter(file);

                    } else if (count < BlockSize.blocksizebitmap) {
                        writer.write(ind+"\n");
                        count++;
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally{
                try {
                    writer.flush();
                    writer.close();
                    writer = null;
                    file = null;
                }
                catch(Exception e)
                {

                }


            }

            /*if(!list.isEmpty()) {
                writefile(filename, list, index);
                list.clear();
                count = 0;
                index = index + 1;
            }
            list = null;*/

            it.remove(); // avoids a ConcurrentModificationException
        }
    }
    public void writefile(String filename, ArrayList<Integer> list,int index)
    {
        String filenamefinal = filename+"_"+index;
        File file = openFile(filenamefinal,"txt",0);
        BufferedWriter writer = openWriter(file);
        try {
            for (int i = 0; i < list.size(); i++) {
                writer.write(list.get(i));
                writer.newLine();
            }
            writer.write("next: " + (index + 1) + "\n");
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}
