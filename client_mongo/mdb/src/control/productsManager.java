package control;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import model.Order;
import model.Products;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductsManager {
    public void addProducts(Products products){
        MongoClient mongoClient = new MongoClient( "116.196.76.185" , 27017 );
        //MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("products");
        //插入文档
        /**
         * 1. 创建文档 org.bson.Document 参数为key-value的格式
         * 2. 创建文档集合List<Document>
         * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用 mongoCollection.insertOne(Document)
         * */
        Document document = new Document("_id", products.getId())
                .append("name", products.getName())
                .append("description", products.getDescription() )
                .append("class_belong", products.getClass_belong() )
                .append("size", products.getSize())
                .append("color",  products.getColor() )
                .append("raw_price", products.getRaw_price() )
                .append("sale_price", products.getSale_price() )
                .append("agent_price", products.getAgent_price())
                .append("time_import", products.getTime_import() )
                .append("time_end_sale", products.getTime_end_sale())
                .append("amount", products.getAmount() )
                .append("src", products.getSrc() );
        collection.insertOne(document);
    }

    public void modifyProducts(Products products){
        MongoClient mongoClient = new MongoClient( "116.196.76.185" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("products");
        //更新文档
        collection.updateMany(Filters.eq("name",  products.getName()), new Document("$set",
                new Document("description", products.getDescription() )
                        .append("class_belong", products.getClass_belong() )
                        .append("size", products.getSize() )
                        .append("color", products.getColor() )
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
        MongoClient mongoClient = new MongoClient( "116.196.76.185", 27017 );
        //MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("products");
        //删除符合条件的第一个文档
        collection.deleteOne(Filters.eq("name",  id));
        //检索查看结果
        FindIterable<Document> findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while(mongoCursor.hasNext()){
            System.out.println(mongoCursor.next());
        }
    }

    public List<String[]> searchProducts() {
        List<String[]> res = new ArrayList<>();
        MongoClient mongoClient = new MongoClient( "116.196.76.185" , 27017 );
        //MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
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
            ArrayList list1 = (ArrayList<String>) mon.get("size");
            Set<String> set1=new HashSet<String>();
            //System.out.println(list1.size()+"$"+list1.get(0));
            for(int i = 0; i < list1.size();i++){
                set1.add(list1.get(i).toString());
            }
            String siz = "";
            for (String x : set1) {
                siz+=x+" ";
            }

            ArrayList list2 = (ArrayList<String>) mon.get("color");
            Set<String> set2=new HashSet<String>();
            //System.out.println(list2.size()+"$"+list2.get(0));
            for(int i = 0; i < list2.size();i++){
                set2.add(list2.get(i).toString());
            }
            String color = "";
            for (String x : set2) {
                color+=x+" ";
            }

            String RPice = mon.get("raw_price").toString();
            String SPrice=mon.get("sale_price").toString();
            String APrice=mon.get("agent_price").toString();

            String amount ="";
            ArrayList list3 = (ArrayList<String>) mon.get("amount");
            //System.out.println(list3.size()+"$"+list3.get(0));
            for(int i = 0; i<list3.size(); i++) {
                amount += list2.get(i)+","+list1.get(i)+","+list3.get(i)+"@";
            }

            String time = mon.get("time_import")+"";
            String tEnd=mon.get("time_end_sale").toString();
            String src=mon.get("src").toString();
            String[] temp=new String[13];
            temp[0] = id;
            temp[1] = name;
            temp[2] =des;
            temp[3]=cla;
            temp[4]= siz;
            temp[5]=color;
            temp[6]=RPice;
            temp[7]=SPrice;
            temp[8]=APrice;
            temp[9]=amount;
            temp[10]=time;
            temp[11]=tEnd;
            temp[12]=src;
            res.add(temp);
        }
        return res;
    }

    public void reduceProduct(Order cur) {
        //System.out.println("我被调用了！！！！");
        MongoClient mongoClient = new MongoClient("116.196.76.185", 27017);
        //MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("products");
        //检索查看结果
        for (int j = 0; j < cur.getProduct_name().size(); j++) {
            BasicDBObject queryObject = new BasicDBObject("name", cur.getProduct_name().get(j));
            FindIterable<Document> findIterable = collection.find(queryObject);
            MongoCursor<Document> mongoCursor = findIterable.iterator();

            while (mongoCursor.hasNext()) {
                Document mon = mongoCursor.next();
                System.out.println(mongoCursor);

                ArrayList list1 = (ArrayList<String>) mon.get("size");
                ArrayList list2 = (ArrayList<String>) mon.get("color");
                ArrayList list3 = (ArrayList<Integer>) mon.get("amount");
                ArrayList<Integer> res = new ArrayList<Integer>();
                res = list3;

                int ans = 0;
                for (int i = 0; i < list3.size(); i++) {
                        if ((list1.get(i).equals(cur.getSize().get(j))) && (list2.get(i).equals(cur.getColor().get(j)))){
                            ans = Integer.valueOf(""+list3.get(i));
                            ans-= Integer.valueOf(cur.getAmount().get(j)+"");
                            res.set(i,ans);
                        }
                    }
//                for (int i = 0; i < res.size(); i++)
//                    System.out.println(res.get(i) + "@");
                collection.updateOne(Filters.eq("name", cur.getProduct_name().get(j)), new Document("$set",
                        new Document("amount", res)));
            }
        }
    }

    public void restoreProduct(Order cur) {
        MongoClient mongoClient = new MongoClient("116.196.76.185", 27017);
        //MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("products");
        for (int j = 0; j < cur.getProduct_name().size(); j++) {
            BasicDBObject queryObject = new BasicDBObject("name",cur.getProduct_name().get(j));
            FindIterable<Document> findIterable = collection.find(queryObject);
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            while (mongoCursor.hasNext()) {
                Document mon = mongoCursor.next();
                System.out.println(mongoCursor);

                ArrayList list1 = (ArrayList<String>) mon.get("size");
                ArrayList list2 = (ArrayList<String>) mon.get("color");
                ArrayList list3 = (ArrayList<Integer>) mon.get("amount");
                ArrayList<Integer> res = new ArrayList<Integer>();
                res = list3;

                int ans = 0;
                for (int i = 0; i < list3.size(); i++) {
                    if ((list1.get(i).equals(cur.getSize().get(j))) && (list2.get(i).equals(cur.getColor().get(j)))){
                        ans = Integer.valueOf(""+list3.get(i));
                        ans += Integer.valueOf(cur.getAmount().get(j)+"");
                        res.set(i,ans);
                    }
                }

                collection.updateOne(Filters.eq("name", cur.getProduct_name().get(j)), new Document("$set",
                        new Document("amount", res)));
            }
        }
    }
}
