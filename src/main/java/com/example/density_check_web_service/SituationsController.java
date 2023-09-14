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

    @ResponseBody
    @GetMapping("/situations/{loc}")
    public List<SituationsResponseDto> findAllByLoc(@PathVariable int loc, Model model)
    {
        return situationsService.findAllByLoc(loc);
    }
}
