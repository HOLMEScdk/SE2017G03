package control;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import model.Customer;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerManager {
    public void addCustomer(Customer customer){
        MongoClient mongoClient = new MongoClient( "116.196.76.185" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("customer");
        //插入文档
        /**
         * 1. 创建文档 org.bson.Document 参数为key-value的格式
         * 2. 创建文档集合List<Document>
         * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用 mongoCollection.insertOne(Document)
         * */
        Document document = new Document("name", customer.getName()).
                append("password", customer.getPassword());
        collection.insertOne(document);
        System.out.println("文档插入成功");
    }

    public void modifyCustomer(Customer customer,String newPwd){
        MongoClient mongoClient = new MongoClient( "116.196.76.185" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("customer");
        //更新文档   将文档中likes=100的文档修改为likes=200
        collection.updateMany(Filters.eq("_id", customer.getName()), new Document("$set",new Document("password",newPwd)));
        //检索查看结果
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }
    }

    public void deleteCustomer(String id){
        MongoClient mongoClient = new MongoClient( "116.196.76.185" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("customer");
        //删除符合条件的第一个文档
        collection.deleteOne(Filters.eq("_id", id));

//        FindIterable<Document> findIterable = collection.find();
//        MongoCursor<Document> mongoCursor = findIterable.iterator();
//        while(mongoCursor.hasNext()){
//            System.out.println(mongoCursor.next());
//        }
    }
    public List<String[]> searchCustomer(){
        List<String[]> res = new ArrayList<>();
        MongoClient mongoClient = new MongoClient( "116.196.76.185",27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("customer");
        //检索查看结果
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();

        while(mongoCursor.hasNext()){
            //System.out.println(mongoCursor.next());
            Document mon = mongoCursor.next();
            String sex = mon.get("sex").toString();
            String password=mon.get("password").toString();
            String id=mon.get("_id").toString();
            String rank;
            if(mon.get("select_rank")==null) rank = "暂无";
            else    rank = ((Integer)mon.get("select_rank")).toString();

//            String id = "temp";
            String[] temp=new String[4];
            temp[0] =id;
            temp[1] = sex;
            temp[2] = password;
            temp[3] = rank;
            res.add(temp);
        }
        return res;
    }

    public static void main(String[] args) {
        Customer customer = new Customer();
        customer.setName("waz");
        customer.setPassword("123456");
        Date now = new Date();
        customer.setRegistert_time(now);
        CustomerManager cm = new CustomerManager();

//        cm.addCustomer(customer);
        //cm.modifyCustomer(customer,"654321");
        cm.searchCustomer();
    }
}
