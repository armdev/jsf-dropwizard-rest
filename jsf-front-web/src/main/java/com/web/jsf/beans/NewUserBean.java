package com.web.jsf.beans;

import com.web.jsf.dto.ContactDTO;
import com.web.rest.client.RESTClientBean;
import java.io.Serializable;
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
@ManagedBean(name = "newUserBean")
@ViewScoped
public class NewUserBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{restClient}")
    private RESTClientBean restClient;

    private ContactDTO contact = new ContactDTO();

    public NewUserBean() {

    }

    @PostConstruct
    public void init() {

    }

    public String addNewContact() {
        restClient.createContact(contact);
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
