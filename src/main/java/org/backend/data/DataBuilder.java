package org.backend.data;

import org.backend.models.BaseMethodsForEmployeeAndPost;
import org.backend.models.Employee;
import org.backend.models.PostEmployee;



public interface DataBuilder extends BaseDataMethods {

    Employee[] parseEmployees(String data);

    PostEmployee[] parsePostEmployees(String data);

    String getSaveString(BaseMethodsForEmployeeAndPost[] postEmployees);




}
