
package com.limi88.financialplanner.pojo.clients;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class SearchConditions implements Parcelable {

    @SerializedName("customer_tags")
    @Expose
    private List<String> customerTags = new ArrayList<String>();

    /**
     * 
     * @return
     *     The customerTags
     */
    public List<String> getCustomerTags() {
        return customerTags;
    }

    /**
     * 
     * @param customerTags
     *     The customer_tags
     */
    public void setCustomerTags(List<String> customerTags) {
        this.customerTags = customerTags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.customerTags);
    }

    public SearchConditions() {
    }

    protected SearchConditions(Parcel in) {
        this.customerTags = in.createStringArrayList();
    }

    public static final Parcelable.Creator<SearchConditions> CREATOR = new Parcelable.Creator<SearchConditions>() {
        @Override
        public SearchConditions createFromParcel(Parcel source) {
            return new SearchConditions(source);
        }

        @Override
        public SearchConditions[] newArray(int size) {
            return new SearchConditions[size];
        }
    };
}
