package databaseSide;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ui.first.FirstPage;
import ui.first.FirstPageFetcher;

/**
 * Created by Saeid Dadkhah on 2016-06-20 6:11 AM.
 * Project: DBFinalProject
 */
public class DBFinalProj implements FirstPageFetcher {

    private MongoDatabase db;
    private FirstPage firstPage;

    public static void main(String[] args) {
        new DBFinalProj();
    }

    public DBFinalProj() {
        MongoClient client = new MongoClient();
        db = client.getDatabase(Constants.DB_NAME);
        firstPage = new FirstPage(this);
    }

    @Override
    public boolean signUp(String username, String password) {
        FindIterable<Document> res = db.getCollection(Constants.C_USERS)
                .find(new Document()
                        .append(Constants.F_USERNAME, username));
        if (res.first() == null) {
            db.getCollection(Constants.C_USERS).insertOne(new Document()
                    .append(Constants.F_USERNAME, username)
                    .append(Constants.F_PASSWORD, password));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean logIn(String username, String password) {
        FindIterable<Document> res = db.getCollection(Constants.C_USERS)
                .find(new Document()
                        .append(Constants.F_USERNAME, username)
                        .append(Constants.F_PASSWORD, password));
        if (res.first() != null) {
            firstPage.dispose();
            return true;
        } else
            return false;
    }

}
