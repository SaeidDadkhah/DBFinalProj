package common;

/**
 * Created by Saeid Dadkhah on 2016-06-22 8:45 PM.
 * Project: Server
 */
public class Constants {

    // DB stands for Database
    public static final String DB_NAME = "DBFinal";

    // SN stands for Server Network
    public static final int SN_PORT = 6673;
    public static final String SN_NAME = "127.0.0.1";

    // C stands for Collection
    public static final String C_USERS = "users";

    // RQ stands for Request
    public static final int R_DISCONNECT = -1;
    public static final int R_SIGNUP = 1;
    public static final int R_LOGIN = 2;

    // RS stands for Request
    public static final int RS_SUCCESSFUL_SIGNUP = 1;
    public static final int RS_UNSUCCESSFUL_SIGNUP = 2;
    public static final int RS_SUCCESSFUL_LOGIN = 3;
    public static final int RS_UNSUCCESSFUL_LOGIN = 4;

    // F stands for Field
    public static final String F_USERNAME = "username";
    public static final String F_PASSWORD = "password";



}
