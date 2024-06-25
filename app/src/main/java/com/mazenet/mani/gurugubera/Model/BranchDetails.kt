package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BranchDetails {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("tenant_id")
    @Expose
    var tenantId: Int? = null
    @SerializedName("head_office_id")
    @Expose
    var headOfficeId: Int? = null
    @SerializedName("branch_name")
    @Expose
    var branchName: String? = null
    @SerializedName("start_date")
    @Expose
    var startDate: String? = null
    @SerializedName("branch_code")
    @Expose
    var branchCode: String? = null
    @SerializedName("state_id")
    @Expose
    var stateId: Int? = null
    @SerializedName("district_id")
    @Expose
    var districtId: Int? = null
    @SerializedName("city_id")
    @Expose
    var cityId: Int? = null
    @SerializedName("pincode")
    @Expose
    var pincode: String? = null
    @SerializedName("branch_wise_penalty")
    @Expose
    var branchWisePenalty: Int? = null
    @SerializedName("bonus")
    @Expose
    var bonus: Int? = null
    @SerializedName("email_id")
    @Expose
    var emailId: String? = null
    @SerializedName("phone_no")
    @Expose
    var phoneNo: Int? = null
    @SerializedName("address_line_1")
    @Expose
    var addressLine1: String? = null
    @SerializedName("address_line_2")
    @Expose
    var addressLine2: String? = null
    @SerializedName("landmark")
    @Expose
    var landmark: String? = null
    @SerializedName("prize_subscriber_penalty")
    @Expose
    var prizeSubscriberPenalty: Int? = null
    @SerializedName("non_prize_subscriber_penalty")
    @Expose
    var nonPrizeSubscriberPenalty: Int? = null
    @SerializedName("bonus_days")
    @Expose
    var bonusDays: Int? = null
    @SerializedName("penalty_days")
    @Expose
    var penaltyDays: Int? = null
    @SerializedName("business_agent_target")
    @Expose
    var businessAgentTarget: Int? = null
    @SerializedName("branch_fd_total_months")
    @Expose
    var branchFdTotalMonths: Int? = null
    @SerializedName("remarks")
    @Expose
    var remarks: String? = null
    @SerializedName("agent_incentive_percent")
    @Expose
    var agentIncentivePercent: Int? = null
    @SerializedName("agent_commission_percent")
    @Expose
    var agentCommissionPercent: Int? = null
    @SerializedName("created_by")
    @Expose
    var createdBy: Int? = null
    @SerializedName("created_at")
    @Expose
    var createdAt: Any? = null
    @SerializedName("updated_by")
    @Expose
    var updatedBy: Int? = null
    @SerializedName("updated_at")
    @Expose
    var updatedAt: Any? = null
    @SerializedName("deleted_by")
    @Expose
    var deletedBy: Any? = null
    @SerializedName("deleted_at")
    @Expose
    var deletedAt: Any? = null
    @SerializedName("deletion_remark")
    @Expose
    var deletionRemark: String? = null
        get() = field
        set(value) {
            field = value
        }
    @SerializedName("status")
    @Expose
    var status: Int? = null
        get() = field
        set(value) {
            field = value
        }
    @SerializedName("sno")
    @Expose
    var sno: Int? = null
        get() = field
        set(value) {
            field = value
        }

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param tenantId
     * @param startDate
     * @param sno
     * @param cityId
     * @param agentIncentivePercent
     * @param remarks
     * @param updatedBy
     * @param agentCommissionPercent
     * @param id
     * @param emailId
     * @param districtId
     * @param createdAt
     * @param deletedAt
     * @param branchFdTotalMonths
     * @param nonPrizeSubscriberPenalty
     * @param deletedBy
     * @param status
     * @param deletionRemark
     * @param prizeSubscriberPenalty
     * @param businessAgentTarget
     * @param penaltyDays
     * @param branchCode
     * @param addressLine2
     * @param addressLine1
     * @param updatedAt
     * @param pincode
     * @param phoneNo
     * @param branchWisePenalty
     * @param landmark
     * @param createdBy
     * @param stateId
     * @param branchName
     * @param bonus
     * @param headOfficeId
     * @param bonusDays
     */
    constructor(
        id: Int?,
        tenantId: Int?,
        headOfficeId: Int?,
        branchName: String,
        startDate: String,
        branchCode: String,
        stateId: Int?,
        districtId: Int?,
        cityId: Int?,
        pincode: String,
        branchWisePenalty: Int?,
        bonus: Int?,
        emailId: String,
        phoneNo: Int?,
        addressLine1: String,
        addressLine2: String,
        landmark: String,
        prizeSubscriberPenalty: Int?,
        nonPrizeSubscriberPenalty: Int?,
        bonusDays: Int?,
        penaltyDays: Int?,
        businessAgentTarget: Int?,
        branchFdTotalMonths: Int?,
        remarks: String,
        agentIncentivePercent: Int?,
        agentCommissionPercent: Int?,
        createdBy: Int?,
        createdAt: Any,
        updatedBy: Int?,
        updatedAt: Any,
        deletedBy: Any,
        deletedAt: Any,
        deletionRemark: String,
        status: Int?,
        sno: Int?
    ) : super() {
        this.id = id
        this.tenantId = tenantId
        this.headOfficeId = headOfficeId
        this.branchName = branchName
        this.startDate = startDate
        this.branchCode = branchCode
        this.stateId = stateId
        this.districtId = districtId
        this.cityId = cityId
        this.pincode = pincode
        this.branchWisePenalty = branchWisePenalty
        this.bonus = bonus
        this.emailId = emailId
        this.phoneNo = phoneNo
        this.addressLine1 = addressLine1
        this.addressLine2 = addressLine2
        this.landmark = landmark
        this.prizeSubscriberPenalty = prizeSubscriberPenalty
        this.nonPrizeSubscriberPenalty = nonPrizeSubscriberPenalty
        this.bonusDays = bonusDays
        this.penaltyDays = penaltyDays
        this.businessAgentTarget = businessAgentTarget
        this.branchFdTotalMonths = branchFdTotalMonths
        this.remarks = remarks
        this.agentIncentivePercent = agentIncentivePercent
        this.agentCommissionPercent = agentCommissionPercent
        this.createdBy = createdBy
        this.createdAt = createdAt
        this.updatedBy = updatedBy
        this.updatedAt = updatedAt
        this.deletedBy = deletedBy
        this.deletedAt = deletedAt
        this.deletionRemark = deletionRemark
        this.status = status
        this.sno = sno
    }


}
