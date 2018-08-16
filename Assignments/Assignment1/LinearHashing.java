package Assignments.Assignment1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by kushagra on 1/13/2017.
 */
public class LinearHashing {
    public Integer nextPointer;
    public HashMap<Integer,Integer> map;
    Integer hik;
    private Integer access;
    public ArrayList<String> overlist;
    public LinearHashing()
    {
        nextPointer = 0;
        map = new HashMap<Integer,Integer>();
        overlist = new ArrayList<String>();
        map.put(0,1);
        map.put(1,1);
        access = 0;
        hik = 1;
    }
    public void insert(SecondaryMemory secondaryMemory,Records record,Integer hash,Boolean flag)
    {
        Integer index = new Double(record.getData()%Math.pow(2,hash)).intValue();
        //System.out.println("index"+index);
        //System.out.println("data"+record.getData());
        /*System.out.println(map.keySet());
        System.out.println(map.values());*/
        if(map.containsKey(index))
        {
            if(!map.get(index).equals(hash))
            {
                index = new Double(record.getData()%Math.pow(2,map.get(index))).intValue();

            }
        }
        if(secondaryMemory.memory[index].hasSpace())
        {
            //System.out.println("space");
            secondaryMemory.memory[index].addRecords(record);
            secondaryMemory.memory[index].next="";
        }
        else
        {
            Bucket bucket = secondaryMemory.memory[index];
            while(!bucket.next.equals("")) {
                /*secondaryMemory.memory[index].next = String.valueOf(secondaryMemory.overflow);
                secondaryMemory.memory[secondaryMemory.overflow].addRecords(record);
                secondaryMemory.overflow++;*/
                //System.out.println("rajeev"+bucket.next);
                    bucket = secondaryMemory.memory[Integer.parseInt(bucket.next)];
                //System.out.println("rjaeev"+bucket.next);

            }
            if(bucket.hasSpace())
            {
                bucket.addRecords(record);
            }
            else {
                if (overlist.size() == 0) {
                    bucket.next = String.valueOf(secondaryMemory.overflow);
                    secondaryMemory.memory[secondaryMemory.overflow].addRecords(record);
                    secondaryMemory.overflow++;
                    // System.out.println("overflow" + secondaryMemory.overflow);
                } else {
                    String over = overlist.get(0);
                    overlist.remove(0);
                    bucket.next = over;
                    secondaryMemory.memory[Integer.parseInt(over)].next = "";
                    secondaryMemory.memory[Integer.parseInt(over)].addRecords(record);
                    //System.out.println("overflow1"+secondaryMemory);

                }
                if(flag==false) {

                    //System.out.println("Nextpointer" + nextPointer);
                    map.put(nextPointer, map.get(nextPointer) + 1);
                    //System.out.println("mapsize" + map.size());
                    map.put(map.size(), map.get(nextPointer));
                    ArrayList<Records> list = new ArrayList<Records>();
                    access++;

                    for (int i = 0; i < secondaryMemory.memory[nextPointer].getRecords().length; i++) {
                        if (secondaryMemory.memory[nextPointer].getRecords()[i] != null) {
                            //System.out.println("map" + map.get(nextPointer));
                            //System.out.println("value" + secondaryMemory.memory[nextPointer].getRecords()[i].getData());
                            Records r = new Records();
                            r.setData(secondaryMemory.memory[nextPointer].getRecords()[i].data);
                            list.add(r);
                            //System.out.println("Hello");
                            secondaryMemory.memory[nextPointer].deleteRecord(i);

                            //insert(secondaryMemory,secondaryMemory.memory[nextPointer].getRecords()[i],map.get(nextPointer));
                        }
                    }
                    bucket = secondaryMemory.memory[nextPointer];
                    while (!bucket.next.equals("")) {
                        access++;
                        //System.out.println(bucket.next);
                        Integer over_index = Integer.parseInt(bucket.next);
                        overlist.add(bucket.next);
                        for (int i = 0; i < secondaryMemory.memory[over_index].getRecords().length; i++) {
                            //System.out.println("Hello1");
                            if (secondaryMemory.memory[over_index].getRecords()[i] != null) {
                                Records r = new Records();
                                r.setData(secondaryMemory.memory[over_index].getRecords()[i].data);
                                list.add(r);
                                secondaryMemory.memory[over_index].deleteRecord(i);
                                //insert(secondaryMemory,secondaryMemory.memory[over_index].getRecords()[i],map.get(nextPointer));
                            }

                        }
                        bucket = secondaryMemory.memory[Integer.parseInt(bucket.next)];
                        //System.out.println(bucket.next);
                    }
                    for (int i = 0; i < list.size(); i++) {
                        //access++;
                        insert(secondaryMemory, list.get(i), map.get(nextPointer),true);
                    }
                    list.clear();


                    Collection<Integer> values = map.values();
                    HashSet set = new HashSet();
                    set.addAll(values);
                    //System.out.println("size" + set.size());
                    Iterator<Integer> it1 = set.iterator();
                    while (it1.hasNext()) {
                        //System.out.println(it1.next() + ",");
                        it1.next();
                    }
                    if (set.size() == 1) {
                        nextPointer = 0;
                        Iterator<Integer> it = set.iterator();
                        if (it.hasNext()) {
                            hik = it.next();
                        }

                    } else {

                        nextPointer++;
                    }
                }
            }


        }


    }
    public Integer getAccess()
    {
        Integer a = access;
        access=0;
        return a;
    }
    public Double search(SecondaryMemory secondaryMemory,ArrayList<Records> list)
    {
        Integer count = 0;
        for(int i=0;i<list.size();i++)
        {
            Records record = list.get(i);
            //System.out.println(record.getData());
            Integer index = new Double(record.getData()%Math.pow(2,hik)).intValue();
            //System.out.println("hik"+hik);
            //System.out.println("index"+index);
            if(map.get(index).equals(hik))
            {
                //System.out.println("OK");
                Integer in = index;
                //System.out.println("In"+in);
                int flag = 0;
                while(flag==0&&map.containsKey(in)&&map.get(in).equals(hik)&&in<map.size())
                {
                    Bucket bucket = secondaryMemory.memory[in];
                    while(flag==0&&bucket!=null)
                    {
                        Records[] records = bucket.getRecords();
                        if(records!=null)
                        {
                            count++;
                            //System.out.println("overflow1");
                            for(int j=0;j<records.length;j++)
                            {
                                if(records[j]!=null)
                                {
                                    if(records[j].getData()==record.getData())
                                    {
                                        //System.out.println("FoundIndex"+in);
                                        flag = 1;
                                        break;
                                    }
                                }
                            }
                        }
                        try {
                            bucket = secondaryMemory.memory[Integer.parseInt(bucket.next)];
                        } catch (Exception e) {
                            bucket = null;
                        }
                    }
                    in++;
                }
            }
            else
            {
                //System.out.println("NOK");
                //System.out.println(map.get(index));
                index = new Double(record.getData()%Math.pow(2,map.get(index))).intValue();
                //System.out.println("Index"+index);
                Integer in = index;
                int flag = 0;

                while(flag==0&&map.containsKey(in)&&map.get(in).equals(map.get(index))&&in<map.size())
                {
                    //System.out.println("In"+in);
                    Bucket bucket = secondaryMemory.memory[in];
                    while(flag==0&&bucket!=null)
                    {
                        Records[] records = bucket.getRecords();
                        if(records!=null)
                        {
                            count++;
                            //System.out.println("overflow");
                            for(int j=0;j<records.length;j++)
                            {
                                if(records[j]!=null)
                                {
                                    //System.out.println("value"+records[j].getData());
                                    if(records[j].getData()==record.getData())
                                    {
                                        //System.out.println("FoundIndex"+in);
                                        flag = 1;
                                        break;
                                    }
                                }
                            }
                        }

                        try {
                            bucket = secondaryMemory.memory[Integer.parseInt(bucket.next)];
                        } catch (Exception e) {
                            bucket = null;
                        }
                    }
                    in++;
                }

            }
            //System.out.println("Count"+count);


        }
        //System.out.println("Size"+list.size());
        Double ans = count.doubleValue()/list.size();
        return ans;
    }


    public void printTable(SecondaryMemory secondaryMemory)
    {
            for (int i = 0; i < map.size(); i++) {
                System.out.print("Index: " + i + " Hash: " + map.get(i) + ": ");
               // writer.write("Index: " + i + " Hash: " + map.get(i) + ": ");
                Bucket bucket = secondaryMemory.memory[i];
            /*Records[] recordss  = bucket.getRecords();
            for (int j = 0; j < recordss.length; j++) {
                    if(recordss[j]!=null)
                    System.out.println("Value:" + recordss[j].getData());
            }*/
                String next=i+"";
                System.out.print("bucket: "+i+" ");
                while (bucket != null) {
                    System.out.print(" bucket: "+next+" ");
                    Records[] recordss = bucket.getRecords();
                    for (int j = 0; j < recordss.length; j++) {
                        if (recordss[j] != null)
                            System.out.print("Value:" + recordss[j].getData() + "->");

                            //writer.write("Value:" + recordss[j].getData() + "->");
                    }
                    //bucket = secondaryMemory.memory[Integer.parseInt(bucket.next)];
                    try {
                        next = bucket.next;
                        bucket = secondaryMemory.memory[Integer.parseInt(bucket.next)];

                    } catch (Exception e) {
                        bucket = null;
                    }

                }
                System.out.println();
               // writer.write("\n");
            }
    }
    public Double storage(Integer records,SecondaryMemory secondaryMemory)
    {
        Integer B = 0;
        for(int i=0;i<map.size();i++)
        {
            Bucket bucket = secondaryMemory.memory[i];
            while (bucket!=null) {
                if (bucket.getEmpty() != RecordSize.size) {
                    B++;
                }
                try {
                    bucket = secondaryMemory.memory[Integer.parseInt(bucket.next)];

                } catch (Exception e) {
                    bucket = null;
                }
            }
        }
        Integer b = RecordSize.size;
        /*System.out.println(B);
        System.out.println(b);
        System.out.println(records);*/
        Double storage = (records.doubleValue()/(B*b));
        return storage;

    }




}
