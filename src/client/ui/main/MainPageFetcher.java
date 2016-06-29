package client.ui.main;

/**
 * Created by Saeid Dadkhah on 2016-06-28 5:31 PM.
 * Project: DBFinalProject
 */
public interface MainPageFetcher {

    boolean deleteAccount(String password);

    void logout();

    boolean send(String receiver, String message);

}
