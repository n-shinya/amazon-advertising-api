package controllers;

import helper.SignedRequest;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.XPath;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemSearch");
        params.put("Keywords", "4902370518986");
        params.put("ResponseGroup", "Medium");
        params.put("SearchIndex", "All");

        HttpResponse res = WS.url(new SignedRequest().sign(params)).get();
        System.out.println(res.getString());
        Document doc = res.getXml();
        Node node = XPath.selectNode("/ItemSearchResponse/Items/Item", doc);
        String title = XPath.selectText("ItemAttributes/Title", node);
        renderJSON("{'Title':" + title + "}");
    }

}