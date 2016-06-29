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
    public static final String C_CHANNELS = "channels";
    public static final String C_GROUPS = "groups";

    // RQ stands for Request
    public static final String RQ_DISCONNECT = "disconnect";
    public static final String RQ_SIGNUP = "signup";
    public static final String RQ_LOGIN = "login";
    public static final String RQ_UPDATE_FRIENDS = "update friends request";//
    public static final String RQ_UPDATE_GROUPS = "update groups request";//
    public static final String RQ_UPDATE_CHANNELS = "update channels request";//
    public static final String RQ_DELETE_ACCOUNT = "delete account";
    public static final String RQ_MESSAGING = "messaging";
    public static final String RQ_NEW_CHANNEL = "new channel";
    public static final String RQ_NEW_GROUP = "new group";
    public static final String RQ_ADD_TO_GROUP = "add to group";

    // RS stands for Request
    public static final String RS_SUCCESSFUL_SIGNUP = "successful signup";
    public static final String RS_UNSUCCESSFUL_SIGNUP = "unsuccessful signup";

    public static final String RS_SUCCESSFUL_DELETE_ACCOUNT = "successful delete account";
    public static final String RS_UNSUCCESSFUL_DELETE_ACCOUNT = "unsuccessful delete account";

    public static final String RS_SUCCESSFUL_LOGIN = "successful login";
    public static final String RS_UNSUCCESSFUL_LOGIN = "unsuccessful login";

    public static final String RS_SUCCESSFUL_MESSAGING = "successful messaging";
    public static final String RS_UNSUCCESSFUL_MESSAGING = "unsuccessful messaging";

    public static final String RS_SUCCESSFUL_NEW_CHANNEL = "successful new channel";
    public static final String RS_UNSUCCESSFUL_NEW_CHANNEL = "unsuccessful new channel";

    public static final String RS_SUCCESSFUL_NEW_GROUP = "successful new group";
    public static final String RS_UNSUCCESSFUL_NEW_GROUP = "unsuccessful new group";

    public static final String RS_SUCCESSFUL_ADD_TO_GROUP = "successful add to group";
    public static final String RS_UNSUCCESSFUL_ADD_TO_GROUP = "unsuccessful add to group";

    public static final String RS_UPDATE_FRIENDS = "update friends response";
    public static final String RS_UPDATE_GROUPS = "update groups response";
    public static final String RS_UPDATE_CHANNELS = "update channels response";


    // F stands for Field
    public static final String F_REQUEST = "request";
    public static final String F_RESPONSE = "response";
    public static final String F_USERNAME = "username";
    public static final String F_FRIEND = "friend";
    public static final String F_SENDER = "sender";
    public static final String F_RECEIVER = "receiver";
    public static final String F_ADMIN = "admin";
    public static final String F_CHANNEL_NAME = "channel name";
    public static final String F_GROUP_NAME = "group name";
    public static final String F_CHANNEL_MEMBER = "channel member";
    public static final String F_GROUP_MEMBER = "group member";
    public static final String F_PASSWORD = "password";
    public static final String F_MESSAGE = "message";


}