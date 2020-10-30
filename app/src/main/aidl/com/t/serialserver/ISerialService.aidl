// ISerialService.aidl
package com.t.serialserver;

import com.t.serialserver.ISerialCallback;

// Declare any non-default types here with import statements

interface ISerialService {
    int getPid();
    int openSerial(ISerialCallback cb);
}
