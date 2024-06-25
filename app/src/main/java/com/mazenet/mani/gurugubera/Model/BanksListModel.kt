package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class BanksListModel {
    @SerializedName("customer_bank_id")
    @Expose
    private var customerBankId: Int? = null
    @SerializedName("customer_bank_name")
    @Expose
    private var customerBankName: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param customerBankId
     * @param customerBankName
     */
    fun BanksListModel(customerBankId: Int?, customerBankName: String){

        this.customerBankId = customerBankId
        this.customerBankName = customerBankName
    }

    fun getCustomerBankId(): Int? {
        return customerBankId
    }

    fun setCustomerBankId(customerBankId: Int?) {
        this.customerBankId = customerBankId
    }

    fun getCustomerBankName(): String? {
        return customerBankName
    }

    fun setCustomerBankName(customerBankName: String) {
        this.customerBankName = customerBankName
    }
    override fun toString(): String {
        return this.customerBankName.toString()
    }
}