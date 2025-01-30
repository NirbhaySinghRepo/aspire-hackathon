package com.adobe.aem.guides.aspire.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Model(adaptables = SlingHttpServletRequest.class)
public class PSSearchModel {
    private static final Logger LOG = LoggerFactory.getLogger(PSSearchModel.class);

    int responseCode = 301;
    String searchResponse = "";

    @ValueMapValue
    private String searchApi;

    @PostConstruct
    protected void init() throws IOException {
        URL url = new URL(searchApi);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        responseCode = connection.getResponseCode();
        LOG.info("Response code is : " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            StringBuilder response = new StringBuilder();
            while (in.readLine() != null) {
                inputLine = in.readLine();
                response.append(inputLine);
            }
            in.close();
            searchResponse = response.toString();
            LOG.info("Response is : " + response.toString());

        }

    }

    public int getResponseCode () {
        return responseCode;
    }

    public String getResponse () {
        return searchResponse;
    }
}
