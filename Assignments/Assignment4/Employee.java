package Assignments.Assignment4;

/**
 * Created by kushagra on 4/3/2017.
 */
public class Employee extends Blocks {


    public Employee(String id)
    {
        super(id,"Employee");
        this.no_records = Sizes.no_employee/Sizes.employee_block;
    }


}
