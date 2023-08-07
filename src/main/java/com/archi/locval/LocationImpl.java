package com.archi.locval;

import java.io.IOException;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Service
public class LocationImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;

    @Override
    public String scrapeWebsite() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("https://www.shoppersstop.com/store-finder");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String html = EntityUtils.toString(response.getEntity());

                Document document = Jsoup.parse(html);
                Elements addresses = document.select("#map_canvas");

                String dataStores = addresses.toString().split("data-stores=\"")[1].split("\"")[0];
                String stores = "{data:" + dataStores.substring(dataStores.indexOf("["));

                stores = stores.replaceAll("&quot;", "\\\"");

                JSONArray storesArray = new JSONObject(stores).getJSONArray("data");

                for (int i = 0; i < storesArray.length(); i++) {
                    JSONObject storeObject = storesArray.getJSONObject(i);
                    String latitude = storeObject.getString("latitude");
                    String longitude = storeObject.getString("longitude");
                    String name = storeObject.getString("name");

                    System.out.print("Location:: " + name + " >> ");
                    Location location = new Location();
                    location.setLatitude(latitude);
                    location.setLongitude(longitude);
                    location.setName(name);
                    location.setDeleted(false);
                    location.setValid(checkIfValid(location));
                    // locationDao.saveLocation(location);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        return "Done";
    }

    public boolean checkIfValid(Location loc) {
        String apiKey = "dummy_key"; // Replace with your actual API key
        boolean isValid = false;
        Double latitude = Double.valueOf(loc.getLatitude()); // Latitude of the location to check
        Double longitude = Double.valueOf(loc.getLongitude()); // Longitude of the location to check
        String keyword = "Shoppers Stop"; // Keyword for the store you want to validate
        try {
            // Construct the URL for the Places API request
            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                    "?location=" + latitude + "," + longitude +
                    "&radius=100" +
                    "&keyword=" + URLEncoder.encode(keyword, "UTF-8") +
                    "&key=" + apiKey;

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder().url(url).method("GET", null)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String responseData = responseBody.string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray resultsArray = jsonObject.getJSONArray("results");
                    if (resultsArray.length() > 0) {
                        JSONObject firstResult = resultsArray.getJSONObject(0);
                        String name = firstResult.getString("name");
                        System.out.println("Store name :: " + name);
                        // Check if the response contains the word "shoppers stop"
                        if (name.equalsIgnoreCase("Shoppers Stop") || name.contains("Shoppers Stop")) {
                            isValid = true;
                        }
                    }
                    else{
                        System.out.println("NO RESULTS");
                    }
                }
            } else {
                System.out.println("Request failed: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    @Override
    public List<InvalidLocationDTO> getInvalidLocations() {
        List<InvalidLocationDTO> invalidLocations = new ArrayList<>();
        List<Location> list = locationDao.getInvalidLocations();

        for(Location location : list){
            InvalidLocationDTO  invalidLocation = new InvalidLocationDTO(location.getName(), location.getLatitude(), location.getLongitude());
            invalidLocations.add(invalidLocation);
        }
        return invalidLocations;
    }
}
