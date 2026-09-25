package com.nenfal.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import net.mvndicraft.mvndicore.MvndiCore;
import net.mvndicraft.mvndicore.database.MvndiDatabases;
import org.bson.Document;

import java.math.BigDecimal;

public class TownRatingDAO {

    private boolean databaseConnected = false;

    void initDatabase() {
        MvndiDatabases dbs = MvndiCore.getInstance().getDatabases();
        // Check MvndiCore
        if (!dbs.isMongoConnected()) {
            databaseConnected = false;
            return;
        }

        MongoCollection<Document> townRatings = dbs.mongoDatabase().getCollection("townratings");

        townRatings.createIndex(Indexes.ascending("uuid"), new IndexOptions().unique(true));
        databaseConnected = true;
    }

    public void saveRating(String townName, BigDecimal townRate) {
    }
}
