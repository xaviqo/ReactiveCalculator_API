package tech.xavi.webflux.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.xavi.webflux.dto.Response;
import tech.xavi.webflux.service.MathService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/math")
public class MathController {

    private final MathService mathService;

    @GetMapping("/square/{input}")
    public Response findSquare(@PathVariable int input){
        return mathService.findSquare(input);
    }

    @GetMapping("/table/{input}")
    public List<Response> multiplicationTable(@PathVariable int input){
        return mathService.multiplicationTable(input);
    }
}

