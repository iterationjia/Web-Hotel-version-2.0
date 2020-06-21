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
    public void testAddCoupon() throws Exception {
        String couponJson ="{\n" +
                "        \"id\": 512\n" +
                "        \"description\":\"测试测试测试我不是特务我不是特务我不是特务\",\n"+
                "        \"hotelId\":1\n"+
                "        \"couponType\": 3\n" +
                "        \"couponName\": \"我是光荣的共青团员\",\n" +
                "        \"targetMoney\": 648\n" +
                "        \"discount\": 0\n" +
                "        \"status\": 1\n" +
                "        \"start_time\": null\n"+
                "        \"end_time\": null\n"+
                "        \"discountMoney\": 168\n"+
                "    }";
        // 前端向后端传的对象以JSON格式传送
        // 嫌写转义符麻烦的话，可以新建一个json文件写好，然后用idea复制，idea会自动帮你加转义符
        String res = mockMvc.perform(
                post("/api/coupon/addCoupon").contentType(MediaType.APPLICATION_JSON).content(couponJson)
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
}