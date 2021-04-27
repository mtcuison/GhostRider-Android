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
package org.opencv.video;

import org.opencv.core.Algorithm;
import org.opencv.core.Mat;

// C++: class DenseOpticalFlow
//javadoc: DenseOpticalFlow

public class DenseOpticalFlow extends Algorithm {

    protected DenseOpticalFlow(long addr) { super(addr); }

    // internal usage only
    public static DenseOpticalFlow __fromPtr__(long addr) { return new DenseOpticalFlow(addr); }

    //
    // C++:  void calc(Mat I0, Mat I1, Mat& flow)
    //

    //javadoc: DenseOpticalFlow::calc(I0, I1, flow)
    public  void calc(Mat I0, Mat I1, Mat flow)
    {
        
        calc_0(nativeObj, I0.nativeObj, I1.nativeObj, flow.nativeObj);
        
        return;
    }


    //
    // C++:  void collectGarbage()
    //

    //javadoc: DenseOpticalFlow::collectGarbage()
    public  void collectGarbage()
    {
        
        collectGarbage_0(nativeObj);
        
        return;
    }


    @Override
    protected void finalize() throws Throwable {
        delete(nativeObj);
    }



    // C++:  void calc(Mat I0, Mat I1, Mat& flow)
    private static native void calc_0(long nativeObj, long I0_nativeObj, long I1_nativeObj, long flow_nativeObj);

    // C++:  void collectGarbage()
    private static native void collectGarbage_0(long nativeObj);

    // native support for java finalize()
    private static native void delete(long nativeObj);

}
