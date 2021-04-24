/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.griderScanner
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.griderscanner.helpers;

import android.util.Log;

import org.opencv.core.CvType;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.List;

public class MathUtils {

    public static MatOfPoint toMatOfPointInt(MatOfPoint2f mat) {
        MatOfPoint matInt = new MatOfPoint();
        mat.convertTo(matInt, CvType.CV_32S);
        return matInt;
    }

    public static MatOfPoint2f toMatOfPointFloat(MatOfPoint mat) {

        MatOfPoint2f matFloat = new MatOfPoint2f();
        mat.convertTo(matFloat, CvType.CV_32FC2);

        return matFloat;
    }

    public static double angle(Point p1, Point p2, Point p0) {

        double dx1 = p1.x - p0.x;
        double dy1 = p1.y - p0.y;
        double dx2 = p2.x - p0.x;
        double dy2 = p2.y - p0.y;
        double angle = (dx1 * dx2 + dy1 * dy2) / Math.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2) + 1e-10);
        return angle;
    }

    public static MatOfPoint2f scaleRectangle(MatOfPoint2f original, double scale) {
        List<Point> originalPoints = original.toList();
        List<Point> resultPoints = new ArrayList<Point>();
        for (Point point : originalPoints) {
            resultPoints.add(new Point(point.x * scale, point.y * scale));
        }

        MatOfPoint2f result = new MatOfPoint2f();
        result.fromList(resultPoints);

        return result;
    }

}
