package control;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.Test;

import java.util.Date;

public class customerManagerTest {
    @Test
    public void addCustomer() throws Exception {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("customer");
        Document document = new Document("_id",Integer.toHexString((int)System.currentTimeMillis()-200))
                .append("sex","woman")
                .append("password","123456")
                .append("register_time",new Date());
        collection.insertOne(document);
        System.out.println("文档插入成功");
    }

    @Test
    public void deleteCustomer() throws Exception {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("customer");
        collection.deleteOne(Filters.eq("_id", "c1001"));
        System.out.println("删除成功");
    }

}