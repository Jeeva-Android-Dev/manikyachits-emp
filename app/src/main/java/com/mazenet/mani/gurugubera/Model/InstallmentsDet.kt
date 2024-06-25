package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class InstallmentsDet {
    @SerializedName("entrollment_id")
    @Expose
    private var entrollmentId: String? = null



    @SerializedName("auction_no")
    @Expose
    private var auctionNo: String? = null
    @SerializedName("bidding_id")
    @Expose
    private var biddingId: String? = null
    @SerializedName("pending_due")
    @Expose
    private var pendingDue: String? = null
    @SerializedName("discount_on_due")
    @Expose
    private var discountOnDue: String? = null
    @SerializedName("discount_on_penalty")
    @Expose
    private var discountOnPenalty: String? = null
    @SerializedName("bonus_days")
    @Expose
    private var bonusDays: String? = null
    @SerializedName("penalty_percentage")
    @Expose
    private var penaltyPercentage: String? = null
    @SerializedName("bonus_percentage")
    @Expose
    private var bonusPercentage: String? = null
    @SerializedName("bonus_amount")
    @Expose
    private var bonusAmount: String? = null
    @SerializedName("instal_pending_day")
    @Expose
    private var instalPendingDay: String? = null
    @SerializedName("paid_amount")
    @Expose
    private var paidAmount: String? = null
    @SerializedName("current_installment_amount")
    @Expose
    private var currentInstallmentAmount: String? = null
    @SerializedName("penalty_amounts")
    @Expose
    private var penaltyAmounts: String? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param bonusPercentage
     * @param discountOnDue
     * @param biddingId
     * @param pendingDue
     * @param bonusAmount
     * @param instalPendingDay
     * @param penaltyPercentage
     * @param entrollmentId
     * @param paidAmount
     * @param auctionNo
     * @param penaltyAmounts
     * @param currentInstallmentAmount
     * @param discountOnPenalty
     * @param bonusDays
     */
    fun InstallmentsDet(
        entrollmentId: String,
        auctionNo: String,
        biddingId: String,
        pendingDue: String,
        discountOnDue: String,
        discountOnPenalty: String,
        bonusDays: String,
        penaltyPercentage: String,
        bonusPercentage: String,
        bonusAmount: String,
        instalPendingDay: String,
        paidAmount: String,
        currentInstallmentAmount: String,
        penaltyAmounts: String
    ) {

        this.entrollmentId = entrollmentId
        this.auctionNo = auctionNo
        this.biddingId = biddingId
        this.pendingDue = pendingDue
        this.discountOnDue = discountOnDue
        this.discountOnPenalty = discountOnPenalty
        this.bonusDays = bonusDays
        this.penaltyPercentage = penaltyPercentage
        this.bonusPercentage = bonusPercentage
        this.bonusAmount = bonusAmount
        this.instalPendingDay = instalPendingDay
        this.paidAmount = paidAmount
        this.currentInstallmentAmount = currentInstallmentAmount
        this.penaltyAmounts = penaltyAmounts
    }

    fun getEntrollmentId(): String? {
        return entrollmentId
    }

    fun setEntrollmentId(entrollmentId: String) {
        this.entrollmentId = entrollmentId
    }

    fun getAuctionNo(): String? {
        return auctionNo
    }

    fun setAuctionNo(auctionNo: String) {
        this.auctionNo = auctionNo
    }



    fun getBiddingId(): String? {
        return biddingId
    }

    fun setBiddingId(biddingId: String) {
        this.biddingId = biddingId
    }

    fun getPendingDue(): String? {
        return pendingDue
    }

    fun setPendingDue(pendingDue: String) {
        this.pendingDue = pendingDue
    }

    fun getDiscountOnDue(): String? {
        return discountOnDue
    }

    fun setDiscountOnDue(discountOnDue: String) {
        this.discountOnDue = discountOnDue
    }

    fun getDiscountOnPenalty(): String? {
        return discountOnPenalty
    }

    fun setDiscountOnPenalty(discountOnPenalty: String) {
        this.discountOnPenalty = discountOnPenalty
    }

    fun getBonusDays(): String? {
        return bonusDays
    }

    fun setBonusDays(bonusDays: String) {
        this.bonusDays = bonusDays
    }

    fun getPenaltyPercentage(): String? {
        return penaltyPercentage
    }

    fun setPenaltyPercentage(penaltyPercentage: String) {
        this.penaltyPercentage = penaltyPercentage
    }

    fun getBonusPercentage(): String? {
        return bonusPercentage
    }

    fun setBonusPercentage(bonusPercentage: String) {
        this.bonusPercentage = bonusPercentage
    }

    fun getBonusAmount(): String? {
        return bonusAmount
    }

    fun setBonusAmount(bonusAmount: String) {
        this.bonusAmount = bonusAmount
    }

    fun getInstalPendingDay(): String? {
        return instalPendingDay
    }

    fun setInstalPendingDay(instalPendingDay: String) {
        this.instalPendingDay = instalPendingDay
    }

    fun getPaidAmount(): String? {
        return paidAmount
    }

    fun setPaidAmount(paidAmount: String) {
        this.paidAmount = paidAmount
    }

    fun getCurrentInstallmentAmount(): String? {
        return currentInstallmentAmount
    }

    fun setCurrentInstallmentAmount(currentInstallmentAmount: String) {
        this.currentInstallmentAmount = currentInstallmentAmount
    }

    fun getPenaltyAmounts(): String? {
        return penaltyAmounts
    }

    fun setPenaltyAmounts(penaltyAmounts: String) {
        this.penaltyAmounts = penaltyAmounts
    }
}