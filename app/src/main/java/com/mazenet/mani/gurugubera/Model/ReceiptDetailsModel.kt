package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ReceiptDetailsModel {
    @SerializedName("receipt_no")
    @Expose
    private var receiptNo: String? = null
    @SerializedName("receipt_date")
    @Expose
    private var receiptDate: String? = null
    @SerializedName("receipt_time")
    @Expose
    private var receiptTime: String? = null
    @SerializedName("employee_id")
    @Expose
    private var employeeId: String? = null
    @SerializedName("employee_name")
    @Expose
    private var employeeName: String? = null
    @SerializedName("customer_name")
    @Expose
    private var customerName: String? = null
    @SerializedName("customer_code")
    @Expose
    private var customerCode: String? = null
    @SerializedName("mobile_no")
    @Expose
    private var mobileNo: String? = null
    @SerializedName("phone_no")
    @Expose
    private var phoneNo: String? = null
    @SerializedName("received_amount")
    @Expose
    private var receivedAmount: String? = null
    @SerializedName("penalty_amount")
    @Expose
    private var penaltyAmount: String? = null
    @SerializedName("bonus_amount")
    @Expose
    private var bonusAmount: String? = null
    @SerializedName("payemnt_type")
    @Expose
    private var payemntType: String? = null
    @SerializedName("debit_to")
    @Expose
    private var debitTo: String? = null
    @SerializedName("cheque_no")
    @Expose
    private var chequeNo: String? = null
    @SerializedName("cheque_date")
    @Expose
    private var chequeDate: String? = null
    @SerializedName("transaction_no")
    @Expose
    private var transactionNo: String? = null
    @SerializedName("transaction_date")
    @Expose
    private var transactionDate: String? = null
    @SerializedName("bank_name")
    @Expose
    private var bankNameId: String? = null
    @SerializedName("branch_name")
    @Expose
    private var branchName: String? = null
    @SerializedName("advance_amount")
    @Expose
    private var advanceAmount: String? = null
    @SerializedName("is_printed")
    @Expose
    private var isPrinted: String? = null
    @SerializedName("groupname")
    @Expose
    private var groupname: String? = null
    @SerializedName("ticketno")
    @Expose
    private var ticketno: String? = null
    @SerializedName("installmentno")
    @Expose
    private var installmentno: String? = null

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param employeeId
     * @param customerName
     * @param receiptNo
     * @param installmentno
     * @param chequeNo
     * @param groupname
     * @param chequeDate
     * @param bonusAmount
     * @param transactionDate
     * @param employeeName
     * @param phoneNo
     * @param receiptDate
     * @param payemntType
     * @param isPrinted
     * @param bankNameId
     * @param penaltyAmount
     * @param customerCode
     * @param ticketno
     * @param branchName
     * @param transactionNo
     * @param receivedAmount
     * @param receiptTime
     * @param debitTo
     * @param mobileNo
     * @param advanceAmount
     */
    fun ReceiptDetailsModel(
        receiptNo: String,
        receiptDate: String,
        receiptTime: String,
        employeeId: String,
        employeeName: String,
        customerName: String,
        customerCode: String,
        mobileNo: String,
        phoneNo: String,
        receivedAmount: String,
        penaltyAmount: String,
        bonusAmount: String,
        payemntType: String,
        debitTo: String,
        chequeNo: String,
        chequeDate: String,
        transactionNo: String,
        transactionDate: String,
        bankNameId: String,
        branchName: String,
        advanceAmount: String,
        isPrinted: String,
        groupname: String,
        ticketno: String,
        installmentno: String
    ) {

        this.receiptNo = receiptNo
        this.receiptDate = receiptDate
        this.receiptTime = receiptTime
        this.employeeId = employeeId
        this.employeeName = employeeName
        this.customerName = customerName
        this.customerCode = customerCode
        this.mobileNo = mobileNo
        this.phoneNo = phoneNo
        this.receivedAmount = receivedAmount
        this.penaltyAmount = penaltyAmount
        this.bonusAmount = bonusAmount
        this.payemntType = payemntType
        this.debitTo = debitTo
        this.chequeNo = chequeNo
        this.chequeDate = chequeDate
        this.transactionNo = transactionNo
        this.transactionDate = transactionDate
        this.bankNameId = bankNameId
        this.branchName = branchName
        this.advanceAmount = advanceAmount
        this.isPrinted = isPrinted
        this.groupname = groupname
        this.ticketno = ticketno
        this.installmentno = installmentno
    }

    fun getReceiptNo(): String? {
        return receiptNo
    }

    fun setReceiptNo(receiptNo: String) {
        this.receiptNo = receiptNo
    }

    fun getReceiptDate(): String? {
        return receiptDate
    }

    fun setReceiptDate(receiptDate: String) {
        this.receiptDate = receiptDate
    }

    fun getReceiptTime(): String? {
        return receiptTime
    }

    fun setReceiptTime(receiptTime: String) {
        this.receiptTime = receiptTime
    }

    fun getEmployeeId(): String? {
        return employeeId
    }

    fun setEmployeeId(employeeId: String) {
        this.employeeId = employeeId
    }

    fun getEmployeeName(): String? {
        return employeeName
    }

    fun setEmployeeName(employeeName: String) {
        this.employeeName = employeeName
    }

    fun getCustomerName(): String? {
        return customerName
    }

    fun setCustomerName(customerName: String) {
        this.customerName = customerName
    }

    fun getCustomerCode(): String? {
        return customerCode
    }

    fun setCustomerCode(customerCode: String) {
        this.customerCode = customerCode
    }

    fun getMobileNo(): String? {
        return mobileNo
    }

    fun setMobileNo(mobileNo: String) {
        this.mobileNo = mobileNo
    }

    fun getPhoneNo(): String? {
        return phoneNo
    }

    fun setPhoneNo(phoneNo: String) {
        this.phoneNo = phoneNo
    }

    fun getReceivedAmount(): String? {
        return receivedAmount
    }

    fun setReceivedAmount(receivedAmount: String) {
        this.receivedAmount = receivedAmount
    }

    fun getPenaltyAmount(): String? {
        return penaltyAmount
    }

    fun setPenaltyAmount(penaltyAmount: String) {
        this.penaltyAmount = penaltyAmount
    }

    fun getBonusAmount(): String? {
        return bonusAmount
    }

    fun setBonusAmount(bonusAmount: String) {
        this.bonusAmount = bonusAmount
    }

    fun getPayemntType(): String? {
        return payemntType
    }

    fun setPayemntType(payemntType: String) {
        this.payemntType = payemntType
    }

    fun getDebitTo(): String? {
        return debitTo
    }

    fun setDebitTo(debitTo: String) {
        this.debitTo = debitTo
    }

    fun getChequeNo(): String? {
        return chequeNo
    }

    fun setChequeNo(chequeNo: String) {
        this.chequeNo = chequeNo
    }

    fun getChequeDate(): String? {
        return chequeDate
    }

    fun setChequeDate(chequeDate: String) {
        this.chequeDate = chequeDate
    }

    fun getTransactionNo(): String? {
        return transactionNo
    }

    fun setTransactionNo(transactionNo: String) {
        this.transactionNo = transactionNo
    }

    fun getTransactionDate(): String? {
        return transactionDate
    }

    fun setTransactionDate(transactionDate: String) {
        this.transactionDate = transactionDate
    }

    fun getBankNameId(): String? {
        return bankNameId
    }

    fun setBankNameId(bankNameId: String) {
        this.bankNameId = bankNameId
    }

    fun getBranchName(): String? {
        return branchName
    }

    fun setBranchName(branchName: String) {
        this.branchName = branchName
    }

    fun getAdvanceAmount(): String? {
        return advanceAmount
    }

    fun setAdvanceAmount(advanceAmount: String) {
        this.advanceAmount = advanceAmount
    }

    fun getIsPrinted(): String? {
        return isPrinted
    }

    fun setIsPrinted(isPrinted: String) {
        this.isPrinted = isPrinted
    }

    fun getGroupname(): String? {
        return groupname
    }

    fun setGroupname(groupname: String) {
        this.groupname = groupname
    }

    fun getTicketno(): String? {
        return ticketno
    }

    fun setTicketno(ticketno: String) {
        this.ticketno = ticketno
    }

    fun getInstallmentno(): String? {
        return installmentno
    }

    fun setInstallmentno(installmentno: String) {
        this.installmentno = installmentno
    }

}