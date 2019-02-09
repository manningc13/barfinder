package com.barfinder.barfinder.fetch;

import com.fasterxml.jackson.databind.util.JSONPObject;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import sun.net.www.http.HttpClient;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Component
@RequiredArgsConstructor
@Slf4j
public class MapLocationFetcher {

//    private final Logger logger = LoggerFactory.getLogger(MapLocationFetcher.class);
    private final String API_KEY = "AIzaSyCsjREtKsKSf7F3xf0lXNpkkAjibiXbVKM";
    private final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/xml?adddress=";
    private Integer responseCode = 0;

    public Pair<Integer, Integer> getClosestBar(String destination) throws Exception{
        String[] destinationLocation = getLongAndLat(destination);
        String latAndLong = destinationLocation[0] + "," + destinationLocation[1];
        String apiUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latAndLong + "&radius=1500&type=bar&keyword=irish&key=" + API_KEY;
        URL url = new URL(apiUrl);
        return new Pair(0,0);
    }

    public String[] getLongAndLat(String address) throws Exception  {
        String apiUrl = BASE_URL + URLEncoder.encode(address, "UTF-8") + "key=" + API_KEY + "&sensors=true";
        URL url = new URL(apiUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.connect();
        responseCode = httpURLConnection.getResponseCode();
        if (responseCode.equals(200)) {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
            Document document = builder.parse(httpURLConnection.getInputStream());

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("/GeocodeResponse/status");

            String status = (String)expr.evaluate(document, XPathConstants.STRING);
            if(status.equals("OK")) {
                expr = xpath.compile("//geometry/location/lat");
                String latitude = (String)expr.evaluate(document, XPathConstants.STRING);
                expr = xpath.compile("//geometry/location/lng");
                String longitude = (String)expr.evaluate(document, XPathConstants.STRING);
                return new String[] {latitude, longitude};
            }
            else
            {
                throw new Exception("Error from the API - response status: "+status);
            }
        }

        return null;
    }
}
