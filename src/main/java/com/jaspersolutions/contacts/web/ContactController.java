package com.jaspersolutions.contacts.web;
import com.jaspersolutions.contacts.domain.Contact;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.jaspersolutions.contacts.domain.ContactType;

import org.apache.commons.io.IOUtils;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@RequestMapping("/contacts")
@Controller
@RooWebScaffold(path = "contacts", formBackingObject = Contact.class)
public class ContactController {
	
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid Contact contact, BindingResult bindingResult, Model uiModel, 
	        @RequestParam("image") MultipartFile multipartFile,
	        HttpServletRequest httpServletRequest) {
	    if (bindingResult.hasErrors()) {
	        populateEditForm(uiModel, contact);
	        return "contacts/create";
	    }
	    uiModel.asMap().clear();
	    contact.setContentType(multipartFile.getContentType());
	    contact.persist();
	    return "redirect:/contacts/" + encodeUrlPathSegment(contact.getId().toString(), httpServletRequest);
	}
	@RequestMapping(value = "/{id}/image", method = RequestMethod.GET)
    public String showImage(@PathVariable("id") Long id, HttpServletResponse response, Model model) {
        Contact contact = Contact.findContact(id);
        if (contact != null) {
            byte[] image = contact.getImage();
            if (image != null) {
                try {
                    response.setContentType(contact.getContentType());
                    OutputStream out = response.getOutputStream();
                    IOUtils.copy(new ByteArrayInputStream(image), out);
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }	
	@InitBinder
	protected void initBinder(HttpServletRequest request,
	        ServletRequestDataBinder binder) throws ServletException {
	    binder.registerCustomEditor(byte[].class,
	            new ByteArrayMultipartFileEditor());
	}

}

