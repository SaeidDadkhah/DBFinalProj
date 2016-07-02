package client.ui.first;

import client.ui.main.BaseFetcher;

/**
 * Created by Saeid Dadkhah on 2016-06-20 7:58 AM.
 * Project: Server
 */
public interface FirstPageFetcher extends BaseFetcher {

    boolean signUp(String username, String password);

    boolean logIn(String username, String password);

}
