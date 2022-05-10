import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;


public class SeatAPI {
    public static ObjectMapper mapper = new ObjectMapper();
    public static OkHttpClient client = new OkHttpClient();
    public static Request request;
    public static Response response;
    public static String jsonString;
    public static RequestBody requestBody;
    public static void apiGetSeatStatusByClassroomAndTime() {
        try {
            // API: 查看JB201教室在18-21点的每个座位的可用情况
            request = new Request.Builder()
                    .url("http://127.0.0.1:5000/seat/status/JB201/18-21")
                    .build();
            response = client.newCall(request).execute();
            jsonString = response.body().string();
            // 后台返回的结果是json格式字符串, 需要进行解析
            Map<String, Boolean> map = mapper.readValue(jsonString, Map.class);
            // 解析完成后, map中包含多条Entry, 每一条Entry是一个键值对(k, v)
            // k是String类型, 表示座位号
            // v是Boolean类型, 表示该座位是否可用
            // 简单来说, map长这样:
            // {
            //    "1": false,
            //    "2": true
            // }
            for (Map.Entry<String, Boolean> entry : map.entrySet()) {
                System.out.println("座位" + entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    public static void apiGetSeatStatusByTime() {
        try {
            // API: 查看18-21点所有教室所有座位的可用情况
            request = new Request.Builder()
                    .url("http://127.0.0.1:5000/seat/status/18-21")
                    .build();
            response = client.newCall(request).execute();
            jsonString = response.body().string();
            // 返回的结果是json格式的字符串, 需要进行解析
            Map<String, Map<String, Boolean>> map = mapper.readValue(jsonString, Map.class);
            // 解析完成后, map中包含多条Entry, 每一条Entry是一个键值对(k, v)
            // k是String类型, 表示教室编号
            // v是Map<String, Boolean>类型, 表示该教室中座位的可用情况, v中又包含多条Entry, 每一条Entry是一个键值对(k_, v_)
            // k_是String类型, 表示座位号, v_是Boolean类型, 表示该座位是否可用
            // 简单来说map长这样:
            // {
            //    "JB201": {"1": false, "2": true},
            //    "JB202": {"1": true, "2": true}
            // }
            for (Map.Entry<String, Map<String, Boolean>> entry : map.entrySet()) {
                System.out.println("教室: " + entry.getKey());
                for (Map.Entry<String, Boolean> entry_ : entry.getValue().entrySet()) {
                    System.out.println("座位" + entry_.getKey() + ": " + entry_.getValue());
                }
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    public static void apiReserveSeat() {
        try {
            // API: 预定座位
            request = new Request.Builder()
                    .url("http://127.0.0.1:5000/seat/reserve/JB201_0/18-20")
                    .build();
            response = client.newCall(request).execute();
            jsonString = response.body().string();
            // 返回的结果是json格式的字符串, 需要进行解析
            Map<String, Object> map = mapper.readValue(jsonString, Map.class);
            // 解析完成后, map中包含两条Entry, 每一条Entry是一个键值对(k, v)
            // 第一条Entry是("Result", Boolean), 表示是否预定成功
            // 第二条Entry是("Description", String), 表示对成功/失败结果的解释
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    public static void apiRegister() {
        try {
            // API: 注册用户
            requestBody = new FormBody.Builder()
                    .add("Username", "test_user")
                    .add("Password", "123456")
                    .build();
            request = new Request.Builder()
                    .url("http://127.0.0.1:5000/register")
                    .post(requestBody)
                    .build();
            response = client.newCall(request).execute();
            jsonString = response.body().string();
            // 返回的结果是json格式的字符串, 需要进行解析
            Map<String, Object> map = mapper.readValue(jsonString, Map.class);
            // 解析完成后, map中包含两条Entry, 每一条Entry是一个键值对(k, v)
            // 第一条Entry是("Result", Boolean), 表示是否注册成功
            // 第二条Entry是("Description", String), 表示对成功/失败结果的解释
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
    public static void apiLogin() {
        try {
            // API: 登录用户
            requestBody = new FormBody.Builder()
                    .add("Username", "test_user")
                    .add("Password", "123456")
                    .build();
            request = new Request.Builder()
                    .url("http://127.0.0.1:5000/login")
                    .post(requestBody)
                    .build();
            response = client.newCall(request).execute();
            jsonString = response.body().string();
            // 返回的结果是json格式的字符串, 需要进行解析
            Map<String, Object> map = mapper.readValue(jsonString, Map.class);
            // 解析完成后, map中包含两条Entry, 每一条Entry是一个键值对(k, v)
            // 第一条Entry是("Result", Boolean), 表示是否注册成功
            // 第二条Entry是("Description", String), 表示对成功/失败结果的解释
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        apiLogin();
    }
}
