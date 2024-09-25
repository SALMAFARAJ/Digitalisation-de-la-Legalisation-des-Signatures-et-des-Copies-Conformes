package com.example.demo.controller;
import java.util.Locale;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import com.aspose.pdf.Document;
import com.aspose.pdf.FontRepository;
import com.aspose.pdf.FontStyles;
import com.aspose.pdf.HorizontalAlignment;
import com.aspose.pdf.ImageStamp;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextStamp;
import com.aspose.pdf.VerticalAlignment;
import com.example.demo.entity.Citoyen;
import com.example.demo.entity.Demande;
import com.example.demo.repository.citoyenRepository;
import com.example.demo.repository.fonctionaireRepository;
import com.example.demo.service.DemandeService;
import com.example.demo.service.ReclamationService;
import com.example.demo.service.citoyenService;
import com.example.demo.service.documentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

@Controller
public class EgalisationPdf {
	

    @Autowired
    private fonctionaireRepository fonc;

    @Autowired
    private citoyenRepository city;

    @Autowired
    private citoyenService citservice;

    @Autowired
    private DemandeService demService;

    @Autowired
    private ReclamationService reclservice;

    @Autowired
    private documentService doc;

    @PostMapping("/viewPdf1")
    public ResponseEntity<Object> viewPdf(Model m, @RequestParam("documentContenu") String documentName,
            HttpSession s, @RequestParam("id_dem") Long id_dem, @RequestParam("id_cit") Long id_cit) {
    	 Locale.setDefault(new Locale("fr", "FR"));
        Date date = new Date(System.currentTimeMillis());

        Long id1 = (Long) s.getAttribute("id_foc");

        Demande d = demService.getDemandeById(id_dem);
        Citoyen c = citservice.getCitoyenById(id_cit);
        String email = citservice.emailCit(id_dem);

        email e;
        com.example.demo.controller.email.sendMail(email);
        m.addAttribute("demande", d);
        m.addAttribute("citoyen", c);
        demService.update_citoy(id_cit, id_dem);
        doc.update_doc(id1, id_dem);
        demService.insert_dateT(date, id_dem);

        Document pdfDocument = new Document("C:/Users/SALMA/Desktop/" + documentName);

        ImageStamp imageStamp = new ImageStamp("C:/Users/SALMA/Desktop/cachet7.jpg");
        imageStamp.setHorizontalAlignment(com.aspose.pdf.HorizontalAlignment.Right);
        imageStamp.setVerticalAlignment(com.aspose.pdf.VerticalAlignment.Bottom);

        imageStamp.setWidth(100);
        imageStamp.setHeight(100);

        imageStamp.setXIndent(pdfDocument.getPages().get_Item(1).getRect().getWidth() - imageStamp.getWidth());
        imageStamp.setYIndent(30);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date);

        TextStamp textStamp = new TextStamp("Le " + dateString);
        textStamp.getTextState().setFont(FontRepository.findFont("Arial"));
        textStamp.getTextState().setFontSize(12);
        textStamp.getTextState().setFontStyle(FontStyles.Bold);
        textStamp.setVerticalAlignment(VerticalAlignment.Bottom);
        textStamp.setHorizontalAlignment(HorizontalAlignment.Center);

        for (int pageNumber = 1; pageNumber <= pdfDocument.getPages().size(); pageNumber++) {
            pdfDocument.getPages().get_Item(pageNumber).addStamp(textStamp);
        }

        Page firstPage = pdfDocument.getPages().get_Item(1);
        firstPage.addStamp(imageStamp);

        pdfDocument.save("C:/Users/SALMA/Desktop/" + documentName);

        String filePath = "C:/Users/SALMA/Desktop/" + documentName;

        try {
            byte[] fileContent = Files.readAllBytes(Paths.get(filePath));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setCacheControl(CacheControl.noCache().mustRevalidate());
            headers.set("Content-Disposition", "inline; filename=\"" + UriUtils.encode(documentName, StandardCharsets.UTF_8) + "\"; target=_blank");


            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (IOException e1) {
            e1.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
