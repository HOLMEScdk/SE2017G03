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
import java.util.Arrays;
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
        Document document = new Document("name", products.getName())
                .append("description", products.getDescription() )
                .append("class_belong", products.getClass_belong() )
                .append("size", Arrays.asList(products.getSize()))
                .append("color",  Arrays.asList(products.getColor()) )
                .append("raw_price", products.getRaw_price() )
                .append("sale_price", products.getSale_price() )
                .append("agent_price", products.getAgent_price())
                .append("time_import", products.getTime_import() )
                .append("time_end_sale", products.getTime_end_sale())
                .append("amount", products.getAmount() )
                .append("src", products.getSrc() );
        collection.insertOne(document);
        System.out.println("文档插入成功");
    }

    public void modifyProducts(Products products){
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("products");
        //更新文档
        collection.updateMany(Filters.eq("_id", products.getId()), new Document("$set",
                new Document("name",products.getName() )
                        .append("description", products.getDescription() )
                        .append("class_belong", products.getClass_belong() )
                        .append("size", Arrays.asList(products.getSize()) )
                        .append("color", Arrays.asList(products.getColor()) )
                        .append("raw_price", products.getRaw_price())
                        .append("sale_price", products.getSale_price())
                        .append("agent_price", products.getAgent_price())
                        .append("time_end_sale", products.getTime_end_sale())
                        .append("amount", products.getAmount())
                        .append("src", products.getSrc())
        ) );
        //检索查看结果
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }
    }

    public void deleteProducts(String id){
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("products");
        //删除符合条件的第一个文档
        collection.deleteOne(Filters.eq("_id", id));
        //检索查看结果
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }
    }

    public List<String[]> searchProducts() {
        List<String[]> res = new ArrayList<>();
        MongoClient mongoClient = new MongoClient( "localhost",27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("products");
        //检索查看结果
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();

        while(mongoCursor.hasNext()){
            //System.out.println(mongoCursor.next());
            Document mon = mongoCursor.next();
            String id=mon.get("_id").toString();
            String name = mon.get("name").toString();
            String des = mon.get("description").toString();
            String cla =mon.get("class_belong").toString();
            String siz=mon.get("size").toString();
            //String color=mon.get("color").toString();
            String RPice = mon.get("raw_price").toString();
            String SPrice=mon.get("sale_price").toString();
            String APrice=mon.get("agent_price").toString();
            //String time = mon.get("time_import").toString();
            String tEnd=mon.get("time_end_sale").toString();
            String[] temp=new String[11];
            temp[0] = id;
            temp[1] = "name";
            temp[2] =des;
            temp[3]=cla;
            temp[4]=siz;
            temp[5]="color";
            temp[6]=RPice;
            temp[7]=SPrice;
            temp[8]=APrice;
            temp[9]="time";
            temp[10]=tEnd;
            res.add(temp);
            System.out.println(temp[0]+temp[1]+temp[2]);
            //TODO 并不是这样的
        }
        return res;
    }
}
