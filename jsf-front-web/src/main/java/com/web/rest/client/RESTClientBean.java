package com.web.rest.client;

import com.web.jsf.dto.ContactDTO;
import com.web.jsf.utils.ParamUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

@ManagedBean(eager = true, name = "restClient")
@ApplicationScoped
public class RESTClientBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public RESTClientBean() {

    }

    @PostConstruct
    public void init() {

    }

    public Integer updateContact(ContactDTO contact) {
        HttpClient httpClient = new DefaultHttpClient();
        Integer userId = 0;
        try {
            HttpPut request = new HttpPut("http://localhost:9000/contact/update");
            JSONObject json = new JSONObject();
            json.put("id", contact.getId());
            json.put("firstName", contact.getFirstName());
            json.put("lastName", contact.getLastName());
            json.put("phone", contact.getPhone());
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "utf8");
            request.setEntity(params);
            HttpResponse response = (HttpResponse) httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            userId = ParamUtil.integerValue(EntityUtils.toString(entity));
          //  System.out.println("Returned: " + EntityUtils.toString(entity));
        } catch (IOException | ParseException ex) {

        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return userId;
    }

    public Integer createContact(ContactDTO contact) {
        HttpClient httpClient = new DefaultHttpClient();
        Integer userId = 0;
        try {
            HttpPost request = new HttpPost("http://localhost:9000/contact");
            JSONObject json = new JSONObject();
            json.put("firstName", contact.getFirstName());
            json.put("lastName", contact.getLastName());
            json.put("phone", contact.getPhone());
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "utf8");
            request.setEntity(params);
            HttpResponse response = (HttpResponse) httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            userId = ParamUtil.integerValue(EntityUtils.toString(entity));
           // System.out.println("Returned " + EntityUtils.toString(entity));
        } catch (IOException | ParseException ex) {

        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return userId;
    }

    public void deleteContact(Integer id) {
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpDelete request = new HttpDelete("http://localhost:9000/contact/delete/" + id);

            HttpResponse response = httpClient.execute(request);

          //  HttpEntity entity = response.getEntity();

         //  ObjectMapper mapper = new ObjectMapper();

          //  System.out.println("Response from delete  " + EntityUtils.toString(entity));

        } catch (IOException | ParseException ex) {

        } finally {
            httpClient.getConnectionManager().shutdown();
        }

    }

    public List<ContactDTO> getContactList() {
        //System.out.println("Contact list ");
        List<ContactDTO> list = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://localhost:9000/contact/all");
            HttpResponse response = client.execute(request);

            HttpEntity entity = response.getEntity();

            ObjectMapper mapper = new ObjectMapper();
           
            list = mapper.readValue(EntityUtils.toString(entity),List.class);
            
          //  list = (List<ContactDTO>) new Gson().fromJson(EntityUtils.toString(entity), ContactDTO.class);
           // System.out.println("List size " + list.size());

        } catch (Exception e) {

            e.printStackTrace();

        }
        return list;

    }

    public ContactDTO getContactById(Integer id) {
        ContactDTO contactDTO = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://localhost:9000/contact/" + id);
            HttpResponse response = client.execute(request);

            HttpEntity entity = response.getEntity();

            ObjectMapper mapper = new ObjectMapper();
            contactDTO = mapper.readValue((EntityUtils.toString(entity)), ContactDTO.class);
            //System.out.println("GET FIRSTNAME " + contactDTO.getFirstName());
         //   System.out.println("GET LASTNAME " + contactDTO.getLastName());
        } catch (Exception e) {

            e.printStackTrace();

        }
        return contactDTO;

    }

}
