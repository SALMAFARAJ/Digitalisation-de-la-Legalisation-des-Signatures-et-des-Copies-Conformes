package com.example.demo.security;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.BFMatcher;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.ORB;
import org.opencv.core.DMatch;
import org.opencv.core.KeyPoint;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.highgui.HighGui;

public class FacialRecognitionUtil {
	private static final int MIN_GOOD_MATCHES = 10; 
    public static boolean compareImages(String image1Path, String image2Path) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Charger les images
        Mat img1 = Imgcodecs.imread(image1Path);
        Mat img2 = Imgcodecs.imread(image2Path);

        // Convertir en niveaux de gris
        Mat gray1 = new Mat();
        Mat gray2 = new Mat();
        Imgproc.cvtColor(img1, gray1, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(img2, gray2, Imgproc.COLOR_BGR2GRAY);

        // Appliquer ORB (Oriented FAST and Rotated BRIEF)
        ORB orb = ORB.create();
        MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
        MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
        Mat descriptors1 = new Mat();
        Mat descriptors2 = new Mat();
        orb.detectAndCompute(gray1, new Mat(), keypoints1, descriptors1);
        orb.detectAndCompute(gray2, new Mat(), keypoints2, descriptors2);

        // Matcher descripteur avec le BFMatcher
        DescriptorMatcher matcher = BFMatcher.create(BFMatcher.BRUTEFORCE_HAMMING);
        MatOfDMatch matches = new MatOfDMatch();
        matcher.match(descriptors1, descriptors2, matches);

        // Calculer la distance moyenne
        double max_dist = 0;
        double min_dist = 100;
        DMatch[] matchesArray = matches.toArray();
        for (int i = 0; i < matchesArray.length; i++) {
            double dist = matchesArray[i].distance;
            if (dist < min_dist) min_dist = dist;
            if (dist > max_dist) max_dist = dist;
        }

        // Filtrer les correspondances "bonnes"
        double threshold = 3 * min_dist;
        MatOfDMatch goodMatches = new MatOfDMatch();
        goodMatches.fromArray(matchesArray);
        goodMatches = filterGoodMatches(goodMatches, threshold);

        // Afficher les correspondances (à des fins de débogage)
        Mat imgMatches = new Mat();
        Features2d.drawMatches(img1, keypoints1, img2, keypoints2, goodMatches, imgMatches);
        HighGui.imshow("Matches", imgMatches);
        HighGui.waitKey();

        // Vérifier si le nombre de correspondances "bonnes" est suffisant
        return goodMatches.size().height > MIN_GOOD_MATCHES;
    }

    private static MatOfDMatch filterGoodMatches(MatOfDMatch matches, double threshold) {
        List<DMatch> matchesList = matches.toList();
        List<DMatch> goodMatchesList = new ArrayList();
        for (DMatch match : matchesList) {
            if (match.distance < threshold) {
                goodMatchesList.add(match);
            }
        }
        MatOfDMatch goodMatches = new MatOfDMatch();
        goodMatches.fromList(goodMatchesList);
        return goodMatches;
    }
}
