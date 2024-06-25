package com.mazenet.mani.gurugubera.Databases

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mazenet.mani.gurugubera.Model.*
import com.mazenet.mani.gurugubera.Utilities.BaseUtils
import com.mazenet.mani.gurugubera.Utilities.Constants
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class DatabaseOffline(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        val Create_Customers_table = ("CREATE TABLE " + Table_Customers + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_c_customerid + " TEXT UNIQUE,"
                + KEY_c_customername + " TEXT,"
                + KEY_c_customercode + " TEXT,"
                + KEY_c_customerpaid + " TEXT,"
                + KEY_c_customerpending + " TEXT,"
                + KEY_c_customermobile + " TEXT,"
                + KEY_c_customerphone + " TEXT"
                + ")")

        val Create_Add_Customer = ("CREATE TABLE 'OfflineAddCustomer' ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "tenant_id TEXT,"
                + "branch_id TEXT,"
                + "name TEXT,"
                + "mobile_no TEXT,"
                + "gender TEXT,"
                + "email_id TEXT,"
                + "address_line_1 TEXT,"
                + "state_id TEXT,"
                + "district_id TEXT,"
                + "city_id TEXT,"
                + "pincode TEXT,"
                + "dob TEXT,"
                + "doj TEXT,"
                + "scheme_id TEXT,"
                + "payment_type_id TEXT,"
                + "agent_id TEXT,"
                + "created_by TEXT"
                + ")" )


        val Create_GroupDetails_table = ("CREATE TABLE " + Table_GroupDetails + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_gd_enrollemntid + " TEXT UNIQUE,"
                + KEY_gd_groupid + " TEXT,"
                + KEY_gd_groupname + " TEXT,"
                + KEY_gd_chitvalue + " TEXT,"
                + KEY_gd_auctiondate + " TEXT,"
                + KEY_gd_due_no + " TEXT,"
                + KEY_gd_pending_due_no + " TEXT,"
                + KEY_gd_ticketno + " TEXT,"
                + KEY_gd_installmentamnt + " TEXT,"
                + KEY_gd_colelcted + " TEXT,"
                + KEY_gd_tobecollected + " TEXT,"
                + KEY_gd_advanceamnt + " TEXT,"
                + KEY_gd_pendingdays + " TEXT,"
                + KEY_gd_bonus + " TEXT,"
                + KEY_gd_discountonpenalty + " TEXT,"
                + KEY_gd_discountondue + " TEXT,"
                + KEY_gd_prizedstatus + " TEXT,"
                + KEY_gd_customerid + " TEXT"
                + ")")

        val Create_Installment_Details_Table = ("CREATE TABLE " + Table_InstallmentDetails + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_gd_enrollemntid + " TEXT,"
                + KEY_id_auctionno + " TEXT,"
                + KEY_id_biddingid + " TEXT,"
                + KEY_gd_tobecollected + " TEXT,"
                + KEY_gd_discountondue + " TEXT,"
                + KEY_gd_discountonpenalty + " TEXT,"
                + KEY_id_bonusdays + " TEXT,"
                + KEY_id_penalty_percentage + " TEXT,"
                + KEY_id_bonus_percentage + " TEXT,"
                + KEY_id_bonus_amount + " TEXT,"
                + KEY_gd_pendingdays + " TEXT,"
                + KEY_gd_colelcted + " TEXT,"
                + KEY_gd_installmentamnt + " TEXT,"
                + KEY_id_penaltyamnt + " TEXT"
                + ")")

        val Create_OfflineReceipt_Table = ("CREATE TABLE " + Table_OfflineReceiptDetails + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_recno + " TEXT,"
                + KEY_offlinerecno + " TEXT,"
                + KEY_gd_enrollemntid + " TEXT,"
                + KEY_gd_otherbranch + " TEXT,"
                + KEY_gd_customerid + " TEXT,"
                + KEY_gd_receiptdate + " TEXT,"
                + KEY_gd_groupid + " TEXT,"
                + KEY_gd_ticketno + " TEXT,"
                + KEY_gd_adjustid + " TEXT,"
                + KEY_gd_receivedamount + " TEXT,"
                + KEY_gd_paytypeid + " TEXT,"
                + KEY_gd_debitto + " TEXT,"
                + KEY_gd_chequeno + " TEXT,"
                + KEY_gd_chequedate + " TEXT,"
                + KEY_gd_chequecleardate + " TEXT,"
                + KEY_gd_banknameid + " TEXT,"
                + KEY_gd_chequebranchname + " TEXT,"
                + KEY_gd_transno + " TEXT,"
                + KEY_gd_transdate + " TEXT,"
                + KEY_gd_receipttime + " TEXT,"
                + KEY_gd_printed + " TEXT,"
                + KEY_gd_remarks + " TEXT,"
                + KEY_Posted + " TEXT,"
                + KEY_gd_advanceamnt + " TEXT,"
                + KEY_gd_advonly + " TEXT"
                + ")")
        val Create_OfflineInstallments_Table = ("CREATE TABLE " + Table_OfflineInstalmentsDetails + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_recno + " TEXT,"
                + KEY_gd_enrollemntid + " TEXT,"
                + KEY_rid_payableamount + " TEXT,"
                + KEY_id_auctionno + " TEXT,"
                + KEY_id_biddingid + " TEXT,"
                + KEY_gd_pendingdays + " TEXT,"
                + KEY_id_penaltyamnt + " TEXT,"
                + KEY_id_bonus_amount + " TEXT,"
                + KEY_gd_discountondue + " TEXT,"
                + KEY_gd_discountonpenalty + " TEXT,"
                + KEY_gd_receiptamount + " TEXT"
                + ")")

        db.execSQL(Create_Customers_table)
        db.execSQL(Create_GroupDetails_table)
        db.execSQL(Create_Installment_Details_Table)
        db.execSQL(Create_OfflineReceipt_Table)
        db.execSQL(Create_Add_Customer)
        db.execSQL(Create_OfflineInstallments_Table)


    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL("DROP TABLE IF EXISTS $Table_Customers")
        db.execSQL("DROP TABLE IF EXISTS $Table_GroupDetails")
        db.execSQL("DROP TABLE IF EXISTS $Table_InstallmentDetails")
        db.execSQL("DROP TABLE IF EXISTS $Table_OfflineReceiptDetails")
        db.execSQL("DROP TABLE IF EXISTS $Table_OfflineInstalmentsDetails")
        db.execSQL("DROP TABLE IF EXISTS OfflineAddCustomer")


        onCreate(db)
    }


    fun deleteCustomersTable() {
        val db = this.writableDatabase
        db.delete(Table_Customers, null, null)
        db.close()
    }
    fun deleteAddCustomer(){
        val db = this.writableDatabase
        db.delete("OfflineAddCustomer", null, null)
        db.close()
    }

    fun deleteGroupDetailsTable() {
        val db = this.writableDatabase
        db.delete(Table_GroupDetails, null, null)
        db.close()
    }

    fun deleteInstallmentsDetailsTable() {
        val db = this.writableDatabase
        db.delete(Table_InstallmentDetails, null, null)
        db.close()
    }

    fun deleteOfflineReceiptsTable() {
        val db = this.writableDatabase
        db.delete(Table_OfflineReceiptDetails, null, null)
        db.close()
    }

    fun deleteOfflineInstallmentsTable() {
        val db = this.writableDatabase
        db.delete(Table_OfflineInstalmentsDetails, null, null)
        db.close()
    }

    fun getCustomerwiseGroupsList(cusid: String): ArrayList<GroupListModel> {
        val db = this.writableDatabase
        val getListsql =
            "SELECT $KEY_gd_enrollemntid,$KEY_gd_groupid,$KEY_gd_groupname,$KEY_gd_chitvalue,$KEY_gd_auctiondate,$KEY_gd_prizedstatus,IFNULL($KEY_gd_installmentamnt,0),IFNULL($KEY_gd_colelcted,0),IFNULL($KEY_gd_tobecollected,0),IFNULL($KEY_gd_advanceamnt,0),IFNULL(${KEY_gd_due_no},0),IFNULL(${KEY_gd_pending_due_no},0),${KEY_gd_ticketno} FROM $Table_GroupDetails WHERE $KEY_gd_customerid = $cusid"
        val cursor = db.rawQuery(getListsql, null)
        println(cursor.count)
        val contactList = ArrayList<GroupListModel>()
        if (cursor.moveToFirst()) {
            do {
                val contact = GroupListModel()
                contact.enrolid = cursor.getString(0)
                val enrolid = cursor.getString(0)
                contact.groupid = cursor.getString(1)
                contact.groupname = cursor.getString(2)
                contact.chitvalue = cursor.getString(3)
                contact.auctiondate = cursor.getString(4)
                contact.prizedstatus = cursor.getString(5)
                contact.instalmentamount = cursor.getString(6)
                contact.collected = cursor.getString(7)
                contact.tobecollected = cursor.getString(8)
                contact.advanceamnt = cursor.getString(9)
                contact.auc_no = cursor.getString(10)
                contact.pen_auc_no = cursor.getString(11)
                contact.ticketno = cursor.getString(12)
                val instalmentqry =
                    "SELECT IFNULL(SUM($KEY_id_penaltyamnt),0),IFNULL(SUM($KEY_id_bonus_amount),0) FROM $Table_InstallmentDetails WHERE $KEY_gd_enrollemntid = $enrolid"
                val instalmentcursor = db.rawQuery(instalmentqry, null)
                if (instalmentcursor.count > 0) {
                    if (instalmentcursor.moveToFirst()) {
                        do {
                            contact.penaltyamnt = instalmentcursor.getString(0)
                            contact.bonusamnt = instalmentcursor.getString(1)
                        } while (instalmentcursor.moveToNext())
                    }
                }
                contactList.add(contact)
                instalmentcursor.close()
            } while (cursor.moveToNext())
        }
        cursor.close()

        db.close()
        return contactList
    }

    fun getenrollmentList(enrolid: String): ArrayList<GroupListModel> {
        val db = this.writableDatabase
        val getListsql =
            "SELECT $KEY_gd_enrollemntid,$KEY_gd_groupid,$KEY_gd_groupname,$KEY_gd_chitvalue,$KEY_gd_auctiondate,$KEY_gd_prizedstatus,IFNULL($KEY_gd_installmentamnt,0),IFNULL($KEY_gd_colelcted,0),IFNULL($KEY_gd_tobecollected,0),IFNULL($KEY_gd_advanceamnt,0),$KEY_gd_ticketno FROM $Table_GroupDetails WHERE $KEY_gd_enrollemntid = '$enrolid'"
        val cursor = db.rawQuery(getListsql, null)
        println(cursor.count)
        val contactList = ArrayList<GroupListModel>()
        if (cursor.moveToFirst()) {
            do {
                val contact = GroupListModel()
                contact.enrolid = cursor.getString(0)
                val enrolid = cursor.getString(0)
                contact.groupid = cursor.getString(1)
                contact.groupname = cursor.getString(2)
                contact.chitvalue = cursor.getString(3)
                contact.auctiondate = cursor.getString(4)
                contact.prizedstatus = cursor.getString(5)
                contact.instalmentamount = cursor.getString(6)
                contact.collected = cursor.getString(7)
                contact.tobecollected = cursor.getString(8)
                contact.advanceamnt = cursor.getString(9)
                contact.ticketno = cursor.getString(10)
                val instalmentqry =
                    "SELECT IFNULL(SUM($KEY_id_penaltyamnt),0),IFNULL(SUM($KEY_id_bonus_amount),0) FROM $Table_InstallmentDetails WHERE $KEY_gd_enrollemntid = $enrolid"
                val instalmentcursor = db.rawQuery(instalmentqry, null)
                if (instalmentcursor.count > 0) {
                    if (instalmentcursor.moveToFirst()) {
                        do {
                            contact.penaltyamnt = instalmentcursor.getString(0)
                            contact.bonusamnt = instalmentcursor.getString(1)
                        } while (instalmentcursor.moveToNext())
                    }
                }
                contactList.add(contact)
                instalmentcursor.close()
            } while (cursor.moveToNext())
        }
        cursor.close()

        db.close()
        return contactList
    }

    fun getinstalls(enrolid: String): ArrayList<InstallmentsDet> {
        val contactList = ArrayList<InstallmentsDet>()
        val selectQuery = "SELECT  * FROM $Table_InstallmentDetails WHERE $KEY_gd_enrollemntid = $enrolid"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val contact = InstallmentsDet()
                contact.setEntrollmentId(cursor.getString(1))
                contact.setAuctionNo(cursor.getString(2))
                contact.setBiddingId(cursor.getString(3))
                contact.setPendingDue(cursor.getString(4))
                contact.setDiscountOnDue(cursor.getString(5))
                contact.setDiscountOnPenalty(cursor.getString(6))
                contact.setBonusDays(cursor.getString(7))
                contact.setPenaltyPercentage(cursor.getString(8))
                contact.setBonusPercentage(cursor.getString(9))
                contact.setBonusAmount(cursor.getString(10))
                contact.setInstalPendingDay(cursor.getString(11))
                contact.setPaidAmount(cursor.getString(12))
                contact.setCurrentInstallmentAmount(cursor.getString(13))
                contact.setPenaltyAmounts(cursor.getString(14))
                contactList.add(contact)

            } while (cursor.moveToNext())
        }
        db.close()

        return contactList
    }


    fun Upd_Off_insmt(
        custid: String,
        enrolid: String,
        biddingid: String,
        penalty: Int,
        bonus: Int,
        receiptamount: Int
    ) {
        val db = this.writableDatabase
        //================Installments Table update========================
        var AvailablePenalty: Int = 0
        var Availablebonus: Int = 0
        var AvailablePending: Int = 0
        val fetchinstalldetailqry =
            "SELECT IFNULL($KEY_id_penaltyamnt,0),IFNULL($KEY_id_bonus_amount,0),IFNULL($KEY_gd_tobecollected,0) FROM $Table_InstallmentDetails WHERE $KEY_id_biddingid = '$biddingid'"
        val cursor = db.rawQuery(fetchinstalldetailqry, null)
        if (cursor.moveToFirst()) {
            do {
                AvailablePenalty = Constants.isEmtytoZero(cursor.getString(0)).toInt()
                Availablebonus = Constants.isEmtytoZero(cursor.getString(1)).toInt()
                AvailablePending = Constants.isEmtytoZero(cursor.getString(2)).toInt()
            } while (cursor.moveToNext())
        }
        AvailablePenalty = AvailablePenalty - penalty
        Availablebonus = Availablebonus - bonus
        AvailablePending = AvailablePending - receiptamount
        val update_instalments =
            "UPDATE $Table_InstallmentDetails SET $KEY_id_penaltyamnt = '$AvailablePenalty',$KEY_id_bonus_amount = '$Availablebonus',$KEY_gd_tobecollected='$AvailablePending'  WHERE $KEY_id_biddingid = $biddingid"
        //================Group Table update==================================
        var Group_collected: Int = 0
        var Group_tobecollected: Int = 0
        val fetchgroupdetailqry =
            "SELECT IFNULL($KEY_gd_colelcted,0),IFNULL($KEY_gd_tobecollected,0),IFNULL($KEY_gd_installmentamnt,0) FROM $Table_GroupDetails WHERE $KEY_gd_enrollemntid = '$enrolid'"
        val group_cursor = db.rawQuery(fetchgroupdetailqry, null)
        if (group_cursor.moveToFirst()) {
            do {
                Group_collected = Constants.isEmtytoZero(group_cursor.getString(0)).toInt()
                Group_tobecollected = Constants.isEmtytoZero(group_cursor.getString(1)).toInt()
            } while (group_cursor.moveToNext())
        }
        Group_collected = Group_collected + receiptamount
        Group_tobecollected = Group_tobecollected - receiptamount
        val update_groupdetails =
            "UPDATE $Table_GroupDetails SET $KEY_gd_tobecollected = '$Group_tobecollected',$KEY_gd_colelcted = '$Group_collected' WHERE $KEY_gd_enrollemntid = '$enrolid'"
        //================Customer Table update===================================
        var Customer_collected: Int = 0
        var Customer_tobecollected: Int = 0
        val fetchCustomerDetailqry =
            "SELECT IFNULL($KEY_c_customerpaid,0),IFNULL($KEY_c_customerpending,0) FROM $Table_Customers WHERE $KEY_c_customerid = '$custid'"
        val Customer_cursor = db.rawQuery(fetchCustomerDetailqry, null)
        if (Customer_cursor.moveToFirst()) {
            do {
                Customer_collected = Constants.isEmtytoZero(Customer_cursor.getString(0)).toInt()
                Customer_tobecollected = Constants.isEmtytoZero(Customer_cursor.getString(1)).toInt()
            } while (Customer_cursor.moveToNext())
        }
        Customer_collected = Customer_collected + receiptamount
        Customer_tobecollected = Customer_tobecollected - receiptamount
        val update_Customerdetails =
            "UPDATE $Table_Customers SET $KEY_c_customerpending = '$Customer_tobecollected',$KEY_c_customerpaid = '$Customer_collected' WHERE $KEY_c_customerid = '$custid'"
        //=================================================================
        db.execSQL(update_instalments)
        db.execSQL(update_groupdetails)
        db.execSQL(update_Customerdetails)
        cursor.close()
        group_cursor.close()
        Customer_cursor.close()
        db.close()
    }
    fun update_off_advance( enrolid: String,advance:Int)
    {
        val db = this.writableDatabase
        var AdvanceAvailable: Int = 0

        val fetchgroupdetailqry =
            "SELECT IFNULL($KEY_gd_advanceamnt,0) FROM $Table_GroupDetails WHERE $KEY_gd_enrollemntid = '$enrolid'"
        val group_cursor = db.rawQuery(fetchgroupdetailqry, null)
        if (group_cursor.moveToFirst()) {
            do {
                AdvanceAvailable = Constants.isEmtytoZero(group_cursor.getString(0)).toInt()
            } while (group_cursor.moveToNext())
        }
        AdvanceAvailable = AdvanceAvailable + advance
        val update_groupdetails =
            "UPDATE $Table_GroupDetails SET $KEY_gd_advanceamnt = '$AdvanceAvailable' WHERE $KEY_gd_enrollemntid = '$enrolid'"
        db.execSQL(update_groupdetails)
        group_cursor.close()
        db.close()
    }
    fun update_amnt_customer_only(cusid:String,receiptamount: Int)
    {
        val db = this.writableDatabase
        var Customer_collected: Int = 0
        var Customer_tobecollected: Int = 0
        val fetchCustomerDetailqry =
            "SELECT IFNULL($KEY_c_customerpaid,0),IFNULL($KEY_c_customerpending,0) FROM $Table_Customers WHERE $KEY_c_customerid = '$cusid'"
        val Customer_cursor = db.rawQuery(fetchCustomerDetailqry, null)
        if (Customer_cursor.moveToFirst()) {
            do {
                Customer_collected = Constants.isEmtytoZero(Customer_cursor.getString(0)).toInt()
                Customer_tobecollected = Constants.isEmtytoZero(Customer_cursor.getString(1)).toInt()
            } while (Customer_cursor.moveToNext())
        }
        Customer_collected = Customer_collected + receiptamount
        Customer_tobecollected = Customer_tobecollected - receiptamount
        val update_Customerdetails =
            "UPDATE $Table_Customers SET $KEY_c_customerpending = '$Customer_tobecollected',$KEY_c_customerpaid = '$Customer_collected' WHERE $KEY_c_customerid = '$cusid'"
        db.execSQL(update_Customerdetails)
        Customer_cursor.close()
        db.close()
    }

    fun update_printed_receipts(recno: String) {
        val db = this.writableDatabase
        val update_posted_receipts =
            "UPDATE $Table_OfflineReceiptDetails SET $KEY_gd_printed = '1' WHERE $KEY_offlinerecno = '$recno'"
        //=================================================================
        db.execSQL(update_posted_receipts)
        db.close()
    }

    fun update_uploaded_receipts(recno: String) {
        val db = this.writableDatabase
        val update_posted_receipts =
            "UPDATE $Table_OfflineReceiptDetails SET $KEY_Posted = 'Yes' WHERE $KEY_recno = '$recno'"
        //=================================================================
        db.execSQL(update_posted_receipts)
        db.close()
    }

    fun addOfflineCustomerList(sched: ArrayList<collectioncustomermodel>) {
        val db = this.writableDatabase
        for (i in sched.indices) {

            val values = ContentValues()
            val contact = sched[i]

            values.put(KEY_c_customerid, contact.getCustomerId())
            values.put(KEY_c_customername, contact.getCustomerName())
            values.put(KEY_c_customercode, contact.getCustomerCode())
            values.put(KEY_c_customerpaid, contact.getTotalPaid())
            values.put(KEY_c_customerpending, contact.getTotalPending())
            values.put(KEY_c_customermobile, contact.getMobileNo())
           // values.put(KEY_c_customerphone, contact.getPhoneNo())
            db.insert(Table_Customers, null, values)
        }
        db.close()
    }

    fun getOfflineCustomersList(): ArrayList<collectioncustomermodel> {
        val contactList = ArrayList<collectioncustomermodel>()
        val selectQuery = "SELECT  * FROM $Table_Customers"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val contact = collectioncustomermodel()
                contact.setCustomerId(cursor.getString(1))
                contact.setCustomerName(cursor.getString(2))
                contact.setCustomerCode(cursor.getString(3))
                contact.setTotalPaid(cursor.getString(4))
                contact.setTotalPending(cursor.getString(5))
                contact.setMobileNo(cursor.getString(6))
               // contact.setPhoneNo(cursor.getString(7))
                contactList.add(contact)

            } while (cursor.moveToNext())
        }
        db.close()

        return contactList
    }

    fun getOFf_receipts_list(): ArrayList<OfflineReceiptsdetailsmodel> {
        val contactList = ArrayList<OfflineReceiptsdetailsmodel>()
        val selectQuery = "SELECT  * FROM $Table_OfflineReceiptDetails where $KEY_Posted = 'No'"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val contact = OfflineReceiptsdetailsmodel()
                contact.of_recno = cursor.getString(1)
                contact.of_offrecno = cursor.getString(2)
                contact.of_enrolid = cursor.getString(3)
                contact.of_otherbranch = cursor.getString(4)
                contact.of_customerid = cursor.getString(5)
                contact.of_receiptdate = cursor.getString(6)
                contact.of_groupid = cursor.getString(7)
                contact.of_ticketno = cursor.getString(8)
                contact.of_adjustid = cursor.getString(9)
                contact.of_receivedamount = cursor.getString(10)
                contact.of_paytypeid = cursor.getString(11)
                contact.of_debitto = cursor.getString(12)
                contact.of_chequeno = cursor.getString(13)
                contact.of_chequedate = cursor.getString(14)
                contact.of_chequecleardate = cursor.getString(15)
                contact.of_banknameid = cursor.getString(16)
                contact.of_chequebranchname = cursor.getString(17)
                contact.of_transno = cursor.getString(18)
                contact.of_transdate = cursor.getString(19)
                contact.of_receipttime = cursor.getString(20)
                contact.of_printed = cursor.getString(21)
                contact.of_remarks = cursor.getString(22)
                contact.of_posted = cursor.getString(23)
                contact.of_advance = cursor.getString(24)
                contact.of_advanceonly = cursor.getString(25)

                contactList.add(contact)

            } while (cursor.moveToNext())
        }
        db.close()

        return contactList
    }

    fun getOFf_receipts_list(dates: String): ArrayList<OfflineReceiptsdetailsmodel> {
        val contactList = ArrayList<OfflineReceiptsdetailsmodel>()
        val selectQuery = "SELECT  * FROM $Table_OfflineReceiptDetails where $KEY_gd_receiptdate LIKE '$dates'"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val contact = OfflineReceiptsdetailsmodel()
                contact.of_recno = cursor.getString(1)
                contact.of_offrecno = cursor.getString(2)
                contact.of_enrolid = cursor.getString(3)
                contact.of_otherbranch = cursor.getString(4)
                contact.of_customerid = cursor.getString(5)
                val custid = cursor.getString(5)
                contact.of_receiptdate = cursor.getString(6)
                contact.of_groupid = cursor.getString(7)
                contact.of_ticketno = cursor.getString(8)
                contact.of_adjustid = cursor.getString(9)
                contact.of_receivedamount = cursor.getString(10)
                contact.of_paytypeid = cursor.getString(11)
                contact.of_debitto = cursor.getString(12)
                contact.of_chequeno = cursor.getString(13)
                contact.of_chequedate = cursor.getString(14)
                contact.of_chequecleardate = cursor.getString(15)
                contact.of_banknameid = cursor.getString(16)
                contact.of_chequebranchname = cursor.getString(17)
                contact.of_transno = cursor.getString(18)
                contact.of_transdate = cursor.getString(19)
                contact.of_receipttime = cursor.getString(20)
                contact.of_printed = cursor.getString(21)
                contact.of_remarks = cursor.getString(22)
                contact.of_posted = cursor.getString(23)
                val cust_detail_qry =
                    "SELECT $KEY_c_customername,$KEY_c_customercode,$KEY_c_customermobile,$KEY_c_customerphone FROM $Table_Customers WHERE $KEY_c_customerid = $custid"
                val cust_detail_cursor = db.rawQuery(cust_detail_qry, null)
                if (cust_detail_cursor.count > 0) {
                    if (cust_detail_cursor.moveToFirst()) {
                        do {
                            contact.custname = cust_detail_cursor.getString(0)
                            contact.custcode = cust_detail_cursor.getString(1)
                            contact.custmobile = cust_detail_cursor.getString(2)
                            contact.custphone = cust_detail_cursor.getString(3)
                        } while (cust_detail_cursor.moveToNext())
                    }
                }
                contactList.add(contact)
                cust_detail_cursor.close()
            } while (cursor.moveToNext())
        }
        db.close()

        return contactList
    }




    fun getOFf_receipts_details(offline_reco: String): ArrayList<OfflineReceiptsdetailsmodel> {
        val contactList = ArrayList<OfflineReceiptsdetailsmodel>()
        val selectQuery =
            "SELECT  * FROM $Table_OfflineReceiptDetails WHERE $KEY_offlinerecno LIKE '$offline_reco' LIMIT 1"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val contact = OfflineReceiptsdetailsmodel()
                contact.of_recno = cursor.getString(1)
                contact.of_offrecno = cursor.getString(2)
                contact.of_enrolid = cursor.getString(3)
                contact.of_otherbranch = cursor.getString(4)
                contact.of_customerid = cursor.getString(5)
                val recno = cursor.getString(1)
                val custid = cursor.getString(5)
                val enrolid = cursor.getString(3)
                contact.of_receiptdate = cursor.getString(6)
                contact.of_groupid = cursor.getString(7)
                contact.of_ticketno = cursor.getString(8)
                contact.of_adjustid = cursor.getString(9)
                contact.of_receivedamount = cursor.getString(10)
                contact.of_paytypeid = cursor.getString(11)
                contact.of_debitto = cursor.getString(12)
                contact.of_chequeno = cursor.getString(13)
                contact.of_chequedate = cursor.getString(14)
                contact.of_chequecleardate = cursor.getString(15)
                contact.of_banknameid = cursor.getString(16)
                contact.of_chequebranchname = cursor.getString(17)
                contact.of_transno = cursor.getString(18)
                contact.of_transdate = cursor.getString(19)
                contact.of_receipttime = cursor.getString(20)
                contact.of_printed = cursor.getString(21)
                contact.of_remarks = cursor.getString(22)
                contact.of_posted = cursor.getString(23)
                val cust_detail_qry =
                    "SELECT $KEY_c_customername,$KEY_c_customercode,$KEY_c_customermobile,$KEY_c_customerphone FROM $Table_Customers WHERE $KEY_c_customerid = $custid"
                val cust_detail_cursor = db.rawQuery(cust_detail_qry, null)
                if (cust_detail_cursor.count > 0) {
                    if (cust_detail_cursor.moveToFirst()) {
                        do {
                            contact.custname = cust_detail_cursor.getString(0)
                            contact.custcode = cust_detail_cursor.getString(1)
                            contact.custmobile = cust_detail_cursor.getString(2)
                            contact.custphone = cust_detail_cursor.getString(3)
                        } while (cust_detail_cursor.moveToNext())
                    }
                }
                val instalmentqry =
                    "SELECT IFNULL(SUM(${KEY_id_penaltyamnt}),0),IFNULL(SUM(${KEY_id_bonus_amount}),0) FROM ${Table_OfflineInstalmentsDetails} WHERE ${KEY_recno} = '$recno'"
                val instalmentcursor = db.rawQuery(instalmentqry, null)
                if (instalmentcursor.count > 0) {
                    if (instalmentcursor.moveToFirst()) {
                        do {
                            contact.penalty = instalmentcursor.getString(0)
                            contact.bonus = instalmentcursor.getString(1)
                        } while (instalmentcursor.moveToNext())
                    }
                }
                val fetch_inst_no =
                    "SELECT $KEY_id_auctionno FROM ${Table_OfflineInstalmentsDetails} WHERE ${KEY_recno} = '$recno'"
                val inst_no_cursor = db.rawQuery(fetch_inst_no, null)
                val instl_no: StringBuilder = StringBuilder()
                if (inst_no_cursor.count > 0) {
                    if (inst_no_cursor.moveToFirst()) {
                        do {
                            instl_no.append(inst_no_cursor.getString(0))
                            instl_no.append(",")
                        } while (inst_no_cursor.moveToNext())
                    }
                }
                contact.installmentnos = instl_no.toString()
                contactList.add(contact)
                cust_detail_cursor.close()
                inst_no_cursor.close()
            } while (cursor.moveToNext())
        }
        db.close()

        return contactList
    }

    fun getOfflineinstalllist(recno: String): ArrayList<OfflineInstallmentsdetailsmodel> {
        val contactList = ArrayList<OfflineInstallmentsdetailsmodel>()
        val selectQuery = "SELECT  * FROM $Table_OfflineInstalmentsDetails WHERE $KEY_recno = '$recno'"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {

                val contact = OfflineInstallmentsdetailsmodel()
                contact.of_recno = cursor.getString(1)
                contact.of_enrolid = cursor.getString(2)
                contact.of_payableamount = cursor.getString(3)
                contact.of_auctionno = cursor.getString(4)
                contact.of_biddingid = cursor.getString(5)
                contact.of_pendingdays = cursor.getString(6)
                contact.of_penaltyamnt = cursor.getString(7)
                contact.of_bonusamnt = cursor.getString(8)
                contact.of_discountondue = cursor.getString(9)
                contact.of_discountonpenalty = cursor.getString(10)
                contact.of_receiptamount = cursor.getString(11)
                contactList.add(contact)

            } while (cursor.moveToNext())
        }
        db.close()

        return contactList
    }

    fun getOfflineAddCustomer() : ArrayList<HashMap<String,Any>> {
        val selectedQuery = "SELECT * FROM OfflineAddCustomer"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectedQuery, null)
        var array = ArrayList<HashMap<String,Any>>()

        if(cursor.moveToFirst()){
            do{
                val map   =  HashMap<String,Any>()
                //map["key"] = cursor.getString(0)
                map["tenant_id"] = cursor.getString(1)
                map["branch_id"] = cursor.getString(2)
                map["name"] = cursor.getString(3)
                map["mobile_no"] = cursor.getString(4)
                map["gender"] = cursor.getString(5)
                map["email_id"] = cursor.getString(6)
                map["address_line_1"] = cursor.getString(7)
                map["state_id"] = cursor.getString(8)
                map["district_id"] = cursor.getString(9)
                map["city_id"] = cursor.getString(10)
                map["pincode"] = cursor.getString(11)
                map["dob"] = cursor.getString(12)
                map["doj"] = cursor.getString(13)
                map["scheme_id"] = cursor.getString(14)
                map["payment_type_id"] = cursor.getString(15)
                map["agent_id"] = cursor.getString(16)
                map["created_by"] = cursor.getString(17)
                array.add(map)
            }while (cursor.moveToNext())
        }

        db.close()
        return array
    }

    fun addOfflineAddCustomer(
        tenant_id : String,
        branch_id: String,
        name : String,
        mobile_no : String,
        gender : String,
        email_id : String,
        address_line_1 : String,
        state_id : String,
        district_id : String,
        city_id : String,
        pincode : String,
        dob : String,
        doj : String,
        scheme_id : String,
        payment_type_id : String,
        agent_id : String,
        created_by : String
    ){
        val db = this.writableDatabase
        val insertsql =
            "INSERT INTO  OfflineAddCustomer ('tenant_id','branch_id','name','mobile_no','gender','email_id','address_line_1','state_id','district_id','city_id'," +
                    "'pincode','dob','doj','scheme_id','payment_type_id','agent_id','created_by') VALUES('$tenant_id','$branch_id','$name','$mobile_no'," +
                    "'$gender','$email_id','$address_line_1','$state_id','$district_id','$city_id'," +
                    "'$pincode','$dob','$doj','$scheme_id','$payment_type_id','$agent_id','$created_by')"
        db.execSQL(insertsql)

        db.close()
    }

    fun addOfflineReceiptdetails(
        recno: String,
        offlinerecno: String,
        enrolid: String,
        otherbranch: String,
        customer_id: String,
        receiptdate: String,
        groupid: String,
        ticketno: String,
        adjustid: String,
        receivedamount: String,
        paytypeid: String,
        debitto: String,
        cheqoueno: String,
        chequedate: String,
        chequecleardate: String,
        chequebankid: String,
        chequebranchname: String,
        transno: String,
        transdate: String,
        receipttime: String,
        printed: String,
        remarks: String,
        posted: String,
        advance:String,
        advanceOnly:String
    ) {
        val db = this.writableDatabase
        val insertsql =
            "INSERT INTO $Table_OfflineReceiptDetails ($KEY_recno,$KEY_offlinerecno,$KEY_gd_enrollemntid,$KEY_gd_otherbranch,$KEY_gd_customerid,$KEY_gd_receiptdate,$KEY_gd_groupid,$KEY_gd_ticketno,$KEY_gd_adjustid,$KEY_gd_receivedamount,$KEY_gd_paytypeid,$KEY_gd_debitto,$KEY_gd_chequeno,$KEY_gd_chequedate,$KEY_gd_chequecleardate,$KEY_gd_banknameid,$KEY_gd_chequebranchname,$KEY_gd_transno,$KEY_gd_transdate,$KEY_gd_receipttime,$KEY_gd_printed,$KEY_gd_remarks,$KEY_Posted,$KEY_gd_advanceamnt,$KEY_gd_advonly) VALUES ('$recno','$offlinerecno','$enrolid','$otherbranch','$customer_id','$receiptdate','$groupid','$ticketno','$adjustid','$receivedamount','$paytypeid','$debitto','$cheqoueno','$chequedate','$chequecleardate','$chequebankid','$chequebranchname','$transno','$transdate','$receipttime','$printed','$remarks','$posted','$advance','$advanceOnly')"
        db.execSQL(insertsql)

        db.close()
    }

    fun addOfflineInstallmentdetails(
        recno: String,
        enrolid: String,
        payableamopunt: String,
        auctionno: String,
        biddingid: String,
        pendingdays: String,
        penaltyamnt: String,
        bonus: String,
        discountondue: String,
        discount_on_penalty: String,
        receiptamount: String
    ) {
        val db = this.writableDatabase
        val insertsql =
            "INSERT INTO $Table_OfflineInstalmentsDetails ($KEY_recno,$KEY_gd_enrollemntid,$KEY_rid_payableamount,$KEY_id_auctionno,$KEY_id_biddingid,$KEY_gd_pendingdays,$KEY_id_penaltyamnt,$KEY_id_bonus_amount,$KEY_gd_discountondue,$KEY_gd_discountonpenalty,$KEY_gd_receiptamount) VALUES ('$recno','$enrolid','$payableamopunt','$auctionno','$biddingid','$pendingdays','$penaltyamnt','$bonus','$discountondue','$discount_on_penalty','$receiptamount')"
        db.execSQL(insertsql)

        db.close()
    }

    fun addInstallmentDetails(sched: ArrayList<InstallmentsDet>) {
        val db = this.writableDatabase
        for (i in sched.indices) {

            val values = ContentValues()
            val contact = sched[i]

            values.put(KEY_gd_enrollemntid, contact.getEntrollmentId())
            values.put(KEY_id_auctionno, contact.getAuctionNo())
            values.put(KEY_id_biddingid, contact.getBiddingId())
            values.put(KEY_gd_tobecollected, contact.getPendingDue())
            values.put(KEY_gd_discountondue, contact.getDiscountOnDue())
            values.put(KEY_gd_discountonpenalty, contact.getDiscountOnPenalty())
            values.put(KEY_id_bonusdays, contact.getBonusDays())
            values.put(KEY_id_penalty_percentage, contact.getPenaltyPercentage())
            values.put(KEY_id_bonus_percentage, contact.getBonusPercentage())
            values.put(KEY_id_bonus_amount, contact.getBonusAmount())
            values.put(KEY_gd_pendingdays, contact.getInstalPendingDay())
            values.put(KEY_gd_colelcted, contact.getPaidAmount())
            values.put(KEY_gd_installmentamnt, contact.getCurrentInstallmentAmount())
            values.put(KEY_id_penaltyamnt, contact.getPenaltyAmounts())

            db.insert(Table_InstallmentDetails, null, values)
        }
        db.close()
    }

    fun addGroupDetails(sched: ArrayList<GroupdetailsModel>) {
        val db = this.writableDatabase
        for (i in sched.indices) {

            val values = ContentValues()
            val contact = sched[i]

            values.put(KEY_gd_enrollemntid, contact.getEntrollmentId())
            values.put(KEY_gd_groupid, contact.getGroupId())
            values.put(KEY_gd_groupname, contact.getGroupName())
            values.put(KEY_gd_chitvalue, contact.getChitValue())
            values.put(KEY_gd_auctiondate, contact.getAuctionDate())
            values.put(KEY_gd_due_no, contact.getdue_no())
            values.put(KEY_gd_pending_due_no, contact.getpending_due_no())
            values.put(KEY_gd_ticketno, contact.getTicketNo())
            values.put(KEY_gd_installmentamnt, contact.getInstallAmount())
            values.put(KEY_gd_colelcted, contact.getPaidAmount())
            values.put(KEY_gd_tobecollected, contact.getPendingAmount())
            values.put(KEY_gd_advanceamnt, contact.getAdvanceAmount())
            values.put(KEY_gd_pendingdays, contact.getPendingDays())
            values.put(KEY_gd_bonus, contact.getBonus())
            values.put(KEY_gd_discountonpenalty, contact.getDiscountonpenalty())
            values.put(KEY_gd_discountondue, contact.getDiscountondue())
            values.put(KEY_gd_prizedstatus, contact.getPrizedStatus())
            values.put(KEY_gd_customerid, contact.getCustomerId())
            db.insert(Table_GroupDetails, null, values)
        }
        db.close()
    }

    fun OfflinecustomerSize(): Int {
        val countQuery = "SELECT  * FROM $Table_Customers"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)
        val value = cursor.count
        db.close()

        return value
    }

    fun OfflineReceiptSize(): Int {
        val countQuery = "SELECT  * FROM $Table_OfflineReceiptDetails WHERE $KEY_Posted = 'No'"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)
        val value = cursor.count
        db.close()

        return value
    }

    fun OfflineReceiptSizeToday(): Int {
        val countQuery =
            "SELECT  * FROM $Table_OfflineReceiptDetails WHERE $KEY_Posted = 'No' AND $KEY_gd_receiptdate = '${BaseUtils.CurrentDate_ymd()}'"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)
        val value = cursor.count
        db.close()

        return value
    }

    fun getGroupName(id: Int): String {

        val selectQuery = "SELECT  $KEY_gd_groupname  FROM ${Table_GroupDetails} where ${KEY_gd_groupid} = '$id'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        var total = ""
        if (cursor.moveToFirst()) {
            do {
                try {
                    total = cursor.getString(0)
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }
            } while (cursor.moveToNext())
        }
        db.close()
        return total
    }


    companion object {

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "db_offlines.db"
        private val KEY_ID = "incid"
        private val Table_Customers = "Customers_table"//GroupDetails Table
        private val KEY_c_customerid = "customerid"
        private val KEY_c_customername = "customername"
        private val KEY_c_customercode = "customercode"
        private val KEY_c_customerpaid = "customerpaid"
        private val KEY_c_customerpending = "customerpending"
        private val KEY_c_customermobile = "customermobile"
        private val KEY_c_customerphone = "customerphone"
        private val Table_GroupDetails = "GroupDetails_table"//GroupDetails Table
        private val KEY_gd_enrollemntid = "gd_enrollemntid"
        private val KEY_gd_customerid = "gd_customerid"
        private val KEY_gd_groupid = "gd_groupid"
        private val KEY_gd_groupname = "gd_groupname"
        private val KEY_gd_chitvalue = "gd_chitvalue"
        private val KEY_gd_auctiondate = "gd_auctiondate"
        private val KEY_gd_due_no = "due_no"
        private val KEY_gd_pending_due_no = "pending_due_no"
        private val KEY_gd_ticketno = "gd_ticketno"
        private val KEY_gd_installmentamnt = "gd_installmentamnt"
        private val KEY_gd_colelcted = "gd_colelcted"
        private val KEY_gd_tobecollected = "gd_tobecollected"
        private val KEY_gd_advanceamnt = "gd_advanceamnt"
        private val KEY_gd_pendingdays = "gd_pendingday"
        private val KEY_gd_bonus = "gd_bonus"
        private val KEY_gd_discountonpenalty = "gd_discountonpenalty"
        private val KEY_gd_discountondue = "gd_discountondue"
        private val KEY_gd_prizedstatus = "gd_prizedstatus"
        private val Table_InstallmentDetails = "InstallmentDetails_table"//InstallmentDetails Table
        private val KEY_id_auctionno = "id_auctionno"
        private val KEY_id_KEY_id_pending_auction_no = "id_pending_auction_no"

        private val KEY_id_biddingid = "id_biddingid"
        private val KEY_id_bonusdays = "id_bonusdays"
        private val KEY_id_penalty_percentage = "id_penalty_percentage"
        private val KEY_id_bonus_percentage = "id_bonus_percentage "
        private val KEY_id_bonus_amount = "id_bonus_amount"
        private val KEY_id_penaltyamnt = "id_penaltyamnt"
        private val Table_OfflineReceiptDetails =
            "OfflineReceipts_table"//ReceiptInstallmentDetails Table(store offline receipts)
        private val Table_OfflineInstalmentsDetails =
            "OfflineInstallments_table"//ReceiptInstallmentDetails Table(store offline receipts)
        private val KEY_recno = "key_recno"
        private val KEY_offlinerecno = "key_offrecno"
        private val KEY_rid_payableamount = "rid_payableamount"
        private val KEY_rid_receiptamount = "rid_payingamount"
        private val KEY_gd_otherbranch = "KEY_gd_otherbranch"
        private val KEY_gd_receiptdate = "KEY_gd_receiptdate"
        private val KEY_gd_adjustid = "KEY_gd_adjustid"
        private val KEY_gd_receivedamount = "KEY_gd_receivedamount"
        private val KEY_gd_paytypeid = "KEY_gd_paytypeid"
        private val KEY_gd_debitto = "KEY_gd_debitto"
        private val KEY_gd_chequeno = "KEY_gd_chequeno"
        private val KEY_gd_chequedate = "KEY_gd_chequedate"
        private val KEY_gd_chequecleardate = "KEY_gd_chequecleardate"
        private val KEY_gd_banknameid = "KEY_gd_banknameid"
        private val KEY_gd_chequebranchname = "KEY_gd_chequebranchname"
        private val KEY_gd_transno = "KEY_gd_transno"
        private val KEY_gd_transdate = "KEY_gd_transdate"
        private val KEY_gd_receipttime = "KEY_gd_receipttime"
        private val KEY_gd_printed = "KEY_gd_printed"
        private val KEY_gd_remarks = "KEY_gd_remarks"
        private val KEY_gd_receiptamount = "KEY_gd_receiptamount"
        private val KEY_gd_advonly = "KEY_gd_advonly"
        private val KEY_Posted = "KEY_Posted"

    }

}
