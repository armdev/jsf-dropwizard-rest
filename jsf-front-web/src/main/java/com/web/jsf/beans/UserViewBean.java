package com.web.jsf.beans;

import com.web.jsf.dto.ContactDTO;
import com.web.rest.client.RESTClientBean;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.faces.view.ViewScoped;

/**
 *
 * @author Home
 */

@ManagedBean(name = "userViewBean")
@ViewScoped
public class UserViewBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{restClient}")
    private RESTClientBean restClient;

    private ContactDTO contact = new ContactDTO();
    @ManagedProperty("#{param.userId}")
    private Integer userId;
    private FacesContext context = null;
    private ExternalContext externalContext = null;

    public UserViewBean() {
        context = FacesContext.getCurrentInstance();
        externalContext = context.getExternalContext();
        contact = new ContactDTO();
    }

    @PostConstruct
    public void init() {
        contact = restClient.getContactById(userId);
        if(contact == null){
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.jsf?hack==false");
            } catch (IOException ex) {
                Logger.getLogger(UserViewBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String update() {
       // System.out.println("userId frontend " + userId);
        contact.setId(userId);
        restClient.updateContact(contact);
        return "index";

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

}
