package jp.jaxa.iss.kibo.rpc.defaultapk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import jp.jaxa.iss.kibo.rpc.defaultapk.constant.Constants;

public class PatternMatcher {
    private Mat[] templates;
    private int[] templateMatchCounts;
    private Mat cameraMatrix;
    private Mat cameraCoefficients;

    public PatternMatcher(Mat cameraMatrix, Mat cameraCoefficients) {
        this.templates = new Mat[Constants.TEMPLATE_FILE_NAMES.length];
        this.templateMatchCounts = new int[Constants.TEMPLATE_FILE_NAMES.length];
        this.cameraMatrix = cameraMatrix;
        this.cameraCoefficients = cameraCoefficients;
    }

    public void loadTemplates(InputStreamGetter getter) throws IOException {
        for (int i = 0; i < Constants.TEMPLATE_FILE_NAMES.length; i++) {
            Log.i("debug", Constants.TEMPLATE_FILE_NAMES[i]);
            InputStream inputStream = getter.getInputStream(Constants.TEMPLATE_FILE_NAMES[i]);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            Mat mat = new Mat();
            Utils.bitmapToMat(bitmap, mat);
            Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2GRAY);
            templates[i] = mat;
            Log.i("debug", "matrix" + templates[i]);
            inputStream.close();
        }
    }

    public int[] matchTemplates(Mat targetImg) {
        Mat undistortImg = new Mat();
        Calib3d.undistort(targetImg, undistortImg, this.cameraMatrix, this.cameraCoefficients);

        for (int tempNum = 0; tempNum < templates.length; tempNum++) {
            Mat template = templates[tempNum].clone();
            int matchCnt = 0;
            List<Point> matches = new ArrayList<>();

            int widthMin = 20;
            int widthMax = 100;
            int changeWidth = 5;
            int changeAngle = 45;

            for (int i = widthMin; i <= widthMax; i += changeWidth) {
                for (int j = 0; j <= 360; j += changeAngle) {
                    Mat resizedTemp = resizeImg(template, i);
                    Mat rotResizedTemp = rotImg(resizedTemp, j);
                    Mat result = new Mat();
                    Imgproc.matchTemplate(undistortImg, rotResizedTemp, result, Imgproc.TM_CCOEFF_NORMED);

                    double threshold = 0.8;
                    Core.MinMaxLocResult mmlr = Core.minMaxLoc(result);
                    double maxVal = mmlr.maxVal;

                    if (maxVal >= threshold) {
                        Mat thresholdedResult = new Mat();
                        Imgproc.threshold(result, thresholdedResult, threshold, 1.0, Imgproc.THRESH_TOZERO);
                        for (int y = 0; y < thresholdedResult.rows(); y++) {
                            for (int x = 0; x < thresholdedResult.cols(); x++) {
                                if (thresholdedResult.get(y, x)[0] > 0) {
                                    matches.add(new Point(x, y));
                                }
                            }
                        }
                    }
                }
            }
            List<Point> filteredMatches = removeDuplicates(matches);
            matchCnt += filteredMatches.size();
            templateMatchCounts[tempNum] = matchCnt;
        }
        return templateMatchCounts;
    }

    public int getMostMatchedTemplateIndex() {
        return getMaxIndex(templateMatchCounts);
    }

    private Mat resizeImg(Mat img, int width) {
        int height = (int) (img.rows() * ((double) width / img.cols()));
        Mat resizedImg = new Mat();
        Imgproc.resize(img, resizedImg, new Size(width, height));
        return resizedImg;
    }

    private Mat rotImg(Mat img, int angle) {
        Point center = new Point(img.cols() / 2.0, img.rows() / 2.0);
        Mat rotatedMat = Imgproc.getRotationMatrix2D(center, angle, 1.0);
        Mat rotatedImg = new Mat();
        Imgproc.warpAffine(img, rotatedImg, rotatedMat, img.size());
        return rotatedImg;
    }

    private List<Point> removeDuplicates(List<Point> points) {
        double length = 10;
        List<Point> filteredList = new ArrayList<>();
        for (Point point : points) {
            boolean isInclude = false;
            for (Point checkPoint : filteredList) {
                double distance = calculateDistance(point, checkPoint);
                if (distance <= length) {
                    isInclude = true;
                    break;
                }
            }
            if (!isInclude) {
                filteredList.add(point);
            }
        }
        return filteredList;
    }

    private double calculateDistance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    private int getMaxIndex(int[] array) {
        int max = 0;
        int maxIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    @FunctionalInterface
    public interface InputStreamGetter {
        InputStream getInputStream(String filename) throws IOException;
    }
}
