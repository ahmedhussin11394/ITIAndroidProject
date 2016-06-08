package com.example.iti.sidemenumodule.network_manager;

/**
 * Created by ITI on 04/06/2016.
 */
public class URLManager {
    public static String ip="http://192.168.1.4:8084";
    public static String imageURL = "";
    public static String getCategoryURL=ip+"/itiProject/rest/categoryURL/getCategories";
    public static String getEmployeesURL=ip+"/itiProject/rest/user/getMaxUser";
    public static String getJobsURL=ip+"/itiProject/rest/project/getLastProject?footer=0";
    public static String postProjectURL=ip+"/itiProject/rest/project/Project";
    public static String getMyJobsURL=ip+"/itiProject/rest/project/projectsOfUserHire";
    public static String getWorkJobsURL=ip+"/itiProject/rest/project/projectsOfUserWork";
}



