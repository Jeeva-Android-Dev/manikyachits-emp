package com.mazenet.mani.gurugubera.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class CollAreaListmodel {

    @SerializedName("id")
    @Expose
    private var id: String? = null
    @SerializedName("collection_area")
    @Expose
    private var collectionArea: String? = null

    /**
     * No args constructor for use in serialization
     *
     */


    /**
     *
     * @param id
     * @param collectionArea
     */
    fun CollAreaListmodel(id: String, collectionArea: String){

        this.id = id
        this.collectionArea = collectionArea
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getCollectionArea(): String? {
        return collectionArea
    }

    fun setCollectionArea(collectionArea: String) {
        this.collectionArea = collectionArea
    }
    override fun toString(): String {
        return this.collectionArea.toString()
    }
}