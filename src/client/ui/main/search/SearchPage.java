package client.ui.main.search;

import client.ui.component.PlaceHolder;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Saeid Dadkhah on 2016-07-01 6:20 PM.
 * Project: DBFinalProject
 */
public class SearchPage extends JDialog {

    private SearchPageFetcher fetcher;

    private JTextField tf_search;
    private JList l_results;

    public static void main(String[] args) {
        new SearchPage(new SearchPageFetcher() {

            @Override
            public String[] searchName(String name) {
                return new String[]{"saeid2", "hasti"};
            }

            @Override
            public String[] searchHashtag(String hashtag) {
                return new String[]{"message1", "message2"};
            }

            @Override
            public void message(String name) {
                System.out.println("Message: " + name);
            }

            @Override
            public void add(String name) {
                System.out.println("Add: " + name);
            }
        });
    }

    public SearchPage(SearchPageFetcher fetcher) {
        this.fetcher = fetcher;
        init();
    }

    private void init() {
        Dimension ss = getToolkit().getScreenSize();
        setSize(3 * (int) ss.getWidth() / 7, 3 * (int) ss.getHeight() / 7);
        setLocation(2 * (int) ss.getWidth() / 7, 2 * (int) ss.getHeight() / 7);
        setModal(true);

        setTitle("Search");

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.top = 4;
        gbc.insets.left = 4;
        gbc.insets.right = 4;
        gbc.insets.bottom = 4;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        gbc.weightx = 0.1;
        gbc.weighty = 1;
        getContentPane().add(new PlaceHolder(this), gbc);

        gbc.gridx = 5;
        getContentPane().add(new PlaceHolder(this), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        getContentPane().add(new JLabel("Insert your search term"), gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0.8;
        tf_search = new JTextField();
        getContentPane().add(tf_search, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0;
        JButton b_searchName = new JButton("Search Name");
        //noinspection unchecked
        b_searchName.addActionListener(e -> l_results.setListData(fetcher.searchName(tf_search.getText())));
        getContentPane().add(b_searchName, gbc);

        gbc.gridx = 4;
        JButton b_searchHashtag = new JButton("Search Hashtag");
        //noinspection unchecked
        b_searchHashtag.addActionListener(e -> l_results.setListData(fetcher.searchHashtag(tf_search.getText())));
        getContentPane().add(b_searchHashtag, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.weightx = 0.8;
        gbc.weighty = 0.8;
        l_results = new JList();
        getContentPane().add(new JScrollPane(l_results), gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        JButton b_message = new JButton("Message");
        b_message.addActionListener(e -> fetcher.message((String) l_results.getSelectedValue()));
        getContentPane().add(b_message, gbc);

        gbc.gridx = 4;
        JButton b_add = new JButton("Add");
        b_add.addActionListener(e -> fetcher.add((String) l_results.getSelectedValue()));
        getContentPane().add(b_add, gbc);

        setVisible(true);
    }

}
