package by.aivahno.textanalizer.controllers;

import by.aivahno.textanalizer.model.Analyze;
import by.aivahno.textanalizer.model.Text;
import by.aivahno.textanalizer.service.TextService;
import by.aivahno.textanalizer.utils.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


@RestController
@RequestMapping("/api")
public class RestApiController {
    private static Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    private TextService textService;

    //Uploading file
    @RequestMapping(value = "/textFile", method = RequestMethod.POST)
    public ResponseEntity<?> handleFileUpload(@RequestParam("file")MultipartFile file){
        File upfile = new File("uploaded.txt");             //создается в корне проекта
        if(file.isEmpty()){
            logger.error("File is empty");
            return new ResponseEntity(new CustomErrorType("File is empty"),
                    HttpStatus.CONFLICT);
        }
        try (BufferedOutputStream stream =
                     new BufferedOutputStream(new FileOutputStream(upfile))){
            logger.info(file.getOriginalFilename());
            byte[] bytes = file.getBytes();
            stream.write(bytes);
            textService.setFile(upfile);
        } catch (Exception e) {
            e.getMessage();
        }


        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    //Deleting file
    @RequestMapping(value = "/textFile", method = RequestMethod.DELETE)
        public ResponseEntity<Text> deleteText(){
            File upfile = new File("uploaded.txt");
            if(!upfile.exists()){
            logger.error("The file is not exist.");
            return new ResponseEntity(new CustomErrorType("The file is not exist."),
                    HttpStatus.CONFLICT);
            }
            logger.info("Deleting file");

            textService.deleteFile();
        return new ResponseEntity<Text>(HttpStatus.NO_CONTENT);
    }

    //TopMatchedWords analyze
    @RequestMapping(value = "textFile/analyze/topMatchedWords")
        public ResponseEntity<?> topMatchedWordsText(){
        File upfile = new File("uploaded.txt");
        if(!upfile.exists()){
            logger.error("The file is not exist.");
            return new ResponseEntity(new CustomErrorType(
                    "The file is not exist, please add the file."), HttpStatus.CONFLICT);
        }
        logger.info("Top 10 Text Analyze");
        Analyze analyze = textService.topMatchedWordsTextAnalyze();
        return new ResponseEntity<List>(analyze.getTopMatchedWords(), HttpStatus.OK);
        }

    //BracketCheck analyze
    @RequestMapping(value = "textFile/analyze/bracketCheck")
    public ResponseEntity<?> bracketCheck(){
        File upfile = new File("uploaded.txt");
        if(!upfile.exists()){
            logger.error("The file is not exist.");
            return new ResponseEntity(new CustomErrorType("The file is not exist, please add the file."), HttpStatus.CONFLICT);
        }
        logger.info("Top 10 Text Analyze");
        Analyze analyze = textService.bracketCheckTextAnalyze();
        return new ResponseEntity<String>(analyze.getBracketCheck(), HttpStatus.OK);
    }
    }


