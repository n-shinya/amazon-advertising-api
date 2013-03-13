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

    public static void index(String keyword) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Operation", "ItemSearch");
        params.put("Keywords", keyword);
        params.put("ResponseGroup", "Large");
        params.put("SearchIndex", "All");

        HttpResponse res = WS.url(new SignedRequest().sign(params)).get();
        Document doc = res.getXml();
        Node node = XPath.selectNode("/ItemSearchResponse/Items/Item", doc);
        String title = XPath.selectText("ItemAttributes/Title", node);
        renderJSON("{'Title':" + title + "}");
    }

}