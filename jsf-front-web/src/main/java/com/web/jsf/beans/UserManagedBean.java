package com.web.jsf.beans;

import com.web.jsf.dto.ContactDTO;
import com.web.rest.client.RESTClientBean;
import java.io.Serializable;
import java.util.List;
import java.util.PropertyResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Home
 */
@ManagedBean(name = "userManagedBean")
@ViewScoped
public class UserManagedBean implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @ManagedProperty(value="#{restClient}")
    private RESTClientBean restClient;
    private Long userCount;

    private ContactDTO contact = new ContactDTO();

    public UserManagedBean() {

    }

    @PostConstruct
    public void init() {

    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public List<ContactDTO> getContactList() {
     //   System.out.println("Calling the list "+ restClient.getContactList());
        return restClient.getContactList();
    }
    
      public String deleteUser(Integer id) {
        if (id != null) {
             // System.out.println("Delete with id "+ id);
            restClient.deleteContact(id);
        }
        return "index";
    }


    public ContactDTO getContact() {
        return contact;
    }

    public void setContact(ContactDTO contact) {
        this.contact = contact;
    }

    public void setRestClient(RESTClientBean restClient) {
        this.restClient = restClient;
    }

    
    
    public PropertyResourceBundle getBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{i18n}", PropertyResourceBundle.class);
    }

}
