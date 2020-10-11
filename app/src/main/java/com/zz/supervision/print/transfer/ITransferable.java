package com.zz.supervision.print.transfer;

import java.io.Serializable;

/**
 * Authored by KaushalD on 8/28/2016.
 */
public interface ITransferable extends Serializable {

    int getRequestCode();

    String getRequestType();

    String getData();
}
