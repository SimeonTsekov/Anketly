package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("db")
public class MainController {

    @Autowired
    private MainService service;

    @GetMapping("/services")
    public List<ServiceType> getServiceTypes() throws IOException {
        return service.getAllServiceTypes();
    }

    @PostMapping("/enroll")
    @PreAuthorize("hasAuthority('enroll')")
    @ResponseStatus(HttpStatus.CREATED)
    public Enrollment enroll(@RequestBody String serviceId) {
        return service.enroll(UUID.fromString(serviceId));
    }

    @PutMapping("/desk/{deskNumber}")
    @PreAuthorize("hasAuthority('desk')")
    public Call call(@RequestBody List<String> serviceIds, @PathVariable int deskNumber) {
        return service.call(serviceIds.stream().map(s -> UUID.fromString(s)).collect(Collectors.toList()), deskNumber);
    }

    @GetMapping("/board")
    public List<Info> info(@RequestParam int top) {
        return service.info(top);
    }

}
