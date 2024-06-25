package com.mazenet.mani.gurugubera.Utilities;


import org.jetbrains.annotations.NotNull;

public interface MvpView {

    @NotNull

    void showProgressDialog();

    void hideProgressDialog();

    void showDialogWithError(int errorCode, String error);

    void showNetWorkError();


}
