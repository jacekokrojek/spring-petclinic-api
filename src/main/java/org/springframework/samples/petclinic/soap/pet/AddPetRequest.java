//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.07.29 at 01:16:33 PM CEST 
//


package org.springframework.samples.petclinic.soap.pet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pet" type="{http://petclinic.samples.springframework.org/soap/pet}Pet"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pet"
})
@XmlRootElement(name = "addPetRequest")
public class AddPetRequest {

    @XmlElement(required = true)
    protected Pet pet;

    /**
     * Gets the value of the pet property.
     * 
     * @return
     *     possible object is
     *     {@link Pet }
     *     
     */
    public Pet getPet() {
        return pet;
    }

    /**
     * Sets the value of the pet property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pet }
     *     
     */
    public void setPet(Pet value) {
        this.pet = value;
    }

}
