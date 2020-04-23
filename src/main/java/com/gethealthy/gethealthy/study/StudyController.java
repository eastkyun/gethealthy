package com.gethealthy.gethealthy.study;

import com.gethealthy.gethealthy.account.CurrentUser;
import com.gethealthy.gethealthy.domain.Account;
import com.gethealthy.gethealthy.domain.Study;
import com.gethealthy.gethealthy.study.form.StudyForm;
import com.gethealthy.gethealthy.study.validator.StudyFormValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class StudyController {

    @Autowired
    private StudyService studyService;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudyFormValidator studyFormValidator;

    @InitBinder("studyForm")
    public void studyFormInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(studyFormValidator);
    }

    @GetMapping("/new-study")
    public String newStudyForm(@CurrentUser Account account, Model model){
        model.addAttribute(account);
        model.addAttribute(new StudyForm());
        return "study/form";
    }

    @PostMapping("/new-study")
    public String newStudySubmin(@CurrentUser Account account, @Valid StudyForm studyForm, Errors errors){
        if(errors.hasErrors()){
            return "study/form ";
        }
        Study newStudy = studyService.createNewStudy(modelMapper.map(studyForm,Study.class),account);
        return "redirect:/study/"+ URLEncoder.encode(newStudy.getPath(), StandardCharsets.UTF_8);
    }

    @GetMapping("/study/{path}")
    public String viewStudy(@CurrentUser Account account, @PathVariable String path, Model model){
        model.addAttribute(account);
        model.addAttribute(studyRepository.findByPath(path));
        return "study/view";

    }
    @GetMapping("/study/{path}/members")
    public String viewStudyMembers(@CurrentUser Account account, @PathVariable String path, Model model) {
        model.addAttribute(account);
        model.addAttribute(studyRepository.findByPath(path));
        return "study/members";
    }
}
