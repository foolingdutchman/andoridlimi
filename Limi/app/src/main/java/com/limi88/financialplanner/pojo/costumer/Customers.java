
package com.limi88.financialplanner.pojo.costumer;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Customers {

    @SerializedName("customer")
    @Expose
    private Customer customer;

    /**
     * 
     * @return
     *     The customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * 
     * @param customer
     *     The customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
