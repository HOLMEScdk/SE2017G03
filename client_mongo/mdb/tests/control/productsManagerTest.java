package control;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class productsManagerTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void firstTest() {
        Assert.assertTrue(true);
    }
    @Test
    public void testAddProducts() throws Exception {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("products");

        String name = "测试商品"+Integer.toHexString((int)System.currentTimeMillis());
        String Desc = "xxx";
        String Class = "xxx";
        double RPrice = 500, SPrice = 1000, APrice = 700;
        String TimeEnd = "2018/3/1";
        String s ="red,s,100,red,m,200,red,l,150,blue,s,200,blue,m,200";
        String[] str=s.split("@|,| ");
        System.out.println(str.length);
        ArrayList<Integer> x= new ArrayList<Integer>();//amount
        ArrayList<String> y=new ArrayList<String>();//color
        ArrayList<String> z=new ArrayList<String>();//size
            for(int i=0; i<str.length; i+=3){
                y.add(str[i]);
                z.add(str[i+1]);
                x.add(Integer.parseInt(str[i+2]));
            }
            int amount[]=new int[x.size()];
            String[] Color =new String[y.size()];
            String[] Size =new String[z.size()];
            for(int i = 0; i < x.size();i++){
                Color[i]=y.get(i);
                Size[i]=z.get(i);
                amount[i]=x.get(i);
            }
        String temp = "100";
        String[] ans=temp.split("\\\\");
        String src = "product\\"+ans[ans.length-1];
        String id =  Integer.toHexString((int)System.currentTimeMillis());

        Document document = new Document("_id", id)
                .append("name", name)
                .append("description",Desc)
                .append("class_belong", Class )
                .append("size", z)
                .append("color",  y)
                .append("raw_price", RPrice )
                .append("sale_price", SPrice)
                .append("agent_price", APrice)
                .append("time_import", new Date() )
                .append("time_end_sale", TimeEnd)
                .append("amount", x)
                .append("src",src);
        collection.insertOne(document);
        System.out.println("文档插入成功");
    }


    @Test
    public void modifyProducts() throws Exception {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("products");
        //更新文档
        collection.updateMany(Filters.eq("_id",  new ObjectId("p2")), new Document("$set",
                new Document("id",new ObjectId("p2"))
                        .append("description","test")
                        .append("class_belong", "test" )
                        .append("size", Arrays.asList(new String[]{"s","m"}))
                        .append("color",  new String[]{"blue","red","yellow"} )
                        .append("raw_price", 300 )
                        .append("sale_price", 500)
                        .append("agent_price", 400)
                        .append("time_import", new Date() )
                        .append("time_end_sale", new Date())
                        .append("amount", 100 )
                        .append("src", "/" )
        ) );
    }

    @Test
    public void deleteProducts() throws Exception {
        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        // 连接到数据库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("SE2017");
        MongoCollection<Document> collection = mongoDatabase.getCollection("products");
        //删除符合条件的第一个文档
        collection.deleteOne(Filters.eq("_id",  new ObjectId("5a4d9804beb6b167900abc19")));
    }

}