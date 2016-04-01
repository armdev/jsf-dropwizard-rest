package com.project.dropwizard.resources;

import com.project.dropwizard.dao.ContactDAO;
import com.project.dropwizard.model.Contact;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.skife.jdbi.v2.DBI;

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

    private final ContactDAO contactDao;
    private final Validator validator;

    public ContactResource(DBI jdbi, Validator validator) {
        contactDao = jdbi.onDemand(ContactDAO.class);
        this.validator = validator;

    }

    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id") int id) {
        // retrieve information about the contact with the provided id
        Contact contact = contactDao.getContactById(id);
        return Response
                .ok(contact)
                .build();
    }

    @GET
    @Path("/all")
    public Response getAllContacts() {
        // retrieve information about the contact with the provided id
        List<Contact> contact = contactDao.findAll();
        return Response
                .ok(contact)
                .build();
    }

    @POST
    public Response createContact(Contact contact) throws URISyntaxException {
        // store the new contact
        int newContactId = contactDao.createContact(contact.getFirstName(), contact.getLastName(), contact.getPhone());
        return Response.created(new URI(String.valueOf(newContactId))).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteContact(@PathParam("id") int id) {
        // delete the contact with the provided id
        //System.out.println("Delete STARTED############# " + id);
        contactDao.deleteContact(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/update")
    public Response updateContact(Contact contact) {
        // update the contact with the provided ID
       // System.out.println("UPDATE STARTED############# " + contact);
        contactDao.updateContact(contact.getId(), contact.getFirstName(),
                contact.getLastName(), contact.getPhone());
        return Response.ok(
                new Contact(contact.getId(), contact.getFirstName(), contact.getLastName(),
                        contact.getPhone())).build();
    }

}
