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

import org.opencv.photo.Tonemap;

// C++: class TonemapMantiuk
//javadoc: TonemapMantiuk

public class TonemapMantiuk extends Tonemap {

    protected TonemapMantiuk(long addr) { super(addr); }

    // internal usage only
    public static TonemapMantiuk __fromPtr__(long addr) { return new TonemapMantiuk(addr); }

    //
    // C++:  float getSaturation()
    //

    //javadoc: TonemapMantiuk::getSaturation()
    public  float getSaturation()
    {
        
        float retVal = getSaturation_0(nativeObj);
        
        return retVal;
    }


    //
    // C++:  float getScale()
    //

    //javadoc: TonemapMantiuk::getScale()
    public  float getScale()
    {
        
        float retVal = getScale_0(nativeObj);
        
        return retVal;
    }


    //
    // C++:  void setSaturation(float saturation)
    //

    //javadoc: TonemapMantiuk::setSaturation(saturation)
    public  void setSaturation(float saturation)
    {
        
        setSaturation_0(nativeObj, saturation);
        
        return;
    }


    //
    // C++:  void setScale(float scale)
    //

    //javadoc: TonemapMantiuk::setScale(scale)
    public  void setScale(float scale)
    {
        
        setScale_0(nativeObj, scale);
        
        return;
    }


    @Override
    protected void finalize() throws Throwable {
        delete(nativeObj);
    }



    // C++:  float getSaturation()
    private static native float getSaturation_0(long nativeObj);

    // C++:  float getScale()
    private static native float getScale_0(long nativeObj);

    // C++:  void setSaturation(float saturation)
    private static native void setSaturation_0(long nativeObj, float saturation);

    // C++:  void setScale(float scale)
    private static native void setScale_0(long nativeObj, float scale);

    // native support for java finalize()
    private static native void delete(long nativeObj);

}
