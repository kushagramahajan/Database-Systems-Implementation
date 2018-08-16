package Assignments.Assignment4;

import java.util.ArrayList;

/**
 * Created by kushagra on 4/3/2017.
 */
public class Layout {
    public static ArrayList<Blocks> tables;
    public Layout()
    {
        tables = new ArrayList<Blocks>();
        createTables();
    }
    private void createTables()
    {
        int total_size = Sizes.no_department+Sizes.no_employee+Sizes.no_project;
        for(int i=0;i<total_size;i++)
        {
            if(i<Sizes.no_employee)
            {
                Employee block = new Employee(i+"");
                tables.add(block);

            }
            else if(i<Sizes.no_employee+Sizes.no_department)
            {
                Department block = new Department(i+"");
                tables.add(block);

            }
            else if(i<Sizes.no_department+Sizes.no_project+Sizes.no_employee)
            {
                Project block = new Project(i+"");
                tables.add(block);

            }
        }

    }


}
