package com.test.ishanishah.user.model;

import java.io.Serializable;
import java.util.List;

public class BridgeModel implements Serializable {
    List<UserData> userData;

    public List<UserData> getUserData() {
        return userData;
    }

    public void setUserData(List<UserData> userData) {
        this.userData = userData;
    }
}
