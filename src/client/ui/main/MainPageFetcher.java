package client.ui.main;

/**
 * Created by Saeid Dadkhah on 2016-06-28 5:31 PM.
 * Project: DBFinalProject
 */
public interface MainPageFetcher extends BaseFetcher {

    void showProfilePage();

    void showSearchPage();

    boolean deleteAccount(String password);

    void showNewGroupPage();

    void showNewChannelPage();

    void logout();

    boolean send(String message);

    void refresh();

    boolean setCurrentContact(String contact);

}
