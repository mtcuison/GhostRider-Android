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
package org.opencv.features2d;

import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FlannBasedMatcher;

// C++: class FlannBasedMatcher
//javadoc: FlannBasedMatcher

public class FlannBasedMatcher extends DescriptorMatcher {

    protected FlannBasedMatcher(long addr) { super(addr); }

    // internal usage only
    public static FlannBasedMatcher __fromPtr__(long addr) { return new FlannBasedMatcher(addr); }

    //
    // C++:   FlannBasedMatcher(Ptr_flann_IndexParams indexParams = makePtr<flann::KDTreeIndexParams>(), Ptr_flann_SearchParams searchParams = makePtr<flann::SearchParams>())
    //

    //javadoc: FlannBasedMatcher::FlannBasedMatcher()
    public   FlannBasedMatcher()
    {
        
        super( FlannBasedMatcher_0() );
        
        return;
    }


    //
    // C++: static Ptr_FlannBasedMatcher create()
    //

    //javadoc: FlannBasedMatcher::create()
    public static FlannBasedMatcher create()
    {
        
        FlannBasedMatcher retVal = FlannBasedMatcher.__fromPtr__(create_0());
        
        return retVal;
    }


    @Override
    protected void finalize() throws Throwable {
        delete(nativeObj);
    }



    // C++:   FlannBasedMatcher(Ptr_flann_IndexParams indexParams = makePtr<flann::KDTreeIndexParams>(), Ptr_flann_SearchParams searchParams = makePtr<flann::SearchParams>())
    private static native long FlannBasedMatcher_0();

    // C++: static Ptr_FlannBasedMatcher create()
    private static native long create_0();

    // native support for java finalize()
    private static native void delete(long nativeObj);

}
