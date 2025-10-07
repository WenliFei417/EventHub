package rpc;

import entity.Item;
import external.TicketMasterAPI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchItem", urlPatterns = {"/search"})
public class SearchItem extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        JSONArray arr = new JSONArray()
//               .put(new JSONObject().put("username", "abcd"))
//                .put(new JSONObject().put("username", "1234"));
//        RpcHelper.writeJsonArray(resp, arr);
        JSONArray array = new JSONArray();
        try {
            double lat = Double.parseDouble(request.getParameter("lat"));
            double lon = Double.parseDouble(request.getParameter("lon"));
            String keyword = request.getParameter("term");

            TicketMasterAPI tmAPI = new TicketMasterAPI();
            List<Item> items = tmAPI.search(lat, lon, keyword);

            for (Item item : items) {
                JSONObject obj = item.toJSONObject();
                array.put(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        RpcHelper.writeJsonArray(response, array);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        resp.getWriter().println("Search POST invoked!");
    }
}
