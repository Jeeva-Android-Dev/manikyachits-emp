package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class FeedbacksList {
    @SerializedName("customer_name")
    @Expose
    private var customerName: String? = null
    @SerializedName("customer_id")
    @Expose
    private var customerId: String? = null
    @SerializedName("feedback")
    @Expose
    private var feedback: String? = null
    @SerializedName("next_followup_date")
    @Expose
    private var nextFollowupDate: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param feedback
     * @param customerName
     * @param customerId
     * @param nextFollowupDate
     */
    fun FeedbacksList(customerName: String, customerId: String, feedback: String, nextFollowupDate: String) {

        this.customerName = customerName
        this.customerId = customerId
        this.feedback = feedback
        this.nextFollowupDate = nextFollowupDate
    }

    fun getCustomerName(): String? {
        return customerName
    }

    fun setCustomerName(customerName: String) {
        this.customerName = customerName
    }

    fun getCustomerId(): String? {
        return customerId
    }

    fun setCustomerId(customerId: String) {
        this.customerId = customerId
    }

    fun getFeedback(): String? {
        return feedback
    }

    fun setFeedback(feedback: String) {
        this.feedback = feedback
    }

    fun getNextFollowupDate(): String? {
        return nextFollowupDate
    }

    fun setNextFollowupDate(nextFollowupDate: String) {
        this.nextFollowupDate = nextFollowupDate
    }
}