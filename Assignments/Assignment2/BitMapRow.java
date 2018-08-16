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
public class BitMapRow {
    public HashMap<Integer,ArrayList<Integer>> index;
    public BitMapRow()
    {
        index = new HashMap<Integer,ArrayList<Integer>>();
    }
    public void populateIndex(ArrayList<Record> records,int flag)
    {
        try
        {
            for(int i=0;i<records.size();i++)
            {
                Record record = records.get(i);
                if(index.containsKey(record.getSale()))
                {
                    index.get(record.getSale()).add(record.getID());
                }
                else
                {
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(record.getID());
                    index.put(record.getSale(),list);
                    list = null;

                }
            }
            if(flag==0)
                storemap();

        }
        catch(Exception e){
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
            File file = openFile(value,"txt",1);
            BufferedReader reader = openReader(file);
            Boolean flag = false;
            int stop = 0;
            int index = 0;
            BitSet Bv = new BitSet(TableSize.tablesize);
            String sen = "";
            while(true) {
                stop = 0;
                //System.out.println(value);
                if(flag)
                    break;
                try {
                    no_of_blocks++;
                    while ((sen = reader.readLine())!=null) {

                        //System.out.println(sen);
                        if(!sen.contains("next"))
                        {
                            Bv.set(Integer.parseInt(sen));
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
                            file = openFile(value,"txt",1);
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
    public void storemap()
    {
        Iterator it = index.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            ArrayList<Integer> set = (ArrayList<Integer>)pair.getValue();
            int count = 0;
            // ArrayList<Integer> list = new ArrayList<Integer>();
            int index = 0;
            String filename = pair.getKey()+"_"+index;
            //System.out.println(filename+" "+set.size());
            File file = openFile(filename, "txt", 1);
            BufferedWriter writer = openWriter(file);
            try {
                int ind = 0;
                for (int i = 0; i < set.size(); i++) {
                    ind = set.get(i);
                    if (count == BlockSize.blocksizebitmaprow) {
                        writer.write(ind+"\n");
                        writer.write("next: "+(index+1));
                        writer.close();
                        writer = null;
                        file = null;
                        index = index + 1;
                        count = 0;
                        filename = pair.getKey()+"_"+index;
                        file = openFile(filename,"txt",1);
                        writer = openWriter(file);

                    } else if (count < BlockSize.blocksizebitmaprow) {
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
                    e.printStackTrace();
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

}
