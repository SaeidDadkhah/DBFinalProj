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
    public static final String C_FRIENDS = "friends";
    public static final String C_HASHTAGS = "hashtags";
    public static final String C_MENTIONS = "mentions";

    // RQ stands for Request
    public static final String RQ_DISCONNECT = "disconnect";
    public static final String RQ_SIGNUP = "signup";
    public static final String RQ_LOGIN = "login";
    public static final String RQ_UPDATE_FRIENDS = "update friends request";//
    public static final String RQ_ADD_FRIENDS = "add friends request";
    public static final String RQ_UPDATE_GROUPS = "update groups request";//
    public static final String RQ_UPDATE_CHANNELS = "update channels request";//
    public static final String RQ_DELETE_ACCOUNT = "delete account";
    public static final String RQ_MESSAGING = "messaging";
    public static final String RQ_CHANNEL_MESSAGING = "channel messaging";
    public static final String RQ_GROUP_MESSAGING = "group messaging";
    public static final String RQ_NEW_CHANNEL = "new channel";
    public static final String RQ_NEW_GROUP = "new group";
    public static final String RQ_ADD_TO_GROUP = "add to group";
    public static final String RQ_JOIN_A_CHANNEL = "join a channel";
    public static final String RQ_GET_PROFILE = "get profile";
    public static final String RQ_UPDATE_PROFILE = "update profile";
    public static final String RQ_HASHTAG = "hashtag";
    public static final String RQ_MENTION = "mention";
    public static final String RQ_HASHTAG_SEARCH = "hashtag search";
    public static final String RQ_NAME_SEARCH = "name search";
    public static final String RQ_WTF = "what is this";
    public static final String RQ_SHOW_MESSAGES = "show messages";
    // RS stands for Request
    public static final String RS_SUCCESSFUL_SIGNUP = "successful signup";
    public static final String RS_UNSUCCESSFUL_SIGNUP = "unsuccessful signup";

    public static final String RS_SUCCESSFUL_SHOW_MESSAGES = "successful show messages";
    public static final String RS_UNSUCCESSFUL_SHOW_MESSAGES = "unsuccessful show messages";

    public static final String RS_SUCCESSFUL_HASHTAG_SEARCH = "successful hashtag search";
    public static final String RS_UNSUCCESSFUL_HASHTAG_SEARCH = "unsuccessful hashtag search";

    public static final String RS_SUCCESSFUL_NAME_SEARCH = "successful name search";
    public static final String RS_UNSUCCESSFUL_NAME_SEARCH = "unsuccessful name search";

    public static final String RS_SUCCESSFUL_DELETE_ACCOUNT = "successful delete account";
    public static final String RS_UNSUCCESSFUL_DELETE_ACCOUNT = "unsuccessful delete account";

    public static final String RS_SUCCESSFUL_LOGIN = "successful login";
    public static final String RS_UNSUCCESSFUL_LOGIN = "unsuccessful login";

    public static final String RS_SUCCESSFUL_MESSAGING = "successful messaging";
    public static final String RS_UNSUCCESSFUL_MESSAGING = "unsuccessful messaging";

    public static final String RS_SUCCESSFUL_CHANNEL_MESSAGING = "successful channel messaging";
    public static final String RS_UNSUCCESSFUL_CHANNEL_MESSAGING = "unsuccessful channel messaging";

    public static final String RS_SUCCESSFUL_GROUP_MESSAGING = "successful group messaging";
    public static final String RS_UNSUCCESSFUL_GROUP_MESSAGING = "unsuccessful group messaging";

    public static final String RS_SUCCESSFUL_NEW_CHANNEL = "successful new channel";
    public static final String RS_UNSUCCESSFUL_NEW_CHANNEL = "unsuccessful new channel";

    public static final String RS_SUCCESSFUL_NEW_GROUP = "successful new group";
    public static final String RS_UNSUCCESSFUL_NEW_GROUP = "unsuccessful new group";

    public static final String RS_SUCCESSFUL_ADD_TO_GROUP = "successful add to group";
    public static final String RS_UNSUCCESSFUL_ADD_TO_GROUP = "unsuccessful add to group";

    public static final String RS_SUCCESSFUL_JOIN_A_CHANNEL = "successful join a channel";
    public static final String RS_UNSUCCESSFUL_JOIN_A_CHANNEL = "unsuccessful join a channel";

    public static final String RS_SUCCESSFUL_MENTION = "successful mention";
    public static final String RS_UNSUCCESSFUL_MENTION = "unsuccessful mention";

    public static final String RS_SUCCESSFUL_ADD_FRIENDS = "successful add friends";
    public static final String RS_UNSUCCESSFUL_ADD_FRIENDS = "unsuccessful add friends";

    public static final String RS_SUCCESSFUL_WTF = "successful what is this";
    public static final String RS_UNSUCCESSFUL_WTF = "unsuccessful what is this";

    public static final String RS_GET_PROFILE = "get profile";
    public static final String RS_UPDATE_PROFILE = "update profile";
    //public static final String RS_UNSUCCESSFUL_GET_PROFILE = "unsuccessful get profile";

    public static final String RS_UPDATE_FRIENDS = "update friends response";
    public static final String RS_UPDATE_GROUPS = "update groups response";
    public static final String RS_UPDATE_CHANNELS = "update channels response";
    public static final String RS_HASHTAG = "hashtag";
    //public static final String RS_WTF = "what is that";


    // F stands for Field
    public static final String F_REQUEST = "request";
    public static final String F_RESPONSE = "response";
    public static final String F_USERNAME = "username";
    public static final String F_FRIENDS = "friends";
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
    public static final String F_TIME = "time";
    public static final String F_NAME = "name";
    public static final String F_BIRTHDAY = "birthday";
    public static final String F_EMAIL = "email";
    public static final String F_BIOGRAPHY = "biography";
    public static final String F_PHONE_NUMBER = "phone number";
    public static final String F_PICTURE = "picture";
    public static final String F_HASHTAG = "hashtag";
    public static final String F_MENTIONED = "mentioned";
    public static final String F_MID = "count";

}