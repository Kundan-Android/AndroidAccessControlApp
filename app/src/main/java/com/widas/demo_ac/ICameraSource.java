package com.widas.demo_ac;

import java.io.File;

public interface ICameraSource {
    void blinkedEvent();
    void pictureTaken(File file);
}
