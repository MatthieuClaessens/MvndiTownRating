package com.nenfal.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.UpdateOptions;
import net.mvndicraft.mvndicore.MvndiCore;
import net.mvndicraft.mvndicore.database.MvndiDatabases;
import org.bson.Document;

import java.math.BigDecimal;

public class TownRatingDAO {

    private boolean databaseConnected = false;
    private MongoCollection<Document> townRatings;

    void initDatabase() {
        MvndiDatabases dbs = MvndiCore.getInstance().getDatabases();
        // Check MvndiCore
        if (!dbs.isMongoConnected()) {
            databaseConnected = false;
            return;
        }
        this.townRatings = dbs.mongoDatabase().getCollection("townratings");

        townRatings.createIndex(Indexes.ascending("Town Name"), new IndexOptions().unique(true));
        databaseConnected = true;
    }

    public void saveRating(String townName, BigDecimal townRate) {
        Document query = new Document("Town Name", townName);
        Document update = new Document("$set", new Document("Town Name", townName)
                .append("Town rate", townRate.doubleValue()));

        UpdateOptions options = new UpdateOptions().upsert(true);
        townRatings.updateOne(query, update, options);
    }

    public BigDecimal getRating(String townName) {
        if (!databaseConnected || townRatings == null) return BigDecimal.ZERO;

        Document doc = townRatings.find(new Document("Town Name", townName)).first();
        if (doc != null && doc.containsKey("Town rate")) {
            return BigDecimal.valueOf(doc.getDouble("Town rate"));
        }
        return BigDecimal.ZERO;
    }

    public void removeRating(String townName) {
        townRatings.deleteOne(new Document("Town Name", townName));
    }
}
