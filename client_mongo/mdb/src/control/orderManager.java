package control;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import model.Order;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderManager {
    public List<Order> searchOrderByProduct() {
        List<Order> orders = null;
        MongoClient mongoClient = new MongoClient( "116.196.76.185" , 27017 );
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
    public List<Order> searchOrder(){
        List<Order> res = new ArrayList<Order>();
        MongoClient mongoClient = new MongoClient( "116.196.76.185",27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("order");
        //检索查看结果
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();

        while(mongoCursor.hasNext()){
            Document mon = mongoCursor.next();
            //System.out.println(mongoCursor.next());
            Order order = new Order();
            order.setId(mon.get("_id").toString());
            //order.setDeal_time((Date)mon.get("deal_time"));
            order.setDeal_time(new Date());
            order.setStatus(mon.get("status").toString());
            order.setProduct_name((ArrayList<String>) mon.get("product_name"));
            //order.setClass_belong((ArrayList<String>) mon.get("class_belong"));
            order.setSize((ArrayList<String>) mon.get("size"));
            order.setColor((ArrayList<String>) mon.get("color"));
            order.setAmount((ArrayList<Integer>) mon.get("amount"));
            order.setSingle_price((ArrayList<Double>) mon.get("single_price"));
            order.setTotal_money((Double)mon.get("total_money"));
            if(mon.get("address")==null)    order.setAddress("null");
            else    order.setAddress(mon.get("address").toString());
            if(mon.get("user_id")==null)    order.setUser_id("null");
            else    order.setUser_id(mon.get("user_id").toString());
            res.add(order);
        }
        return res;
    }

    public void modifyOrder(Order cur,String str) {
        MongoClient mongoClient = new MongoClient( "116.196.76.185" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("order");
        collection.updateMany(Filters.eq("_id",  new ObjectId(cur.getId())), new Document("$set",new Document("status",str)));
    }

    public void deleteOrder(String id) {
        MongoClient mongoClient = new MongoClient( "116.196.76.185" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("order");
        //删除符合条件的第一个文档
        collection.deleteOne(Filters.eq("_id",  new ObjectId(id)));
    }
}
