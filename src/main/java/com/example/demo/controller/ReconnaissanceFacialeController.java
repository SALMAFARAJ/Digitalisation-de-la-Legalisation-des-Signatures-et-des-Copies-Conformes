package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.*;

import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.MatOfFloat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.core.CvException;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.example.demo.entity.Citoyen;
import com.example.demo.repository. *;
@Controller
public class ReconnaissanceFacialeController {
    @Autowired
    private citoyenRepository citoyenRepository;
    
    @GetMapping("/reconnaissance")
    public String hi() {
    	return "reconnaissance";
    }
    
    @PostMapping("/capturer")
    public ResponseEntity<String> capturerPhoto(@RequestParam Long idCitoyen, @RequestParam MultipartFile photo) {
        String cheminPhoto = "C:/Users/SALMA/Desktop/" + idCitoyen + ".jpg";

        try {
            photo.transferTo(new File(cheminPhoto));
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erreur lors du téléchargement de la photo", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Photo capturée avec succès", HttpStatus.OK);
    }



    private boolean comparerImages(String cheminPhotoCapturee, String cheminPhotoStockee) {
        // Charger les images en tant que matrices OpenCV
        Mat imageCapturee = Imgcodecs.imread(cheminPhotoCapturee);
        Mat imageStockee = Imgcodecs.imread(cheminPhotoStockee);

        // Redimensionner les images pour une comparaison plus rapide (à adapter selon vos besoins)
        Imgproc.resize(imageCapturee, imageCapturee, new Size(300, 400));
        Imgproc.resize(imageStockee, imageStockee, new Size(300, 400));

        // Convertir les images en niveaux de gris
        Imgproc.cvtColor(imageCapturee, imageCapturee, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(imageStockee, imageStockee, Imgproc.COLOR_BGR2GRAY);

        // Appliquer la différence absolue entre les deux images
        Mat diff = new Mat();
        Core.absdiff(imageCapturee, imageStockee, diff);

        // Calculer la somme des différences
        Scalar diffSum = Core.sumElems(diff);

        // Définir un seuil de différence (à ajuster selon vos besoins)
        double seuil = 10000;

        // Comparer la somme des différences avec le seuil
        if (diffSum.val[0] < seuil) {
            return true; // Reconnaissance faciale réussie
        } else {
            return false; // Reconnaissance faciale échouée
        }
    }
}
