package Assignments.Assignment1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static java.lang.Math.exp;
import static java.lang.Math.pow;

/**
 * Created by kushagra on 1/13/2017.
 */
public class ExtendibleHashing {
    public HashMap<Integer,Integer> map;
    public int globalDepth;
    ArrayList<Integer> overflowTrack;
    public static int bucketAddr;
    public static int spCost;
    public static HashSet<Integer> spHash;
    public static int bits=10;
    public static int glhash=0;

    public ExtendibleHashing(){
        map = new HashMap<Integer,Integer>();
        map.put(0,0);
        globalDepth=0;
        overflowTrack=new ArrayList<Integer>();
        spHash=new HashSet<Integer>();
    }

    public void insert(SecondaryMemory secondaryMemory,Records record,Integer hash) {
        //Integer index = new Double(record.getData()%Math.pow(2,hash)).intValue();
        int data = record.getData();
        //int mask=(1<<32-globalDepth);
        if (globalDepth == 0) {
            //System.out.println("Global depth 0 detected !");

            if(secondaryMemory.memory[0].hasSpace()){
                //System.out.println("Record "+record.getData()+" added to bucket 0");
                secondaryMemory.memory[0].addRecords(record);
                if(hash==1){
                    ExtendibleHashing.spHash.add(0);
                }
            }

            else if(hash==1){

                //System.out.println("Overflow bucket entry called while rehashing");
                int finalBucketIndex=0;
                Bucket bucket = secondaryMemory.memory[0];
                while(!bucket.next.equals("")) {

                    finalBucketIndex=Integer.parseInt(bucket.next);
                    bucket = secondaryMemory.memory[Integer.parseInt(bucket.next)];
                }

                //ExtendibleHashing.spCost+=1;
                if (secondaryMemory.memory[finalBucketIndex].hasSpace()){
                    secondaryMemory.memory[finalBucketIndex].addRecords(record);
                    ExtendibleHashing.spHash.add(finalBucketIndex);
                    if(finalBucketIndex>1023 && finalBucketIndex<1200001){
                        ExtendibleHashing.spHash.add(1400001+(int)((finalBucketIndex-1023)/(RecordSize.size/2)));
                    }
                }
                else{
                    // add to overflow bucket

                    if(!overflowTrack.isEmpty()){

                        secondaryMemory.memory[finalBucketIndex].setNext(overflowTrack.get(0).toString());
                        int nextVal=Integer.parseInt(secondaryMemory.memory[finalBucketIndex].getNext());
                        secondaryMemory.memory[nextVal].addRecords(record);
                        ExtendibleHashing.spHash.add(nextVal);

                        if(nextVal>1023 && nextVal<1200001){
                            ExtendibleHashing.spHash.add(1400001+(int)((nextVal-1023)/(RecordSize.size/2)));
                        }
                        overflowTrack.remove(0);

                        //System.out.println("Overflow bucket added from arraylist!");

                    }
                    else{
                        secondaryMemory.memory[finalBucketIndex].setNext(String.valueOf(secondaryMemory.overflow));
                        int nextVal=Integer.parseInt(secondaryMemory.memory[finalBucketIndex].getNext());
                        secondaryMemory.memory[nextVal].addRecords(record);
                        secondaryMemory.overflow++;
                        ExtendibleHashing.spHash.add(nextVal);
                        if(nextVal>1023 && nextVal<1200001){
                            ExtendibleHashing.spHash.add(1400001+(int)((nextVal-1023)/(RecordSize.size/2)));
                        }
//                        System.out.println("Value added to overflow bucket!");
//                        System.out.println("Overflow bucket address: "+secondaryMemory.overflow);
                    }

                }


            }
            else{

//                if(!secondaryMemory.memory[0].hasSpace() && hash==0){      // newly added for spcost only
//                    Bucket tbucket=secondaryMemory.memory[0];
//                    while(!tbucket.next.equals("")) {
//                        spCost+=1;
//                        tbucket = secondaryMemory.memory[Integer.parseInt(tbucket.next)];
//                    }
//                    spCost+=1;
//                }



                //splitting or rehash for globaldepth=0
                //System.out.println("Global depth changed to 1 from 0 and then rehash");
                //rehash
                int lengthOfMainMemory=(int)Math.pow(2,globalDepth+1);

                map.put(1,1);
                globalDepth+=1;
                int index=0;
                int sizeOfBucket;
                Bucket bucketToBeRehashed=secondaryMemory.memory[map.get(0)];

                while(!bucketToBeRehashed.next.equals("")) {

                    Records[] recordsInBucket=bucketToBeRehashed.getRecords();
                    sizeOfBucket=bucketToBeRehashed.getSize();
                    secondaryMemory.memory[index].setEmpty(RecordSize.size);

                    for(int l=0;l<sizeOfBucket;l++){

                        insert(secondaryMemory,recordsInBucket[l], 1);
                    }

                    index = Integer.parseInt(bucketToBeRehashed.next);
                    bucketToBeRehashed = secondaryMemory.memory[Integer.parseInt(bucketToBeRehashed.next)];


                }


                sizeOfBucket=secondaryMemory.memory[index].getSize();
                //Bucket bucketToBeRehashed=secondaryMemory.memory[map.get(0)];

                Records[] recordsInBucket=secondaryMemory.memory[index].getRecords();

                //first remove the record from its current location
                secondaryMemory.memory[index].setEmpty(RecordSize.size);



                for(int k=0;k<sizeOfBucket;k++) {
                    // System.out.println("other entries rehash");
                    insert(secondaryMemory,recordsInBucket[k], 1);


                }



                //int noOfBitsModified=((int) (Math.log(data) / Math.log(2)))+1;
                int noOfBitsModified=bits;
                int hashKeyModified = (data >>> (noOfBitsModified - globalDepth))&((int)(Math.pow(2,globalDepth)-1));
                //int hashKeyModified=data >>> (32 - globalDepth);
                int bucketForHashKeyModified = map.get(hashKeyModified);

                int finalBucketIndex=bucketForHashKeyModified;
                if(hash==0){
                    glhash=hashKeyModified;
                }

                Bucket bucket = secondaryMemory.memory[bucketForHashKeyModified];
                while(!bucket.next.equals("")) {

                    finalBucketIndex=Integer.parseInt(bucket.next);
                    bucket = secondaryMemory.memory[Integer.parseInt(bucket.next)];

                }

                if (secondaryMemory.memory[finalBucketIndex].hasSpace()){

                    //ExtendibleHashing.spCost+=1;
                    ExtendibleHashing.spHash.add(finalBucketIndex);
                    if(finalBucketIndex>1023 && finalBucketIndex<1200001){
                        ExtendibleHashing.spHash.add(1400001+(int)((finalBucketIndex-1023)/(RecordSize.size/2)));
                    }

//                    System.out.println("Record added to bucket after rehash for globaldepth 0!!!");
//                    System.out.println("Record: "+ record.getData()+" added to bucket "+finalBucketIndex);
                    secondaryMemory.memory[finalBucketIndex].addRecords(record);
                }
                else{
                    // add to overflow bucket
                    if(!overflowTrack.isEmpty()){
                        //ExtendibleHashing.spCost+=1;
                        secondaryMemory.memory[finalBucketIndex].setNext(overflowTrack.get(0).toString());
                        int nextVal=Integer.parseInt(secondaryMemory.memory[finalBucketIndex].getNext());
                        secondaryMemory.memory[nextVal].addRecords(record);

                        ExtendibleHashing.spHash.add(nextVal);
                        if(nextVal>1023 && nextVal<1200001){
                            ExtendibleHashing.spHash.add(1400001+(int)((nextVal-1023)/(RecordSize.size/2)));
                        }
                        overflowTrack.remove(0);
                        //System.out.println("Overflow bucket added from arraylist!");

                    }
                    else {

                        //ExtendibleHashing.spCost+=1;

                        secondaryMemory.memory[finalBucketIndex].setNext(String.valueOf(secondaryMemory.overflow));
                        int nextVal = Integer.parseInt(secondaryMemory.memory[finalBucketIndex].getNext());
                        secondaryMemory.memory[nextVal].addRecords(record);
                        secondaryMemory.overflow++;

                        ExtendibleHashing.spHash.add(nextVal);
                        if(nextVal>1023 && nextVal<1200001){
                            ExtendibleHashing.spHash.add(1400001+(int)((nextVal-1023)/(RecordSize.size/2)));
                        }
                        //                        System.out.println("Value added to overflow bucket!");
//                        System.out.println("Overflow bucket address: " + secondaryMemory.overflow);

                    }
                }


            }


        }
        else if(globalDepth>=10){
            //int noOfBits=((int) (Math.log(data) / Math.log(2)))+1;
            int noOfBits=bits;
            int hashkey;

            int hashkeyold;

            if(noOfBits>=globalDepth)
                hashkey = (data >>> (noOfBits - globalDepth))&((int)(Math.pow(2,globalDepth)-1));
            else
                hashkey=data & ((int)(Math.pow(2,globalDepth)-1));

//            if(noOfBits>=(globalDepth-1))
//                hashkeyold = (data >>> (noOfBits - (globalDepth-1)))&((int)(Math.pow(2,globalDepth-1)-1));
//            else
//                hashkeyold = data & ((int)(Math.pow(2,globalDepth-1)-1));


            if(hash==0){
                glhash=hashkey;
            }

            //System.out.println("noofbits: "+noOfBits);
            int bucketForHashKey = map.get(hashkey);
            //System.out.println("bucket: "+bucketForHashKey);
            // System.out.println("Size: "+secondaryMemory.memory[bucketForHashKey].getSize());
            if (secondaryMemory.memory[bucketForHashKey].hasSpace()) {
                if(hash==1){
                    ExtendibleHashing.spHash.add(bucketForHashKey);
                    if(bucketForHashKey>1023 && bucketForHashKey<1200001){
                        ExtendibleHashing.spHash.add(1400001+(int)((bucketForHashKey-1023)/(RecordSize.size/2)));
                    }
                }
//                System.out.println("Record added to existing bucket!!!");
//                System.out.println("Record "+record.getData()+" added to bucket "+bucketForHashKey);
//                System.out.println("data: "+ data + " , hashkey: "+hashkey);
                secondaryMemory.memory[bucketForHashKey].addRecords(record);
            }
            else if(hash==1){

                //        System.out.println("Overflow bucket entry called while rehashing");

                int finalBucketIndex=bucketForHashKey;
                Bucket bucket = secondaryMemory.memory[bucketForHashKey];
                while(!bucket.hasSpace() && !bucket.next.equals("")) {      //change made

                    finalBucketIndex=Integer.parseInt(bucket.next);
                    bucket = secondaryMemory.memory[Integer.parseInt(bucket.next)];

                }

                // ExtendibleHashing.spCost+=1;
                if (secondaryMemory.memory[finalBucketIndex].hasSpace()){

                    secondaryMemory.memory[finalBucketIndex].addRecords(record);
                    ExtendibleHashing.spHash.add(finalBucketIndex);
                    if(finalBucketIndex>1023 && finalBucketIndex<1200001){
                        ExtendibleHashing.spHash.add(1400001+(int)((finalBucketIndex-1023)/(RecordSize.size/2)));
                    }

//                    Bucket tempBucket=secondaryMemory.memory[finalBucketIndex];
//                    while(!tempBucket.getNext().equals("")){
//                        overflowTrack.add(Integer.valueOf(tempBucket.getNext()));
//                        tempBucket=secondaryMemory.memory[Integer.parseInt(tempBucket.getNext())];
//                    }
//                    secondaryMemory.memory[finalBucketIndex].setNext("");       //change made

                }
                else{
                    // add to overflow bucket
                    if(!overflowTrack.isEmpty()){

                        secondaryMemory.memory[finalBucketIndex].setNext(overflowTrack.get(0).toString());
                        int nextVal=Integer.parseInt(secondaryMemory.memory[finalBucketIndex].getNext());
                        secondaryMemory.memory[nextVal].addRecords(record);
                        ExtendibleHashing.spHash.add(nextVal);
                        if(nextVal>1023 && nextVal<1200001){
                            ExtendibleHashing.spHash.add(1400001+(int)((nextVal-1023)/(RecordSize.size/2)));
                        }

                        overflowTrack.remove(0);
                        //                  System.out.println("Overflow bucket added from arraylist!");

                    }
                    else {

                        secondaryMemory.memory[finalBucketIndex].setNext(String.valueOf(secondaryMemory.overflow));
                        int nextVal = Integer.parseInt(secondaryMemory.memory[finalBucketIndex].getNext());
                        secondaryMemory.memory[nextVal].addRecords(record);
                        ExtendibleHashing.spHash.add(nextVal);
                        if(nextVal>1023 && nextVal<1200001){
                            ExtendibleHashing.spHash.add(1400001+(int)((nextVal-1023)/(RecordSize.size/2)));
                        }

                        secondaryMemory.overflow++;
//                        System.out.println("Value added to overflow bucket!");
//                        System.out.println("Overflow bucket address: " + secondaryMemory.overflow);
                    }
                }

            }
            else{


//                if(!secondaryMemory.memory[bucketForHashKey].hasSpace() && hash==0){       //newly added for spcost only
//                    Bucket tbucket=secondaryMemory.memory[bucketForHashKey];
//                    while(!tbucket.next.equals("")) {
//                        spCost+=1;
//                        tbucket = secondaryMemory.memory[Integer.parseInt(tbucket.next)];
//                    }
//                    spCost+=1;
//                }


                //global depth >= 10
                // rehashing start
                int lengthOfMainMemory = (int)Math.pow(2,globalDepth+1);
                int lengthOfMainMemoryBefore = (int)Math.pow(2,globalDepth);

                globalDepth+=1;

                int countBucketAddresses=0;

                for(int k=lengthOfMainMemoryBefore;k<lengthOfMainMemory;k++){

                    if(countBucketAddresses==RecordSize.size || countBucketAddresses==(RecordSize.size-1)){
                        countBucketAddresses=0;
                        secondaryMemory.memory[secondaryMemory.mainMemoryOverflow].setNext(String.valueOf(secondaryMemory.mainMemoryOverflow+1));
                        secondaryMemory.mainMemoryOverflow+=1;
                        secondaryMemory.memory[secondaryMemory.mainMemoryOverflow].setEmpty(RecordSize.size);
                    }

                    countBucketAddresses+=2;
                    Records recordToInsert=new Records();
                    recordToInsert.setData(k);
                    secondaryMemory.memory[secondaryMemory.mainMemoryOverflow].addRecords(recordToInsert);

                    Records recordToInsert2=new Records();
                    recordToInsert2.setData(k);

                    secondaryMemory.memory[secondaryMemory.mainMemoryOverflow].addRecords(recordToInsert2);

//                    spCost+=1;          //check whether this is correct as otherwise value will become very large

                    map.put(k,k);

                }


                //System.out.println("New entries in hash table inserted");

                for(int k=lengthOfMainMemoryBefore-1;k>=1;k--){

                    secondaryMemory.memory[map.get(2*k)].setRecords(secondaryMemory.memory[map.get(k)].getRecords());
                    secondaryMemory.memory[map.get(2*k)].setEmpty(secondaryMemory.memory[map.get(k)].getEmpty());
                    secondaryMemory.memory[map.get(2*k)].setNext(secondaryMemory.memory[map.get(k)].getNext());

                    secondaryMemory.memory[map.get(k)].setEmpty(RecordSize.size);
                    secondaryMemory.memory[map.get(k)].setNext("");

                }

                //              System.out.println("Buckets assigned to new locations !");

                for(int k=0;k<lengthOfMainMemory;k+=2){

                    Bucket bucketToBeRehashed=secondaryMemory.memory[map.get(k)];
                    int sizeOfBucket;
                    int index=k;

                    while(!bucketToBeRehashed.next.equals("")) {

                        Records[] recordsInBucket=bucketToBeRehashed.getRecords();
                        sizeOfBucket=bucketToBeRehashed.getSize();
                        secondaryMemory.memory[index].setEmpty(RecordSize.size);

                        for(int l=0;l<sizeOfBucket;l++){

//                            System.out.println("inside loop to insert during rehash");

                            insert(secondaryMemory,recordsInBucket[l], 1);
                        }

                        index = Integer.parseInt(bucketToBeRehashed.next);
                        bucketToBeRehashed = secondaryMemory.memory[Integer.parseInt(bucketToBeRehashed.next)];


                    }



                    Records[] recordsInBucket=bucketToBeRehashed.getRecords();
//                    System.out.println("Records in bucket "+map.get(k)+" : ");
//                    for(int x=0;x<bucketToBeRehashed.getSize();x++){
//                        System.out.println("Record "+x+" = "+recordsInBucket[x].getData());
//                    }

                    sizeOfBucket=bucketToBeRehashed.getSize();
                    secondaryMemory.memory[index].setEmpty(RecordSize.size);


                    //System.out.println("before loop to insert during rehash, --- size of bucket: "+secondaryMemory.memory[map.get(k)].getSize());
                    // System.out.println("Bucket index: "+map.get(k));
                    for(int l=0;l<sizeOfBucket;l++){
                        //  System.out.println("inside loop to insert during rehash for data: "+recordsInBucket[l].getData());
                        insert(secondaryMemory,recordsInBucket[l], 1);


                    }


                    //commented on trial basis
//                    if(!secondaryMemory.memory[map.get(k)].getNext().equals("")){
//                        int tempVarIndex=Integer.parseInt(secondaryMemory.memory[map.get(k)].getNext());
//                        if(secondaryMemory.memory[tempVarIndex].getSize()==0){
//
//                            Bucket tempBucket=secondaryMemory.memory[map.get(k)];
//                            while(!tempBucket.getNext().equals("")){
//                                overflowTrack.add(Integer.valueOf(tempBucket.getNext()));
//                                tempBucket=secondaryMemory.memory[Integer.parseInt(tempBucket.getNext())];
//                            }
//
//                            secondaryMemory.memory[map.get(k)].setNext("");
//                        }
//                    }

                    Bucket tempBucket=secondaryMemory.memory[map.get(k)];
                    int flag=0;
                    ArrayList<Integer> tempArrIndex=new ArrayList<Integer>();
                    int index1=k;
                    while(!tempBucket.next.equals("")){
                        flag=1;

                        index1=Integer.parseInt(tempBucket.getNext());
                        tempBucket=secondaryMemory.memory[index1];
                        if(tempBucket.getSize()==0){
                            overflowTrack.add(index1);
                            tempArrIndex.add(index1);
                        }
                    }
                    if(!secondaryMemory.memory[map.get(k)].getNext().equals("")){
                        int tempInt=Integer.parseInt(secondaryMemory.memory[map.get(k)].getNext());
                        if(secondaryMemory.memory[tempInt].getSize()==0){
                            secondaryMemory.memory[map.get(k)].setNext("");
                        }
                    }
//                    if(flag==1){
//                        if(tempBucket.getSize()==0){
//                            overflowTrack.add(index1);
//                            tempArrIndex.add(index1);
//                        }
//                    }
                    for(int y=0;y<tempArrIndex.size();y++){
                        secondaryMemory.memory[tempArrIndex.get(y)].setNext("");
                    }

                }


                //rehash end
                // else for depth>=10
                //int noOfBitsModified=((int) (Math.log(data) / Math.log(2)))+1;
                int noOfBitsModified=bits;
                int hashKeyModified;
                if(noOfBitsModified>=globalDepth)
                    hashKeyModified= (data >>> (noOfBitsModified - globalDepth))&((int)(Math.pow(2,globalDepth)-1));
                else
                    hashKeyModified=data & ((int)(Math.pow(2,globalDepth)-1));

                //int hashKeyModified=data >>> (32 - globalDepth);
                int bucketForHashKeyModified = map.get(hashKeyModified);

                if(hash==0){
                    glhash=hashKeyModified;
                }

                int finalBucketIndex=bucketForHashKeyModified;

                Bucket bucket = secondaryMemory.memory[bucketForHashKeyModified];
                while(!bucket.hasSpace() && !bucket.next.equals("")) {          //change made

                    finalBucketIndex=Integer.parseInt(bucket.next);
                    bucket = secondaryMemory.memory[Integer.parseInt(bucket.next)];
                }

                if (secondaryMemory.memory[finalBucketIndex].hasSpace()){
                    // ExtendibleHashing.spCost+=1;

//                    System.out.println("Record added to bucket after rehash for globaldepth > 0!!!");
//                    System.out.println("Record: "+record.getData()+" added to bucket "+finalBucketIndex);
                    secondaryMemory.memory[finalBucketIndex].addRecords(record);
                    ExtendibleHashing.spHash.add(finalBucketIndex);
                    if(finalBucketIndex>1023 && finalBucketIndex<1200001){
                        ExtendibleHashing.spHash.add(1400001+(int)((finalBucketIndex-1023)/(RecordSize.size/2)));
                    }

//                    Bucket tempBucket=secondaryMemory.memory[finalBucketIndex];
//                    while(!tempBucket.getNext().equals("")){
//                        overflowTrack.add(Integer.valueOf(tempBucket.getNext()));
//                        tempBucket=secondaryMemory.memory[Integer.parseInt(tempBucket.getNext())];
//                    }
//
//                    secondaryMemory.memory[finalBucketIndex].setNext("");       //change made
                }
                else{
                    // add to overflow bucket

                    if(!overflowTrack.isEmpty()){

                        //ExtendibleHashing.spCost+=1;

                        secondaryMemory.memory[finalBucketIndex].setNext(overflowTrack.get(0).toString());
                        int nextVal=Integer.parseInt(secondaryMemory.memory[finalBucketIndex].getNext());
                        secondaryMemory.memory[nextVal].addRecords(record);

                        ExtendibleHashing.spHash.add(nextVal);
                        if(nextVal>1023 && nextVal<1200001){
                            ExtendibleHashing.spHash.add(1400001+(int)((nextVal-1023)/(RecordSize.size/2)));
                        }

                        overflowTrack.remove(0);
                        //      System.out.println("Overflow bucket added from arraylist!");

                    }
                    else {

                        //ExtendibleHashing.spCost+=1;

                        secondaryMemory.memory[finalBucketIndex].setNext(String.valueOf(secondaryMemory.overflow));
                        int nextVal = Integer.parseInt(secondaryMemory.memory[finalBucketIndex].getNext());
                        secondaryMemory.memory[nextVal].addRecords(record);

                        ExtendibleHashing.spHash.add(nextVal);
                        if(nextVal>1023 && nextVal<1200001){
                            ExtendibleHashing.spHash.add(1400001+(int)((nextVal-1023)/(RecordSize.size/2)));
                        }

                        secondaryMemory.overflow++;
//                        System.out.println("Value added to overflow bucket!");
//                        System.out.println("Overflow bucket address: " + secondaryMemory.overflow);
                    }

                }



            }


        }
        else{

            //globaldepth<10
            //System.out.println("After other entries rehash");

            //int noOfBits=((int) (Math.log(data) / Math.log(2)))+1;
            int noOfBits=bits;
            int hashkey;
            if(noOfBits>=globalDepth)
                hashkey = (data >>> (noOfBits - globalDepth))&((int)(Math.pow(2,globalDepth)-1));
            else
                hashkey=data & ((int)(Math.pow(2,globalDepth)-1));

            //System.out.println("noofbits: "+noOfBits);
            int bucketForHashKey = map.get(hashkey);

            if(hash==0){
                glhash=hashkey;
            }

            //System.out.println("bucket: "+bucketForHashKey);
            // System.out.println("Size: "+secondaryMemory.memory[bucketForHashKey].getSize());
            if (secondaryMemory.memory[bucketForHashKey].hasSpace()) {
//                System.out.println("Record added to existing bucket in gd<10!!!");
//                System.out.println("Record "+record.getData()+" added to bucket "+bucketForHashKey);
//                System.out.println("data: "+ data + " , hashkey: "+hashkey);
                if(hash==1){
                    //spCost+=1;
                    ExtendibleHashing.spHash.add(bucketForHashKey);
                    if(bucketForHashKey>1023 && bucketForHashKey<1200001){
                        ExtendibleHashing.spHash.add(1400001+(int)((bucketForHashKey-1023)/(RecordSize.size/2)));
                    }
                }
                secondaryMemory.memory[bucketForHashKey].addRecords(record);
            }

            else if(hash==1){

                //System.out.println("here");

                int finalBucketIndex=bucketForHashKey;
                Bucket bucket = secondaryMemory.memory[bucketForHashKey];
                while(!bucket.hasSpace() && !bucket.next.equals("")) {      //change made
                    //System.out.println("kj");
                    finalBucketIndex=Integer.parseInt(bucket.next);
                    //System.out.println(finalBucketIndex);
                    bucket = secondaryMemory.memory[Integer.parseInt(bucket.next)];

                }

//                if(!secondaryMemory.memory[bucketForHashKey].getNext().equals("")){
//                    int tempVarIndex=Integer.parseInt(secondaryMemory.memory[bucketForHashKey].getNext());
//                    if(secondaryMemory.memory[tempVarIndex].getSize()==0)
//                        secondaryMemory.memory[bucketForHashKey].setNext("");
//                }

                //System.out.println("before: "+ExtendibleHashing.spCost);

                //ExtendibleHashing.spCost+=1;
                //System.out.println("here: "+ExtendibleHashing.spCost);

                if (secondaryMemory.memory[finalBucketIndex].hasSpace()){

                    secondaryMemory.memory[finalBucketIndex].addRecords(record);
                    ExtendibleHashing.spHash.add(finalBucketIndex);

                    if(finalBucketIndex>1023 && finalBucketIndex<1200001){
                        ExtendibleHashing.spHash.add(1400001+(int)((finalBucketIndex-1023)/(RecordSize.size/2)));
                    }
//                    Bucket tempBucket=secondaryMemory.memory[finalBucketIndex];
//                    while(!tempBucket.getNext().equals("")){
//                        overflowTrack.add(Integer.valueOf(tempBucket.getNext()));
//                        tempBucket=secondaryMemory.memory[Integer.parseInt(tempBucket.getNext())];
//                    }
//                    secondaryMemory.memory[finalBucketIndex].setNext("");       //change made
                    //System.out.println("PROBLEM HERE!!");

                }
                else{
                    // add to overflow bucket
                    if(!overflowTrack.isEmpty()){

                        secondaryMemory.memory[finalBucketIndex].setNext(overflowTrack.get(0).toString());
                        int nextVal=Integer.parseInt(secondaryMemory.memory[finalBucketIndex].getNext());
                        secondaryMemory.memory[nextVal].addRecords(record);

                        ExtendibleHashing.spHash.add(nextVal);
                        if(nextVal>1023 && nextVal<1200001){
                            ExtendibleHashing.spHash.add(1400001+(int)((nextVal-1023)/(RecordSize.size/2)));
                        }

                        overflowTrack.remove(0);
                        //System.out.println("Overflow bucket added from arraylist!");

                    }
                    else {

                        secondaryMemory.memory[finalBucketIndex].setNext(String.valueOf(secondaryMemory.overflow));
                        int nextVal = Integer.parseInt(secondaryMemory.memory[finalBucketIndex].getNext());
                        secondaryMemory.memory[nextVal].addRecords(record);
                        secondaryMemory.overflow++;

                        ExtendibleHashing.spHash.add(nextVal);
                        if(nextVal>1023 && nextVal<1200001){
                            ExtendibleHashing.spHash.add(1400001+(int)((nextVal-1023)/(RecordSize.size/2)));
                        }

//                        System.out.println("Value added to overflow bucket!");
//                        System.out.println("Overflow bucket address: " + secondaryMemory.overflow);
                    }
                }



            } else {



//                if(!secondaryMemory.memory[bucketForHashKey].hasSpace() && hash==0){       //newly added for spcost only
//                    Bucket tbucket=secondaryMemory.memory[bucketForHashKey];
//                    while(!tbucket.next.equals("")) {
//                        spCost+=1;
//                        tbucket = secondaryMemory.memory[Integer.parseInt(tbucket.next)];
//                    }
//                    spCost+=1;
//                }


                // rehashing start
                int lengthOfMainMemory=(int)Math.pow(2,globalDepth+1);
                int lengthOfMainMemoryBefore=(int)Math.pow(2,globalDepth);

                globalDepth+=1;

                for(int k=lengthOfMainMemoryBefore;k<lengthOfMainMemory;k++){
                    map.put(k,k);
                }

                //          System.out.println("New entries in hash table inserted");


                for(int k=lengthOfMainMemoryBefore-1;k>=1;k--){

                    secondaryMemory.memory[map.get(2*k)].setRecords(secondaryMemory.memory[map.get(k)].getRecords());
                    secondaryMemory.memory[map.get(2*k)].setEmpty(secondaryMemory.memory[map.get(k)].getEmpty());
                    secondaryMemory.memory[map.get(2*k)].setNext(secondaryMemory.memory[map.get(k)].getNext());

                    secondaryMemory.memory[map.get(k)].setEmpty(RecordSize.size);
                    secondaryMemory.memory[map.get(k)].setNext("");

                }

//                printHash(secondaryMemory);
//                System.out.println("\nEND OF PRINT HASH----------------------\n");

                //            System.out.println("Buckets assigned to new locations !");

                for(int k=0;k<lengthOfMainMemory;k+=2){

                    Bucket bucketToBeRehashed=secondaryMemory.memory[map.get(k)];
                    int sizeOfBucket;
                    int index=k;

                    while(!bucketToBeRehashed.next.equals("")) {
                        //System.out.println("again");
                        Records[] recordsInBucket=bucketToBeRehashed.getRecords();
                        sizeOfBucket=bucketToBeRehashed.getSize();
                        //System.out.println("again1");
                        secondaryMemory.memory[index].setEmpty(RecordSize.size);
                        //System.out.println("again2   size : "+sizeOfBucket);
                        for(int l=0;l<sizeOfBucket;l++){

                            //                          System.out.println("inside loop to insert during rehash");
                            //     System.out.println(recordsInBucket[l].getData());
                            insert(secondaryMemory,recordsInBucket[l], 1);

                        }
//                        System.out.println("Bucket to be rehashed");
//                        System.out.println(bucketToBeRehashed.next);
                        index = Integer.parseInt(bucketToBeRehashed.next);
                        //  System.out.println("index: "+index);
                        bucketToBeRehashed = secondaryMemory.memory[Integer.parseInt(bucketToBeRehashed.next)];

                    }


                    Records[] recordsInBucket=bucketToBeRehashed.getRecords();
//                    System.out.println("Records in bucket "+map.get(k)+" : ");
//                    for(int x=0;x<bucketToBeRehashed.getSize();x++){
//                        System.out.println("Record "+x+" = "+recordsInBucket[x].getData());
//                    }

                    sizeOfBucket=bucketToBeRehashed.getSize();
                    secondaryMemory.memory[index].setEmpty(RecordSize.size);


//                    System.out.println("before loop to insert during rehash, --- size of bucket: "+secondaryMemory.memory[map.get(k)].getSize());
                    // System.out.println("Bucket index: "+map.get(k));
                    for(int l=0;l<sizeOfBucket;l++){
//                        System.out.println("inside loop to insert during rehash for data: "+recordsInBucket[l].getData());
                        insert(secondaryMemory,recordsInBucket[l], 1);

                    }


                    // commented on trial basis

//                    if(!secondaryMemory.memory[map.get(k)].getNext().equals("")){
//                        int tempVarIndex=Integer.parseInt(secondaryMemory.memory[map.get(k)].getNext());
//                        if(secondaryMemory.memory[tempVarIndex].getSize()==0){
//
//                            Bucket tempBucket=secondaryMemory.memory[map.get(k)];
//                            while(!tempBucket.getNext().equals("")){
//                                overflowTrack.add(Integer.valueOf(tempBucket.getNext()));
//                                tempBucket=secondaryMemory.memory[Integer.parseInt(tempBucket.getNext())];
//                            }
//
//                            secondaryMemory.memory[map.get(k)].setNext("");
//                        }
//                    }


                    Bucket tempBucket=secondaryMemory.memory[map.get(k)];
                    int flag=0;
                    ArrayList<Integer> tempArrIndex=new ArrayList<Integer>();
                    int index1=k;
                    while(!tempBucket.next.equals("")){
                        flag=1;
                        //System.out.println("hhe");
                        index1=Integer.parseInt(tempBucket.getNext());
                        tempBucket=secondaryMemory.memory[index1];
                        if(tempBucket.getSize()==0){
                            overflowTrack.add(index1);
                            tempArrIndex.add(index1);
                        }
                    }

                    if(!secondaryMemory.memory[map.get(k)].getNext().equals("")){
                        int tempInt=Integer.parseInt(secondaryMemory.memory[map.get(k)].getNext());
                        if(secondaryMemory.memory[tempInt].getSize()==0){
                            secondaryMemory.memory[map.get(k)].setNext("");
                        }
                    }
//                    if(flag==1){
//                            if(tempBucket.getSize()==0){
//                                overflowTrack.add(index1);
//                                tempArrIndex.add(index1);
//                            }
//                    }
                    for(int y=0;y<tempArrIndex.size();y++){
                        secondaryMemory.memory[tempArrIndex.get(y)].setNext("");
                    }


                }

                //System.out.println("outside the k loop");

                //rehashing end
                //int noOfBitsModified=((int) (Math.log(data) / Math.log(2)))+1;
                int noOfBitsModified=bits;
                int hashKeyModified;
                if(noOfBitsModified>=globalDepth)
                    hashKeyModified= (data >>> (noOfBitsModified - globalDepth))&((int)(Math.pow(2,globalDepth)-1));
                else
                    hashKeyModified=data & ((int)(Math.pow(2,globalDepth)-1));

                //int hashKeyModified=data >>> (32 - globalDepth);
                int bucketForHashKeyModified = map.get(hashKeyModified);

                if(hash==0){
                    glhash=hashKeyModified;
                }

                int finalBucketIndex=bucketForHashKeyModified;

                Bucket bucket = secondaryMemory.memory[bucketForHashKeyModified];
                while(!bucket.hasSpace() && !bucket.next.equals("")) {          //change made

                    finalBucketIndex=Integer.parseInt(bucket.next);
                    bucket = secondaryMemory.memory[Integer.parseInt(bucket.next)];
                }


                if (secondaryMemory.memory[finalBucketIndex].hasSpace()){

                    //ExtendibleHashing.spCost+=1;
                    ExtendibleHashing.spHash.add(finalBucketIndex);
                    if(finalBucketIndex>1023 && finalBucketIndex<1200001){
                        ExtendibleHashing.spHash.add(1400001+(int)((finalBucketIndex-1023)/(RecordSize.size/2)));
                    }

//                    System.out.println("Record added to bucket after rehash for globaldepth > 0!!!");
//                    System.out.println("Record: "+record.getData()+" added to bucket "+finalBucketIndex);
                    secondaryMemory.memory[finalBucketIndex].addRecords(record);

//                    Bucket tempBucket=secondaryMemory.memory[finalBucketIndex];
//                    while(!tempBucket.getNext().equals("")){
//                        overflowTrack.add(Integer.valueOf(tempBucket.getNext()));
//                        tempBucket=secondaryMemory.memory[Integer.parseInt(tempBucket.getNext())];
//                    }

//                    secondaryMemory.memory[finalBucketIndex].setNext("");       //change made
                }
                else{
                    // add to overflow bucket

                    if(!overflowTrack.isEmpty()){
                        //ExtendibleHashing.spCost+=1;
                        secondaryMemory.memory[finalBucketIndex].setNext(overflowTrack.get(0).toString());
                        int nextVal=Integer.parseInt(secondaryMemory.memory[finalBucketIndex].getNext());
                        secondaryMemory.memory[nextVal].addRecords(record);

                        if(nextVal>1023 && nextVal<1200001){
                            ExtendibleHashing.spHash.add(1400001+(int)((nextVal-1023)/(RecordSize.size/2)));
                        }
                        ExtendibleHashing.spHash.add(nextVal);

                        overflowTrack.remove(0);
//                        System.out.println("Overflow bucket added from arraylist!");

                    }
                    else {
                        //ExtendibleHashing.spCost+=1;
                        secondaryMemory.memory[finalBucketIndex].setNext(String.valueOf(secondaryMemory.overflow));
                        int nextVal = Integer.parseInt(secondaryMemory.memory[finalBucketIndex].getNext());
                        secondaryMemory.memory[nextVal].addRecords(record);

                        ExtendibleHashing.spHash.add(nextVal);
                        if(nextVal>1023 && nextVal<1200001){
                            ExtendibleHashing.spHash.add(1400001+(int)((nextVal-1023)/(RecordSize.size/2)));
                        }

                        secondaryMemory.overflow++;
//                        System.out.println("Value added to overflow bucket!");
//                        System.out.println("Overflow bucket address: " + secondaryMemory.overflow);
                    }

                }

            }


        }
    }


    public void merge(SecondaryMemory secondaryMemory){

        //modify the buckets
        int bucketRange=(int)(Math.pow(2,globalDepth));
        //System.out.println("bucket Range:"+bucketRange);
        int counter=0;
        int j=0;
        bucketAddr=1200001;
        for(int i=0;i<bucketRange;){

            j=0;
            //System.out.println(i);
            int flag=0;
            secondaryMemory.memory[bucketAddr].setEmpty(RecordSize.size);

            while(true){
                counter=0;
                for(int l=0;l<(int)Math.pow(2,j);l++){
                    if(flag==2){
                        counter=RecordSize.size+1;
                        //System.out.println("Value of j:"+j);
                        continue;
                    }
                    if(i+l>=bucketRange){
                        flag=1;
                        for(int s=0;s<l;s++){
                            map.remove(i+s);
                            map.put(i+s,bucketAddr-1200001);
                            //secondaryMemory.memory[bucketAddr].addRecordArray(secondaryMemory.memory[i+s].getRecords());
                            for(int t=0;t<secondaryMemory.memory[i+s].getSize();t++){
                                secondaryMemory.memory[bucketAddr].addRecords(secondaryMemory.memory[i+s].getRecords()[t]);
                            }

                        }
                        bucketAddr+=1;
                        break;
                    }

                    if(secondaryMemory.memory[i+l].getSize()==RecordSize.size && !secondaryMemory.memory[i+l].getNext().equals("")){
                        flag=2;
                        if(j==0){
                            flag=3;
                            //System.out.println("Value of j inside flag=2 chain part: "+j+" flag: "+flag);

                            map.remove(i+l);
                            map.put(i+l,bucketAddr-1200001);

                            secondaryMemory.memory[bucketAddr].setEmpty(secondaryMemory.memory[i+l].getEmpty());
                            secondaryMemory.memory[bucketAddr].setNext(secondaryMemory.memory[i+l].getNext());
                            secondaryMemory.memory[bucketAddr].setRecords(secondaryMemory.memory[i+l].getRecords());

                            //i++;
                            bucketAddr+=1;
                            break;
                        }
                        j--;
                        //System.out.println("Value of j inside flag=2 chain part: "+j+" flag: "+flag);
                        continue;

//                        secondaryMemory.memory[bucketAddr].setEmpty(secondaryMemory.memory[i+l].getEmpty());
//                        secondaryMemory.memory[bucketAddr].setNext(secondaryMemory.memory[i+l].getNext());
//                        secondaryMemory.memory[bucketAddr].setRecords(secondaryMemory.memory[i+l].getRecords());
//                        System.out.println("Inside add records ");
//                        System.out.println("size of bucketaddr: "+secondaryMemory.memory[bucketAddr].getSize());
//
//                        map.remove(i+l);
//                        map.put(i+l,bucketAddr-1200001);
//
//                        bucketAddr+=1;
//                        break;
                    }

                    counter=counter+secondaryMemory.memory[map.get(i+l)].getSize();
                    // System.out.println("Value of counter: "+counter);
                    if(counter>RecordSize.size){
                        break;
                    }


                }
                if(flag==1 || flag==3){
                    break;
                }

                if(counter>RecordSize.size){
                    //if(flag!=2)
                    j-=1;
//                    if(flag==2){
//                        j--;
//                    }

                    //System.out.println("for j:"+j+" and for i:"+i+" records added to temp bucket: "+bucketAddr);

                    for(int l=0;l<(int)Math.pow(2,j);l++){
                        //secondaryMemory.memory[bucketAddr].addRecordArray(secondaryMemory.memory[i+l].getRecords());
                        for(int s=0;s<secondaryMemory.memory[i+l].getSize();s++){
                            secondaryMemory.memory[bucketAddr].addRecords(secondaryMemory.memory[i+l].getRecords()[s]);
//                            System.out.println("Inside add records ");
//                            System.out.println("size of bucketaddr: "+secondaryMemory.memory[bucketAddr].getSize());

                        }

                        map.remove(i+l);
                        map.put(i+l,bucketAddr-1200001);
                        //System.out.println("Changed hash map ---- key:"+(int)(i+l)+"  Value: "+(int)(bucketAddr-1200001));


                    }
                    bucketAddr+=1;
                    break;
                }
                else{
                    j++;
                }

            }
            if(flag==1){

                break;
            }
//            if(flag==3){
//                i++;
//
//            }
//            if(flag==2){
//                break;
//            }
            i=i+(int)(Math.pow(2,j));

        }

        int counter1=-1;
        int bucketIndex=0;
        for(int i=1024;i<bucketRange;i++){
//            for(int k=1;k<RecordSize.size;k+=2){
//
//            }

            int val=map.get(i);
            counter1+=2;
            if(counter1>=RecordSize.size){
                bucketIndex+=1;
                counter1=1;
            }
            Records[] tempBucketRecords=secondaryMemory.memory[1400001+bucketIndex].getRecords();
            Records recordInserted=new Records();
            recordInserted.setData(val);
            tempBucketRecords[counter1]=recordInserted;

            secondaryMemory.memory[1400001+bucketIndex].setRecords(tempBucketRecords);

        }


        for(int i=0;i<(bucketAddr-1200001);i++){

            secondaryMemory.memory[i].setEmpty(secondaryMemory.memory[1200001+i].getEmpty());
            secondaryMemory.memory[i].setNext(secondaryMemory.memory[1200001+i].getNext());
            secondaryMemory.memory[i].setRecords(secondaryMemory.memory[1200001+i].getRecords());
//            System.out.println("Inside the copy portion ");
//            System.out.println("size of bucketaddr: "+secondaryMemory.memory[i].getSize());

        }


    }



    public void merge1(SecondaryMemory secondaryMemory){

        int numBuckets=0;
        //modify the buckets
        int bucketRange=(int)(Math.pow(2,globalDepth));
        //System.out.println("bucket Range:"+bucketRange);
        int counter=0;
        int j=0;
        bucketAddr=1200001;
        for(int i=0;i<bucketRange;){

            j=0;
            //System.out.println(i);
            int flag=0;
            secondaryMemory.memory[bucketAddr].setEmpty(RecordSize.size);
            secondaryMemory.memory[bucketAddr].setNext("");

            while(true){
                //System.out.println("again1");
                counter=0;
                for(int l=0;l<(int)Math.pow(2,j);l++){
                    if(flag==2){
                        counter=RecordSize.size+1;
                        //System.out.println("again2");
                        //System.out.println("Value of j:"+j);
                        continue;
                    }
                    if(i+l>=bucketRange){
                        flag=1;
                        for(int s=0;s<l;s++){
//                            map.remove(i+s);
//                            map.put(i+s,bucketAddr-1200001);
                            //secondaryMemory.memory[bucketAddr].addRecordArray(secondaryMemory.memory[i+s].getRecords());
                            //System.out.println("again3");
                            for(int t=0;t<secondaryMemory.memory[i+s].getSize();t++){
                                secondaryMemory.memory[bucketAddr].addRecords(secondaryMemory.memory[i+s].getRecords()[t]);
                            }

                        }
                        bucketAddr+=1;
                        break;
                    }

                    if(secondaryMemory.memory[i+l].getSize()==RecordSize.size && !secondaryMemory.memory[i+l].getNext().equals("")){
                        flag=2;
                        //System.out.println("again3");

                        if(j==0){
                            flag=3;
                            //System.out.println("Value of j inside flag=2 chain part: "+j+" flag: "+flag);

//                            map.remove(i+l);
//                            map.put(i+l,bucketAddr-1200001);
//                            set.add(bucketAddr-1200001);

                            secondaryMemory.memory[bucketAddr].setEmpty(secondaryMemory.memory[i+l].getEmpty());
                            secondaryMemory.memory[bucketAddr].setNext(secondaryMemory.memory[i+l].getNext());
                            secondaryMemory.memory[bucketAddr].setRecords(secondaryMemory.memory[i+l].getRecords());

                            //i++;
                            bucketAddr+=1;
                            break;
                        }
                        j--;
                        //System.out.println("again4");
                        //System.out.println("Value of j inside flag=2 chain part: "+j+" flag: "+flag);
                        continue;

//                        secondaryMemory.memory[bucketAddr].setEmpty(secondaryMemory.memory[i+l].getEmpty());
//                        secondaryMemory.memory[bucketAddr].setNext(secondaryMemory.memory[i+l].getNext());
//                        secondaryMemory.memory[bucketAddr].setRecords(secondaryMemory.memory[i+l].getRecords());
//                        System.out.println("Inside add records ");
//                        System.out.println("size of bucketaddr: "+secondaryMemory.memory[bucketAddr].getSize());
//
//                        map.remove(i+l);
//                        map.put(i+l,bucketAddr-1200001);
//
//                        bucketAddr+=1;
//                        break;
                    }

                    counter=counter+secondaryMemory.memory[i+l].getSize();
                    // System.out.println("Value of counter: "+counter);
                    if(counter>RecordSize.size){
                        break;
                    }


                }
                if(flag==1 || flag==3){
                    break;
                }

                if(counter>RecordSize.size){
                    //if(flag!=2)
                    j-=1;
//                    if(flag==2){
//                        j--;
//                    }

                    //System.out.println("for j:"+j+" and for i:"+i+" records added to temp bucket: "+bucketAddr);

                    for(int l=0;l<(int)Math.pow(2,j);l++){
                        //secondaryMemory.memory[bucketAddr].addRecordArray(secondaryMemory.memory[i+l].getRecords());
//                        for(int s=0;s<secondaryMemory.memory[i+l].getSize();s++){
//                            secondaryMemory.memory[bucketAddr].addRecords(secondaryMemory.memory[i+l].getRecords()[s]);
//                            System.out.println("Inside add records ");
//                            System.out.println("size of bucketaddr: "+secondaryMemory.memory[bucketAddr].getSize());

                        //}

//                        map.remove(i+l);
//                        map.put(i+l,bucketAddr-1200001);
                        //System.out.println("Changed hash map ---- key:"+(int)(i+l)+"  Value: "+(int)(bucketAddr-1200001));

                    }
                    bucketAddr+=1;
                    break;
                }
                else{
                    j++;
                }

            }
            if(flag==1){

                break;
            }
//            if(flag==3){
//                i++;
//
//            }
//            if(flag==2){
//                break;
//            }
            i=i+(int)(Math.pow(2,j));

        }



    }


    public void printHash(SecondaryMemory secondaryMemory){

        merge(secondaryMemory);

        int firstRange;
        if(globalDepth>=10){
            firstRange=1024;
        }
        else {
            firstRange=(int)(Math.pow(2,globalDepth));
        }

        System.out.println("THE HASH TABLE:\n");

        for(int k=0;k<firstRange;k++) {

            System.out.println("Key : "+k);
            Bucket bucketToBeRehashed = secondaryMemory.memory[map.get(k)];

            int sizeOfBucket;
            int indexOfBucket=map.get(k);

            while (!bucketToBeRehashed.next.equals("")) {
                System.out.println("        For bucket index : "+indexOfBucket+"   with size: "+bucketToBeRehashed.getSize());
                Records[] recordsInBucket = bucketToBeRehashed.getRecords();
                sizeOfBucket = bucketToBeRehashed.getSize();

                for (int l = 0; l < sizeOfBucket; l++) {
                    System.out.println("                    Record Values : "+recordsInBucket[l].getData());
                }
                indexOfBucket=Integer.parseInt(bucketToBeRehashed.next);
                bucketToBeRehashed = secondaryMemory.memory[Integer.parseInt(bucketToBeRehashed.next)];
            }

            //final buckett in chaining
            System.out.println("        For bucket index : "+indexOfBucket+"   with size: "+bucketToBeRehashed.getSize());

            Records[] recordsInBucket = bucketToBeRehashed.getRecords();
            sizeOfBucket = bucketToBeRehashed.getSize();

            for (int l = 0; l < sizeOfBucket; l++) {
                System.out.println("                    Record Values : "+recordsInBucket[l].getData());
            }

            System.out.println("");

        }



        if(secondaryMemory.mainMemoryOverflow>1400001){



            System.out.println("Portion of buckets in secondary memory");
            for(int i=1400001;i<secondaryMemory.mainMemoryOverflow;i++) {

                for (int k = 1; k < secondaryMemory.memory[i].getSize(); k += 2) {
                    System.out.println("Key : " + secondaryMemory.memory[i].getRecords()[k-1].getData());

                    int sizeOfBucket;
                    int indexOfBucket = secondaryMemory.memory[i].getRecords()[k].getData();

                    Bucket bucketToBeRehashed = secondaryMemory.memory[indexOfBucket];

                    while (!bucketToBeRehashed.next.equals("")) {
                        System.out.println("        For bucket index : " + indexOfBucket + "   with size: " + bucketToBeRehashed.getSize());
                        Records[] recordsInBucket = bucketToBeRehashed.getRecords();
                        sizeOfBucket = bucketToBeRehashed.getSize();

                        for (int l = 0; l < sizeOfBucket; l++) {
                            System.out.println("                    Record Values : " + recordsInBucket[l].getData());
                        }
                        indexOfBucket = Integer.parseInt(bucketToBeRehashed.next);
                        bucketToBeRehashed = secondaryMemory.memory[Integer.parseInt(bucketToBeRehashed.next)];
                    }

                    //final buckett in chaining
                    System.out.println("        For bucket index : " + indexOfBucket + "   with size: " + bucketToBeRehashed.getSize());

                    Records[] recordsInBucket = bucketToBeRehashed.getRecords();
                    sizeOfBucket = bucketToBeRehashed.getSize();

                    for (int l = 0; l < sizeOfBucket; l++) {
                        System.out.println("                    Record Values : " + recordsInBucket[l].getData());
                    }

                    System.out.println("");

                }
            }

        }

    }


    float storageUtil(SecondaryMemory secondaryMemory,int N){
        int B=secondaryMemory.overflow-overflowTrack.size()-1600001 + secondaryMemory.mainMemoryOverflow-1400001 ;
        //System.out.println("overflow track size: "+overflowTrack.size());
//        for(int i=0;i<overflowTrack.size();i++){
//            System.out.println(overflowTrack.get(i));
//        }

        B+=(bucketAddr-1200001);
        int bucketRange=(int)(Math.pow(2,globalDepth));
        int counter=0;
        int j=0;

        System.out.println("Number of buckets: "+B);
        int temp=secondaryMemory.overflow-overflowTrack.size()-1600001;
        System.out.println("Number of buckets for overflow : "+temp);

        temp=secondaryMemory.mainMemoryOverflow-1400001;
        System.out.println("Number of buckets for main memory : "+temp);

//        for(int i=1400001;i<secondaryMemory.mainMemoryOverflow;i++){
//            System.out.println(secondaryMemory.memory[i].getSize());
//        }
        int x=B-(secondaryMemory.mainMemoryOverflow-1400001);
        return (float)N/(x*RecordSize.size);


    }


    float storageUtil1(SecondaryMemory secondaryMemory,int N){
        int B=secondaryMemory.overflow-overflowTrack.size()-1600001 + secondaryMemory.mainMemoryOverflow-1400001 ;
        //System.out.println("overflow track size: "+overflowTrack.size());
//        for(int i=0;i<overflowTrack.size();i++){
//            System.out.println(overflowTrack.get(i));
//        }

        B+=(bucketAddr-1200001);
        int bucketRange=(int)(Math.pow(2,globalDepth));
        int counter=0;
        int j=0;

        //System.out.println("Number of buckets: "+B);
        int temp=secondaryMemory.overflow-overflowTrack.size()-1600001;
        //System.out.println("Number of buckets for overflow : "+temp);

        temp=secondaryMemory.mainMemoryOverflow-1400001;
        //System.out.println("Number of buckets for main memory : "+temp);

//        for(int i=1400001;i<secondaryMemory.mainMemoryOverflow;i++){
//            System.out.println(secondaryMemory.memory[i].getSize());
//        }
        int x=B-(secondaryMemory.mainMemoryOverflow-1400001);
        return (float)B;


    }




    int costSearch(SecondaryMemory secondaryMemory,int element){
        int numBuckets=0;
        //int noOfBits=((int) (Math.log(element) / Math.log(2)))+1;
        int noOfBits=bits;
        int hashkey;

        if(noOfBits>=globalDepth)
            hashkey = (element >>> (noOfBits - globalDepth))&((int)(Math.pow(2,globalDepth)-1));
        else
            hashkey=element & ((int)(Math.pow(2,globalDepth)-1));

        if(hashkey>1023){
            numBuckets+=/*(int)(hashkey-1023)/(RecordSize.size/2)+*/1;
        }

        Bucket tbucket=secondaryMemory.memory[map.get(hashkey)];

        int flag=0;

        while(!tbucket.next.equals("")) {
            numBuckets+=1;
            Records[] tempRecords=tbucket.getRecords();

            for(int i=0;i<tbucket.getSize();i++){
                if(element==tempRecords[i].getData()){
                    flag=1;
                    break;
                }
            }
            if(flag==1){
                break;
            }
            tbucket = secondaryMemory.memory[Integer.parseInt(tbucket.next)];
        }

        if(flag==0){
            numBuckets+=1;
        }

        return numBuckets;
    }


}
