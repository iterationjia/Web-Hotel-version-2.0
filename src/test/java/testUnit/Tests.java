package testUnit;

import com.alibaba.fastjson.JSON;
import com.example.hotel.HotelApplication;
import com.example.hotel.data.order.OrderMapper;
import org.json.JSONObject;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotelApplication.class)

public class Tests {

    int oid = 235;   //注意修改！！！
    int hid = 20;     //注意修改！！！
    private MockMvc mockMvc;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    private WebApplicationContext wac;

    @org.junit.Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @org.junit.After
    public void tearDown() {

    }

    @org.junit.Test
    //测试前置条件:数据库中必须有userId为1的用户且订单号须为数据库max（id）+1（由于auto-increment，id自增）
    public void test1() throws Exception {
        String orderJson ="{\n" +
                "    \"userId\":1,\n" +
                "    \"hotelId\":2,\n" +
                "    \"hotelName\": \"儒家酒店\","+
                "    \"checkInDate\":\"2020-06-19 15\",\n" +
                "    \"checkOutDate\":\"2020-06-20 15\",\n" +
                "    \"roomType\":\"Family\",\n" +
                "    \"roomNum\":1,\n" +
                "    \"peopleNum\":1,\n" +
                "    \"haveChild\":false,\n" +
                "    \"createDate\":\"2020-06-18 16\",\n" +
                "    \"price\":3000,\n" +
                "    \"clientName\":\"老用户\",\n" +
                "    \"phoneNumber\":\"110\"\n" +
                "}";

        String res = mockMvc.perform(
                post("/api/order/addOrder").contentType(MediaType.APPLICATION_JSON).content(orderJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
        String res_0 = mockMvc.perform(
                get("/api/order/"+oid+"/annulOrder")
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res_0).getBoolean("success"));
    }

    @org.junit.Test
    //测试前置条件:数据库中必须有userId为1的用户且订单号须为数据库max（id）+1（由于auto-increment，id自增），也就是test1的下一个
    public void test2() throws Exception {
        String orderJson ="{\n" +
                "    \"userId\":1,\n" +
                "    \"hotelId\":2,\n" +
                "    \"hotelName\": \"儒家酒店\","+
                "    \"checkInDate\":\"2020-06-19 15\",\n" +
                "    \"checkOutDate\":\"2020-06-20 15\",\n" +
                "    \"roomType\":\"Family\",\n" +
                "    \"roomNum\":1,\n" +
                "    \"peopleNum\":1,\n" +
                "    \"haveChild\":false,\n" +
                "    \"createDate\":\"2020-06-18 16\",\n" +
                "    \"price\":3000,\n" +
                "    \"clientName\":\"老用户\",\n" +
                "    \"phoneNumber\":\"110\"\n" +
                "}";

        String res = mockMvc.perform(
                post("/api/order/addOrder").contentType(MediaType.APPLICATION_JSON).content(orderJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));

        String res_0 = mockMvc.perform(
                get("/api/order/"+(oid+1)+"/execOrder")
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res_0).getBoolean("success"));

        String orderJson_0 = JSON.toJSONString(orderMapper.getOrderById(oid+1));

        String res_1 = mockMvc.perform(
                post("/api/order/checkOut").contentType(MediaType.APPLICATION_JSON).content(orderJson_0)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res_1).getBoolean("success"));


        String orderJson_1 ="{\n" +
                "    \"id\":"+(oid+1)+",\n" +
                "    \"hotelId\":2,\n" +
                "    \"star\":3,\n" +
                "    \"comment\":\"行\"\n" +
                "}";

        String res_2 = mockMvc.perform(
                post("/api/order/updateOrderComment").contentType(MediaType.APPLICATION_JSON).content(orderJson_1)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res_2).getBoolean("success"));
    }

    @org.junit.Test
    //测试前置条件:hid须为数据库max（id）+1（由于auto-increment，id自增）
    public void test3() throws Exception {
        String hotelJson ="{\n" +
                "        \"name\": \"老八酒店\",\n" +
                "        \"description\": \"奥利给兄弟们，造它就完了！\",\n" +
                "        \"bizRegion\":\"XiDan\","+
                "        \"rate\": 0,"+
                "        \"address\": \"葫芦岛\",\n" +
                "        \"phoneNum\": \"12306\",\n" +
                "        \"hotelStar\": \"Five\",\n" +
                "        \"managerId\": 6\n" +
                "    }";
        String res = mockMvc.perform(
                post("/api/hotel/addHotel").contentType(MediaType.APPLICATION_JSON).content(hotelJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));

        String hotelJson_0 ="{\n" +
                "        \"name\": \"老舅酒店\",\n" +
                "        \"description\": \"奥利给兄弟们，造它就完了！\",\n" +
                "        \"address\": \"葫芦岛\",\n" +
                "        \"phoneNum\": \"12306\",\n" +
                "        \"hotelStar\": \"Five\",\n" +
                "        \"id\": "+hid+"\n" +
                "    }";
        String res_0 = mockMvc.perform(
                post("/api/hotel/editHotel").contentType(MediaType.APPLICATION_JSON).content(hotelJson_0)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res_0).getBoolean("success"));

        String res_1 = mockMvc.perform(
                get("/api/hotel/all")
        ).andReturn().getResponse().getContentAsString();
        JSONObject object = new JSONObject(res_1);
        assertTrue(object.getBoolean("success"));

        String res_2 = mockMvc.perform(
                get("/api/hotel/"+String.valueOf(hid)+"/detail")
        ).andReturn().getResponse().getContentAsString();
        JSONObject object_0 = new JSONObject(res_2);
        assertTrue(object_0.getBoolean("success"));

        String res_3 = mockMvc.perform(
                post("/api/hotel/"+String.valueOf(hid)+"/deleteHotel")
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res_3).getBoolean("success"));
    }
}
