package controllers;

import helper.SignedRequest;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.XPath;
import play.mvc.Controller;

import com.google.gson.Gson;

public class Application extends Controller {

    public static void index(String keyword) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemSearch");
        params.put("Keywords", keyword);
        params.put("ResponseGroup", "Medium");
        params.put("SearchIndex", "All");

        HttpResponse res = WS.url(new SignedRequest().sign(params)).get();
        System.out.println(res.getString());
        Document doc = res.getXml();
        Node node = XPath.selectNode("/ItemSearchResponse/Items/Item", doc);
        
        Map<String, String> item = new HashMap<String, String>();
        item.put("title", XPath.selectText("ItemAttributes/Title", node));
        item.put("company", XPath.selectText("ItemAttributes/Label", node));
        item.put("image", XPath.selectText("MediumImage/URL", node));
        item.put("price", XPath.selectText("OfferSummary/LowestNewPrice/Amount", node));
        renderJSON(new Gson().toJson(item));
    }

}