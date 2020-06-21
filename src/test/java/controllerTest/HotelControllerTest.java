package controllerTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.hotel.HotelApplication;
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
public class HotelControllerTest {

    private MockMvc mockMvc;

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
    public void testAddHotel() throws Exception {
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
        // 前端向后端传的对象以JSON格式传送
        // 嫌写转义符麻烦的话，可以新建一个json文件写好，然后用idea复制，idea会自动帮你加转义符
        String res = mockMvc.perform(
                post("/api/hotel/addHotel").contentType(MediaType.APPLICATION_JSON).content(hotelJson)
                // 这里是模拟向后端发起一个post请求 url写上，JSON传对象，内容就是orderJson
                // 如果是get请求就写get，后边content啥的应该不用写
                // 请求的参数如果在url里，那就直接在url写上对应的数据
                /*
                如果是get请求**传参**(不是url传参)，则写get("url").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("key","value")
                有几个param就在后面点几个param，不过这种方式似乎只有搜索酒店用到了
                 */
        ).andReturn().getResponse().getContentAsString();
        // 这里是接收请求的返回值，也就是ResponseVO，以字符串形式
        assertTrue(new JSONObject(res).getBoolean("success"));
        // ResponseVO里面有Boolean success, String message, Object content三个成员，我判断success是不是true就知道测试是否成功
    }

    @org.junit.Test
    public void testRetrieveAllHotels() throws Exception {
        String res = mockMvc.perform(
            get("/api/hotel/all")
        ).andReturn().getResponse().getContentAsString();
        JSONObject object = new JSONObject(res);
        System.out.println(object.getString("content"));
        assertTrue(object.getBoolean("success"));
    }

    @org.junit.Test
    public void testRetreieveManagerAllHotels() throws Exception {
        String managerId = "6";
        String res = mockMvc.perform(
            get("/api/hotel/"+managerId+"/managerHotels")
        ).andReturn().getResponse().getContentAsString();
        JSONObject object = new JSONObject(res);
        System.out.println(object.getString("content"));
        assertTrue(object.getBoolean("success"));
    }

    @org.junit.Test
    public void testRetrieveSearchedHotels() throws Exception {
        String region = "XiDan";
        String address = "南京";
        String name = "";
        String star = "";
        int rate0 = 3;
        int rate1 = 5;
        String res = mockMvc.perform(
            get("/api/hotel/search").param("region", region).param("address", address)
            .param("name", name).param("star", star).param("rate0", String.valueOf(rate0))
            .param("rate1", String.valueOf(rate1))
        ).andReturn().getResponse().getContentAsString();
        JSONObject object = new JSONObject(res);
        System.out.println(object.getString("content"));
        assertTrue(object.getBoolean("success"));
    }

    @org.junit.Test
    public void testAddRoomInfo() throws Exception {
        // 前置条件：hotelId为2的酒店不存在BigBed
        String hotelRoomJson = "{\n" +
                "        \"roomType\": \"BigBed\",\n" +
                "        \"hotelId\": 2,\n" +
                "        \"price\": 300\n" +
                "    }";
        String res = mockMvc.perform(
            post("/api/hotel/roomInfo").contentType(MediaType.APPLICATION_JSON).content(hotelRoomJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testRetrieveHotelDetail() throws Exception {
        // 前置条件：hotelId为1的酒店存在
        int hotelId = 1;
        String res = mockMvc.perform(
            get("/api/hotel/"+String.valueOf(hotelId)+"/detail")
        ).andReturn().getResponse().getContentAsString();
        JSONObject object = new JSONObject(res);
        System.out.println(object.getString("content"));
        assertTrue(object.getBoolean("success"));
    }

    @org.junit.Test
    public void testEditHotel() throws Exception {
        // 前置条件：hotelId为2的酒店存在
        String hotelJson ="{\n" +
                "        \"name\": \"老八酒店\",\n" +
                "        \"description\": \"奥利给兄弟们，造它就完了！\",\n" +
                "        \"address\": \"葫芦岛\",\n" +
                "        \"phoneNum\": \"12306\",\n" +
                "        \"hotelStar\": \"Five\",\n" +
                "        \"id\": 2\n" +
                "    }";
        String res = mockMvc.perform(
                post("/api/hotel/editHotel").contentType(MediaType.APPLICATION_JSON).content(hotelJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testEditRoom() throws Exception {
        // 前置条件：roomId为2的客房存在
        int roomId = 2;
        int val = 30;
        String res1 = mockMvc.perform(
            get("/api/hotel/editRoomPrice")
            .param("roomId", String.valueOf(roomId))
            .param("val", String.valueOf(val))
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res1).getBoolean("success"));

        String res2 = mockMvc.perform(
            get("/api/hotel/editRoomTotal")
            .param("roomId", String.valueOf(roomId))
            .param("val", String.valueOf(val))
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res2).getBoolean("success"));

        String res3 = mockMvc.perform(
            get("/api/hotel/editRoomCurNum")
            .param("roomId", String.valueOf(roomId))
            .param("val", String.valueOf(val))
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res3).getBoolean("success"));
    }

    @org.junit.Test
    public void testDeleteRoom() throws Exception {
        // 前置条件：roomId为2的客房存在
        int roomId = 2;
        String res = mockMvc.perform(
            post("/api/hotel/"+String.valueOf(roomId)+"/deleteRoom")
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testDeleteHotel() throws Exception {
        // 前置条件：hotelId为2的酒店存在
        int hotelId = 2;
        String res = mockMvc.perform(
            post("/api/hotel/"+String.valueOf(hotelId)+"/deleteHotel")
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void tesSetHotelManager() throws Exception {
        // 前置条件：hotelId为2的酒店存在
        int hotelId = 2;
        int managerId = 6;
        String res = mockMvc.perform(
            post("/api/hotel/"+String.valueOf(hotelId)+"/"+String.valueOf(managerId)+"/setHotelManager")
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }
}
