//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2025.01.17 at 08:18:11 PM MSK 
//


package org.korolev.dens.productservice.jaxb;

import jakarta.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="coordinates" type="{http://org/korolev/dens/productservice/jaxb}CoordinatesGetResponse" minOccurs="0"/&gt;
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="partNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="manufactureCost" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="unitOfMeasure" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="owner" type="{http://org/korolev/dens/productservice/jaxb}PersonGetResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "name",
    "coordinates",
    "price",
    "partNumber",
    "manufactureCost",
    "unitOfMeasure",
    "owner"
})
@XmlRootElement(name = "updateProductRequest", namespace = "http://org/korolev/dens/productservice/jaxb")
public class UpdateProductRequest {

    @XmlElement(required = true, namespace = "http://org/korolev/dens/productservice/jaxb")
    protected String id;
    @XmlElement(required = true, namespace = "http://org/korolev/dens/productservice/jaxb")
    protected String name;
    @XmlElement(namespace = "http://org/korolev/dens/productservice/jaxb")
    protected CoordinatesGetResponse coordinates;
    @XmlElement(namespace = "http://org/korolev/dens/productservice/jaxb")
    protected Double price;
    @XmlElement(required = true, namespace = "http://org/korolev/dens/productservice/jaxb")
    protected String partNumber;
    @XmlElement(required = true, namespace = "http://org/korolev/dens/productservice/jaxb")
    protected int manufactureCost;
    @XmlElement(required = true, namespace = "http://org/korolev/dens/productservice/jaxb")
    protected String unitOfMeasure;
    @XmlElement(namespace = "http://org/korolev/dens/productservice/jaxb")
    protected PersonGetResponse owner;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the coordinates property.
     * 
     * @return
     *     possible object is
     *     {@link CoordinatesGetResponse }
     *     
     */
    public CoordinatesGetResponse getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the value of the coordinates property.
     * 
     * @param value
     *     allowed object is
     *     {@link CoordinatesGetResponse }
     *     
     */
    public void setCoordinates(CoordinatesGetResponse value) {
        this.coordinates = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPrice(Double value) {
        this.price = value;
    }

    /**
     * Gets the value of the partNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartNumber() {
        return partNumber;
    }

    /**
     * Sets the value of the partNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartNumber(String value) {
        this.partNumber = value;
    }

    /**
     * Gets the value of the manufactureCost property.
     * 
     */
    public int getManufactureCost() {
        return manufactureCost;
    }

    /**
     * Sets the value of the manufactureCost property.
     * 
     */
    public void setManufactureCost(int value) {
        this.manufactureCost = value;
    }

    /**
     * Gets the value of the unitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Sets the value of the unitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitOfMeasure(String value) {
        this.unitOfMeasure = value;
    }

    /**
     * Gets the value of the owner property.
     * 
     * @return
     *     possible object is
     *     {@link PersonGetResponse }
     *     
     */
    public PersonGetResponse getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonGetResponse }
     *     
     */
    public void setOwner(PersonGetResponse value) {
        this.owner = value;
    }

}
