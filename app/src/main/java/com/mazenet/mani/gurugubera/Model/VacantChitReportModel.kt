package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class VacantChitReportModel {
    @SerializedName("group_id")
    @Expose
    private var groupId: String? = null
    @SerializedName("branch_id")
    @Expose
    private var branchId: String? = null
    @SerializedName("branch_name")
    @Expose
    private var branchName: String? = null
    @SerializedName("group_name")
    @Expose
    private var groupName: String? = null
    @SerializedName("group_starting_date")
    @Expose
    private var groupStartingDate: String? = null
    @SerializedName("chit_value")
    @Expose
    private var chitValue: String? = null
    @SerializedName("no_of_vacants")
    @Expose
    private var noOfVacants: String? = null
    @SerializedName("no_of_dummy_chits")
    @Expose
    private var noOfDummyChits: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param groupId
     * @param groupStartingDate
     * @param groupName
     * @param noOfVacants
     * @param chitValue
     * @param branchId
     * @param branchName
     * @param noOfDummyChits
     */
    fun VacantChitReportModel(
        groupId: String,
        branchId: String,
        branchName: String,
        groupName: String,
        groupStartingDate: String,
        chitValue: String,
        noOfVacants: String,
        noOfDummyChits: String
    ){

        this.groupId = groupId
        this.branchId = branchId
        this.branchName = branchName
        this.groupName = groupName
        this.groupStartingDate = groupStartingDate
        this.chitValue = chitValue
        this.noOfVacants = noOfVacants
        this.noOfDummyChits = noOfDummyChits
    }

    fun getGroupId(): String? {
        return groupId
    }

    fun setGroupId(groupId: String) {
        this.groupId = groupId
    }

    fun getBranchId(): String? {
        return branchId
    }

    fun setBranchId(branchId: String) {
        this.branchId = branchId
    }

    fun getBranchName(): String? {
        return branchName
    }

    fun setBranchName(branchName: String) {
        this.branchName = branchName
    }

    fun getGroupName(): String? {
        return groupName
    }

    fun setGroupName(groupName: String) {
        this.groupName = groupName
    }

    fun getGroupStartingDate(): String? {
        return groupStartingDate
    }

    fun setGroupStartingDate(groupStartingDate: String) {
        this.groupStartingDate = groupStartingDate
    }

    fun getChitValue(): String? {
        return chitValue
    }

    fun setChitValue(chitValue: String) {
        this.chitValue = chitValue
    }

    fun getNoOfVacants(): String? {
        return noOfVacants
    }

    fun setNoOfVacants(noOfVacants: String) {
        this.noOfVacants = noOfVacants
    }

    fun getNoOfDummyChits(): String? {
        return noOfDummyChits
    }

    fun setNoOfDummyChits(noOfDummyChits: String) {
        this.noOfDummyChits = noOfDummyChits
    }


}