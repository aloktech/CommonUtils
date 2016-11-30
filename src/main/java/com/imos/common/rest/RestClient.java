/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.common.rest;

import static com.imos.common.rest.HttpMethod.DELETE;
import static com.imos.common.rest.HttpMethod.GET;
import static com.imos.common.rest.HttpMethod.POST;
import static com.imos.common.rest.HttpMethod.PUT;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import lombok.Getter;
import lombok.Setter;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author Alok
 */
public class RestClient {

    private WebTarget target;

    private Client client;

    @Setter
    @Getter
    private String baseUrl, data;
    @Setter
    @Getter
    private List<String> paths;
    @Setter
    @Getter
    private HttpMethod httpMethod;
    @Setter
    @Getter
    private Class classType;

    public RestClient() {
        paths = new ArrayList<>();
    }

    public RestClient setUrlPath() {
        target = client.target(getBaseURI());
        paths.stream().forEach((path) -> {
            target = target.path(path);
        });
        return this;
    }

    public String execute() {
        Response response;
        switch (httpMethod) {
            case GET:
                response = target.request(MediaType.APPLICATION_JSON).
                        get();

                return response.readEntity(String.class);
            case POST:
                response = target.request(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON).
                        post(Entity.entity(data, MediaType.APPLICATION_JSON));

                return response.readEntity(String.class);
            case PUT:
                break;
            case DELETE:
                break;
        }
        return "";
    }

    public String executePOST(String data) {
        Response response = target.request(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON).
                post(Entity.entity(data, MediaType.APPLICATION_JSON));

        return response.readEntity(String.class);
    }

    public RestClient configure() {
        ClientConfig config = new ClientConfig();

        client = ClientBuilder.newClient(config);

        return this;
    }

    private URI getBaseURI() {
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = "http://localhost:8097/";
        }
        return UriBuilder.fromUri(baseUrl).build();
    }

}
