package Assignments.Assignment1;

import java.util.*;

/**
 * Created by Rajeev Nagarwal on 1/13/2017.
 */
public class LinearHashing {
    public Integer nextPointer;
    public HashMap<Integer,Integer> map;
    Integer hik;
    public LinearHashing()
    {
        nextPointer = 0;
        map = new HashMap<Integer,Integer>();
        map.put(0,1);
        map.put(1,1);
        hik = 1;
    }
    public void insert(SecondaryMemory secondaryMemory,Records record,Integer hash)
    {
        Integer index = new Double(record.getData()%Math.pow(2,hash)).intValue();
        System.out.println("index"+index);
        if(secondaryMemory.memory[index].hasSpace())
        {
            System.out.println("space");
            secondaryMemory.memory[index].addRecords(record);
        }
        else
        {
            Bucket bucket = secondaryMemory.memory[index];
            while(!bucket.next.equals("")) {
                /*secondaryMemory.memory[index].next = String.valueOf(secondaryMemory.overflow);
                secondaryMemory.memory[secondaryMemory.overflow].addRecords(record);
                secondaryMemory.overflow++;*/
                bucket = secondaryMemory.memory[Integer.parseInt(bucket.next)];
            }
            bucket.next = String.valueOf(secondaryMemory.overflow);
            secondaryMemory.memory[secondaryMemory.overflow].addRecords(record);
            secondaryMemory.overflow++;
            System.out.println("overflow"+secondaryMemory.overflow);
            System.out.println("Nextpointer"+nextPointer);
            map.put(nextPointer,map.get(nextPointer)+1);
            System.out.println("mapsize"+map.size());
            map.put(map.size(),map.get(nextPointer));
            for(int i=0;i<secondaryMemory.memory[nextPointer].getRecords().length;i++)
            {
                if(secondaryMemory.memory[nextPointer].getRecords()[i]!=null)
                {
                    System.out.println("map"+map.get(nextPointer));
                    System.out.println("value"+secondaryMemory.memory[nextPointer].getRecords()[i]);
                    insert(secondaryMemory,secondaryMemory.memory[nextPointer].getRecords()[i],map.get(nextPointer));
                }
            }
            bucket = secondaryMemory.memory[nextPointer];
            while(!bucket.next.equals(""))
            {
                Integer over_index = Integer.parseInt(bucket.next);
                for(int i=0;i<secondaryMemory.memory[over_index].getRecords().length;i++)
                {
                    if(secondaryMemory.memory[over_index].getRecords()[i]!=null)
                    {
                        insert(secondaryMemory,secondaryMemory.memory[over_index].getRecords()[i],map.get(nextPointer));
                    }

                }

            }
            Collection<Integer> values = map.values();
            HashSet set = new HashSet();
            set.addAll(values);
            System.out.println("size"+set.size());
            Iterator<Integer> it1 = set.iterator();
            while(it1.hasNext())
            {
                System.out.println(it1.next()+",");
            }
            if(set.size()==1)
            {
                nextPointer = 0;
                Iterator<Integer> it = set.iterator();
                if(it.hasNext())
                {
                    hik = it.next();
                }

            }
            else {

                nextPointer++;
            }

        }

    }


}
