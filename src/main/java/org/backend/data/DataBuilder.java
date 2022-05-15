package org.backend.data;

import org.backend.employee.Employee;
import org.backend.employee.PostEmployee;



public interface DataBuilder extends BaseDataMethods {

    Employee[] parseEmployees(String data);

    PostEmployee[] parsePostEmployees(String data);

    String getString(PostEmployee[] postEmployees);

    String getString(Employee[] postEmployees);



}
