package com.example.axellageraldinc.crudmysql;

/**
 * Created by axellageraldinc on 9/11/17.
 */

public class ApiConnect {
    private static final String ipConfig = "http://10.72.41.102";
    private static final String ROOT_URL = ipConfig + "/PHP_MySQL/api.php?apicall=";

    private static final String ROOT_URL_CREATE = ROOT_URL + "create";
    private static final String ROOT_URL_READ = ROOT_URL + "getAll";
    private static final String ROOT_URL_UPDATE = ROOT_URL + "update";
    private static final String ROOT_URL_DELETE = ROOT_URL + "delete";
}
