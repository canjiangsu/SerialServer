// ISerialCallback.aidl
package com.t.serialserver;

// Declare any non-default types here with import statements

interface ISerialCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void RespDataFromSerial(int value);
}
