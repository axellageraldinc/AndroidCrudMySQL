package com.example.axellageraldinc.crudmysql;

/**
 * Created by axellageraldinc on 9/11/17.
 */

public class ApiConnect {
    public static final String ipConfig = "http://10.72.4.248";
    public static final String ROOT_URL = ipConfig + "/PHP_MySQL/api.php?apicall=";

    public static final String ROOT_URL_CREATE = ROOT_URL + "create";
    public static final String ROOT_URL_READ = ROOT_URL + "getAll";
    public static final String ROOT_URL_UPDATE = ROOT_URL + "update";
    public static final String ROOT_URL_DELETE = ROOT_URL + "delete";
}
