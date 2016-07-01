package client.ui.main.search;

/**
 * Created by Saeid Dadkhah on 2016-07-01 6:22 PM.
 * Project: DBFinalProject
 */
public interface SearchPageFetcher {

    String[] searchName(String name);

    String[] searchHashtag(String hashtag);

    void add(String name);

    void message(String name);

}
