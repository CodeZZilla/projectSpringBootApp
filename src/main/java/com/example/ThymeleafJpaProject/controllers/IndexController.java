package com.example.ThymeleafJpaProject.controllers;

import com.example.ThymeleafJpaProject.models.RadioStation;
import com.example.ThymeleafJpaProject.services.RadioStationService;
import org.apache.commons.io.FileUtils;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;


@Controller
public class IndexController {

    private final RadioStationService service;

    public IndexController(RadioStationService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("listStation", service.listAll());
        return "index";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("station", new RadioStation());
        return "createForm";
    }

    @GetMapping("/generate")
    public ResponseEntity<byte[]> getPDF() {
        List<RadioStation> radioStations = service.listAll();

        File exportFile = new File("document.docx");
        byte[] contents;
        try {

            WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
            MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();

            mainDocumentPart.addStyledParagraphOfText("Title", "Document №");
            for (var item : radioStations) {
                mainDocumentPart.addParagraphOfText(item.getRadiostationName() + " - " +
                        item.getCount());
            }

            wordPackage.save(exportFile);

            contents = FileUtils.readFileToByteArray(new File("document.docx"));
            HttpHeaders headers = new HttpHeaders();

            // Here you have to set the actual filename of your pdf
            String filename = "output.docx";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
            return response;
        } catch (Exception e) {
            return new ResponseEntity("Err", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/save")
    public String saveStations(@ModelAttribute("radioStation") RadioStation radioStation,
                               BindingResult errors, HttpServletRequest request) {
        if (errors.hasErrors()) {
            return "redirect:/";
        } else {
            service.save(radioStation);
            return "redirect:/";
        }
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("editForm");
        RadioStation radioStation = service.get(id);
        mav.addObject("editStation", radioStation);
        return mav;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") int id) {
        service.delete(id);
        return "redirect:/";
    }
}
