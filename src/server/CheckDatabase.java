package server;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import common.Constants;
import org.bson.Document;

/**
 * Created by Saeid Dadkhah on 2016-06-28 4:38 PM.
 * Project: DBFinalProject
 */
public class CheckDatabase {

    public static void main(String[] args) {
        MongoDatabase mdb = new MongoClient().getDatabase(Constants.DB_NAME);
        FindIterable<Document> res = mdb.getCollection(Constants.C_USERS).find();
        System.out.println("Printing:");
        for (Document doc : res) {
            System.out.println(doc.toJson());
        }
    }

}
