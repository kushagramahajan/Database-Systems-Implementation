package Assignments.Assignment4;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by kushagra on 4/4/2017.
 */
public class PageTable {
    public static int seed;
    public static int last;
    public static int page_fault;
    public static String mlatest;
    public HashMap<String,Buffer> pageTable;
    public PageTable()
    {
        pageTable = new HashMap<String,Buffer>();
        seed = last = 0;
        page_fault = 0;
        mlatest="";

    }
    public void pageReference(String address,String type,String policy)
    {
        last++;
        if(!pageTable.containsKey(address))
        {
            //System.out.println("fault") ;
            PageTable.page_fault++;
            if(BufferManager.hasCapacity())
            {
                BufferManager.list.add(new Blocks(address,type));
                pageTable.put(address,new Buffer(BufferManager.list.size()-1+""));
                pageTable.get(address).last_used = last;
                mlatest=address;
            }
            else {
                if (policy.equals("LRU")) {
                    Iterator<String> it = pageTable.keySet().iterator();
                    int min = Integer.MAX_VALUE;
                    String m = "";
                    while(it.hasNext())
                    {
                        String key = it.next();
                        Buffer buffer = pageTable.get(key);
                        if(buffer.last_used<=min)
                        {
                            m = key;
                            min = buffer.last_used;
                        }

                    }
                    //System.out.println(m);
                    String add = pageTable.get(m).address;
                    pageTable.remove(m,pageTable.get(m));
                    BufferManager.list.set(Integer.parseInt(add),new Blocks(address,type));
                    pageTable.put(address,new Buffer(add));
                    pageTable.get(address).last_used=last;
                    mlatest = address;




                } else if (policy.equals("MRU")) {
                    /*Iterator<String> it = pageTable.keySet().iterator();
                    int max = -1;
                    String m = "";
                    while(it.hasNext())
                    {
                        String key = it.next();
                        Buffer buffer = pageTable.get(key);
                        if(buffer.last_used>=max)
                        {
                            m = key;
                            max = buffer.last_used;
                        }

                    }*/
                    String add = pageTable.get(mlatest).address;
                    pageTable.remove(mlatest,pageTable.get(mlatest));
                    BufferManager.list.set(Integer.parseInt(add),new Blocks(address,type));
                    pageTable.put(address,new Buffer(add));
                    pageTable.get(address).last_used=last;
                    mlatest = address;

                }
            }


        }
        else
        {
            //System.out.println("hello");
            pageTable.get(address).last_used = last;
            mlatest = address;
        }
        /*System.out.println("Buffer Start");
        for(int i=0;i<BufferManager.list.size();i++)
        {
            System.out.println(BufferManager.list.get(i).id+" "+BufferManager.list.get(i).type);
        }
        System.out.println("Buffer End");*/
    }


}
