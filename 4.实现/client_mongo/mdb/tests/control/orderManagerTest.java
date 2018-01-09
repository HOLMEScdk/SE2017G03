package control;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

public class orderManagerTest {
    @Test
    public void modifyOrder() throws Exception {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("order");
        collection.updateMany(Filters.eq("_id",  new ObjectId("5a3e79f4c1acd110c4b24a13")), new Document("$set",new Document("status","已完成")));
        System.out.println("修改成功！");
    }

    @Test
    public void deleteOrder() throws Exception {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("order");
        //删除符合条件的第一个文档
        collection.deleteOne(Filters.eq("_id",  new ObjectId("  ")));//id填进去
        System.out.println("删除成功！");
    }

}