package com.example.density_check_web_service;

import com.example.density_check_web_service.domain.Situations.dto.SituationsResponseDto;
import com.example.density_check_web_service.service.SituationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.websocket.server.PathParam;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class SituationsController {
    private final SituationsService situationsService;

    @ResponseBody
    @GetMapping("/situations")
    public List<SituationsResponseDto> findAll()
    {
        return situationsService.findAll();
    }

    @GetMapping("/situations/{loc}")
    public String findAllByLoc(@PathVariable int loc, RedirectAttributes redirectAttributes)
    {
        redirectAttributes.addFlashAttribute("graphData", situationsService.findAllByLoc(loc));
        return "redirect:/situation";
    }
}
