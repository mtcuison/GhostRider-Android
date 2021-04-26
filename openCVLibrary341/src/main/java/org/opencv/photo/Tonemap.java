/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.openCVLibrary341
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

//
// This file is auto-generated. Please don't modify it!
//
package org.opencv.photo;

import org.opencv.core.Algorithm;
import org.opencv.core.Mat;

// C++: class Tonemap
//javadoc: Tonemap

public class Tonemap extends Algorithm {

    protected Tonemap(long addr) { super(addr); }

    // internal usage only
    public static Tonemap __fromPtr__(long addr) { return new Tonemap(addr); }

    //
    // C++:  float getGamma()
    //

    //javadoc: Tonemap::getGamma()
    public  float getGamma()
    {
        
        float retVal = getGamma_0(nativeObj);
        
        return retVal;
    }


    //
    // C++:  void process(Mat src, Mat& dst)
    //

    //javadoc: Tonemap::process(src, dst)
    public  void process(Mat src, Mat dst)
    {
        
        process_0(nativeObj, src.nativeObj, dst.nativeObj);
        
        return;
    }


    //
    // C++:  void setGamma(float gamma)
    //

    //javadoc: Tonemap::setGamma(gamma)
    public  void setGamma(float gamma)
    {
        
        setGamma_0(nativeObj, gamma);
        
        return;
    }


    @Override
    protected void finalize() throws Throwable {
        delete(nativeObj);
    }



    // C++:  float getGamma()
    private static native float getGamma_0(long nativeObj);

    // C++:  void process(Mat src, Mat& dst)
    private static native void process_0(long nativeObj, long src_nativeObj, long dst_nativeObj);

    // C++:  void setGamma(float gamma)
    private static native void setGamma_0(long nativeObj, float gamma);

    // native support for java finalize()
    private static native void delete(long nativeObj);

}
