package control;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import model.Products;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class productsManager {
    public void addProducts(Products products){
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("products");
        //插入文档
        /**
         * 1. 创建文档 org.bson.Document 参数为key-value的格式
         * 2. 创建文档集合List<Document>
         * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用 mongoCollection.insertOne(Document)
         * */
        Document document = new Document("_id", products.getName()).
                append("class_belong", products.getClass_belong() ).
                append("class_belong", products.getClass_belong() ).
                //append("size", products.getClass_belong() ).
                //append("color", products.getClass_belong() );
                append("raw_price", products.getRaw_price()).
                append("sale_price", products.getSale_price()).
                append("agent_price", products.getAgent_price()).
                append("time_import", products.getTime_import()).
                append("time_end_sale", products.getTime_end_sale()).
                append("amount", products.getAmount());
        collection.insertOne(document);
        System.out.println("文档插入成功");
    }

    public void modifyProducts(Products products,String newPwd){
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("products");
        //更新文档   将文档中likes=100的文档修改为likes=200
        collection.updateMany(Filters.eq("_id", products.getName()), new Document("$set",
                new Document("password",newPwd)
                        .append("class_belong", products.getClass_belong() )
                        .append("class_belong", products.getClass_belong() )
                        //append("size", products.getClass_belong() ).
                        //append("color", products.getClass_belong() );
                        .append("raw_price", products.getRaw_price())
                        .append("sale_price", products.getSale_price())
                        .append("agent_price", products.getAgent_price())
                        .append("time_import", products.getTime_import())
                        .append("time_end_sale", products.getTime_end_sale())
                        .append("amount", products.getAmount())) );
        //检索查看结果
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }
    }

    public void deleteProducts(Products products){
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("products");
        //删除符合条件的第一个文档
        collection.deleteOne(Filters.eq("_id", products.getName()));
        //检索查看结果
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }
    }

    public List<Products> loadAllProducts() {
        List<Products> list = null;
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("products");
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            Products p = new Products();
            System.out.println(mongoCursor.next());
        }
        return list;
    }
}
