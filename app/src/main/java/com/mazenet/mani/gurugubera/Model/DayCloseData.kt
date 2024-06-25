package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName




class DayCloseData {

    @SerializedName("start_date")
    @Expose
    var startDate: String? = null

    @SerializedName("branch_info")
    @Expose
    var branchInfo: String? = null

    @SerializedName("cash_collection")
    @Expose
    var cashCollection: String? = null

    @SerializedName("cash_penalty")
    @Expose
    var cashPenalty: String? = null

    @SerializedName("advance_cash")
    @Expose
    var advanceCash: String? = null

    @SerializedName("other_charges_cash")
    @Expose
    var otherChargesCash: String? = null

    @SerializedName("cheque_received_collection")
    @Expose
    var chequeReceivedCollection: String? = null

    @SerializedName("cheque_penalty")
    @Expose
    var chequePenalty: String? = null

    @SerializedName("advance_cheque")
    @Expose
    var advanceCheque: String? = null

    @SerializedName("other_charges_cheque")
    @Expose
    var otherChargesCheque: String? = null

    @SerializedName("cheque_cleared_collection")
    @Expose
    var chequeClearedCollection: String? = null

    @SerializedName("cheque_cleared_penalty")
    @Expose
    var chequeClearedPenalty: String? = null

    @SerializedName("advance_cleared_cheque")
    @Expose
    var advanceClearedCheque: String? = null

    @SerializedName("dd_collection")
    @Expose
    var ddCollection: String? = null

    @SerializedName("dd_penalty")
    @Expose
    var ddPenalty: String? = null

    @SerializedName("advance_dd")
    @Expose
    var advanceDd: String? = null

    @SerializedName("other_charges_dd")
    @Expose
    var otherChargesDd: String? = null

    @SerializedName("neft_collection")
    @Expose
    var neftCollection: String? = null

    @SerializedName("neft_penalty")
    @Expose
    var neftPenalty: String? = null

    @SerializedName("advance_neft")
    @Expose
    var advanceNeft: String? = null

    @SerializedName("other_charges_neft")
    @Expose
    var otherChargesNeft: String? = null

    @SerializedName("card_collection")
    @Expose
    var cardCollection: String? = null

    @SerializedName("card_penalty")
    @Expose
    var cardPenalty: String? = null

    @SerializedName("advance_card")
    @Expose
    var advanceCard: String? = null

    @SerializedName("bp_adjust_collection")
    @Expose
    var bpAdjustCollection: String? = null

    @SerializedName("bp_adjust_penalty")
    @Expose
    var bpAdjustPenalty: String? = null

    @SerializedName("adv_adjustment_collection")
    @Expose
    var advAdjustmentCollection: String? = null

    @SerializedName("adv_adjustment_penalty")
    @Expose
    var advAdjustmentPenalty: String? = null

    @SerializedName("cash_payment")
    @Expose
    var cashPayment: String? = null

    @SerializedName("cheque_payment")
    @Expose
    var chequePayment: String? = null

    @SerializedName("dd_payment")
    @Expose
    var ddPayment: String? = null

    @SerializedName("neft_payment")
    @Expose
    var neftPayment: String? = null

    @SerializedName("card_payment")
    @Expose
    var cardPayment: String? = null

    @SerializedName("other_branch_cash_collection")
    @Expose
    var otherBranchCashCollection: String? = null

    @SerializedName("other_branch_cash_penalty")
    @Expose
    var otherBranchCashPenalty: String? = null

    @SerializedName("other_branch_cheque_collection")
    @Expose
    var otherBranchChequeCollection: String? = null

    @SerializedName("other_branch_cheque_penalty")
    @Expose
    var otherBranchChequePenalty: String? = null

    @SerializedName("other_branch_dd_collection")
    @Expose
    var otherBranchDdCollection: String? = null

    @SerializedName("other_branch_dd_penalty")
    @Expose
    var otherBranchDdPenalty: String? = null

    @SerializedName("other_branch_neft_collection")
    @Expose
    var otherBranchNeftCollection: String? = null

    @SerializedName("other_branch_neft_penalty")
    @Expose
    var otherBranchNeftPenalty: String? = null

    @SerializedName("other_branch_card_collection")
    @Expose
    var otherBranchCardCollection: String? = null

    @SerializedName("other_branch_card_penalty")
    @Expose
    var otherBranchCardPenalty: String? = null

    @SerializedName("total_cash")
    @Expose
    var totalCash: String? = null

    @SerializedName("total_cheque_left")
    @Expose
    var totalChequeLeft: String? = null

    @SerializedName("total_cheque_right")
    @Expose
    var totalChequeRight: String? = null

    @SerializedName("total_dd")
    @Expose
    var totalDd: String? = null

    @SerializedName("total_neft")
    @Expose
    var totalNeft: String? = null

    @SerializedName("total_card")
    @Expose
    var totalCard: String? = null

    @SerializedName("total_payment")
    @Expose
    var totalPayment: String? = null

    @SerializedName("total_adjustment")
    @Expose
    var totalAdjustment: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param cashPayment
     * @param otherChargesCash
     * @param chequePenalty
     * @param otherBranchChequeCollection
     * @param otherBranchCashPenalty
     * @param bpAdjustPenalty
     * @param totalPayment
     * @param advanceCash
     * @param advanceCheque
     * @param cashCollection
     * @param neftPenalty
     * @param otherChargesNeft
     * @param totalCash
     * @param chequeReceivedCollection
     * @param otherBranchCashCollection
     * @param advanceNeft
     * @param totalDd
     * @param neftCollection
     * @param cardCollection
     * @param totalChequeRight
     * @param totalChequeLeft
     * @param totalAdjustment
     * @param otherChargesCheque
     * @param totalNeft
     * @param startDate
     * @param advanceClearedCheque
     * @param otherBranchCardPenalty
     * @param otherChargesDd
     * @param chequePayment
     * @param ddPenalty
     * @param branchInfo
     * @param cashPenalty
     * @param chequeClearedCollection
     * @param neftPayment
     * @param cardPayment
     * @param otherBranchDdCollection
     * @param cardPenalty
     * @param ddPayment
     * @param advanceDd
     * @param otherBranchDdPenalty
     * @param chequeClearedPenalty
     * @param bpAdjustCollection
     * @param otherBranchNeftPenalty
     * @param otherBranchChequePenalty
     * @param advanceCard
     * @param advAdjustmentCollection
     * @param advAdjustmentPenalty
     * @param totalCard
     * @param ddCollection
     * @param otherBranchNeftCollection
     * @param otherBranchCardCollection
     */
    constructor(
        startDate: String?,
        branchInfo: String?,
        cashCollection: String?,
        cashPenalty: String?,
        advanceCash: String?,
        otherChargesCash: String?,
        chequeReceivedCollection: String?,
        chequePenalty: String?,
        advanceCheque: String?,
        otherChargesCheque: String?,
        chequeClearedCollection: String?,
        chequeClearedPenalty: String?,
        advanceClearedCheque: String?,
        ddCollection: String?,
        ddPenalty: String?,
        advanceDd: String?,
        otherChargesDd: String?,
        neftCollection: String?,
        neftPenalty: String?,
        advanceNeft: String?,
        otherChargesNeft: String?,
        cardCollection: String?,
        cardPenalty: String?,
        advanceCard: String?,
        bpAdjustCollection: String?,
        bpAdjustPenalty: String?,
        advAdjustmentCollection: String?,
        advAdjustmentPenalty: String?,
        cashPayment: String?,
        chequePayment: String?,
        ddPayment: String?,
        neftPayment: String?,
        cardPayment: String?,
        otherBranchCashCollection: String?,
        otherBranchCashPenalty: String?,
        otherBranchChequeCollection: String?,
        otherBranchChequePenalty: String?,
        otherBranchDdCollection: String?,
        otherBranchDdPenalty: String?,
        otherBranchNeftCollection: String?,
        otherBranchNeftPenalty: String?,
        otherBranchCardCollection: String?,
        otherBranchCardPenalty: String?,
        totalCash: String?,
        totalChequeLeft: String?,
        totalChequeRight: String?,
        totalDd: String?,
        totalNeft: String?,
        totalCard: String?,
        totalPayment: String?,
        totalAdjustment: String?
    ) : super() {
        this.startDate = startDate
        this.branchInfo = branchInfo
        this.cashCollection = cashCollection
        this.cashPenalty = cashPenalty
        this.advanceCash = advanceCash
        this.otherChargesCash = otherChargesCash
        this.chequeReceivedCollection = chequeReceivedCollection
        this.chequePenalty = chequePenalty
        this.advanceCheque = advanceCheque
        this.otherChargesCheque = otherChargesCheque
        this.chequeClearedCollection = chequeClearedCollection
        this.chequeClearedPenalty = chequeClearedPenalty
        this.advanceClearedCheque = advanceClearedCheque
        this.ddCollection = ddCollection
        this.ddPenalty = ddPenalty
        this.advanceDd = advanceDd
        this.otherChargesDd = otherChargesDd
        this.neftCollection = neftCollection
        this.neftPenalty = neftPenalty
        this.advanceNeft = advanceNeft
        this.otherChargesNeft = otherChargesNeft
        this.cardCollection = cardCollection
        this.cardPenalty = cardPenalty
        this.advanceCard = advanceCard
        this.bpAdjustCollection = bpAdjustCollection
        this.bpAdjustPenalty = bpAdjustPenalty
        this.advAdjustmentCollection = advAdjustmentCollection
        this.advAdjustmentPenalty = advAdjustmentPenalty
        this.cashPayment = cashPayment
        this.chequePayment = chequePayment
        this.ddPayment = ddPayment
        this.neftPayment = neftPayment
        this.cardPayment = cardPayment
        this.otherBranchCashCollection = otherBranchCashCollection
        this.otherBranchCashPenalty = otherBranchCashPenalty
        this.otherBranchChequeCollection = otherBranchChequeCollection
        this.otherBranchChequePenalty = otherBranchChequePenalty
        this.otherBranchDdCollection = otherBranchDdCollection
        this.otherBranchDdPenalty = otherBranchDdPenalty
        this.otherBranchNeftCollection = otherBranchNeftCollection
        this.otherBranchNeftPenalty = otherBranchNeftPenalty
        this.otherBranchCardCollection = otherBranchCardCollection
        this.otherBranchCardPenalty = otherBranchCardPenalty
        this.totalCash = totalCash
        this.totalChequeLeft = totalChequeLeft
        this.totalChequeRight = totalChequeRight
        this.totalDd = totalDd
        this.totalNeft = totalNeft
        this.totalCard = totalCard
        this.totalPayment = totalPayment
        this.totalAdjustment = totalAdjustment
    }
}