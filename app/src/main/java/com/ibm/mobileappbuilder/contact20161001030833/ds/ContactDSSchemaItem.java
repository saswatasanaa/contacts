
package com.ibm.mobileappbuilder.contact20161001030833.ds;

import ibmmobileappbuilder.mvp.model.MutableIdentifiableBean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class ContactDSSchemaItem implements Parcelable, MutableIdentifiableBean {

    private transient String cloudantIdentifiableId;

    @SerializedName("Name") public String name;
    @SerializedName("Email") public String email;
    @SerializedName("Phone") public String phone;
    @SerializedName("org") public String org;
    @SerializedName("Comments") public String comments;

    @Override
    public void setIdentifiableId(String id) {
        this.cloudantIdentifiableId = id;
    }

    @Override
    public String getIdentifiableId() {
        return cloudantIdentifiableId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cloudantIdentifiableId);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(org);
        dest.writeString(comments);
    }

    public static final Creator<ContactDSSchemaItem> CREATOR = new Creator<ContactDSSchemaItem>() {
        @Override
        public ContactDSSchemaItem createFromParcel(Parcel in) {
            ContactDSSchemaItem item = new ContactDSSchemaItem();
            item.cloudantIdentifiableId = in.readString();

            item.name = in.readString();
            item.email = in.readString();
            item.phone = in.readString();
            item.org = in.readString();
            item.comments = in.readString();
            return item;
        }

        @Override
        public ContactDSSchemaItem[] newArray(int size) {
            return new ContactDSSchemaItem[size];
        }
    };
}


