package Table;

import java.io.Serializable;

public class Subsidy implements Serializable {
    public String SUBH;
    public Employee employee;
    public SubsidyInfo subsidyInfo;

    public String getSUBH() {
        return SUBH;
    }

    public String getName(){
        return employee.Name;
    }
}
