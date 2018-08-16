package Assignments.Assignment2;

/**
 * Created by kushagra on 2/14/2017.
 */
public class Record {
    private Integer ID;
    private String name;
    private Integer sale;
    public Record(Integer id,String name,Integer sale)
    {
        this.ID = id;
        this.name = name;
        this.sale = sale;
    }
    public Integer getID()
    {
        return this.ID;
    }
    public String getName()
    {
        return this.name;
    }
    public Integer getSale()
    {
        return this.sale;
    }
    public void setID(Integer id)
    {
        this.ID = id;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setSale(Integer sale)
    {
        this.sale = sale;
    }





}
