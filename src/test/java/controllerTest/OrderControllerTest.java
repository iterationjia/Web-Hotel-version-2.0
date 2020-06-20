import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotelApplication.class)
@Transactional // 这个标签是把测试中对数据库的操作当作一个事务，使测试不对数据库造成污染，去掉就会实际修改数据库
public class OrderControllerTest {

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
    //测试前置条件:数据库中必须有userId为1的用户
    public void testAddOrder() throws Exception {
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
        // 前端向后端传的对象以JSON格式传送
        // 嫌写转义符麻烦的话，可以新建一个json文件写好，然后用idea复制，idea会自动帮你加转义符
        String res = mockMvc.perform(
            post("/api/order/addOrder").contentType(MediaType.APPLICATION_JSON).content(orderJson)
                // 这里是模拟向后端发起一个post请求 url写上，JSON传对象，内容就是orderJson
                // 如果是get请求就写get，后边content啥的应该不用写
                // 请求的数据如果在url里，那就直接在url写上对应的数据
                // 如果是get请求传参的方式，则写get("url").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("key","value")
                // 有几个param就在后面点几个param，不过这种方式似乎只有搜索酒店用到了
        ).andReturn().getResponse().getContentAsString();
        // 这里是接收请求的返回值，也就是ResponseVO，以字符串形式
        assertTrue(new JSONObject(res).getBoolean("success"));
        // ResponseVO里面有Boolean success, String message, Object content三个成员，我判断success是不是true就知道测试是否成功
    }

    @org.junit.Test
    //测试前置条件:数据库中必须有orderId为201的已预订订单
    public void testExecOrder() throws Exception {
        String res = mockMvc.perform(
                get("/api/order/99/execOrder")
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    //测试前置条件:数据库中必须有orderId为201的已预订订单
    public void testSetOrderExcep() throws Exception {
        String res = mockMvc.perform(
                get("/api/order/201/setOrderExcep")
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    //测试前置条件:数据库中必须有orderId为203的异常订单
    public void testRecoverOrder() throws Exception {
        String res = mockMvc.perform(
                get("/api/order/203/recoverOrder")
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testRetrieveAllOrders() throws Exception {
        String res = mockMvc.perform(
                get("/api/order/getAllOrders")
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    //测试前置条件:数据库中必须有managerId为2的酒店工作人员
    public void testGetManagerOrders() throws Exception {
        String res = mockMvc.perform(
                get("/api/order/2/getManagerOrders")
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    //测试前置条件:数据库中必须有userId为1的用户
    public void testRetrieveUserOrders() throws Exception {
        String res = mockMvc.perform(
                get("/api/order/1/getUserOrders")
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    //测试前置条件:数据库中必须有userId为1的用户和hotelId为2的酒店
    public void testGetUserHotelOrders() throws Exception {
        String res = mockMvc.perform(
                get("/api/order/1/2/getUserHotelOrders")
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    //测试前置条件:数据库中必须有orderId为201的已预订订单
    public void testAnnulOrder() throws Exception {
        String res = mockMvc.perform(
                get("/api/order/201/annulOrder")
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    //测试前置条件:数据库中必须有hotelId为2的酒店
    public void testRetrieveComments() throws Exception {
        String res = mockMvc.perform(
                get("/api/order/2/comments")
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    //测试前置条件:数据库中必须有orderId为202的已入住订单
    public void testCheckOut() throws Exception {
        String orderJson = JSON.toJSONString(orderMapper.getOrderById(202));
        String res = mockMvc.perform(
                post("/api/order/checkOut").contentType(MediaType.APPLICATION_JSON).content(orderJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    //测试前置条件:数据库中必须有orderId为204的已退房订单
    public void testUpdateOrderComment() throws Exception {
        String orderJson = JSON.toJSONString(orderMapper.getOrderById(204));
        String res = mockMvc.perform(
                post("/api/order/updateOrderComment").contentType(MediaType.APPLICATION_JSON).content(orderJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }



}