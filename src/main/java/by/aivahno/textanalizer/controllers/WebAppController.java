package by.aivahno.textanalizer.controllers;

import by.aivahno.textanalizer.model.Analyze;
import by.aivahno.textanalizer.service.TextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


//Web app controller.
@Controller
public class WebAppController {

    private static Logger logger = LoggerFactory.getLogger(WebAppController.class);
    @Autowired
    private TextService textService;

    //The method intercepts the GET request from the index.html page

    @RequestMapping(value = "/textFile", method = RequestMethod.GET)
    public String index(){
        return "textFile";
    }

    //The method intercepts the POST request and if the file is error-free, creates
    // a new file in the root directory of the project.

    @RequestMapping(value = "/textFile", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){
        File uploadFile = new File("uploaded.txt");
        if(uploadFile.exists())
            uploadFile.delete();
        if(file.isEmpty()){
            redirectAttributes.addFlashAttribute("message", "File is empty. Please select a file to upload / " +
                    "Файл пустой, или не выбран, выберите, пожалуйста, файл");
            return "redirect:textFile";
        }
        if(!file.getOriginalFilename().endsWith(".txt")){
            redirectAttributes.addFlashAttribute("message", "Incorrect file format. Please select a file to upload");
            return "redirect:textFile";
        }

        try (BufferedOutputStream stream =
                     new BufferedOutputStream(new FileOutputStream(uploadFile))){
            logger.info(file.getOriginalFilename());
            byte[] bytes = file.getBytes();
            stream.write(bytes);
            textService.setFile(uploadFile);
            redirectAttributes.addFlashAttribute("message", "You successfully uploaded (Вы загрузили файл): " + file.getOriginalFilename());
        } catch (Exception e) {
            e.getMessage();
        }

        return "redirect:/analyze";
    }


    @GetMapping("/analyze")
    public String uploadStatus(){
        return "analyze";
    }


   //  The method intercepts the request GET /analyzing/topMatchedWords request, and does topMatchedWords text analyze

    @GetMapping("/analyzing/topMatchedWords")
    public String topMatchedWords(RedirectAttributes redirectAttributes){
        Analyze analyze = textService.topMatchedWordsTextAnalyze();
        List<String> words = new ArrayList<>();
        List<Integer> raiting = new ArrayList<>();
        for(Map.Entry <String , Integer > map: analyze.getTopMatchedWords()){
            words.add(map.getKey());
            raiting.add(map.getValue());
        }
        redirectAttributes.addFlashAttribute("raiting", raiting);
        redirectAttributes.addFlashAttribute("words", words);

        return "redirect:/analyze";
    }

        //The method intercepts GET /analyzing/bracketCheck request, and analyzes the correctness of the brackets
            // in the text.

    @GetMapping("/analyzing/bracketCheck")
    public String bracketCheck(RedirectAttributes redirectAttributes){
        Analyze analyze = textService.bracketCheckTextAnalyze();
        redirectAttributes.addFlashAttribute("bracketResult", analyze.getBracketCheck());
        return "redirect:/analyze";
    }

}

