package client.ui.first;

/**
 * Created by Saeid Dadkhah on 2016-06-20 7:58 AM.
 * Project: Server
 */
public interface FirstPageFetcher {

    boolean signUp(String username, String password);

    boolean logIn(String username, String password);

    void closing();

}