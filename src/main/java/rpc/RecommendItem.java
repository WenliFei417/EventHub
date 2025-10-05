package rpc;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

@WebServlet(name = "RecommendItem", urlPatterns = {"/recommendation"})
public class RecommendItem extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        JSONArray events = new JSONArray()
                .put(new JSONObject()
                        .put("name", "abcd")
                        .put("address", "san francisco")
                        .put("time", "01/01/2017"))
                .put(new JSONObject()
                        .put("name", "1234")
                        .put("address", "san jose")
                        .put("time", "01/02/2017"));

        RpcHelper.writeJsonArray(resp, events);
    }
}