package Assignments.Assignment4;

/**
 * Created by kushagra on 4/4/2017.
 */
public class Buffer {
    public String address;
    public int last_used;
    public Buffer(String address)
    {
        this.address = address;
        this.last_used = 0;
    }

}
