package controllerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.hotel.HotelApplication;
import com.alibaba.fastjson.JSON;
import com.example.hotel.data.coupon.CouponMapper;
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
public class CouponControllerTest {

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
    public void testAddHotelTargetMoneyCoupon() throws Exception {
        String couponJson ="{\n" +
                "        \"hotelId\": 1,\n" +
                "        \"targetMoney\": 648,\n" +
                "        \"discountMoney\": 168,\n" +
                "        \"name\":\"爹！\",\n" +
                "        \"id\":1,\n" +
                "        \"type\":3,\n" +
                "        \"description\":\"爹！求你了！过！\",\n" +
                "        \"status\":1\n" +
                "    }";
        // 前端向后端传的对象以JSON格式传送
        // 嫌写转义符麻烦的话，可以新建一个json文件写好，然后用idea复制，idea会自动帮你加转义符
        String res = mockMvc.perform(
                post("/api/coupon/hotelTargetMoney").contentType(MediaType.APPLICATION_JSON).content(couponJson)
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
    public void testAddTimeCoupon() throws Exception {
        String couponJson ="{\n" +
                "        \"hotelId\": 1,\n" +
                "        \"targetMoney\": 666,\n" +
                "        \"discountMoney\": 166,\n" +
                "        \"name\":\"爹！DIE!\",\n" +
                "        \"id\":2,\n" +
                "        \"type\":4,\n" +
                "        \"description\":\"爹！辰哥是我爹！\",\n" +
                "        \"status\":1\n" +
                "    }";
        String res = mockMvc.perform(
                post("/api/coupon/time").contentType(MediaType.APPLICATION_JSON).content(couponJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testAddMemberCoupon() throws Exception {
        String couponJson ="{\n" +
                "        \"hotelId\": 1,\n" +
                "        \"targetMoney\": 618,\n" +
                "        \"discountMoney\": 186,\n" +
                "        \"name\":\"爹！DIE!DIE!\",\n" +
                "        \"id\":3,\n" +
                "        \"type\":1,\n" +
                "        \"description\":\"爹！辰哥是我爹！大伙都是我爹！\",\n" +
                "        \"status\":1\n" +
                "    }";
        String res = mockMvc.perform(
                post("/api/coupon/member").contentType(MediaType.APPLICATION_JSON).content(couponJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testAddManyHousesCoupon() throws Exception {
        String couponJson ="{\n" +
                "        \"hotelId\": 1,\n" +
                "        \"targetMoney\": 816,\n" +
                "        \"discountMoney\": 618,\n" +
                "        \"name\":\"爹！DIE!DIE!DIE!\",\n" +
                "        \"id\":4,\n" +
                "        \"type\":2,\n" +
                "        \"description\":\"爹！辰哥是我爹！大伙都是我爹！父亲节快乐！\",\n" +
                "        \"status\":1\n" +
                "    }";
        String res = mockMvc.perform(
                post("/api/coupon/manyHouses").contentType(MediaType.APPLICATION_JSON).content(couponJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testGetHotelAllCoupons() throws Exception {
        //前置条件：coupon表中存在hotelId为0的行
        String res = mockMvc.perform(
                get("/api/coupon/hotelAllCoupons").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("hotelId","0")
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testGetOrderMatchCoupons() throws Exception {
        //前置条件：orderlist表中存在userID为9，hotelId为2，orderPrice为13466，roomNum为1，checkIn为2020-06-11 14，checkOut为2020-07-15 14的行
        String res = mockMvc.perform(
                get("/api/coupon/orderMatchCoupons").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("userId","9").param("hotelId","2").param("orderPrice","13466").param("roomNum","1").param("checkIn","2020-06-11 14").param("checkOut","2020-07-15 14")
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

    @org.junit.Test
    public void testDeleteUser() throws Exception {
        //前置条件：coupon表中存在id为1的行
        String deleteJson ="{\n" +
                "        \"id\": 1\n" +
                "   }";
        String res = mockMvc.perform(
                post("/api/coupon/1/deleteCoupon").contentType(MediaType.APPLICATION_JSON).content(deleteJson)
        ).andReturn().getResponse().getContentAsString();
        assertTrue(new JSONObject(res).getBoolean("success"));
    }

}