package org.backend.config;
//Replace in the future with a normal properties file
public class Config {
    public static final String REGEX_EMPLOYEE = "(?m)id:\\s(?<id>.+)" +
            "\\nfirstName:\\s(?<firstname>.+)" +
            "\\nlastName:\\s(?<lastname>.+)" +
            "\\ndescription:\\s(?<description>.+)" +
            "\\npostId:\\s(?<postid>.+)" +
            "\\ncharacteristics:\\s(?<charac>.+)" +
            "\\nimage:\\s(?<image>.+)";
    public static final String REGEX_POST = "(?m)(?<postId>[^:]+):\\s(?<postName>.+)";
    public static final String EMPLOYEE_FILE_NAME="employees.txt";
    public static final String POST_FILE_NAME="posts.txt";
    public static final String FILE_GLOB_EXTENSION = "glob:*.txt";
    public static final String DIRECTORY_GLOB_EXTENSION = "glob:*";
}
