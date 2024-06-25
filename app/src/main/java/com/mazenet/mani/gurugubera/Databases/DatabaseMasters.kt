package com.mazenet.mani.gurugubera.Databases

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mazenet.mani.gurugubera.Model.*
import com.mazenet.mani.gurugubera.Utilities.Constants
import java.util.*


class DatabaseMasters(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        val Create_tenant_table = ("CREATE TABLE " + Table_Tenants + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_tenantid + " TEXT,"
                + KEY_tenantname + " TEXT"
                + ")")

        val Create_branch_table = ("CREATE TABLE " + Table_Branches + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_branchid + " INTEGER,"
                + KEY_branchname + " TEXT,"
                + KEY_tenantid + " INTEGER"
                + ")")

        val Create_state_table = ("CREATE TABLE " + Table_states + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_stateid + " INTEGER,"
                + KEY_countryid + " INTEGER,"
                + KEY_statename + " TEXT"
                + ")")

        val Create_district_table = ("CREATE TABLE " + Table_district + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_districtid + " INTEGER,"
                + KEY_stateid + " INTEGER,"
                + KEY_districtname + " TEXT"
                + ")")

        val Create_city_table = ("CREATE TABLE " + Table_cities + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_districtid + " INTEGER,"
                + KEY_stateid + " INTEGER,"
                + KEY_cityid + " INTEGER,"
                + KEY_cityname + " TEXT"
                + ")")

        val Create_followupstatustype_table = ("CREATE TABLE " + Table_followupstatus_type + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_folowupstatusd + " INTEGER,"
                + KEY_followupstatusname + " TEXT"
                + ")")
        val Create_banklist_table = ("CREATE TABLE " + Table_BAnksList + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_bl_bankid + " INTEGER,"
                + KEY_bl_bankname + " TEXT"
                + ")")
        val Create_remarklist_table = ("CREATE TABLE " + Table_Remarklist + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_rl_id + " TEXT,"
                + KEY_rl_remarkname + " TEXT,"
                + KEY_tenantid + " TEXT"
                + ")")
        val Create_paymenttype_table = ("CREATE TABLE " + Table_PaymentType + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_pt_paytypeid + " INTEGER,"
                + KEY_pt_paytypename + " TEXT"
                + ")")

        val Create_GroupDetails_table = ("CREATE TABLE " + Table_GroupDetails + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_gd_enrollemntid + " TEXT,"
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

        val Create_ReceiptInstallment_Details_Table = ("CREATE TABLE " + Table_ReceiptInstallmentDetails + "("
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
                + KEY_id_penaltyamnt + " TEXT,"
                + KEY_rid_payableamount + " TEXT,"
                + KEY_rid_receiptamount + " TEXT"
                + ")")

        val Create_TempInstallments_Table = ("CREATE TABLE " + Table_TempInstallments + "("
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
                + KEY_id_penaltyamnt + " TEXT,"
                + KEY_rid_payableamount + " TEXT,"
                + KEY_rid_receiptamount + " TEXT,"
                + KEY_id_nearpenaltyamnt + " TEXT"
                + ")")

        db.execSQL(Create_tenant_table)
        db.execSQL(Create_branch_table)
        db.execSQL(Create_state_table)
        db.execSQL(Create_district_table)
        db.execSQL(Create_city_table)
        db.execSQL(Create_followupstatustype_table)
        db.execSQL(Create_GroupDetails_table)
        db.execSQL(Create_Installment_Details_Table)
        db.execSQL(Create_ReceiptInstallment_Details_Table)
        db.execSQL(Create_paymenttype_table)
        db.execSQL(Create_banklist_table)
        db.execSQL(Create_remarklist_table)
        db.execSQL(Create_TempInstallments_Table)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $Table_Tenants")
        db.execSQL("DROP TABLE IF EXISTS $Table_Branches")
        db.execSQL("DROP TABLE IF EXISTS $Table_states")
        db.execSQL("DROP TABLE IF EXISTS $Table_district")
        db.execSQL("DROP TABLE IF EXISTS $Table_cities")
        db.execSQL("DROP TABLE IF EXISTS $Table_followupstatus_type")
        db.execSQL("DROP TABLE IF EXISTS $Table_GroupDetails")
        db.execSQL("DROP TABLE IF EXISTS $Table_InstallmentDetails")
        db.execSQL("DROP TABLE IF EXISTS $Table_ReceiptInstallmentDetails")
        db.execSQL("DROP TABLE IF EXISTS $Table_PaymentType")
        db.execSQL("DROP TABLE IF EXISTS $Table_BAnksList")
        db.execSQL("DROP TABLE IF EXISTS $Table_Remarklist")
        db.execSQL("DROP TABLE IF EXISTS $Table_TempInstallments")

        onCreate(db)
    }

    fun deleteTenantTable() {
        val db = this.writableDatabase
        db.delete(Table_Tenants, null, null)
        db.close()
    }

    fun deleteRemarkTable() {
        val db = this.writableDatabase
        db.delete(Table_Remarklist, null, null)
        db.close()
    }

    fun deletePaymenttypeTable() {
        val db = this.writableDatabase
        db.delete(Table_PaymentType, null, null)
        db.close()
    }
    fun deleteBankListTable() {
        val db = this.writableDatabase
        db.delete(Table_BAnksList, null, null)
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

    fun deleteRceiptInstallmentsDetailsTable() {
        val db = this.writableDatabase
        db.delete(Table_ReceiptInstallmentDetails, null, null)
//        db.close()
    }
    fun deleteTable_TempInstallments() {
        val db = this.writableDatabase
        db.delete(Table_TempInstallments, null, null)
//        db.close()
    }

    fun deleteStateTable() {
        val db = this.writableDatabase
        db.delete(Table_states, null, null)
        db.close()
    }

    fun deletedistrictTable() {
        val db = this.writableDatabase
        db.delete(Table_district, null, null)
        db.close()
    }

    fun deleteCityTable() {
        val db = this.writableDatabase
        db.delete(Table_cities, null, null)
        db.close()
    }

    fun deleteFollowuostatusTypeTable() {
        val db = this.writableDatabase
        db.delete(Table_followupstatus_type, null, null)
        db.close()
    }

    fun deleteBranchTable() {
        val db = this.writableDatabase
        db.delete(Table_Branches, null, null)
        db.close()
    }

    fun addBranches(sched: ArrayList<BranchModel>) {
        val db = this.writableDatabase
        for (i in sched.indices) {

            val values = ContentValues()
            val contact = sched[i]

            values.put(KEY_branchid, contact.getBranchId())
            values.put(KEY_branchname, contact.getBranchName())
            values.put(KEY_tenantid, contact.getTenantId())
            db.insert(Table_Branches, null, values)
        }
        db.close()
    }
    fun addRemarks(sched: ArrayList<RemarksListmodel>) {
        val db = this.writableDatabase
        for (i in sched.indices) {

            val values = ContentValues()
            val contact = sched[i]

            values.put(KEY_rl_id, contact.getCollectionRemarksId())
            values.put(KEY_rl_remarkname, contact.getRemarkName())
            values.put(KEY_tenantid, contact.getTenantId())
            db.insert(Table_Remarklist, null, values)
        }
        db.close()
    }


    fun getRemarks(): ArrayList<RemarksListmodel> {
        val contactList = ArrayList<RemarksListmodel>()
        val selectQuery = "SELECT  * FROM $Table_Remarklist"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val contact = RemarksListmodel()
                contact.setCollectionRemarksId(cursor.getString(1))
                contact.setRemarkName(cursor.getString(2))
                contact.setTenantId(cursor.getString(3))
                contactList.add(contact)

            } while (cursor.moveToNext())
        }
        db.close()

        return contactList
    }
    fun getBranches(): ArrayList<BranchModel> {
        val contactList = ArrayList<BranchModel>()
        val selectQuery = "SELECT  * FROM $Table_Branches"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val contact = BranchModel()
                contact.setBranchId(cursor.getInt(1))
                contact.setBranchName(cursor.getString(2))
                contact.setTenantId(cursor.getInt(3))
                contactList.add(contact)

            } while (cursor.moveToNext())
        }
        db.close()

        return contactList
    }

    fun addStates(sched: ArrayList<statesModel>) {
        val db = this.writableDatabase
        for (i in sched.indices) {

            val values = ContentValues()
            val contact = sched[i]

            values.put(KEY_stateid, contact.getStateId())
            values.put(KEY_countryid, contact.getCountryId())
            values.put(KEY_statename, contact.getStateName())
            db.insert(Table_states, null, values)
        }
        db.close()
    }

    fun getStates(): ArrayList<statesModel> {
        val contactList = ArrayList<statesModel>()
        val selectQuery = "SELECT  * FROM $Table_states"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val contact = statesModel()
                contact.setStateId(cursor.getInt(1))
                contact.setCountryId(cursor.getInt(2))
                contact.setStateName(cursor.getString(3))
                contactList.add(contact)

            } while (cursor.moveToNext())
        }
        db.close()

        return contactList
    }

    fun addDistrict(sched: ArrayList<districtModel>) {
        val db = this.writableDatabase
        for (i in sched.indices) {

            val values = ContentValues()
            val contact = sched[i]

            values.put(KEY_stateid, contact.getStateId())
            values.put(KEY_districtid, contact.getDistrictId())
            values.put(KEY_districtname, contact.getDistrictName())
            db.insert(Table_district, null, values)
        }
        db.close()
    }

    fun addPaymentType(sched: ArrayList<PaymentTypeModel>) {
        val db = this.writableDatabase
        for (i in sched.indices) {

            val values = ContentValues()
            val contact = sched[i]

            values.put(KEY_pt_paytypeid, contact.getTenantId())
            values.put(KEY_pt_paytypename, contact.getPaymentName())
            db.insert(Table_PaymentType, null, values)
        }
        db.close()
    }
    fun addBankList(sched: ArrayList<BanksListModel>) {
        val db = this.writableDatabase
        for (i in sched.indices) {

            val values = ContentValues()
            val contact = sched[i]

            values.put(KEY_bl_bankid, contact.getCustomerBankId())
            values.put(KEY_bl_bankname, contact.getCustomerBankName())
            db.insert(Table_BAnksList, null, values)
        }
        db.close()
    }
    fun getBankList(): ArrayList<BanksListModel> {
        val contactList = ArrayList<BanksListModel>()
        val selectQuery = "SELECT  * FROM $Table_BAnksList"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val contact = BanksListModel()
                contact.setCustomerBankId(cursor.getInt(1))
                contact.setCustomerBankName(cursor.getString(2))
                contactList.add(contact)

            } while (cursor.moveToNext())
        }
        db.close()
        return contactList
    }

    fun getPaymentType(): ArrayList<PaymentTypeModel> {
        val contactList = ArrayList<PaymentTypeModel>()
        val selectQuery = "SELECT  * FROM $Table_PaymentType"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val contact = PaymentTypeModel()
                contact.setTenantId(cursor.getInt(1))
                contact.setPaymentName(cursor.getString(2))
                contactList.add(contact)

            } while (cursor.moveToNext())
        }
        db.close()
        return contactList
    }

    fun getDistricts(): ArrayList<districtModel> {
        val contactList = ArrayList<districtModel>()
        val selectQuery = "SELECT  * FROM $Table_district"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val contact = districtModel()
                contact.setDistrictId(cursor.getInt(1))
                contact.setStateId(cursor.getInt(2))
                contact.setDistrictName(cursor.getString(3))
                contactList.add(contact)

            } while (cursor.moveToNext())
        }
        db.close()

        return contactList
    }

    fun addCity(sched: ArrayList<citymodel>) {
        val db = this.writableDatabase
        for (i in sched.indices) {

            val values = ContentValues()
            val contact = sched[i]

            values.put(KEY_stateid, contact.getStateId())
            values.put(KEY_districtid, contact.getDistrictId())
            values.put(KEY_cityid, contact.getCityId())
            values.put(KEY_cityname, contact.getCityName())
            db.insert(Table_cities, null, values)
        }
        db.close()
    }

    fun getCities(): ArrayList<citymodel> {
        val contactList = ArrayList<citymodel>()
        val selectQuery = "SELECT  * FROM $Table_cities"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val contact = citymodel()
                contact.setDistrictId(cursor.getInt(1))
                contact.setStateId(cursor.getInt(2))
                contact.setCityId(cursor.getInt(3))
                contact.setCityName(cursor.getString(4))
                contactList.add(contact)

            } while (cursor.moveToNext())
        }
        db.close()

        return contactList
    }

    fun addFollowStatusType(sched: ArrayList<followstatustypeModel>) {
        val db = this.writableDatabase
        for (i in sched.indices) {

            val values = ContentValues()
            val contact = sched[i]

            values.put(KEY_folowupstatusd, contact.getFollowupStatusId())
            values.put(KEY_followupstatusname, contact.getFollowupStatusName())
            db.insert(Table_followupstatus_type, null, values)
        }
        db.close()
    }

    fun getenrollmentList(): ArrayList<GroupListModel> {
        val db = this.writableDatabase
        val getListsql =
           // "SELECT $KEY_gd_enrollemntid,$KEY_gd_groupid,$KEY_gd_groupname,$KEY_gd_chitvalue,$KEY_gd_auctiondate,$KEY_gd_prizedstatus,IFNULL($KEY_gd_installmentamnt,0),IFNULL($KEY_gd_colelcted,0),IFNULL($KEY_gd_tobecollected,0),IFNULL($KEY_gd_advanceamnt,0),$KEY_Auc_no,$KEY_Pen_Auc_no,$KEY_gd_ticketno FROM $Table_GroupDetails"
            "SELECT $KEY_gd_enrollemntid,$KEY_gd_groupid,$KEY_gd_groupname,$KEY_gd_chitvalue,$KEY_gd_auctiondate,$KEY_gd_prizedstatus,IFNULL($KEY_gd_installmentamnt,0),IFNULL($KEY_gd_colelcted,0),IFNULL($KEY_gd_tobecollected,0),IFNULL($KEY_gd_advanceamnt,0),IFNULL($KEY_gd_due_no,0),IFNULL($KEY_gd_pending_due_no,0),$KEY_gd_ticketno FROM $Table_GroupDetails"
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
            "SELECT $KEY_gd_enrollemntid,$KEY_gd_groupid,$KEY_gd_groupname,$KEY_gd_chitvalue,$KEY_gd_auctiondate,$KEY_gd_prizedstatus,IFNULL($KEY_gd_installmentamnt,0),IFNULL($KEY_gd_colelcted,0),IFNULL($KEY_gd_tobecollected,0),IFNULL($KEY_gd_advanceamnt,0),$KEY_gd_ticketno FROM $Table_GroupDetails WHERE $KEY_gd_enrollemntid = $enrolid"
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

    fun getInstallmentDetails(enrolid: String) {
        val db = this.writableDatabase
        val installmentList = ArrayList<InstallmentListModel>()
        val instalmentqry =
            "SELECT IFNULL($KEY_id_penaltyamnt,0)," +
                    "IFNULL($KEY_id_bonus_amount,0),IFNULL($KEY_gd_discountondue,0)," +
                    "IFNULL($KEY_gd_discountonpenalty,0),IFNULL($KEY_id_auctionno,0),IFNULL($KEY_id_bonusdays,0)," +
                    "IFNULL($KEY_id_bonus_percentage,0),IFNULL($KEY_id_penalty_percentage,0)," +
                    "IFNULL($KEY_gd_tobecollected,0),IFNULL($KEY_gd_colelcted,0)," +
                    "IFNULL($KEY_gd_installmentamnt,0),IFNULL($KEY_id_biddingid,0)," +
                    "IFNULL($KEY_gd_pendingdays,0) FROM $Table_InstallmentDetails" +
                    " WHERE $KEY_gd_enrollemntid = $enrolid"
        val instalmentcursor = db.rawQuery(instalmentqry, null)
        if (instalmentcursor.count > 0) {
            if (instalmentcursor.moveToFirst()) {
                do {

                    val receiptarray = InstallmentListModel()
                    receiptarray.penalty = Constants.isEmtytoZero(instalmentcursor.getString(0))
                    receiptarray.bonus = Constants.isEmtytoZero(instalmentcursor.getString(1))
                    receiptarray.discontondue = Constants.isEmtytoZero(instalmentcursor.getString(2))
                    receiptarray.discountonpenalty = Constants.isEmtytoZero(instalmentcursor.getString(3))
                    receiptarray.auctionno = instalmentcursor.getString(4)
                    receiptarray.bonusdays = Constants.isEmtytoZero(instalmentcursor.getString(5))
                    receiptarray.bonus_percentage = Constants.isEmtytoZero(instalmentcursor.getString(6))
                    receiptarray.penalty_percentage = Constants.isEmtytoZero(instalmentcursor.getString(7))
                    receiptarray.pending = Constants.isEmtytoZero(instalmentcursor.getString(8))
                    receiptarray.paid = Constants.isEmtytoZero(instalmentcursor.getString(9))
                    receiptarray.installment = Constants.isEmtytoZero(instalmentcursor.getString(10))
                    receiptarray.biddingid = instalmentcursor.getString(11)
                    receiptarray.pendingdays = Constants.isEmtytoZero(instalmentcursor.getString(12))
                    receiptarray.enrolid = enrolid
                    receiptarray.payableamount = "0"
                    receiptarray.Receiptamount = "0"
                    installmentList.add(receiptarray)
                } while (instalmentcursor.moveToNext())
            }
        }
        instalmentcursor.close()
        deleteRceiptInstallmentsDetailsTable()
        deleteTable_TempInstallments()
        addInstallmentList(installmentList)
        addTempInstallments(installmentList)
        db.close()
    }

    fun updatepayamount(biddingid: String, payamount: String,receiptAmount:String,nearpenalty:String) {
        val db = this.writableDatabase
        val upudatesql =
            "UPDATE $Table_TempInstallments SET $KEY_rid_payableamount = $payamount,$KEY_rid_receiptamount = '$receiptAmount',$KEY_id_nearpenaltyamnt = '$nearpenalty' WHERE $KEY_id_biddingid = '$biddingid'"
        db.execSQL(upudatesql)
        db.close()
    }
    fun makereceiptzero() {
        val db = this.writableDatabase
        val upudatesql =
            "UPDATE $Table_TempInstallments SET $KEY_rid_payableamount = '0' WHERE 1"
        db.execSQL(upudatesql)
        db.close()
    }
    fun updatepenalty(biddingid: String, penalty: String) {
        val db = this.writableDatabase
        val upudatesql =
            "UPDATE $Table_TempInstallments SET $KEY_id_penaltyamnt = '$penalty'  WHERE $KEY_id_biddingid = '$biddingid'"
        println("update penalty $upudatesql")
        db.execSQL(upudatesql)
        db.close()

    }

    fun addInstallmentList(sched: ArrayList<InstallmentListModel>) {
        val db = this.writableDatabase
        for (i in sched.indices) {

            val values = ContentValues()
            val contact = sched[i]

            values.put(KEY_gd_enrollemntid, contact.enrolid)
            values.put(KEY_id_auctionno, contact.auctionno)
            values.put(KEY_id_biddingid, contact.biddingid)
            values.put(KEY_gd_tobecollected, contact.pending)
            values.put(KEY_gd_discountondue, contact.discontondue)
            values.put(KEY_gd_discountonpenalty, contact.discountonpenalty)
            values.put(KEY_id_bonusdays, contact.bonus)
            values.put(KEY_id_penalty_percentage, contact.penalty_percentage)
            values.put(KEY_id_bonus_percentage, contact.bonus_percentage)
            values.put(KEY_id_bonus_amount, contact.bonus)
            values.put(KEY_gd_pendingdays, contact.pendingdays)
            values.put(KEY_gd_colelcted, contact.paid)
            values.put(KEY_gd_installmentamnt, contact.installment)
            values.put(KEY_id_penaltyamnt, contact.penalty)
            values.put(KEY_rid_payableamount, contact.payableamount)
            values.put(KEY_rid_receiptamount, contact.Receiptamount)
            db.insert(Table_ReceiptInstallmentDetails, null, values)
        }
        db.close()
    }
    fun addTempInstallments(sched: ArrayList<InstallmentListModel>) {
        val db = this.writableDatabase
        for (i in sched.indices) {

            val values = ContentValues()
            val contact = sched[i]

            values.put(KEY_gd_enrollemntid, contact.enrolid)
            values.put(KEY_id_auctionno, contact.auctionno)

            values.put(KEY_id_biddingid, contact.biddingid)
            values.put(KEY_gd_tobecollected, contact.pending)
            values.put(KEY_gd_discountondue, contact.discontondue)
            values.put(KEY_gd_discountonpenalty, contact.discountonpenalty)
            values.put(KEY_id_bonusdays, contact.bonus)
            values.put(KEY_id_penalty_percentage, contact.penalty_percentage)
            values.put(KEY_id_bonus_percentage, contact.bonus_percentage)
            values.put(KEY_id_bonus_amount, contact.bonus)
            values.put(KEY_gd_pendingdays, contact.pendingdays)
            values.put(KEY_gd_colelcted, contact.paid)
            values.put(KEY_gd_installmentamnt, contact.installment)
            values.put(KEY_id_penaltyamnt, contact.penalty)
            values.put(KEY_rid_payableamount, contact.payableamount)
            values.put(KEY_rid_receiptamount, contact.Receiptamount)
            values.put(KEY_id_nearpenaltyamnt, contact.Receiptamount)
            db.insert(Table_TempInstallments, null, values)
        }
        db.close()
    }

    fun getInstallmentList(): ArrayList<InstallmentListModel> {
        val contactList = ArrayList<InstallmentListModel>()
        val selectQuery = "SELECT  * FROM $Table_ReceiptInstallmentDetails"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val contact = InstallmentListModel()
                contact.enrolid = (cursor.getString(1))
                contact.auctionno = (cursor.getString(2))
                contact.biddingid = (cursor.getString(3))
                contact.pending = (cursor.getString(4))
                contact.discontondue == (cursor.getString(5))
                contact.discountonpenalty == (cursor.getString(6))
                contact.bonusdays == (cursor.getString(7))
                contact.penalty_percentage == (cursor.getString(8))
                contact.bonus_percentage = (cursor.getString(9))
                contact.bonus = (cursor.getString(10))
                contact.pendingdays = (cursor.getString(11))
                contact.paid = (cursor.getString(12))
                contact.installment = (cursor.getString(13))
                contact.penalty = (cursor.getString(14))
                contact.payableamount = (cursor.getString(15))
                contact.Receiptamount = (cursor.getString(16))
                contactList.add(contact)

            } while (cursor.moveToNext())
        }
        db.close()

        return contactList
    }
    fun getTempInstallments(): ArrayList<InstallmentListModel> {
        val contactList = ArrayList<InstallmentListModel>()
        val selectQuery = "SELECT  * FROM $Table_TempInstallments"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val contact = InstallmentListModel()
                contact.enrolid = (cursor.getString(1))
                contact.auctionno = (cursor.getString(2))
                contact.biddingid = (cursor.getString(3))
                contact.pending = (cursor.getString(4))
                contact.discontondue == (cursor.getString(5))
                contact.discountonpenalty == (cursor.getString(6))
                contact.bonusdays == (cursor.getString(7))
                contact.penalty_percentage == (cursor.getString(8))
                contact.bonus_percentage = (cursor.getString(9))
                contact.bonus = (cursor.getString(10))
                contact.pendingdays = (cursor.getString(11))
                contact.paid = (cursor.getString(12))
                contact.installment = (cursor.getString(13))
                contact.penalty = (cursor.getString(14))
                contact.payableamount = (cursor.getString(15))
                contact.Receiptamount = (cursor.getString(16))
                contact.nearpenalty = (cursor.getString(17))
                contactList.add(contact)

            } while (cursor.moveToNext())
        }
        db.close()

        return contactList
    }

    fun getFollowupstatusType(): ArrayList<followstatustypeModel> {
        val contactList = ArrayList<followstatustypeModel>()
        val selectQuery = "SELECT  * FROM $Table_followupstatus_type"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val contact = followstatustypeModel()
                contact.setFollowupStatusId(cursor.getInt(1))
                contact.setFollowupStatusName(cursor.getString(2))
                contactList.add(contact)

            } while (cursor.moveToNext())
        }
        db.close()

        return contactList
    }

    fun get_paymentTypeName(typeid:String):String
    {
        val selectQuery = "SELECT $KEY_pt_paytypename  FROM $Table_PaymentType where $KEY_pt_paytypeid = '$typeid'"
        println("query $selectQuery")
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        var typename = ""
        if (cursor.moveToFirst()) {
            do {
                try {
                    typename = cursor.getString(0)
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }
            } while (cursor.moveToNext())
        }
        db.close()
        return typename
    }
    fun get_BankName(typeid:String):String {
        val selectQuery = "SELECT $KEY_bl_bankname  FROM $Table_BAnksList WHERE $KEY_bl_bankid = '$typeid'"
        println("query $selectQuery")
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        var typename = ""
        if (cursor.moveToFirst()) {
            do {
                try {
                    typename = cursor.getString(0)
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }
            } while (cursor.moveToNext())
        }
        db.close()
        return typename
    }

    fun getbranchname(id: Int): String {

        val selectQuery = "SELECT  *  FROM $Table_Branches where $KEY_branchid = '$id'"
        println("query $selectQuery")
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        var total = ""
        if (cursor.moveToFirst()) {
            do {
                try {
                    total = cursor.getString(2)
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }
            } while (cursor.moveToNext())
        }
        db.close()
        return total
    }

    fun BranchTableSize(): Int {
        val countQuery = "SELECT  * FROM $Table_Branches"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)
        val value = cursor.count
        db.close()

        return value
    }

    fun RemarksListSize(): Int {
        val countQuery = "SELECT  * FROM $Table_Remarklist"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)
        val value = cursor.count
        db.close()

        return value
    }

    fun PaymentTypeSize(): Int {
        val countQuery = "SELECT  * FROM $Table_PaymentType"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)
        val value = cursor.count
        db.close()

        return value
    }

    fun BankListSize(): Int {
        val countQuery = "SELECT  * FROM $Table_BAnksList"
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)
        val value = cursor.count
        db.close()

        return value
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
            db.insert(Table_GroupDetails, null, values)
        }
        db.close()
    }

    companion object {

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "manikya_chits"

        private val Table_Tenants = "tenants_table"//Tenants Table
        private val KEY_ID = "incid"


        private val KEY_tenantid = "tenant_id"
        private val KEY_tenantname = "tenant_name"
        private val Table_Branches = "branch_table"//Bramches Table
        private val KEY_branchid = "branch_id"
        private val KEY_branchname = "branch_name"
        private val Table_states = "state_table"//State Table
        private val KEY_stateid = "state_id"
        private val KEY_countryid = "country_id"
        private val KEY_statename = "state_name"
        private val Table_district = "district_table"//District Table
        private val KEY_districtid = "disrtrict_id"
        private val KEY_districtname = "district_name"
        private val Table_cities = "cities_table"//City Table
        private val KEY_cityid = "city_id"
        private val KEY_cityname = "city_name"
        private val Table_followupstatus_type = "followupstatus_type_table"//FollowupStatusType Table
        private val KEY_folowupstatusd = "followupstatus_id"
        private val KEY_followupstatusname = "followupstatus_name"
        private val Table_GroupDetails = "GroupDetails_table"//GroupDetails Table
        private val KEY_gd_enrollemntid = "gd_enrollemntid"
        private val KEY_gd_due_no = "due_no"
        private val KEY_gd_pending_due_no = "pending_due_no"
        private val KEY_gd_customerid = "gd_customerid"
        private val KEY_gd_groupid = "gd_groupid"
        private val KEY_gd_groupname = "gd_groupname"
        private val KEY_gd_chitvalue = "gd_chitvalue"
        private val KEY_gd_auctiondate = "gd_auctiondate"
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

        private val KEY_id_biddingid = "id_biddingid"
        private val KEY_id_bonusdays = "id_bonusdays"
        private val KEY_id_penalty_percentage = "id_penalty_percentage"
        private val KEY_id_bonus_percentage = "id_bonus_percentage "
        private val KEY_id_bonus_amount = "id_bonus_amount"
        private val KEY_id_penaltyamnt = "id_penaltyamnt"
        private val KEY_id_nearpenaltyamnt = "id_nearpenaltyamnt"
        private val Table_ReceiptInstallmentDetails =
            "ReceiptInstallmentDetails_table"//ReceiptInstallmentDetails Table(Single use for receipt fragment)
         private val Table_TempInstallments =
            "TempInstallments_table"//ReceiptInstallmentDetails Table(Single use for receipt fragment)
        private val KEY_rid_payableamount = "rid_payableamount"
        private val KEY_rid_receiptamount = "rid_payingamount"
        private val Table_PaymentType = "paymenttype_table"//InstallmentDetails Table
        private val KEY_pt_paytypeid = "pt_paytypeid"
        private val KEY_pt_paytypename = "pt_paytypename"
        private val Table_BAnksList =
            "BAnksList_table"//ReceiptInstallmentDetails Table(Single use for manipulating receipts)
        private val KEY_bl_bankid = "bl_bankid"
        private val KEY_bl_bankname = "bl_bankname"
        private val Table_Remarklist =
            "Remarksist_Ltable"//ReceiptInstallmentDetails Table(Single use for manipulating receipts)
        private val KEY_rl_id = "KEY_rl_id"
        private val KEY_rl_remarkname = "KEY_rl_remarkname"
    }

}
