package control;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import model.Order;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class orderManager {
    public List<Order> searchOrderByProduct() {
        List<Order> orders = null;
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("order");
        //TODO
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }
        return orders;
    }
    public List<String[]> searchOrder(){
        List<String[]> res = new ArrayList<>();
        MongoClient mongoClient = new MongoClient( "localhost",27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("order");
        //检索查看结果
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();

        while(mongoCursor.hasNext()){
            //System.out.println(mongoCursor.next());
            Document mon = mongoCursor.next();
            String id = mon.get("_id").toString();
            String name = mon.get("name").toString();
            String class_belong=mon.get("class_belong").toString();
            String size=mon.get("size").toString();
            String color=mon.get("color").toString();
            String amount=mon.get("amount").toString();
            String total_money=mon.get("total_money").toString();
            String[] temp=new String[7];
            temp[0] =id;
            temp[1] =name;
            temp[2] =class_belong;
            temp[3] = size;
            temp[4] = color;
            temp[5] = amount;
            temp[6] = total_money;
            res.add(temp);
            //System.out.println(temp[0]+temp[1]+temp[2]);
            //TODO 并不是这样的
        }
        return res;
    }
}
