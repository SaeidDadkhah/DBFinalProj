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
    public static final String C_MESSAGES = "messages";

    // RQ stands for Request
    public static final String RQ_DISCONNECT = "disconnect";
    public static final String RQ_SIGNUP = "signup";
    public static final String RQ_LOGIN = "login";
    public static final String RQ_DELETE_ACCOUNT = "delete account";
    public static final String RQ_MESSAGING = "messaging";

    // RS stands for Request
    public static final String RS_SUCCESSFUL_SIGNUP = "successful signup";
    public static final String RS_UNSUCCESSFUL_SIGNUP = "unsuccessful signup";

    public static final String RS_SUCCESSFUL_DELETE_ACCOUNT = "successful delete account";
    public static final String RS_UNSUCCESSFUL_DELETE_ACCOUNT = "unsuccessful delete account";

    public static final String RS_SUCCESSFUL_LOGIN = "successful login";
    public static final String RS_UNSUCCESSFUL_LOGIN = "unsuccessful login";

    public static final String RS_SUCCESSFUL_MESSAGING = "successful messaging";
    public static final String RS_UNSUCCESSFUL_MESSAGING = "unsuccessful messaging";

    // F stands for Field
    public static final String F_REQUEST = "request";
    public static final String F_RESPONSE = "response";
    public static final String F_USERNAME = "username";
    public static final String F_SENDER = "sender";
    public static final String F_RECEIVER = "receiver";
    public static final String F_CHANAL_MEMBER = "chanal member";
    public static final String F_PASSWORD = "password";
    public static final String F_MESSAGE = "message";

}