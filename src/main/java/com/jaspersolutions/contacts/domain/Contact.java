package com.jaspersolutions.contacts.domain;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@Table(uniqueConstraints=
@UniqueConstraint(columnNames = {"firstName", "lastName"}))
public class Contact {

    /**
     */
    @NotNull
    @Size(min = 3,message = "Enter Legal First Name")
    private String firstName;

    /**
     */
    @NotNull
    @Size(min = 3,message = "Enter Legal Last Name")
    private String lastName;

    /**
     */
    @NotNull
    @Size(min = 3)
    private String address;

    /**
     */
    @Enumerated
    private ContactType contactType;

    /**
     */
    @Size(min=10,max=10,message = "Enter10DigitMobileNumber")
    @Column(unique=true) 
    private String phone;
    private byte[] image;
    private String contentType;
}
