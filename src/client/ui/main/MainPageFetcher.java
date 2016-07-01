package client.ui.main;

import client.ui.BaseFetchet;
import client.ui.main.profile.ProfilePageFetcher;

/**
 * Created by Saeid Dadkhah on 2016-06-28 5:31 PM.
 * Project: DBFinalProject
 */
public interface MainPageFetcher extends BaseFetchet {

    void showProfilePage();

    void showSearchPage();

    boolean deleteAccount(String password);

    void logout();

    boolean send(String message);

    boolean setCurrentContact(String contact);

}
